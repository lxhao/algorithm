CC      = g++ 
CFLAGS  = -g -Wall -std=c++11 
LDFLAGS = -lm
VALGRIND = 

EXE  = a3searc 
OBJS = a3searc.o porter2_stemmer.o 

all :	$(EXE)

.SUFFIXES: .c .o .cpp

${EXE}:${OBJS}
	$(CC) -o $@ $(OBJS) ${CFLAGS}

.cpp.o:  
	$(CC) $(CFLAGS) -c $*.cpp -o $*.o  

run: ${EXE}
	$(VALGRIND) ./a3searc /home/lxhao/CLionProjects/code/try/books200m index "apple" "family" "weather" "puzzlement" "explain"

clean:
	rm -f *.o *~
	rm -rf *.dSYM
	rm -f $(EXE)
	rm -f ./index/*



