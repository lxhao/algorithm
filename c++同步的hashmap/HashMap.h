#ifndef HASH_MAP_H_
#define HASH_MAP_H_

#include <cstdint>
#include <iostream>
#include <functional>
#include <mutex>
#include "HashNode.h"

constexpr size_t HASH_SIZE_DEFAULT = 1031; // A prime number as hash size gives a better distribution of values in buckets
namespace CTSL //Concurrent Thread Safe Library
{
template <typename K, typename V, typename F = std::hash<K> >
    class HashMap {
    public:
        HashMap(size_t hashSize_ = HASH_SIZE_DEFAULT) : hashSize(hashSize_) {
            hashTable = new HashBucket<K, V>[hashSize]; //create the hash table as an array of hash buckets
        }

        ~HashMap() {
            delete [] hashTable;
        }
        //Copy and Move of the HashMap are not supported at this moment
        HashMap(const HashMap&) = delete;
        HashMap(HashMap&&) = delete;
        HashMap& operator=(const HashMap&) = delete;
        HashMap& operator=(HashMap&&) = delete;

        //Function to find an entry in the hash map matching the key.
        //If key is found, the corresponding value is copied into the parameter "value" and function returns true.
        //If key is not found, function returns false.
        bool find(const K &key, V & value) const {
            size_t hashValue = hashFn(key) % hashSize ;
            return hashTable[hashValue].find(key, value);
        }

        //Function to insert into the hash map.
        //If key already exists, update the value, else insert a new node in the bucket with the <key, value> pair.
        bool insert(const K &key, const V &value) {
            size_t hashValue = hashFn(key) % hashSize ;
            if (hashTable[hashValue].insert(key, value)){
                return true;
            }
            return false;
        }

        //Function to remove an entry from the bucket, if found
        void erase(const K &key) {
            size_t hashValue = hashFn(key) % hashSize ;
            hashTable[hashValue].erase(key);
        }

        //Function to clean up the hasp map, i.e., remove all entries from it
        void clear() {
            for(size_t i = 0; i < hashSize; i++)
            {
                (hashTable[i]).clear();
            }
        }

    private:
        HashBucket<K, V> * hashTable;
        F hashFn;
        const size_t hashSize;
    };
}
#endif

