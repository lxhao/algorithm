/*
log.c
This file is part of pserv
http://pserv.sourceforge.net

Copyright (c) 2001-2002 Riccardo Mottola. All rights reserved.
mail: rmottola@users.sourceforge.net

This file is free software, released under GPL. Please read acclosed license
*/

#include "log.h"
#include "main.h"

/* external variables */
extern char logFileName[MAX_PATH_LEN+1];
extern FILE *lf;

/* Opens the server log file
 * to be called once at the beginning
 */
int logFileOpen()
{
    char timeStr[256];
    struct tm *timeStruct;
    time_t timeTemp;

    time(&timeTemp);
    timeStruct = (struct tm *) localtime(&timeTemp);
    strftime(timeStr, 256, "%d %b %Y %H:%M:%S %Z", timeStruct);

    lf = fopen (logFileName, "a");
    if (lf == NULL)
        return -1;
    fprintf(lf, "\n# Server started on %s\n", timeStr);
    fflush(lf); /* otherwise we have mysterious problems with fork() !! */

    DBGPRINTF(("Opened Log File.\n"));
    return 0;
}

/* Closes the log file
 */
int logFileClose()
{
    fclose(lf);
    DBGPRINTF(("Closed Log File.\n"));
    return 0;
}

/* Writes an event in the log.
 * the event kind is given by eventType
 */
int logWriter(eventType, docPath, bytes, req, code)
int eventType;
char *docPath;
long int bytes;
struct request req;
int code;
{
    char timeStr[256];
    struct tm *timeStruct;
    time_t timeTemp;

    time(&timeTemp);
    timeStruct = (struct tm *) localtime(&timeTemp);
    strftime(timeStr, 256, "%d %b %Y %H:%M:%S %Z", timeStruct);
    switch (eventType)
    {
    case LOG_GET_SUCCESS:
        fprintf(lf, "%s	%s	200	%s	%s	%ld	%s\n", timeStr, req.method, req.address, req.userAgent, bytes, docPath);
	break;
    case LOG_HEAD_SUCCESS:
        fprintf(lf, "%s	%s	200	%s	%s	%ld	%s\n", timeStr, req.method, req.address, req.userAgent, bytes, docPath);
        break;
    case LOG_FILE_NOT_FOUND:
        fprintf(lf, "%s	%s	404	%s	%s	%s\n", timeStr, req.method, req.address, req.userAgent, docPath);
	break;
    case LOG_FORBIDDEN:
        fprintf(lf, "%s	%s	403	%s	%s	%s\n", timeStr, req.method, req.address, req.userAgent, docPath);
        break;
    case LOG_LENGTH_REQUIRED:
        fprintf(lf, "%s	%s	411	%s	%s	%s\n", timeStr, req.method, req.address, req.userAgent, docPath);
        break;
    case LOG_POST_BUFFER_OVERFLOW:
        fprintf(lf, "#post buffer overflow, %s\n", timeStr);
        fprintf(lf, "%s	%s	500	%s	%s	%s\n", timeStr, req.method, req.address, req.userAgent, docPath);
        break;
    case LOG_CGI_SUCCESS:
        fprintf(lf, "%s	%s	200	%s	%s	%ld	%s\n", timeStr, req.method, req.address, req.userAgent, bytes, req.documentAddress);
        break;
    case LOG_CGI_FAILURE:
        fprintf(lf, "#CGI-error %d,	%s	%s	%s	%s	%s\n", code, timeStr, req.method, req.address, req.userAgent, req.documentAddress);
        break;
    case LOG_BAD_EXTENSION:
        fprintf(lf, "#requestError bad Extension, %s\n", timeStr);
        break;
    case LOG_GENERIC_ERROR:
        fprintf(lf, "#unclassified error occoured, %s\n", timeStr);
        break;
    case LOG_INPUT_LINE_TOO_LONG:
        fprintf(lf, "#input line too long, %s\n", timeStr);
        break;
    default:
        printf ("Unknown event to log! Programming error!\n");
    }
    fflush(lf);
    return 0;
}
