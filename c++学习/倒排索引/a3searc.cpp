#include <iostream>
#include "InvertedIndex.h"
#include <vector>
#include <dirent.h>
#include <utility>
#include <functional>
#include <algorithm>
#include "msgpack.hpp"
#include "IndexWrite.h"
#include <unordered_map>


void printRes(unordered_map<string, unordered_map<string,int> > indextable );
void readInput(int argc, char* argv[]);
vector<string> readFiles(const string dirPath);
void showVec(const vector<char*>& valList);
void buildIndex(const string dirPath, const vector<string>& files, InvertedIndex &invertedIndex);
vector<pair<string, float> > seachItemsCount(unordered_map<string, unordered_map<string,int> > indexMap,const vector<string> seachItems,const vector<string> files);
void printResult(vector<pair<string, float> > &res);
bool less_second(const pair<string, float> &m1, const pair<string,float> & m2);


using namespace std;

//input: 输入文件路径,缓存文件路径,要查询的单词(多个)
//根据输入文件建立倒排索引
//找出每个单词在每个文件的出现次数,求出平均值,把文章和次数加入到有序容器中
//output: 输出结果
int main(int argc, char* argv[])
{
    if(argc < 4) {
        cout << "参数输入错误" << endl;
        return -1;
    }

    string fileDir = argv[1];
    string indexFile = argv[2];
    vector<string> seachItems;

    StringUtil stringUtil;
    for(int i = 3; i < argc; i++)
    {
        stringUtil.toLowerCase(argv[i]);
        seachItems.push_back(argv[i]);
    }

   unordered_map<string, unordered_map<string,int> > indexMap;
    vector<unsigned char> out_data;
    IndexWrite indexWriter;
    vector<string> files = readFiles(fileDir);
    //缓存文件存在
    if(indexWriter.file_get_contents(indexFile, out_data)) {
        cout << "缓存文件存在" << endl;
        indexMap = indexWriter.readMap(out_data);
    } else {
        InvertedIndex invertedIndex;
        buildIndex(fileDir, files, invertedIndex);
        indexMap = invertedIndex.result_index();
        indexWriter.writeMap(indexFile, indexMap);
    }
    vector<pair<string, float> > itemsCount =  seachItemsCount(indexMap, seachItems, files);
    printResult(itemsCount);
    return 0;
}

//key 为文件名, value为出现的次数
//参数为索引表和查询条件,文件
vector<pair<string, float> > seachItemsCount(unordered_map<string, unordered_map<string,int> > indexMap,const vector<string> seachItems,const vector<string> files) {

    vector< pair<string, float> > res;
    int filesSize = files.size();
    int itemsSize = seachItems.size();
    int count;
    int totalCount;
    for(int fileIndex = 0; fileIndex < filesSize; fileIndex++) {
        totalCount = 0;
        for(int itemIndex = 0; itemIndex < itemsSize; itemIndex++) {
            count = indexMap[seachItems[itemIndex]][files[fileIndex]];
            if(count == 0) {
                break;
            }
            totalCount += count;
            if(itemIndex == itemsSize - 1) {
                res.push_back(make_pair(files[fileIndex], totalCount * 1.0 / itemsSize));
            }
        }
    }
    return res;
}

//根据目录建立索引
void buildIndex(const string dirPath, const vector<string>& files, InvertedIndex &invertedIndex)
{
    int count = files.size();
    for (int i = 0; i < count;i++)
    {
        invertedIndex.StatWords(dirPath, files[i]);
    }
}

bool less_second(const pair<string, float> &m1, const pair<string,float> &m2) {
    if(m1.second != m2.second) {
        return m1.second > m2.second;
    }
    return m1.first < m2.first;
}

//输出结果
void printResult(vector<pair<string, float> > &res)
{
    sort(res.begin(), res.end(), less_second);
    int count = res.size();
    for (int i = 0; i < count;i++)
    {
        cout << res[i].first << endl;
    }
}

//打印容器的值 调试用
void showVec(const vector<char*>& valList)
{
    int count = valList.size();
    for (int i = 0; i < count;i++)
    {
        cout << valList.at(i) << endl;
    }
}

//读取目录下面的文件
vector<string> readFiles(const string dirPath)
{
    struct dirent *ptr;
    DIR *dir;
    dir = opendir(dirPath.c_str());
    vector<string> files;
    while((ptr = readdir(dir))!=NULL)
    {
        if(ptr->d_name[0] == '.')
            continue;
        files.push_back(ptr->d_name);
    }
    closedir(dir);
    return files;
}


void readInput(int argc, char* argv[])
{

}

//打印索引表的key值,调试用
void printRes(unordered_map<string, unordered_map<string,int> > indextable )
{
    unordered_map<string, unordered_map<string, int> >::iterator map_it;
    map_it = indextable.begin();

    //遍历整个索引表输出，因为MAP的键值是严格弱排序，因此输出是字典序
    while(map_it != indextable.end())
    {
        string word = map_it -> first;
        cout << word << endl;
        map_it++;
    }
}



