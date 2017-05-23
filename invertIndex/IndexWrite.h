#include "msgpack.hpp"
#include <fstream>
#include <iostream>
#include <sstream>
#include <string>
#include <unordered_map>

using namespace std;
class IndexWrite {

public:
    bool file_get_contents(const string& filename, vector<unsigned char>& buffer);
    void writeMap(string filename, unordered_map<string, unordered_map<string, int> >& indexMap);
    unordered_map<string, unordered_map<string, int> > readMap(vector<unsigned char> out_data);
};
