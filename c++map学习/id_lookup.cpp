#include "id_lookup.h"

id_lookup::id_lookup(){

}

id_lookup::~id_lookup(){

}


bool id_lookup::insert(const string& ip_address, const string& mac_address, uint16_t port) {
    std::lock_guard<std::mutex> lock(g_i_mutex);
    auto res = ip_key_table.insert(make_pair(ip_address, make_pair(mac_address, port)));
    //return false if the key already exists
    if(!res.second) {
        return false;
    }
    mac_key_table.insert(make_pair(mac_address, make_pair(ip_address, port)));
    return true;
}

bool id_lookup::get_port_from_ip(const string& ip_address, uint16_t& result) {
    auto it = ip_key_table.find(ip_address);
    if(it == ip_key_table.end()) {
        return false;
    }
    result = it->second.second;
    return true;
}

bool id_lookup::get_port_from_mac(const string& mac_address, uint16_t& result) {
    auto it = mac_key_table.find(mac_address);
    if(it == mac_key_table.end()) {
        return false;
    }
    result = it->second.second;
    return true;
}

bool id_lookup::get_ip_from_mac(const string& mac_address, string& result) {
    auto it = mac_key_table.find(mac_address);
    if(it == mac_key_table.end()) {
        result = "";
        return false;
    }
    result = it->second.first;
    return true;
}

unsigned int id_lookup::size() {
    return ip_key_table.size();
}

bool id_lookup::get_mac_from_ip(const string& ip_address, string& result) {
    auto it = ip_key_table.find(ip_address);
    if(it == ip_key_table.end()) {
        result = "";
        return false;
    }
    result = it->second.first;
    return true;
}
