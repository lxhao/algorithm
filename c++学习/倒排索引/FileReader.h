#ifndef FILEREADER_H_INCLUDED
#define FILEREADER_H_INCLUDED
#include <vector>
#include <string>
#include <sstream>
#include <iostream>
#include <fstream>

using namespace std;

class FileReader {
public :
    vector<string>  readData(string filePath);
};

#endif // FILEREADER_H_INCLUDED
