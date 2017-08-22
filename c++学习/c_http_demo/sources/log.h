/*
log.h
This file is part of pserv
http://pserv.sourceforge.net

Copyright (c) 2001-2002-2003 Riccardo Mottola. All rights reserved.
mail: rmottola@users.sourceforge.net

This file is free software, released under GPL. Please read acclosed license
*/



/* logging type definitions */
#define LOG_GET_SUCCESS 1
#define LOG_HEAD_SUCCESS 2
#define LOG_FILE_NOT_FOUND 3
#define LOG_FORBIDDEN 4
#define LOG_CGI_SUCCESS 5
#define LOG_CGI_FAILURE 6
#define LOG_BAD_EXTENSION 7
#define LOG_GENERIC_ERROR 8
#define LOG_INPUT_LINE_TOO_LONG 9
#define LOG_LENGTH_REQUIRED 10
#define LOG_POST_BUFFER_OVERFLOW 11


/* log.c */
int logFileOpen();
int logFileClose();
int logWriter();

