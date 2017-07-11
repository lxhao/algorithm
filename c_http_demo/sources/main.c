/*
main.c
This file is part of pserv
http://pserv.sourceforge.net

Copyright (c) 2001-2005 Riccardo Mottola. All rights reserved.
mail: rmottola@users.sourceforge.net

This file is free software, distributed under GPL. Please read acclosed license
*/

#include "main.h"
#include "log.h"
#include "mime.h"
#include "handlers.h"

#ifndef HAVE_MEMMOVE
#include "missing.c"
#endif

/* ------ Global Variables ---- */
/*---------全局变量------------*/

int 	    	theSocket, newSocket;
int 	    	error;
int 	    	newSocketReady;
char	    	homePath[MAX_PATH_LEN+1];
char	    	defaultFileName[MAX_PATH_LEN+1];
char	    	logFileName[MAX_PATH_LEN+1];
char	    	mimeTypesFileName[MAX_PATH_LEN+1];
char	    	cgiRoot[MAX_PATH_LEN+1]; /* root for CGI scripts exec */
struct timeval	sockTimeVal;
mimeData    	*mimeArray;  /* here we will hold all MIME data, inited once, never to be changed  */
							 /*存储所有MIME数据，一次性初始化并且不再改变*/
int 	    	mimeEntries; /* the number of known mimetypes present in *mimeArray and loaded at startup */
							 /*存储在*mimeArray中已知类型的数量并在启动时载入*/
FILE	    	*lf;         /* log file */
							 /*日志文件*/
FILE            *sockStream; /* stream version of the new socket */
							 /*新嵌套字流类型*/
#define required_argument 1
/*#define no_argument (0)*/



/* ------ Signal Handlers  -------
   -------信号处理--------------*/

RETSIGTYPE intCatch ()
{
    signal_MACRO(SIGINT, intCatch);
    printf("shutting down the server....\n");
    if (newSocketReady)
    {
        if (close(newSocket))
            printf("error closing new socket\n");
    }
    if (close(theSocket))
        printf("error closing mother socket\n");
    logFileClose();
    exit(0);
}

RETSIGTYPE brokenPipe()
{
    signal_MACRO(SIGPIPE, brokenPipe);
    printf("BrokenPipe!\n");
    if (newSocketReady)
    {
        printf ("new socket was ready....\n");
        if (!close(newSocket))
            printf("closed ns\n");
    } else {
        printf("This makes no sense!!! broken pipe on a closed socket!\n");
        exit(0);
    }
    newSocketReady = NO;
}


/* ------- General Purpose Functions -----
   ------------通用功能------------*/
int sayError(int sock, int err, char* errorFilePath, struct request req)
/* output HTML code for given error code */
/*以HTML代码输出设定好的错误代码*/

{
    char outBuff[512+MAX_PATH_LEN]; /* should be enough since we hard-code output and we must contain the path! */

    switch (err)
    {
    case INPUT_LINE_TOO_LONG:
        generateMimeHeader(sock, 400, "text/html", NULL, req.protocolVersion, FULL_HEADER);
        strcpy(outBuff, "<HTML>\n<HEAD>\n<TITLE>Error</TITLE>\n</HEAD>\n<BODY>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "<H1>Error: Browser request line too long.\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "</BODY>\n</HTML>\n");
        write (sock, outBuff, strlen(outBuff));
        logWriter(LOG_INPUT_LINE_TOO_LONG, NULL, 0, NULL, 0);
        break;
    case POST_BUFFER_OVERFLOW:
        generateMimeHeader(sock, 500, "text/html", NULL, req.protocolVersion, FULL_HEADER);
        strcpy(outBuff, "<HTML>\n<HEAD>\n<TITLE>Error</TITLE>\n</HEAD>\n<BODY>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "<H1>Error: Internal Server Error (Content-length in post data too long)\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "</BODY>\n</HTML>\n");
        write (sock, outBuff, strlen(outBuff));
        logWriter(LOG_POST_BUFFER_OVERFLOW, NULL, 0, NULL, 0);
        break;
    case FORBIDDEN:
        generateMimeHeader(sock, 403, "text/html", NULL, req.protocolVersion, FULL_HEADER);
        strcpy(outBuff, "<HTML>\n<HEAD>\n<TITLE>Error</TITLE>\n</HEAD>\n<BODY>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "<H1>Error 403: Forbidden.</H1>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "<HR><P>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, errorFilePath);
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "</P>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "</BODY>\n</HTML>\n");
        write (sock, outBuff, strlen(outBuff));
        logWriter(LOG_FORBIDDEN, errorFilePath, 0, req, 0);
        break;
    case NOT_FOUND:
        generateMimeHeader(sock, 404, "text/html", NULL, req.protocolVersion, FULL_HEADER);
        strcpy(outBuff, "<HTML>\n<HEAD>\n<TITLE>Error</TITLE>\n</HEAD>\n<BODY>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "<H1>Error 404: File not found.</H1>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "<HR><P>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, errorFilePath);
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "</P>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "</BODY>\n</HTML>\n");
        write (sock, outBuff, strlen(outBuff));
        logWriter(LOG_FILE_NOT_FOUND, errorFilePath, 0, req, 0);
        break;
    case LENGTH_REQUIRED:
        generateMimeHeader(sock, 411, "text/html", NULL, req.protocolVersion, FULL_HEADER);
        strcpy(outBuff, "<HTML>\n<HEAD>\n<TITLE>Error</TITLE>\n</HEAD>\n<BODY>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "<H1>Error 411: Length Required</H1>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "<HR><P>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, errorFilePath);
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "</P>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "</BODY>\n</HTML>\n");
        write (sock, outBuff, strlen(outBuff));
        logWriter(LOG_LENGTH_REQUIRED, 0, req, 0);
        break;
    case UNHANDLED_METHOD:
        DBGPRINTF(("unhandled method case\n"));
        generateMimeHeader(sock, 501, "text/html", NULL, req.protocolVersion, FULL_HEADER);
        strcpy(outBuff, "<HTML>\n<HEAD>\n<TITLE>Error</TITLE>\n</HEAD>\n<BODY>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "<H1>Error: Not Implemented: Unhandled Method.\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "</BODY>\n</HTML>\n");
        write (sock, outBuff, strlen(outBuff));
        break;
    default:
        DBGPRINTF(("generic SayError case\n"));
        generateMimeHeader(sock, 500, "text/html", NULL, req.protocolVersion, FULL_HEADER);
        strcpy(outBuff, "<HTML>\n<HEAD>\n<TITLE>Error</TITLE>\n</HEAD>\n<BODY>\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "<H1>Error: Unknown error trapped.\n");
        write (sock, outBuff, strlen(outBuff));
        strcpy(outBuff, "</BODY>\n</HTML>\n");
        write (sock, outBuff, strlen(outBuff));
        logWriter(LOG_GENERIC_ERROR, NULL, 0, NULL, 0);
        break;
    } /* of switch */
    return 0;
}


/* substitute escaped % characters with their equivalent */
/*等价地替换掉%字符*/
int convertPercents(char s[], int l)

{
    char tok[4];
    char tokSubst;
    size_t tokLen;
    char *tokPtr;
    size_t tokPos;

    /* we match and subsitute the %20 with a blank space */
	/*匹配%20，并用空格代替*/
    strcpy(tok, "%20");
    tokLen = strlen(tok);
    tokSubst = ' ';
    tokPtr = strstr(s, tok);
    while (tokPtr)
    {
        tokPos = tokPtr - s;  /* calculate the position */
							                /*计算%20出现的位置*/
	      s[tokPos] = tokSubst;
	      /* now we shift all characters left including the terminator */
	      /* assumes tokSubst len of 1 (= one char) */
	      memmove(tokPtr + 1, tokPtr + tokLen, l - tokPos - tokLen + 1);
        tokLen = strlen(tok);
        tokPtr = strstr(s, tok);
    }
    return 0;
}


int analyzeRequest (int sock, char req[], struct request* reqStruct)
/* extracts meaningful tokens from the client request and stores them in the request struct */
/*从客户端请求中提取有效块，保存至request结构*/

{
    char token[BUFFER_SIZE + 1];   /* we add one to be able the trailing new line we append for security */
								   /*额外增加1块以跟踪为安全附加的新行*/
    char reqArray[MAX_REQUEST_LINES][BUFFER_SIZE]; /* no need to add one here since we trim newline */
								   				   /*无需+1*/
    int i, j, k;
    int reqSize;
    int readLines;
    int tokenEnd;

    /* we copy the header lines to an array for easier parsing */
    /* but first we make sure that our string has a newline and an end */
	/*首先确保string有换行和终止符，然后将头行复制到一个数组以便剖析*/
    req[BUFFER_SIZE] = '\0';
    reqSize = strlen(req);
    req[reqSize] = '\n';
    reqSize++;
    req[reqSize] = '\0';
    i = 0; j = 0;
    while (i < MAX_REQUEST_LINES && j < reqSize)
    {
        k = 0;
        while (req[j] != '\n')
            token[k++] = req[j++];
        token[k-1] = '\0';   /* the line read ends with an \n, we skip it and count it as read */
        j++;
        strcpy(reqArray[i], token);
        i++;
    }
    readLines = i - 1;
#ifdef PRINTF_DEBUG
    for (k = 0; k < readLines; k++)
        printf("%d - |%s|\n", k, reqArray[k]);
#endif
    /* first line: method, path and protocol version */
	/*第一行：方法，路径，协议版本*/
    /* we copy to a temporary buffer to be more secure against overflows */
	/*我们复制到一个临时缓冲区以避免溢出*/
    i = j = 0;
    while (reqArray[0][i] != ' ' && reqArray[0][i] != '\0' && j < METHOD_LEN)
        token[j++] = reqArray[0][i++];
    token[j] = '\0';      /* to make sure we have a terminated string */
    strcpy(reqStruct->method, token);  /* copy back */
    if (reqArray[0][i] == '\0')
        tokenEnd = YES;
    else
        tokenEnd = NO;
    i++;
    /* we look for the document address */
	/*寻找文件地址*/
    j = 0;
    reqStruct->documentAddress[0] = '\0';
    if (!tokenEnd)
    {
        while (reqArray[0][i] != ' ' && reqArray[0][i] != '\0' && reqArray[0][i] != '?' && j < MAX_PATH_LEN)
            token[j++] = reqArray[0][i++];
        token[j] = '\0';      /* to make sure we have a terminated string */
        /* now we need to convert some escapings from the path like %20 */
	      convertPercents(token, j);
        strcpy(reqStruct->documentAddress, token);  /* copy back */
        if (reqArray[0][i] == '\0')
            tokenEnd = YES;
        else
            tokenEnd = NO;
        i++;

        /* we need now to separate path from query string ("?" separated) */
		/*将路径从完整URL中分离出来*/
        if (reqArray[0][i-1] == '?')
        {
            k = 0;
            token[0] = '\0';
            while (reqArray[0][i] != ' ' && reqArray[0][i] != '?' && reqArray[0][i] != '\0' && k < MAX_QUERY_STRING_LEN)
                token[k++] = reqArray[0][i++];
            token[k] = '\0';      /* to make sure we have a terminated string */
            strcpy(reqStruct->queryString, token);  /* copy back */
            i++;
        }
    }
    /* we analyze the HTTP protocol version */
	/*分析HTTP协议版本*/
    /* default is 0.9 since that version didn't report itself */
    strcpy(reqStruct->protocolVersion, "HTTP/0.9");
    j = 0;
    if (!tokenEnd)
    {
        while (reqArray[0][i] != ' ' && reqArray[0][i] != '\0' && j < PROTOCOL_LEN)
            token[j++] = reqArray[0][i++];
        token[j] = '\0';      /* to make sure we have a terminated string */
        if (j)
            strcpy(reqStruct->protocolVersion, token);  /* copy back */
    }

    /* Connection type */
    reqStruct->keepAlive = NO;
    if (!strncmp(reqArray[1], "Connection: Keep-Alive", strlen("Connection: Keep-Alive")))
        reqStruct->keepAlive = YES;
    else if (!strncmp(reqArray[1], "Connection: keep-alive", strlen("Connection: keep-alive")))
        reqStruct->keepAlive = YES;
    /* user-agent, content-length and else */
    i = 1;
    j = NO;
    reqStruct->userAgent[0] = '\0';
    while (i < readLines)
    {
        if (!strncmp(reqArray[i], "User-Agent:", strlen("User-Agent:")))
        {
            strncpy(reqStruct->userAgent, &reqArray[i][strlen("User-Agent: ")], USER_AGENT_LEN - 1);
            reqStruct->userAgent[USER_AGENT_LEN] = '\0';
        }
	      else if (!strncmp(reqArray[i], "Content-Length:", strlen("Content-length:")) || !strncmp(reqArray[i], "Content-length:", strlen("Content-length:")))
    	  {
	      strcpy(token, &reqArray[i][strlen("Content-length: ")]);
	      sscanf(token, "%ld", &(reqStruct->contentLength));
#ifdef PRINTF_DEBUG
	      printf("content length %ld\n", reqStruct->contentLength);
#endif
	      }
        i++;
    }
    /* if we didn't find a User-Agent we fill in a (N)ot(R)ecognized */
    if (reqStruct->userAgent[0] == '\0')
        strcpy(reqStruct->userAgent, "NR");

    return 0;
}


int handleMethod (int port, int sock, struct request req)
/* analyze method of Request and take necessary actions */
/*分析请求方法并响应*/
{
    char completeFilePath[MAX_PATH_LEN+MAX_PATH_LEN+MAX_INDEX_NAME_LEN+1]; /* to be sure root+path+indexname stay inside */
    char mimeType[MAX_MIMETYPE_LEN+1];
    struct stat fileStats;

    /* now we check if the given path tries to get out of the root
     * POSIX file name have / as a separator, so // is still a separator */
    {
        register int i,j;
        register int sL;
        char         dirName[MAX_PATH_LEN+1];
        register int depthCount;

        depthCount = 0;
        sL = strlen(req.documentAddress);
        if (sL > 3)
        {
            if (req.documentAddress[1] == '.' && req.documentAddress[2] == '.' && req.documentAddress[3] == '/')
            {
                sayError(sock, FORBIDDEN, req.documentAddress, req);
                return -1;
            }
        }
	      dirName[0] = '\0';
        j = 0;
        for (i = 1; i < sL; i++)
        {
            if (req.documentAddress[i] == '/')
            {
                dirName[j] = '\0';
                if (strcmp(dirName, ".."))
                {
		                /* ignore ./ */
		                if (strcmp(dirName, "."))
                    /* count only the first of multiple / spearators */
                        if (req.documentAddress[i-1] != '/')
                            depthCount++;
                }
                else
                    depthCount--;
                j = 0;
            } else
                dirName[j++] = req.documentAddress[i];
        }
        if (depthCount < 0)
        {
            sayError(sock, FORBIDDEN, req.documentAddress, req);
            return -1;
        }
    }

    if (req.method[0]=='G' && req.method[1]=='E' && req.method[2]=='T' && req.method[3]=='\0')
    {
        /* GET method */
#ifdef PRINTF_DEBUG
        printf ("handling get of %s\n", req.documentAddress);
#endif
#ifdef ENABLE_CGI
        /* first we check if the path contains the directory selected for cgi's and in case handle it */
        if (!strncmp(req.documentAddress, CGI_MATCH_STRING, strlen(CGI_MATCH_STRING)))
        {
            cgiHandler(port, sock, req, NULL);
        } else
#endif /* ENABLE_CGI */
        { /* GET for standard files */
#ifdef ENABLE_CGI
	    /* we check that the path doesn't contain the cgi match string
	     * we don't want to serve scripts as files */
            if (strstr(req.documentAddress, CGI_MATCH_STRING))
            {
                sayError(sock, FORBIDDEN, req.documentAddress, req);
                return -1;
            }
#endif /* ENABLE_CGI */

            strcpy(completeFilePath, homePath);
            strcat(completeFilePath, req.documentAddress);

            /* now we check if the given file is a directory or a plain file */
            stat(completeFilePath, & fileStats);
            if ((fileStats.st_mode & S_IFDIR) == S_IFDIR)
            {
                /* if does not end with a slash, we get an error */
                if(completeFilePath[strlen(completeFilePath)-1] != '/')
                {
                    sayError(sock, NOT_FOUND, req.documentAddress, req);
                    return -1;
                }
#ifdef AUTO_INDEX
                if (generateIndex(sock, completeFilePath, mimeType, req))
                {
                    /* we got an error, generateIndex was not able to handle the request itself
                     * this means that there already exists and index
                     * we append the default file name */
                    strcat(completeFilePath, defaultFileName);
                    analyzeExtension(mimeType, completeFilePath);
                    dumpFile(sock, completeFilePath, mimeType, req);
                }
#else
                /* we append the default file name */
                strcat(completeFilePath, defaultFileName);
                analyzeExtension(mimeType, completeFilePath);
                dumpFile(sock, completeFilePath, mimeType, req);
#endif
            } else
            { /* it is a plain file */
                analyzeExtension(mimeType, completeFilePath);
                dumpFile(sock, completeFilePath, mimeType, req);
            }
        }
    } else if (req.method[0]=='H' && req.method[1]=='E' && req.method[2]=='A' && req.method[3]=='D' && req.method[4]=='\0')
    {
        /* HEAD method */
        DBGPRINTF(("handling head of %s\n", req.documentAddress));
        /* first we check if the path contains the directory selected for cgi's and in case handle it */
#ifdef ENABLE_CGI
        if (!strncmp(req.documentAddress, CGI_MATCH_STRING, strlen(CGI_MATCH_STRING)))
        {
            cgiHandler(port, sock, req, NULL);
        } else
#endif /* ENABLE_CGI */
        {
            strcpy(completeFilePath, homePath);
            strcat(completeFilePath, req.documentAddress);
            /* now we check if the given file is a directory or a plain file */
            stat(completeFilePath, &fileStats);
            if ((fileStats.st_mode & S_IFDIR) == S_IFDIR)
            {
                /* if does not end with a slash, we get an error */
                if(completeFilePath[strlen(completeFilePath)-1] != '/')
                {
                    sayError(sock, NOT_FOUND, req.documentAddress, req);
                    return -1;
                }
                /* we append the default file name */
                strcat(completeFilePath, defaultFileName);
            }
            analyzeExtension(mimeType, completeFilePath);
            dumpHeader(sock, completeFilePath, mimeType, req);
        }
    } else if (req.method[0]=='P' && req.method[1]=='O' && req.method[2]=='S' && req.method[3]=='T' && req.method[4]=='\0')
    {
        /* POST method */
        /* we add 5 characters to be able to hold a \r\n\r\n\0 sequence at the end */
        char buff[POST_BUFFER_SIZE+5];
        int totalRead;
        int stuckCounter; /* if we receive too many errors */
        int readFinished;
	      int ch;

        DBGPRINTF(("Handling of POST method\n"));
        /* first we check if the path contains the directory selected for cgi's and in case handle it */
        if (strncmp(req.documentAddress, CGI_MATCH_STRING, strlen(CGI_MATCH_STRING)))
        {
            /* non cgi POST is not supported */
            sayError(sock, UNHANDLED_METHOD, "", req);
            return -1;
        }
#ifdef ENABLE_CGI
        DBGPRINTF(("begin of post handling\n"));
        buff[0] = '\0';
        readFinished = NO;
        totalRead = 0;
        stuckCounter = 0;
        if (req.contentLength < 0)
        {
            sayError(sock, LENGTH_REQUIRED, "", req);
            return -1;
        } else if (req.contentLength >= POST_BUFFER_SIZE)
        {
            sayError(sock, BUFFER_OVERFLOW, "", req);
            return -1;
        }
        while (!readFinished)
        {
	          ch = (char)fgetc(sockStream);
	          if(ch == EOF)
	          {
	              perror("fgetc");
                if (errno == EAGAIN)
		            {
	                  clearerr(sockStream);
		                printf("resource not available on POST data read\n");
		                stuckCounter++;
                    if (stuckCounter >= MAX_STUCK_COUNTER)
		                {
		                    printf("stuck in post data read\n");
		                    close(newSocket);
		                    newSocketReady = NO;
		                    readFinished = YES;
		                }
		            } else
		            {
		                perror("fgetc in post");
		                printf("unrecoverable error\n");
		                close(newSocket);
		                newSocketReady = NO;
		                readFinished = YES;
		            }
	          } else
	          {
		            stuckCounter = 0;
	              buff[totalRead] = ch; totalRead++;
	    	        if (totalRead == req.contentLength)
		                readFinished = YES;
	          }
        }
	      clearerr(sockStream);
        DBGPRINTF(("total read %d\n", totalRead));
        if (totalRead == 0)
        {
            printf("Request read error\n");
        } else
        {
            if (buff[totalRead - 1] != '\n') /* we need a trailing \n or the script will wait forever */
            {
                buff[totalRead++] = '\n';
                buff[totalRead] = '\0';
            }
            DBGPRINTF(("buff: |%s|\n", buff));
            cgiHandler(port, sock, req, buff);
        }
#endif /* ENABLE_CGI */
#ifndef ENABLE_CGI
	/* we can't handle post with CGI since CGI is disabled... */
        sayError(sock, UNHANDLED_METHOD, "", req);
        return -1;
#endif /* not ENABLE_CGI */
    /* end of POST */
    } else
    {
        sayError(sock, UNHANDLED_METHOD, "", req);
        return -1;
    }
    return 0;
}

int Para_CmdParse(int* serverPort, int* maxChildren, int argc, char* argv[])
{
	//printf("call success\n");
	static char *shortopts = "c:d:f:ho:l:m:t";
	static char *l_opt_arg;
	struct option
	{
		const char *name;
		int has_arg;
		int *flag;
		int val;
	};
	int no_argument = 0;
	int LONG_MAX = 0xffffffff;
	int LONG_MIN = 0;
	static struct option longopts[]  = 
	{
		{"CGIRoot",			required_argument, NULL, 'c'},
		{"ConfigFile",		required_argument, NULL, 'f'},
		{"DefaultFile", 	required_argument, NULL, 'd'},
		{"DocumentRoot",	required_argument, NULL, 'o'},
		{"ListenPort",		required_argument, NULL, 'l'},
		{"MaxClient",		required_argument, NULL, 'm'},
		{"TimeOut", 		required_argument, NULL, 't'},
		{"Help",			0, NULL, 'h'},
		{0, 0, 0 ,0},
	};
	int c;
	int len;
	int value;
	/*���������������������ò���*/
	while((c = getopt_long(argc,argv, shortopts, longopts, NULL)) != -1)
	{
		switch(c)
		{
			case 'c':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0] != ':')
				{
					len = strlen(l_opt_arg);
					memcpy(cgiRoot, l_opt_arg, len+1);
				}
				break;
			case 'd':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0] != ':')
				{
					len = strlen(l_opt_arg);
					memcpy(defaultFileName, l_opt_arg, len+1);
				}
				break;
			case 'o':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0] != ':')
				{
					len = strlen(l_opt_arg);
					memcpy(homePath, l_opt_arg, len+1);
				}
				break;
			case 'l':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0] != ':')
				{
					len = strlen(l_opt_arg);
					value = strtol(l_opt_arg, NULL, 10);
					if(value != LONG_MAX && value != LONG_MIN);
						*serverPort = value;
				}
				break;
			case 'm':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0] != ':')
				{
					len = strlen(l_opt_arg);
					value = strtol(l_opt_arg, NULL, 10);
					if(value != LONG_MAX && value != LONG_MIN);
						*maxChildren = value;
				}
				break;
			case 't':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0] != ':')
				{
					len = strlen(l_opt_arg);
					value = strtol(l_opt_arg, NULL, 10);
					if(value != LONG_MAX && value != LONG_MIN);
						sockTimeVal.tv_sec = value;
				}
				break;
			case '?':
				printf("Invalid para\n");
				break;
			case 'h':
				printf("help file dose not exist\n");
				break;

		}
	}
	return 0;
}

int initParameters (int* serverPort, int* maxChildren, int argc,char* argv[])
/* initializes the operation parameters by reading them from the config file specified in the config file */
{
    char configFile[MAX_PATH_LEN+1];
    char str1[BUFFER_SIZE+1];
    char str2[BUFFER_SIZE+1];
    FILE *f;

    strcpy(configFile, DEFAULT_CONFIG_LOCATION);
    strcat(configFile, CONFIG_FILE_NAME);
    //if (argc > 0)
    //    printf("%s\n", *argv);  /* we shall insert here command-line arguments processing */
    f = fopen(configFile, "r");
    if (f == NULL)
    {
        printf("Error opening config file. Setting defaults.\n");
        *serverPort = DEFAULT_PORT;
        *maxChildren = DEFAULT_MAX_CHILDREN;
        strcpy(homePath, DEFAULT_DOCS_LOCATION);
        strcpy(defaultFileName, DEFAULT_FILE_NAME);
        sockTimeVal.tv_sec = DEFAULT_SEC_TO;
        sockTimeVal.tv_usec = DEFAULT_USEC_TO;
        strcpy(logFileName, DEFAULT_LOG_FILE);
        strcpy(mimeTypesFileName, DEFAULT_MIME_FILE);
        strcpy(cgiRoot, DEFAULT_CGI_ROOT);
        return -1;
    }
    if (!feof(f)) fscanf(f, "%s %s", str1, str2);
    *serverPort = 0;
    if (str1 != NULL && str2 != NULL && !strcmp(str1, "port"))
        sscanf(str2, "%d", serverPort);
    if (*serverPort <= 0)
    {
        *serverPort = DEFAULT_PORT;
        printf("Error reading port from file, setting default, %d\n", *serverPort);
    }
    //printf("port: %d\n", *serverPort);
    if (!feof(f)) fscanf(f, "%s %s", str1, str2);
    *maxChildren = 0;
    if (str1 != NULL && str2 != NULL && !strcmp(str1, "maxChildren"))
        sscanf(str2, "%d", maxChildren);
    if (*maxChildren <= 0)
    {
        *maxChildren = DEFAULT_MAX_CHILDREN;
        printf("Error reading maxChildren from file, setting default, %d\n", *maxChildren);
    }
    //printf("maxChildren: %d", *maxChildren);
#ifndef FORKING_REQUEST
    printf(" (but forking disabled at compile time)");
#endif
    printf("\n");
    if (!feof(f)) fscanf(f, "%s %s", str1, str2);
    if (str1 != NULL && str2 != NULL && !strcmp(str1, "documentsPath"))
    {
        sscanf(str2, "%s", homePath);
        if (homePath == NULL)
        {
            strcpy(homePath, DEFAULT_DOCS_LOCATION);
            printf("Error reading documentPath from file, setting default, %s\n", homePath);
        }
    } else
    {
        strcpy(homePath, DEFAULT_DOCS_LOCATION);
        printf("Error reading documentPath from file, setting default, %s\n", homePath);
    }
    if (!feof(f)) fscanf(f, "%s %s", str1, str2);
    if (str1 != NULL && str2 != NULL && !strcmp(str1, "defaultFile"))
    {
        sscanf(str2, "%s", defaultFileName);
        if (defaultFileName == NULL) {
            strcpy(defaultFileName, DEFAULT_FILE_NAME);
            printf("Error reading defaultFile from file, setting default, %s\n", defaultFileName);
        }
    } else
    {
        strcpy(defaultFileName, DEFAULT_FILE_NAME);
        printf("Error reading defaultFile from file, setting default, %s\n", defaultFileName);
    }
    if (strlen(defaultFileName) > MAX_INDEX_NAME_LEN)
    {
        printf("Error: the default file name is too long, exiting.\n");
        return -1;
    }

    if (!feof(f)) fscanf(f, "%s %s", str1, str2);
    sockTimeVal.tv_sec = -1;
    if (str1 != NULL && str2 != NULL && !strcmp(str1, "secTimeout"))
        sscanf(str2, "%ld", (long int *)&(sockTimeVal.tv_sec));
    if (sockTimeVal.tv_sec < 0)
    {
        sockTimeVal.tv_sec = DEFAULT_SEC_TO;
        printf("Error reading secTimeout from file, setting default, %ld\n", (long int)sockTimeVal.tv_sec);
    }
    //printf("timeout sec: %ld, ", (long int)sockTimeVal.tv_sec);
    if (!feof(f)) fscanf(f, "%s %s", str1, str2);
    sockTimeVal.tv_usec = -1;
    if (str1 != NULL && str2 != NULL && !strcmp(str1, "uSecTimeout"))
        sscanf(str2, "%ld", (long int *)&(sockTimeVal.tv_usec));
    if (sockTimeVal.tv_usec < 0)
    {
        sockTimeVal.tv_usec = DEFAULT_USEC_TO;
        printf("Error reading usecTimeout from file, setting default, %ld\n", (long int)sockTimeVal.tv_usec);
    }
    //printf("usec: %ld\n", (long int)sockTimeVal.tv_usec);
    if (!feof(f)) fscanf(f, "%s %s", str1, str2);
    if (str1 != NULL && str2 != NULL && !strcmp(str1, "logFile"))
    {
        sscanf(str2, "%s", logFileName);
        if (logFileName == NULL)
        {
            strcpy(logFileName, DEFAULT_LOG_FILE);
            printf("Error reading logFile from file, setting default, %s\n", logFileName);
        }
    } else
    {
        strcpy(logFileName, DEFAULT_LOG_FILE);
        printf("Error reading logFile from file, setting default, %s\n", logFileName);
    }
    if (!feof(f)) fscanf(f, "%s %s", str1, str2);
    if (str1 != NULL && str2 != NULL && !strcmp(str1, "mimeTypesFile"))
    {
        sscanf(str2, "%s", mimeTypesFileName);
        if (mimeTypesFileName == NULL)
        {
            strcpy(mimeTypesFileName, DEFAULT_MIME_FILE);
            printf("Error reading mimeTypesFileName from file, setting default, %s\n", mimeTypesFileName);
        }
    } else {
        strcpy(mimeTypesFileName, DEFAULT_MIME_FILE);
        printf("Error reading mimeTypesFileName from file, setting default, %s\n", mimeTypesFileName);
    }
    if (!feof(f)) fscanf(f, "%s %s", str1, str2);
    if (str1 != NULL && str2 != NULL && !strcmp(str1, "cgiRoot"))
    {
        sscanf(str2, "%s", cgiRoot);
        if (cgiRoot == NULL)
        {
            strcpy(cgiRoot, DEFAULT_CGI_ROOT);
            printf("Error reading cgiRoot from file, setting default, %s\n", cgiRoot);
        }
    } else
    {
        strcpy(cgiRoot, DEFAULT_CGI_ROOT);
        printf("Error reading cgiRoot from file, setting default, %s\n", cgiRoot);
    }
    fclose(f);
	Para_CmdParse(serverPort, maxChildren,  argc, argv);
    printf("port: %d\n", *serverPort);
    printf("maxChildren: %d\n", *maxChildren);
    printf("timeout sec: %ld, ", (long int)sockTimeVal.tv_sec);
    printf("usec: %ld\n", (long int)sockTimeVal.tv_usec);
    initMimeTypes();
    return 0;
}

int main (int argc,char* argv[])
/* contains the main connection accept loop which calls analyzers and handlers */
{
    int                port;                 /* the port the server is listening to */
    int                maxChildren;          /* maximum number of running children, configured even if without fork() */
    char               buff[BUFFER_SIZE+1];
    int                totalRead;
    int                stuckCounter;         /* to behold read progress and catch nasty loops */
    int                readFinished;
    struct request     gottenReq;
    int                isKeepAlive;
    struct sockaddr_in listenName;           /* data struct for the listen port */
    struct sockaddr_in acceptedSockStruct;   /* sockaddr for the internetworking */
    sockLenType        acceptedSocketLen;    /* size of the structure */
#ifdef SOCKADDR_REUSE
    int                sockReuse;
#endif
#ifdef FORKING_REQUEST
    int                pid;                  /* here we record the childs' pid */
    int                runningChildren;
#endif

    printf("Pserv 3.5 By 周华健，黄旭东，刘叶\n");
    initParameters(&port, &maxChildren, argc, argv); /* read config and settings */

    listenName.sin_family = AF_INET;
    listenName.sin_addr.s_addr=INADDR_ANY;
    listenName.sin_port = htons(port);
    acceptedSocketLen = sizeof(acceptedSockStruct);


    /* signal init */
    signal_MACRO(SIGINT,  intCatch);
    signal_MACRO(SIGPIPE, brokenPipe);

    if (logFileOpen())
    {
        printf("log file creation error or other misconfiguration\n");
        exit(0);
    }

    theSocket = socket (AF_INET, SOCK_STREAM, 0);
    if (theSocket == -1)
    {
        printf("socket creation error occoured\n");
        return -1;
    }
#ifdef SOCKADDR_REUSE
    sockReuse = YES;
    error = setsockopt (theSocket, SOL_SOCKET, SO_REUSEADDR, &sockReuse, sizeof(sockReuse));
    if (error == -1)
    {
        printf("socket reuse option setting failed\n");
    }
#endif
    error = bind (theSocket, (struct sockaddr*)  &listenName, sizeof(listenName));
    if (error == -1)
    {
        printf("socket binding error occoured\n");
        return -2;
    }
    error = listen(theSocket, BACK_LOG);
    if (error)
    {
        printf ("listen error\n");
        return -1;
    }
    isKeepAlive = NO;
#ifdef FORKING_REQUEST
    runningChildren = 0;
#endif
    while (1)
    {
        DBGPRINTF(("listen\n"));
        newSocket = accept (theSocket, (struct sockaddr *) &acceptedSockStruct, &acceptedSocketLen);
#ifdef FORKING_REQUEST
	  pid = fork();
	  if (pid)
	  {
	    /* the parent process */
	    int exitResult;
	    int i;

	    if (pid < 0)
	    {
	        /* we checked for a fork error */
		      printf ("A forking error occoured!\n");
      }
	    runningChildren++;
      newSocketReady = NO;
      close(newSocket);
	    /* we take care of the running children and their status */
	    /* if too many childrends are running, we block and wait for an exit */
	    if (runningChildren >= maxChildren)
	    {
	        wait(&exitResult);
	        DBGPRINTF(("Child exited with: %d\n", exitResult));
          runningChildren--;
      }
	    /* here we collect quickly possible exits without blocking */
	    for (i = 0; i < runningChildren; i++);
      {
		      if (waitpid(-1, &exitResult, WNOHANG) > 0)
          {
	            DBGPRINTF(("Child exited with: %d\n", exitResult));
              runningChildren--;
          }
	    }
	  } else
	  {
#endif
	    /* the child process */
      if (newSocket == -1)
      {
          newSocketReady = NO;
          printf("error accepting\n");
      } else
      {
          strcpy(gottenReq.address, inet_ntoa(acceptedSockStruct.sin_addr));
          DBGPRINTF(("accepted from %s\n", gottenReq.address));
          newSocketReady = YES;
          error = setsockopt (newSocket, SOL_SOCKET, SO_RCVTIMEO, &sockTimeVal, sizeof(sockTimeVal));
#ifdef PRINTF_DEBUG
          if(error)
              perror("setsockopt: ");
#endif
          sockStream = fdopen(newSocket, "r+");

		      if (sockStream != NULL)
		      {
		          int ch;
		          buff[0] = '\0';
              readFinished = NO;
		          stuckCounter = 0;
		          totalRead = 0;

		          while (!readFinished)
		          {
		    	        ch = fgetc(sockStream);
			            if (ch == EOF)
			            {
			                if (errno == EAGAIN)
                      {
                          DBGPRINTF(("resource not available on header read\n"));
				                  clearerr(sockStream);
                          stuckCounter++;
                          if (stuckCounter >= MAX_STUCK_COUNTER)
                          {
                              DBGPRINTF(("Loop in read catched! closing connection.\n"));
                              if (newSocketReady)
                              {
                                  DBGPRINTF(("new socket was ready....\n"));
                                  close(newSocket);
				                      }
                              newSocketReady = NO;
                              readFinished = YES;
                          }
                      } else
                      {
                          DBGPRINTF(("read error: %d\n", errno));
                          newSocketReady = NO;
                          readFinished = YES;
                          close(newSocket);
			                }
			            } else
			            {
	                    /* we always have read at least one character */
                      buff[totalRead] = (char)ch; totalRead++;

                      /* Header should normally end with cr-cr */
			                if (totalRead > 2)
			                {
			    	              if (totalRead >= BUFFER_SIZE) /* checking for buffer overflow */
                          {
                            	DBGPRINTF(("Buffer overflow on request read\n"));
                            	sayError(newSocket, INPUT_LINE_TOO_LONG, "", gottenReq);
                            	readFinished = YES;
                          } else if (buff[totalRead-1] == '\n' && buff[totalRead-3] == '\n')
				                  {
				                       buff[totalRead] = '\0';
                               readFinished = YES;
				                  }
                      }
			            }
		          } /* end of header read while */
		          if (totalRead <= 0)
              {
                	DBGPRINTF(("Request read error\n"));
              } else if (buff[totalRead-1] != '\n' && buff[0]=='P' && buff[1]=='O' && buff[2]=='S' &&  buff[3]=='T' && buff[4]=='\0')
              {
		              /* POST method */
                	/* POST doesn't terminate data with a newline but GET should */
                	DBGPRINTF(("Unterminated request header\n"));
                	sayError(newSocket, INPUT_LINE_TOO_LONG, "", gottenReq);
              } else
              {
                	analyzeRequest(newSocket, buff, &gottenReq);
                	error = setsockopt (newSocket, SOL_SOCKET, SO_SNDTIMEO, &sockTimeVal, sizeof(sockTimeVal));
                	handleMethod(port, newSocket, gottenReq);
                	/* detecting the keep-alive connection, if it shall be ever better implemented
                	if (gottenReq.keepAlive) {
                            isKeepAlive = YES;
                            DBGPRINTF(("bogus keep alive thing\n"));
                	} */
              }
          } /* sockStream != NULL */

          if (close(newSocket))
              DBGPRINTF(("error closing socket after connection\n"));
          else
              newSocketReady = NO;
      }
#ifdef FORKING_REQUEST
	    exit(0);
    } /* end of fork if */
#endif
    } /* end of while */
    close(theSocket); /* if we shall ever exit the infinite loop... */
}
