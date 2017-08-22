#include "InvertedIndex.h"

int InvertedIndex::StatWords(const string dir, const string filename)
{
    string fullPath = dir + "/" + filename;

    ifstream inFile;
    string line;
    inFile.open(fullPath.c_str());

    if (!inFile) {
        cout << "Error opening file" << fullPath << endl;
        return -1;
    }

    while (!inFile.eof()) {
        getline(inFile, line);
        int len = line.length();
        line[len - 1] = '\0'; /*去掉换行符*/
        char* cstr = new char[line.length() + 1];
        strcpy(cstr, line.c_str());
        ParseWordsByLine(cstr, filename);
    }
    inFile.close();
    return 0;
}

int InvertedIndex::ParseWordsByLine(char* str_line, const string filename)
{
    if (strlen(str_line) == 0) {
        return -1;
    }

    const char* needle = " \n";
    /*strtok在s中查找包含在delim中的字符并用NULL(‘\0’)来替换，直到找遍整个字符串。
      返回值：从s开头开始的一个个被分割的串。当没有被分割的串时则返回NULL。*/
    vector<char*> words;
    char* wordTmp = strtok(str_line, needle);
    while (wordTmp != NULL) {
        words.push_back(wordTmp);
        wordTmp = strtok(NULL, needle);
    }
    for (vector<char*>::const_iterator iter = words.begin(); iter != words.end(); iter++) {
        char* word = *iter;
        stringUtil.toLowerCase(word);
        if (morphstr(word, NOUN)) {
            word = morphstr(word, NOUN);
        }
        InsertWordToMap(word, filename);
    }
    return 0;
}

void InvertedIndex::InsertWordToMap(char* word, const string filename)
{
    if (strlen(word) == 0) {
        return;
    }
    string word_str = word;
    string file_name_str = filename;

    unordered_map<string, unordered_map<string, int> >::iterator it;
    it = result_index_.find(word_str);

    if (it == result_index_.end()) //not found
    {
        unordered_map<string, int> file_word_count;
        file_word_count.insert(pair<string, int>(file_name_str, 1));
        result_index_[word_str] = file_word_count;
    } else {
        unordered_map<string, int> file_word_count = result_index_[word_str];
        file_word_count[file_name_str]++;
        result_index_[word_str] = file_word_count;
    }
}
