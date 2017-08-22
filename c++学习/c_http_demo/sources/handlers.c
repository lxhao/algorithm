/*
handlers.c
This file is part of pserv
http://pserv.sourceforge.net

Copyright (c) 2001-2002-2003-2004 Riccardo Mottola. All rights reserved.
mail: rmottola@users.sourceforge.net

This file is free software, released under GPL. Please read acclosed license
*/

#include <errno.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <assert.h>

#include "handlers.h"
#include "log.h"
#include "main.h"
#include "mime.h"

#ifdef AUTO_INDEX
#include <dirent.h>
#include <time.h>
#endif

#define ENABLE_CGI 1

// 读取来自 main.c 中的全局变量
extern char cgiRoot[MAX_PATH_LEN+1];         /* root for CGI scripts exec */
extern int  port;                            /* server port */
extern char defaultFileName[MAX_PATH_LEN+1]; /* default name for index, default or similar file */

/*------------------------------------------------------------*/
// 发送缓存数据到指定套接字
//   sock : 套接字   buff : 需要发送的数据  len : 数据长度
//   返回：发送数据长度 len （报错则返回 -1）
/*------------------------------------------------------------*/
int sendChunk(int sock, char* buff, int len)
{
    register int sockSent;
    int          totalSent;

    {
    printf("DEBUG: Send Buff Contentes Begin\n");
		int i = 0;
		for(i=0;i<len;i++)
			printf("%c",buff[i]);
		printf("\nDEBUG: Send Buff Contentes End\n");
    }

    /*if ((sockSent = send(sock, buff, len, 0)) < len )
    {
        if (sockSent < 0)
        {
            if (errno == EAGAIN)
            {
		            sockSent = 0;
                DBGPRINTF(("output resource temporarily not available (1st cycle)\n"));
            } else if (errno == EPIPE)
            {
                printf("broken pipe during raw out\n");
	              return -1;
            } else if (errno == EBADF)
            {
                DBGPRINTF(("invalid out descriptor\n"));
	              return -1;
            }
            else
                DBGPRINTF(("error during raw sock writing! %d\n", errno));
	      }
	      totalSent += sockSent;*/
    totalSent = 0;
    // 循环直至全部字符发送完毕
    while (totalSent < len)
    {
        if ((sockSent = send(sock, buff+totalSent, len-totalSent, 0)) < len - totalSent)
		        if (sockSent < 0)
              // 出错处理
                switch (errno) {
                  case EAGAIN:
                    sockSent = 0;
                    DBGPRINTF(("output resource temporarily not available\n"));
                    break;
                  case EPIPE:
                    DBGPRINTF(("broken pipe during raw out\n"));
                    return -1;
                  case EBADF:
                    DBGPRINTF(("invalid out descriptor\n"));
                    return -1;
                  default:
                    DBGPRINTF(("error during raw sock writing! %d\n", errno));
                    return -1;
                }
                  /*
                    if (errno == EAGAIN)
	                  {
			                  sockSent = 0;
                        DBGPRINTF(("output resource temporarily not available\n"));
                    } else if (errno == EPIPE)
                    {
                        DBGPRINTF(("broken pipe during raw out\n"));
	                      return -1;
                    } else if (errno == EBADF)
                    {
                        DBGPRINTF(("invalid out descriptor\n"));
	                      return -1;
                    } else
                    {
                        DBGPRINTF(("error during raw sock writing! %d\n", errno));
			                  return -1;
		                }
                    */
	      totalSent += sockSent;
    } /* while */
    //}
    return len;
}

/*------------------------------------------------------------*/
// 由传入请求处理对应CGI的信息
//   sock : 套接字   req : 存储请求的内容  postStr : 需要传输的字符串
/*------------------------------------------------------------*/
#ifdef ENABLE_CGI
int cgiHandler(int port, int sock, struct request req, char* postStr)
{
    char    *envPath;
    char    *relativePath;
    char    completedPath[2*MAX_PATH_LEN+1]; /* documentAddress + cgiRoot */
    char    scriptWorkingDir[2*MAX_PATH_LEN+1];
    char    **newArgv;
    char    **newEnvp;
    int     i;
    int     outStdPipe[2]; /* we will redirect the script output to a pipe so we can read it */
    int     inStdPipe[2];  /* we will redirect the script input to a pipe so we can read it */
    int     pid;           /* we fork and execute inside the child the script */
    char    pipeReadBuf[PIPE_READ_BUF+1];
    int     howMany;
    int     totalSentFromPipe; /* amount of bytes sucked from the pipe and pushed in to the socket */
    int     fatal;

    /* constructing script paths */
    /* we assume the CGI_MATCH_STRING is stricly at the beginning */
    // documentAddress = "/cgi-bin/xxxx.xxx"
    // relativePath = "/xxxx.xxx"
    relativePath = &(req.documentAddress[strlen(CGI_MATCH_STRING) - 1]);
    // cgiRoot = “xxx/xxx/cgi-bin”
    strcpy(completedPath, cgiRoot);
    strcat(completedPath, relativePath);

    /* reconstruct where the script will be running */
    // completedPath = "xxx/xxx/cgi-bin/xxxx.xxx"
    strcpy(scriptWorkingDir, completedPath);

    // 去除完整地址最后的文件名 获得cgi脚本的文件存储路径
    i = strlen(scriptWorkingDir);
    howMany = strlen(cgiRoot);
    while (i > howMany && scriptWorkingDir[i] != '/')
        i--;
    scriptWorkingDir[i] = '\0';
    // scriptWorkingDir = "xxx/xxx/cgi-bin"
    DBGPRINTF(("script working dir: %s \n", scriptWorkingDir));

    /* we check if we are trying to execute a directory... or a non existent or not accessible file */
    {
        struct stat fileStats;

        if (completedPath[strlen(completedPath)-1] == '/')
        {
          // 如果不是文件名结尾 而是目录结尾 则报错
            sayError(sock, FORBIDDEN, req.documentAddress, req);
            return -1;
        }

        if (stat(completedPath, &fileStats) < 0)
        { /* the file doesn't exist ot some other error, we don't worry more about it */
            sayError(sock, NOT_FOUND, req.documentAddress, req);
            return -1;
        } else if ((fileStats.st_mode & S_IFDIR) == S_IFDIR)
        {
            sayError(sock, FORBIDDEN, req.documentAddress, req);
            return -1;
        }
    }

    /* first we create the pipes needed for stdout redirection */
    if (pipe(outStdPipe))
    {
        DBGPRINTF(("Pipe creation error\n"));
        return -1;
    }
    if (pipe(inStdPipe))
    {
        DBGPRINTF(("Pipe creation error\n"));
        return -1;
    }

    /* now we fork to subsequently execve */
    pid = fork();
    if (pid)
    { /* this is the parent process */
        if (pid < 0)
        { /* we check for creation error */
            printf ("Forking error during cgi exec: %d\n", errno);
            return -1;
        }
        /* we close the unused end of the pipe */
        close(outStdPipe[WRITE]);
        close(inStdPipe[READ]);

        /* check if method is POST */
        if (req.method[0]=='P' && req.method[1]=='O' && req.method[2]=='S' && req.method[3]=='T' && req.method[4]=='\0')
        {
            /* we have to feed the stdin of the script */
            if(!strlen(postStr))
            {
                DBGPRINTF(("cannot post empty data\n"));
                return -1;
            }
            howMany = write(inStdPipe[WRITE], postStr, strlen(postStr));
            if (howMany < 0)
                DBGPRINTF(("Error during script pipe read (POST).\n"));
        }

        // 从管道读入数据 并全部发送回客户端
        totalSentFromPipe = 0;
        fatal = NO;
        howMany = 1;
        while (howMany > 0 && !fatal)
        {
            howMany = read(outStdPipe[READ], pipeReadBuf, PIPE_READ_BUF);
	          if (howMany > 0)
	          {
            	if (sendChunk(sock, pipeReadBuf, howMany) < 0)
                  fatal = YES;
            	else
                  totalSentFromPipe += howMany;
	          } else
	    	          fatal = YES; /* it may be EOF too */
        }

        /* now we finished and we clean up */
        wait(&i);
        if (i) /* check if execution exited cleanly or with code */
            logWriter(LOG_CGI_FAILURE, NULL, 0, req, i);
        else
            logWriter(LOG_CGI_SUCCESS, NULL, totalSentFromPipe, req, 0);
        close(outStdPipe[READ]);
        close(inStdPipe[WRITE]);
    } else
    { /* this is the child process */
        /* now we do some environment setup work */
        newArgv = (char **)calloc(MAX_ARGV_LEN + 1, sizeof(char*));
        for (i = 0; i < MAX_ENVP_LEN + 1; i++)
            newArgv[i] = calloc(MAX_PATH_LEN, sizeof(char));
        newEnvp = (char **)calloc(MAX_ENVP_LEN + 1, sizeof(char*));

        i = 0;
        // relativePath = "/xxxx.xxx"
        // newArgv[0] = "xxxx.xxx"
        strcpy(newArgv[i++], &relativePath[1]);  /* here we should pass the progname */

        // 将queryString（请求的数据，与url以‘?’分隔）按照+号（原为空格）拆分成多行参数
        if (strlen(req.queryString))
        {
            int toParse;
            int j, k;

            toParse = YES;
            j = strlen(req.queryString);
            // 不处理跟随url传入的参数组
            while (toParse && j > 0)
            {
                if (req.queryString[j] == '=')
                    toParse = NO;
                j--;
            }
            if (toParse)
            {
                j = 0;
                k = 0;
                howMany = strlen(req.queryString);
                while (j < howMany)
                {
                    if (req.queryString[j] == '+')
                    {
                        newArgv[i++][k] = '\0';
                        k = 0;
                    } else
                        newArgv[i][k++] = req.queryString[j];
                    j++;
                }
                i++; /* after all we will have at least one argument! */
            }
        }
        // 以新的空行结束所有读出的请求数据
        newArgv[i] = NULL; /* we correctly terminate argv */

        i = 0;
	      /* beware of not overfilling this array, check MAX_ENVP_LEN */
        newEnvp[i] = (char *) calloc(128, sizeof(char));
        strcpy(newEnvp[i], "SERVER_SOFTWARE=");
        strcat(newEnvp[i], SERVER_SOFTWARE_STR);
        strcat(newEnvp[i], "/");
        strcat(newEnvp[i++], SERVER_VERSION_STR);
        newEnvp[i] = (char *) calloc(METHOD_LEN+16, sizeof(char));
        strcpy(newEnvp[i], "REQUEST_METHOD=");
        strcat(newEnvp[i++], req.method);
        newEnvp[i] = (char *) calloc(MAX_PATH_LEN+16, sizeof(char));
        strcpy(newEnvp[i], "SCRIPT_NAME=");
        strcat(newEnvp[i++], req.documentAddress);
        newEnvp[i] = (char *) calloc(32, sizeof(char));
        strcpy(newEnvp[i], "GATEWAY_INTERFACE=");
        strcat(newEnvp[i++], CGI_VERSION);
        newEnvp[i] = (char *) calloc(18, sizeof(char));
        sprintf(newEnvp[i++], "SERVER_PORT=%d", port);
        newEnvp[i] = (char *) calloc(MAX_QUERY_STRING_LEN+16, sizeof(char));
        strcpy(newEnvp[i], "QUERY_STRING=");
        strcat(newEnvp[i++], req.queryString);
        newEnvp[i] = (char *) calloc(PROTOCOL_LEN+17, sizeof(char));
	      strcpy(newEnvp[i], "SERVER_PROTOCOL=");
  	    strcat(newEnvp[i++], req.protocolVersion);
        newEnvp[i] = (char *) calloc(ADDRESS_LEN+13, sizeof(char));
	      strcpy(newEnvp[i], "REMOTE_ADDR=");
	      strcat(newEnvp[i++], req.address);
        newEnvp[i] = (char *) calloc(USER_AGENT_LEN+17, sizeof(char));
	      strcpy(newEnvp[i], "HTTP_USER_AGENT=");
	      strcat(newEnvp[i++], req.userAgent);
        newEnvp[i] = (char *) calloc(MAX_PATH_LEN+17, sizeof(char));
	      completedPath[MAX_PATH_LEN]='\0';
	      strcpy(newEnvp[i], "SCRIPT_FILENAME=");
	      strcat(newEnvp[i++], completedPath);

        /* extracting PATH env variable */
        envPath = getenv("PATH");
	      /* we get the path from the env itself so we assume it safe */
        newEnvp[i] = (char *) calloc(MAX_PATH_LEN+16, sizeof(char));
        strcpy(newEnvp[i], "PATH=");
        strcat(newEnvp[i++], envPath);

	      /* terminate the array */
        // 以空行结束整个参数数组
	      newEnvp[i] = NULL;

        /* we change the current working directory to the scripts one */
        if(chdir(scriptWorkingDir))
            DBGPRINTF(("error while changing PWD in script execution: %d\n", errno));

        close(outStdPipe[READ]);    /* we close the unused end*/
        dup2(outStdPipe[WRITE], 1); /* we duplicate the pipe to the stdout */
        close(outStdPipe[WRITE]);   /* we close the pipe, since we use the duplicate */

        close(inStdPipe[WRITE]);    /* we close the unused end*/
        dup2(inStdPipe[READ], 0);   /* we duplicate the pipe to the stdin */
        close(inStdPipe[READ]);     /* we close the pipe, since we use the duplicate */

        /* generate a reduced mimeHeader, no type, no size, etc */
        generateMimeHeader(sock, 200, "", NULL, req.protocolVersion, CGI_ONLY_HEADER);

        /* now we execute the script replacing the current child */
        execve(completedPath, newArgv, newEnvp);
	      /* we reach this line only if an execution error occoured */
	      /* logging will happen in the father */
	      printf("\n<HTML><HEAD><TITLE>CGI Error</TITLE></HEAD><BODY><H1>CGI Exec error</H1></BODY></HTML>\n");
        exit(-1);
    }
    return 0;
}
#endif /* ENABLE_CGI */

/*------------------------------------------------------------*/
// 存储头部所指向的文件的信息（找不到文件时直接返回客户端）
//   sock : 客户端套接字   filePath : 文件所在目录（以它本身结尾，包括扩展名）
//   mimeType : CGI数据的类型  req : 存储客户端请求的结构体
/*------------------------------------------------------------*/
/* generate a full header for a given file */
int dumpHeader(int sock, char filePath[], char mimeType[], struct request req)
{
    FILE    	*inFile;
    struct stat fileStats;
    int     	sentBytes;

    /* we reconstruct a partial filepath so to include the index file instead of / */
    inFile = fopen(filePath, "r");
    if (inFile == NULL) {
        sayError(sock, NOT_FOUND, req.documentAddress, req);
        return -1;
    }
    stat(filePath, &fileStats);
    sentBytes = generateMimeHeader(sock, 200, mimeType, &fileStats, req.protocolVersion, FULL_HEADER);
    logWriter(LOG_HEAD_SUCCESS, req.documentAddress, (long int)sentBytes, req, 0);

    fclose(inFile);
    return 0;
}

/*------------------------------------------------------------*/
// 从文件中读取内容并发送到客户端
//   sock : 客户端套接字   filePath : 文件所在目录（以它本身结尾，包括扩展名）
//   mimeType : CGI数据的类型  req : 存储客户端请求的结构体
/*------------------------------------------------------------*/
int dumpFile(int sock, char filePath[], char mimeType[], struct request req)
{
    FILE    	*inFile;
    struct stat fileStats;
    char    	outBuff[OUT_SOCK_BUFF_SIZE+1];
    int     	howMany;
    int     	fatal;

    // 处理文件信息
    inFile = fopen(filePath, "r");
    if (inFile == NULL) {
    	  DBGPRINTF(("File not found.\n"));
        sayError(sock, NOT_FOUND, req.documentAddress, req);
        return -1;
    }
    stat(filePath, &fileStats);
    generateMimeHeader(sock, 200, mimeType, &fileStats, req.protocolVersion, FULL_HEADER);
    logWriter(LOG_GET_SUCCESS, req.documentAddress, (long int)fileStats.st_size, req, 0);

    howMany = 0;
    if (strncmp(mimeType, "text", 4)) /* check if it is a text type */
    {   /* raw binary output routine */
        fatal = NO;
        while(!feof(inFile) && !fatal)
        {
            howMany = fread (outBuff, sizeof(char), OUT_SOCK_BUFF_SIZE, inFile);
            if (howMany > 0)
		            if (sendChunk(sock, outBuff, howMany) < 0)
                    fatal = YES;
        }
    } else
    {   /* TEXT output routine */
        fatal = NO;
        while(!feof(inFile) && !fatal)
        {
            howMany = fread (outBuff, sizeof(char), OUT_SOCK_BUFF_SIZE, inFile);
            if (howMany > 0)
            {
#ifdef ON_THE_FLY_CONVERSION
		 { // 回车转义符全部替换成换行
		     int i;
		     for (i = 0; i < howMany; i++)
		         if(outBuff[i] == '\r') outBuff[i] = '\n';
		 }
#endif
                if (sendChunk(sock, outBuff, howMany) < 0)
                    fatal = YES;
            }
        }
    }
    fclose(inFile);
    return 0;
}

/*------------------------------------------------------------*/
// 自动管理index.html主页
// 如果不存在，则自动生存一个并返回给客户端
//   sock : 客户端套接字   dirPath : 文件所在目录（不包括它自身名称）
//   req : 存储客户端请求的结构体
//  返回：如果文件存在 返回 -1；不存在则自动生成一个并返回 0  
/*------------------------------------------------------------*/
#ifdef AUTO_INDEX
int generateIndex(int sock, char dirPath[], char mimeType[], struct request req)
{
    struct stat fileStats;
    struct dirent *dp;
    DIR    *dfd;
    char   indexFilePath[MAX_PATH_LEN+1];
    FILE   *tempFile;
    size_t generatedBytes;
    char   tempStr[MAX_PATH_LEN+1];
    char   linkStr[MAX_PATH_LEN+1];
    char   linkPath[MAX_PATH_LEN+1];
    time_t currTime;
    char   timeStr[256];

    currTime = time(NULL);
    strftime(timeStr, 256, "%a, %d %b %Y %H:%M:%S %Z", (struct tm *) localtime(&currTime));

    /* first we check if an index does already exist, in case we exit with 1 */
    strcpy(indexFilePath, dirPath);
    strcat(indexFilePath, defaultFileName);
    if (stat(indexFilePath, &fileStats))
    {
        if (errno == EACCES)
            return -1; /* the index file exists but we have no access */
        // 文件不存在
    } else
        return -1; /* the index file exists */

    /* the directory is without the trailing slash */
    /* I don't feel like allocating another string */
    dirPath[strlen(dirPath)-1]='\0';
    if ((dfd = opendir(dirPath)) == NULL)
    {
        DBGPRINTF(("Can't open dir: %s\n", dirPath));
        return 1;
    }
    dirPath[strlen(dirPath)]='/'; /* we put the slash back in */

    /* now we get a tempfile where to store the created index, so we can at the end know its length */
    tempFile = tmpfile();
    generatedBytes = 0;
    strcpy(tempStr, "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">\n<HTML><HEAD><TITLE>");
    strcat(tempStr, req.documentAddress);
    strcat(tempStr, "</TITLE></HEAD>\n");
    generatedBytes += strlen(tempStr);
    fprintf(tempFile, "%s", tempStr);

    sprintf(tempStr, "<BODY><H1>Index of: %s </H1> ", req.documentAddress);
    strcat(tempStr, "\n");
    generatedBytes += strlen(tempStr);
    fprintf(tempFile, "%s", tempStr);

    sprintf(tempStr, "<HR><P><I>Date: %s</I></P><HR>", timeStr);
    strcat(tempStr, "<BLOCKQUOTE>\n");
    generatedBytes += strlen(tempStr);
    fprintf(tempFile, "%s", tempStr);

    /* now we read the directory entries */
    while ((dp = readdir(dfd)) != NULL)
    {
        char dirToStat[MAX_PATH_LEN+1]; /* temporary directory to stat on */
        if (strcmp(dp->d_name, "."))
        {   /* not self */
            // 不相等的情况
            if (strcmp(dp->d_name, ".."))
                strcpy(linkStr, dp->d_name);
            else
                strcpy(linkStr, "Parent Directory");
            strcpy(linkPath, dp->d_name);

            /* now we check if the entry is a dir */
            strcpy(dirToStat, dirPath);
            strcat(dirToStat, dp->d_name);
            stat(dirToStat, &fileStats);
            if ((fileStats.st_mode & S_IFMT) == S_IFDIR)
            {
                /* so it is a directory */
                sprintf(tempStr, "<A HREF=\"%s/\">%s/</A><BR>\n", dp->d_name, linkStr);
            } else
            {
		            char fileSize[32];
                off_t byteSize;

                byteSize = fileStats.st_size;
                if (byteSize < 1024)
                    sprintf(fileSize, "%d bytes", (int) byteSize);
                else if (byteSize < 1024*1024)
                    sprintf(fileSize, "%1.2f Kbytes", (float) byteSize / 1024);
                else
                    sprintf(fileSize, "%1.2f Mbytes", (float) byteSize / (1024*1024));

                sprintf(tempStr, "<A HREF=\"%s\">%s</A> (%s)<BR>\n", dp->d_name, linkStr, fileSize);
            }

            generatedBytes += strlen(tempStr);
            fprintf(tempFile, "%s", tempStr);
        }
    }
    strcpy(tempStr, "</BLOCKQUOTE>\n");
    generatedBytes += strlen(tempStr);
    fprintf(tempFile, "%s", tempStr);
    sprintf(tempStr, "<HR><P ALIGN=\"RIGHT\">pico Server %s</P></HTML>\n", SERVER_VERSION_STR);
    generatedBytes += strlen(tempStr);
    fprintf(tempFile, "%s", tempStr);

    /* we rewind the temporary file */
    fseek(tempFile, (long) 0, SEEK_SET);
    clearerr(tempFile);

    /* now let's fake some statistics */
    fileStats.st_size = generatedBytes;
    fileStats.st_mtime = currTime;
    generateMimeHeader(sock, 200, "text/html", &fileStats, req.protocolVersion, FULL_HEADER);
    logWriter(LOG_GET_SUCCESS, req.documentAddress, (long int)generatedBytes, req, 0);

    /* now we output it */
    {
        int  howMany;
        int  fatal;
        char outBuff[OUT_SOCK_BUFF_SIZE+1];

        howMany = 0;
        fatal = NO;
        while(!feof(tempFile) && !fatal)
        {
            howMany = fread (outBuff, sizeof(char), OUT_SOCK_BUFF_SIZE, tempFile);
            if (howMany > 0)
                if (sendChunk(sock, outBuff, howMany) < 0)
                    fatal = YES;
        }
    }

    fclose(tempFile);
    return 0;
}
#endif
