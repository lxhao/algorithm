#include <iostream>
#include <thread>
#include "id_lookup.h"
#include <stdio.h>
#include <strstream>


using namespace std;


int main() {
    id_lookup table;
    table.insert("10.0.0.1", "aa:aa:aa:aa:aa:aa", 1);  // returns true
    table.insert("10.0.0.2", "bb:bb:bb:bb:bb:bb", 2);  // returns true
    table.insert("10.0.0.3", "cc:cc:cc:cc:cc:cc", 3);  // returns true
    table.insert("10.0.0.1", "ff:ff:ff:ff:ff:ff", 4);  // returns false, does not insert because of conflict

    uint16_t result = 0;
    table.get_port_from_ip("10.0.0.1", result);  // returns true, sets result to 1
    table.get_port_from_ip("10.0.0.3", result);  // returns true, sets result to 3
    table.get_port_from_ip("0.0.0.0", result);  // returns false, sets result to 0

    string str_result;
    table.get_ip_from_mac("bb:bb:bb:bb:bb:bb", str_result);  // returns true, sets str_result to "10.0.0.2"
    table.get_ip_from_mac("ff:ff:ff:ff:ff:ff", str_result);  // returns false, sets str_result to ""
    table.get_mac_from_ip("10.0.0.1", str_result); // returns true, sets str_result to "aa:aa:aa:aa:aa:aa"
    table.get_mac_from_ip("255.255.255.255", str_result); // returns false, sets str_result to ""

    return 0;
}

