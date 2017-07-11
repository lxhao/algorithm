/*
main.h
This file is part of pserv
http://pserv.sourceforge.net

Copyright (c) 2001-2005 Riccardo Mottola. All rights reserved.
mail: rmottola@users.sourceforge.net

*/

/*
This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

#ifdef HAVE_CONFIG_H
#include "config.h"   /* configure generated definitions */
#endif /* HAVE_CONFIG_H */

/* --- CPP parsing options --- */
#define PRINTF_DEBUG           /* enable this to print some debugging messages */
#undef ON_THE_FLY_CONVERSION /* enable this for line ending conversion */

#define AUTO_INDEX              /* enables auto-index of directories */
#define SOCKADDR_REUSE          /* enables reuse oth the socket (quick relaunch) */


/* --- Configure options --- */
#define CONFIG_FILE_NAME    "pserv.conf"  /* name of the configuration file, concatenated to DEFAULT_CONFIG_LOCATION */
#define SERVER_SOFTWARE_STR "pServ"
#define SERVER_VERSION_STR  PACKAGE_VERSION
#define CGI_MATCH_STRING    "/cgi-bin/"
#define CGI_VERSION 	    "CGI/1.1"
#define MIME_TYPE_DEFAULT   "application/octet-stream"

/* configuration file location */
#define DEFAULT_CONFIG_LOCATION "/usr/local/etc/pserv/"

/* hard-wired defaults, if loading of config file fails */
#define DEFAULT_PORT	    	2000
#define DEFAULT_MAX_CHILDREN	5
#define DEFAULT_DOCS_LOCATION	"/usr/local/var/www"
#define DEFAULT_FILE_NAME   	"index.html"
#define DEFAULT_SEC_TO	    	1
#define DEFAULT_USEC_TO     	100
#define DEFAULT_LOG_FILE    	"/usr/local/var/log/pserv.log"
#define DEFAULT_MIME_FILE   	"/usr/local/etc/pserv/mime_types.dat"
#define DEFAULT_CGI_ROOT    	"/usr/local/var/www/cgi-bin"
#define DEFAULT_SERVER_NAME 	"localhost"

/* amount of connections queued in listening */
#define BACK_LOG 4


/* ------------------------ */

#define YES 1
#define NO 0

/* mime header generation types */
#define FULL_HEADER 1
#define CGI_ONLY_HEADER 2

/* internal error types */
#define INPUT_LINE_TOO_LONG 	-1
#define UNHANDLED_METHOD    	-2
#define NOT_FOUND   	    	-3
#define FORBIDDEN   	    	-4
#define BUFFER_OVERFLOW     	-5
#define LENGTH_REQUIRED     	-6
#define POST_BUFFER_OVERFLOW	-7

/* handlers buffer configuration */
#define PIPE_READ_BUF	    4096
#define OUT_SOCK_BUFF_SIZE  1440 /* don't set this too large or you may get segmentation fault */

/* connection error retries */
#define MAX_STUCK_COUNTER   	3 /* maximum number of empty reads() from socket */
#define MAX_EMPTY_READS_COUNTER 3 /* maximum number of empty reads() with EAGAIN to get timeout, used in CGI Post reading */

/* argument and environment array length for execve() */
#define MAX_ARGV_LEN	16
#define MAX_ENVP_LEN	16

/* includes */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <netinet/in.h>   /* for sockaddr_in */
#include <netdb.h>        /* for hostent */ 
#include <arpa/inet.h>
#include <signal.h>
#include <errno.h>        /* we want to catch some of these after all */
#include <unistd.h>       /* protos for read, write, close, etc */
#include <dirent.h>       /* for MAXNAMLEN */


/* buffers and other size configurations */

   
#define MAX_PATH_LEN	    MAXNAMLEN+1   /* the maximum path name of your system should be available in MAXNAMLEN
                                             defined in dirent.h. If not, set a reasonable value like 1024 */
#define MAX_INDEX_NAME_LEN  16    /* the default file name len, like index.html or default.html */
#define BUFFER_SIZE 	    2048  /* it has to hold at least PATH + QUERY_STRING during request read*/
#define POST_BUFFER_SIZE    MAX_PATH_LEN+MAX_QUERY_STRING_LEN  /* holds the maximum amount of data that can be read by POST */
#define MAX_EXTENSION_LEN   64
#define MAX_MIMETYPE_LEN    32

#define MAX_REQUEST_LINES   16 /* number of lines accepted in request header */

/* sizes of the tokens for the request */
#define ADDRESS_LEN 	    	16
#define METHOD_LEN  	    	16
#define PROTOCOL_LEN	    	16
#define USER_AGENT_LEN	    	256
#define MAX_QUERY_STRING_LEN	1024

/* ------ Structures ------- */
/* we use +1 to be safe with handling the end-of-string */
typedef struct {
    char ext[MAX_EXTENSION_LEN+1];
    char type[MAX_MIMETYPE_LEN+1];
} mimeData;

struct request
{
    char     address[ADDRESS_LEN+1];
    char     method[METHOD_LEN+1];
    char     documentAddress[MAX_PATH_LEN+1];
    char     queryString[MAX_QUERY_STRING_LEN+1];
    char     protocolVersion[PROTOCOL_LEN+1];
    int      keepAlive;
    char     userAgent[USER_AGENT_LEN+1];
    long int contentLength;
    char     rest[BUFFER_SIZE+1];
};


/* ---- configuration dependent types and macros ---- */

#ifdef __sgi
#define sockLenType int
#else
#ifdef _AIX
#define sockLenType size_t
#else
#define sockLenType socklen_t
#endif
#endif

#if 0 /* how to handle this cast */
#define signal_MACRO(SIGNAL_NUMBER, FUNCTION_NAME) \
        signal(SIGNAL_NUMBER, FUNCTION_NAME)
#define signal_MACRO(SIGNAL_NUMBER, FUNCTION_NAME) \
        signal(SIGNAL_NUMBER, (void *)FUNCTION_NAME)
#endif
#define signal_MACRO(SIGNAL_NUMBER, FUNCTION_NAME) \
        signal(SIGNAL_NUMBER, FUNCTION_NAME)

#ifdef PRINTF_DEBUG
#define DBGPRINTF(s) printf s 
#else
#define DBGPRINTF(s)
#endif



/* --- function declarations --- */

/* main.c */
RETSIGTYPE intCatch();
RETSIGTYPE killCatch();
RETSIGTYPE brokenPipe();
int sayError();
int convertPercents();
int analyzeRequest();
int handleMethod();
int initParameters();





