#include <string.h>
#include <iostream>
#include <unordered_map>
#include <unistd.h>
#include <algorithm>
#include <dirent.h>
#include <fstream>
#include<sys/stat.h>
#include <sstream>
#include <chrono>
#include "porter2_stemmer.h"


using namespace std;

void mkdirs(const char *muldir);

vector<string> readFiles(const string &dirPath);

bool isFileExists(const string &filepath);

void buindIndex(const string &wordDir, const vector<string> &wordFiles, const string &indexDir);

void parseWords(stringstream &contents, unordered_map<string, int> &indexMap);

void writeMap(const unordered_map<string, int> &indexMap, const string &filename);

void readIndex(const string &indexFile, unordered_map<string, int> &indexMap);

float getIndexCount(unordered_map<string, int> &indexMap, const vector<string> &seachItems);

void printResult(vector<pair<string, float> > &resultVec);

bool less_second(const pair<string, float> &m1, const pair<string, float> &m2);

void toLowerCase(string &s);

int main(int argc, char *argv[]) {
    using timer = std::chrono::high_resolution_clock;
    timer::time_point start_time = timer::now();
    if (argc < 4) {
        return -1;
    }

    string fileDir = argv[1];
    string indexDir = argv[2];
    vector<string> seachItems;
    for (int i = 3; i < argc; i++) {
        toLowerCase((string &) argv[i]);
        string word = argv[i];
        Porter2Stemmer::trim(word);
        Porter2Stemmer::stem(word);
        seachItems.push_back(word);
    }

    //索引文件存放目录不存在则创建
    if (!isFileExists(indexDir)) {
        mkdirs(indexDir.c_str());
    }

    if (!isFileExists(fileDir)) {
        return -1;
    }

    //索引文件
    vector<string> indexFiles = readFiles(indexDir);
    //单词文件
    vector<string> wordFiles = readFiles(fileDir);

    //索引文件不存在, 创建索引文件
    if (wordFiles.size() != indexFiles.size()) {
        buindIndex(fileDir, wordFiles, indexDir);
    }

    //k:文字  v:平均次数
    vector<pair<string, float> > resultVec;

    unordered_map<string, int> indexMap;
    float count;
    for (string filename : wordFiles) {
        readIndex(indexDir + "/" + filename, indexMap);
        //计算出现次数
        count = getIndexCount(indexMap, seachItems);
        if (count > 0) {
            resultVec.push_back(make_pair(filename, count));
        }
        indexMap.clear();
    }
    printResult(resultVec);
    timer::time_point end_time = timer::now();
    std::cout << "Time elapsed: "
              << std::chrono::duration_cast<std::chrono::milliseconds>(
                      end_time - start_time).count() << "ms" << std::endl;
}

bool less_second(const pair<string, float> &m1, const pair<string, float> &m2) {
    if (m1.second != m2.second) {
        return m1.second > m2.second;
    }
    return m1.first < m2.first;
}

void printResult(vector<pair<string, float> > &resultVec) {
    sort(resultVec.begin(), resultVec.end(), less_second);
    for (auto e : resultVec) {
        cout << e.first << endl;
    }
}

float getIndexCount(unordered_map<string, int> &indexMap, const vector<string> &seachItems) {
    float res = 0;
    for (string word : seachItems) {
        //不包含单词
        if (indexMap.find(word) == indexMap.end()) {
            return 0;
        }
        res += indexMap[word];
    }
    return res / seachItems.size();
}

void buindIndex(const string &wordDir, const vector<string> &wordFiles, const string &indexDir) {
    ifstream fileStream;
    stringstream buffer;
    //每个文件的索引map,所有文件重复使用这个map, 每个文件建立索引完成后直接写入到文件
    unordered_map<string, int> indexMap;
    //遍历文件,对每个文件建立索引
    for (string filename : wordFiles) {
        string fullPath = wordDir + "/" + filename;
        fileStream.open(fullPath);
        if (fileStream.is_open()) {
            buffer << fileStream.rdbuf();
            //根据读出的字符串建立索引
            parseWords(buffer, indexMap);
            //清空map和缓存
            writeMap(indexMap, indexDir + "/" + filename);
            indexMap.clear();
            buffer.clear();
            fileStream.close();
        }
    }
}

void writeMap(const unordered_map<string, int> &indexMap, const string &filename) {
    ofstream out(filename);
    if (out.is_open()) {
        out << indexMap.size() << endl;
        for (auto e : indexMap) {
            out << e.first << "\t" << e.second << endl;
        }
        out.flush();
        out.close();
    }
}

void readIndex(const string &indexFile, unordered_map<string, int> &indexMap) {
    ifstream in = ifstream(indexFile);
    if (!in.is_open()) {
        return;
    }
    stringstream buffer;
    buffer << in.rdbuf();
    int lineNumber;
    buffer >> lineNumber;
    for (int i = 0; i < lineNumber; i++) {
        string word;
        int count;
        buffer >> word;
        buffer >> count;
        indexMap[word] = count;
    }
    in.close();
}

void parseWords(stringstream &contents, unordered_map<string, int> &indexMap) {
    string word;
    while (contents >> word) {
        toLowerCase(word);
        char c = word[word.length() - 1];
        //最后一个字符不是字母
        if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z')) {
            word.pop_back();
        }
        char a[word.length() + 1];
        a[word.length()] = 0;
        for (int i = 0; i < word.length(); i++) {
            a[i] = word[i];
        }
        Porter2Stemmer::trim(word);
        Porter2Stemmer::stem(word);
        indexMap[word]++;
    }
}

void mkdirs(const char *muldir) {
    int i, len;
    char str[512];
    strncpy(str, muldir, 512);
    len = strlen(str);
    for (i = 0; i < len; i++) {
        if (str[i] == '/') {
            str[i] = '\0';
            if (access(str, 0) != 0) {
                mkdir(str, 0777);
            }
            str[i] = '/';
        }
    }
    if (len > 0 && access(str, 0) != 0) {
        mkdir(str, 0777);
    }
    return;
}

//读取目录下面的文件
vector<string> readFiles(const string &dirPath) {
    struct dirent *ptr;
    DIR *dir;
    dir = opendir(dirPath.c_str());
    vector<string> files;
    while ((ptr = readdir(dir)) != NULL) {
        if (ptr->d_name[0] == '.')
            continue;
        files.push_back(ptr->d_name);
    }
    closedir(dir);
    return files;
}

//文件是否存在
bool isFileExists(const string &filepath) {
    fstream _file;
    _file.open(filepath, ios::in);
    if (!_file) {
        return false;
    } else {
        return true;
    }
}

void toLowerCase(string &s) {
    for (int i = 0; s[i] != '\0'; i++) {
        if (s[i] >= 'a' && s[i] <= 'z') {
            continue;
        } else if (s[i] >= 'A' && s[i] <= 'Z') {
            s[i] += 32;
        }
    }
}
