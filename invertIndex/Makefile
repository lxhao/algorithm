CC      = g++ 
CFLAGS  = -g -Wall -std=c++11 
LDFLAGS = -lm
VALGRIND = 

EXE  = a3searc 
OBJS = a3searc.o InvertedIndex.o StringUtil.o libWN.a libmsgpackc.a IndexWrite.o

all :	$(EXE)

.SUFFIXES: .c .o .cpp

${EXE}:${OBJS}
	$(CC) -o $@ $(OBJS) ${CFLAGS}

.cpp.o:  
	$(CC) $(CFLAGS) -c $*.cpp -o $*.o 

run: ${EXE}
	$(VALGRIND) ./a3searc dir dir/index  "investor"

clean:
	rm -f *.o *~
	rm -rf *.dSYM
	rm -f $(EXE)



