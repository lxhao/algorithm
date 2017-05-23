CC      = g++ 
CFLAGS  = -g -Wall -std=c++11
LDFLAGS = -lpthread

EXE  = main
OBJS = id_lookup.o main.o

${EXE}:${OBJS}
	$(CC) -o $@ $(OBJS) ${CFLAGS} ${LDFLAGS}

.SUFFIXES: .o .cpp

.cpp.o:  
	$(CC) $(CFLAGS) -c $*.cpp -o $*.o 

run:  ${EXE}
	$(VALGRIND) ./main

clean:
	rm -f $(OBJS) $(EXE)


