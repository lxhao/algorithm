#include <iostream>
#include <thread>
#include "id_lookup_threadsafe.h"
#include <stdio.h>
#include <strstream>
#include <vector>

using namespace std;


int main() {
    id_lookup table;

    vector<int> res1;
    res1.push_back(table.insert("10.0.0.1", "aa:aa:aa:aa:aa:aa", 1));// returns true;
    res1.push_back(table.insert("10.0.0.2", "bb:bb:bb:bb:bb:bb", 2)); // returns true;
    res1.push_back(table.insert("10.0.0.3", "cc:cc:cc:cc:cc:cc", 3));// returns true
    res1.push_back(table.insert("10.0.0.1", "ff:ff:ff:ff:ff:ff", 4));// returns false, does not insert because of conflic

    for (int i = 0; i < 4; ++i){
        cout << res1[i] << "; ";
    }
    cout << endl;
//    table.insert("10.0.0.1", "aa:aa:aa:aa:aa:aa", 1);  // returns true
//    table.insert("10.0.0.2", "bb:bb:bb:bb:bb:bb", 2);  // returns true
//    table.insert("10.0.0.3", "cc:cc:cc:cc:cc:cc", 3);  // returns true
//    table.insert("10.0.0.1", "ff:ff:ff:ff:ff:ff", 4);  // returns false, does not insert because of conflict

    uint16_t result = 0;
    vector<pair<bool, uint16_t>> res2;
    res2.push_back(make_pair(table.get_port_from_ip("10.0.0.1", result), result));
    res2.push_back(make_pair(table.get_port_from_ip("10.0.0.3", result), result));
    res2.push_back(make_pair(table.get_port_from_ip("0.0.0.0", result), result));
    for (int i = 0; i < 3; ++i){
        cout << res2[i].first << " " << res2[i].second << "; ";
    }
    cout << endl;
//    table.get_port_from_ip("10.0.0.1", result);  // returns true, sets result to 1
//    table.get_port_from_ip("10.0.0.3", result);  // returns true, sets result to 3
//    table.get_port_from_ip("0.0.0.0", result);  // returns false, sets result to 0

    string str_result;
    vector<pair<bool, string>> res3;
    res3.push_back(make_pair(table.get_ip_from_mac("bb:bb:bb:bb:bb:bb", str_result), str_result));
    res3.push_back(make_pair(table.get_ip_from_mac("ff:ff:ff:ff:ff:ff", str_result), str_result));
    res3.push_back(make_pair(table.get_mac_from_ip("10.0.0.1", str_result), str_result));
    res3.push_back(make_pair(table.get_mac_from_ip("255.255.255.255", str_result), str_result));
    for (int i = 0; i < 4; ++i){
        cout << res3[i].first << " " << res3[i].second << "; ";
    }
    cout << endl;
//    table.get_ip_from_mac("bb:bb:bb:bb:bb:bb", str_result);  // returns true, sets str_result to "10.0.0.2"
//    table.get_ip_from_mac("ff:ff:ff:ff:ff:ff", str_result);  // returns false, sets str_result to ""
//    table.get_mac_from_ip("10.0.0.1", str_result); // returns true, sets str_result to "aa:aa:aa:aa:aa:aa"
//    table.get_mac_from_ip("255.255.255.255", str_result); // returns false, sets str_result to ""

    return 0;
}

