#ifndef ID_LOOKUP_THREADSAFE_H
#define ID_LOOKUP_THREADSAFE_H

#include <string>
#include <mutex>
#include <thread>
#include <stdio.h>
#include "HashMap.h"

using namespace std;

class id_lookup {
    private :
        // key: ip_address value: mac_address and port
        CTSL::HashMap<string, pair<string, uint16_t> > ip_key_table;
        // key: mac_address value: ip_address and port
        CTSL::HashMap<string, pair<string, unsigned short> > mac_key_table;
//        std::mutex g_i_mutex;

    public :
        id_lookup();
        ~id_lookup();
        unsigned int size();
        bool insert(const string& ip_address, const string& mac_address, uint16_t port);
        bool get_port_from_ip(const string& ip_address, uint16_t& result);
        bool get_port_from_mac(const string& mac_address, uint16_t& result);
        bool get_ip_from_mac(const string& mac_address, string& result);
        bool get_mac_from_ip(const string& ip_address, string& result);

};
#endif // ID_LOOKUP_THREADSAFEH
