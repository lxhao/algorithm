/*
mime.c
This file is part of pserv
http://pserv.sourceforge.net

Copyright (c) 2001-2002-2003-2004 Riccardo Mottola. All rights reserved.
mail: rmottola@users.sourceforge.net

This file is free software, released under GPL. Please read acclosed license
*/

#include <ctype.h>        /* tolower char conversion */

#include "mime.h"
#include "handlers.h" /* for sendChunk routine */
#include "log.h"
#include "main.h"

extern char 	mimeTypesFileName[MAX_PATH_LEN+1];
extern mimeData *mimeArray;
extern int  	mimeEntries;

int lowerCase(str)
/* converts the given string to lowercase, by modifying it */
/* should need some not-terminatesdstring protection ? */
char *str;
{
    int i;
    
    i = 0;
    while (str[i] != '\0')
    {
        str[i] = tolower(str[i]);
	      i++;
    }
    return 0;
}

/*------------------------------------------------------------*/
// 初始化mine类型数组
/*------------------------------------------------------------*/
int initMimeTypes()
/* load mime types from file */
{
    FILE    *f;
    int     entries;
    int     i;
    char    str[BUFFER_SIZE];

    /* first we check how many entries we have */
    f = fopen(mimeTypesFileName, "r");
    if (f == NULL)
    {
        printf("Error opening mime types file. Setting defaults.\n");
        entries = 3;
        mimeArray = (mimeData *) calloc(entries, sizeof(mimeData));
        if (mimeArray == NULL) {
            printf("Errory while allocating mime types Array. Exiting.\n");
            return -2;
        }
        strcpy(mimeArray[0].ext, "html");
        strcpy(mimeArray[0].type, "text/html");
        strcpy(mimeArray[1].ext, "gif");
        strcpy(mimeArray[1].type, "image/gif");
        strcpy(mimeArray[2].ext, "jpg");
        strcpy(mimeArray[2].type, "image/jpg");
        mimeEntries = entries;
        return -1;
    }

    // 统计总共支持的mine类型数量
    entries = 0;
    while (!feof(f)) {
        fscanf(f, "%s", str);
        if(!feof(f)) {
            fscanf(f, "%s", str);
            entries++;
        }
    }

    /* now we rewind the file and read those values at last */
    fseek(f, (long) 0, SEEK_SET);
    clearerr(f);

    DBGPRINTF(("attempting to read %d mime entries\n", entries));
    /* init the mime data array */
    mimeArray = (mimeData *) calloc(entries, sizeof(mimeData));
    if (mimeArray == NULL) {
        DBGPRINTF(("Error while allocating mime types Array. Exiting.\n"));
        return -2;
    }
    i = 0;

    while(i < entries && !feof(f)) {
        fscanf(f, "%s", str);
        strcpy(mimeArray[i].ext, str);
        fscanf(f, "%s", str);
        strcpy(mimeArray[i].type, str);
        i++;
    }
    fclose(f);
    mimeEntries = entries;
    return 0;
}

/*------------------------------------------------------------*/
// 分析所需的url的文件类型
//  mimeType : 要传出的mime文件类型
//  addr : 文件的地址
/*------------------------------------------------------------*/
int analyzeExtension(mimeType, addr)
/* get extension of given file and determine mime type */
/* if an error happens or otherwise the extension is not recognized then MIME_TYPE_DEFAULT is used */
char mimeType[MAX_MIMETYPE_LEN];
char addr[MAX_PATH_LEN];
{
    char ext[MAX_EXTENSION_LEN];
    int i;
    int addrLen;
    int extStart;
    int extLen;
    int found;

    addrLen = strlen(addr);
    /* let's find out where the extension starts */
    i = addrLen;
    while ((i > 0) && (addr[i] != '.') && (addr[i] != '/'))
        i--;
    found = (addr[i] == '.') ? YES : NO;
    if ((i == 0) || (addrLen - i - 1 > MAX_EXTENSION_LEN))
    {
        ext[0] = '\0';
        DBGPRINTF(("error recognizing extension: i = %d, extlen = %d\n", i, addrLen-i-1));
        strcpy(mimeType, MIME_TYPE_DEFAULT);
        logWriter(LOG_BAD_EXTENSION, NULL, 0, NULL, 0);
        return -1;
    }
    if (!found)
    {
        strcpy(mimeType, MIME_TYPE_DEFAULT); /* default mime type */
        return 0;
    }

    // 找到了文件后缀名开始的位置  此时 i 指向 . 的位置
    extStart = i + 1;
    extLen = addrLen - extStart;
    i = 0;
    for (i = 0; i < extLen; i++)
        ext[i] = addr[i + extStart];
    ext[i] = '\0';

    /* determine mime type */
    found = NO;
    i = 0;
    lowerCase(ext); // 后缀名小写化
    // 逐一匹配 直至找到对应的类型后缀名
    while (i < mimeEntries && !found)
    {
        if (!strcmp(ext, mimeArray[i].ext))
        {
            strcpy(mimeType, mimeArray[i].type);
            found = YES;
        } else
            i++;
    }
    if (!found)
        strcpy(mimeType, MIME_TYPE_DEFAULT); /* default mime type */
    return 0;
}

/*------------------------------------------------------------*/
// 处理传送给sock的mime内容头部
//  statusCode : 返回的数据类型代码  contentType : 内容类型
//  statistics : 数据结构  sentProtocol : http版本号（判别 0.9版本 使用）
/*------------------------------------------------------------*/
int generateMimeHeader(sock, statusCode, contentType, statistics, sentProtocol, kind)
/* prepares and writes to the socket the required connection and MIME header */
/* I kept it all together for practical purposes, not for logical clarity    */
/* it returns the bytes sent                                                 */
int sock;
int statusCode;
char contentType[];
struct stat *statistics;
char sentProtocol[];
int kind; /* if FULL_HEADER or CGI_ONLY_HEADER */
{
    long int size;
    char lastModifiedStr[256];
    char outBuff[1024];  /* we assume that the whole header can be sent one time on socket */
    char description[128];
    char tempStr[1024];
    char timeStr[256];
    struct tm *timeStruct;
    time_t timeTemp;

    switch (statusCode) {
        case 200:
            strcpy(description, "OK");
            break;
        case 403:
            strcpy(description, "Forbidden");
            break;
        case 404:
            strcpy(description, "Not Found");
            break;
        case 500:
            strcpy(description, "Server Error");
            break;
        case 501:
            strcpy(description, "Not Implemeted");
            break;
        default:
            strcpy(description, "no description");
            break;
    }
    /* if we have a 0.9 conenciton we don't generate any header! */
    if (!strcmp(sentProtocol, "HTTP/0.9"))
        return 0; /* no header for 0.9 and we don't send any bytes */
    sprintf(tempStr, "HTTP/1.0 %d %s\n", statusCode, description);
    strcpy(outBuff, tempStr);
    strcat(outBuff, "Connection: close\n");
    time(&timeTemp);
    timeStruct = (struct tm *) localtime(&timeTemp);
    strftime(timeStr, 256, "%a, %d %b %Y %H:%M:%S %Z", timeStruct);
    sprintf(tempStr, "Date: %s\n", timeStr);
    strcat(outBuff, tempStr);
    sprintf(tempStr, "Server: %s/%s\n", SERVER_SOFTWARE_STR, SERVER_VERSION_STR);
    strcat(outBuff, tempStr);
    if (kind==FULL_HEADER)
    { /* the CGI script will append its own header so we won't append a new-line */
        sprintf(tempStr, "Content-Type: %s\n", contentType);
        strcat(outBuff, tempStr);
        if (statistics)
        {
            size = (long int)(*statistics).st_size;
            if (size > 0)
            {
                sprintf(tempStr, "Content-length: %ld\n", size);
                strcat(outBuff, tempStr);
            }
            timeStruct = (struct tm *) gmtime(&(*statistics).st_mtime);
            /* here we always output GMT as a timezone, since %Z returns locale TZ on some OS, like IRIX */
            /* we anyway used gmtime() the line above, so it should work */
            strftime(lastModifiedStr, 256, "%a, %d %b %Y %H:%M:%S GMT", timeStruct);
            sprintf(tempStr, "Last-Modified: %s\n", lastModifiedStr);
        }
        strcat(outBuff, tempStr);
        strcat(outBuff, "\n");
    }
    if (sendChunk(sock, outBuff, strlen(outBuff)) < 0)
        DBGPRINTF(("error during header write: %d\n", errno));
    return strlen(outBuff);
}
