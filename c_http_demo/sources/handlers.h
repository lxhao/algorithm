/*
handlers.h
This file is part of pserv
http://pserv.sourceforge.net

Copyright (c) 2001-2005 Riccardo Mottola. All rights reserved.
mail: rmottola@users.sourceforge.net

This file is free software, released under GPL. Please read acclosed license
*/



#define READ 0
#define WRITE 1


/* handlers.c */
int sendChunk();
int cgiHandler();
int dumpHeader();
int dumpFile();
int generateIndex();

