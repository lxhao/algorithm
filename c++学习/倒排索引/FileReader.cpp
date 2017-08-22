#include "FileReader.h"
using namespace std;

vector<string >  FileReader::readData(string filePath)
{
    vector<string> lines;

    ifstream inFile;
    string line;
    inFile.open(filePath.c_str());

    if (!inFile)
    {
        cout << "Error opening file" << filePath << endl;
        return lines;
    }

    while (!inFile.eof())
    {
        getline(inFile, line);
        lines.push_back(line);
    }
    inFile.close();
    return lines;
}



