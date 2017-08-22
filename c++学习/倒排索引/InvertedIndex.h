#include "StringUtil.h"
#include "wn.h"
#include <fstream>
#include <iostream>
#include <iostream>
#include <unordered_map>
#include <sstream>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <string>
#include <strings.h>
#include <vector>

#define MAXLINE 1024

using namespace std;

class InvertedIndex {
public:
    InvertedIndex()
    {
        wninit();
    }

    ~InvertedIndex()
    {
        result_index_.clear();
    }

    int StatWords(const string dir, const string filename);

    unordered_map<string, unordered_map<string, int> > result_index()
    {
        return result_index_;
    }

private:
    StringUtil stringUtil;
    //存放倒排索引结果，key是单词，value也是map，该map的key是文件名，value是该单词在该文件中出现的次数
    unordered_map<string, unordered_map<string, int> > result_index_;

    int ParseWordsByLine(char* str_line, const string filename);
    void InsertWordToMap(char* word, const string filename);
};
