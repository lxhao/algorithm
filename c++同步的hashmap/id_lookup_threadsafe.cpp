#include "id_lookup_threadsafe.h"
#include "HashMap.h"
id_lookup::id_lookup(){

}

id_lookup::~id_lookup(){

}


bool id_lookup::insert(const string& ip_address, const string& mac_address, uint16_t port) {
//    std::lock_guard<std::mutex> lock(g_i_mutex);

    auto res = ip_key_table.insert(ip_address, make_pair(mac_address, port));
    //return false if the key already exists
    if(!res) {
        return false;
    }
    mac_key_table.insert(mac_address, make_pair(ip_address, port));
    return true;
}

bool id_lookup::get_port_from_ip(const string& ip_address, uint16_t& result) {
    pair<string, uint16_t> tmp;
    if (ip_key_table.find(ip_address, tmp)){
        result = tmp.second;
        return true;
    }
    result = 0;
    return false;
}

bool id_lookup::get_port_from_mac(const string& mac_address, uint16_t& result) {
    pair<string, uint16_t> tmp;
    if (mac_key_table.find(mac_address, tmp)){
        result = tmp.second;
        return true;
    }
    result = 0;
    return false;
}

bool id_lookup::get_ip_from_mac(const string& mac_address, string& result) {
    pair<string, uint16_t> tmp;
    if (mac_key_table.find(mac_address, tmp)){
        result = tmp.first;
        return true;
    }
    result = "";
    return false;
}

//unsigned int id_lookup::size() {
//    return ip_key_table;
//}

bool id_lookup::get_mac_from_ip(const string& ip_address, string& result) {
    pair<string, uint16_t> tmp;
    if (ip_key_table.find(ip_address, tmp)){
        result = tmp.first;
        return true;
    }
    result = "";
    return false;
}
