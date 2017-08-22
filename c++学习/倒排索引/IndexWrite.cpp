#include "IndexWrite.h"

unordered_map<string, unordered_map<string, int> > IndexWrite::readMap(vector<unsigned char> out_data)
{
    msgpack::zone zone_;
    msgpack::object obj1 = msgpack::unpack(zone_, (const char*)&out_data[0], out_data.size(), 0);
    unordered_map<string, unordered_map<string, int> > indexMap = obj1.as<unordered_map<string, unordered_map<string, int> > >();
    return indexMap;
}

void IndexWrite::writeMap(string filename, unordered_map<string, unordered_map<string, int> >& indexMap)
{
    stringstream ss;
    msgpack::pack(ss, indexMap);
    ss.seekg(0);
    FILE* f = fopen(filename.c_str(), "wb+");
    fwrite(ss.str().data(), sizeof(&ss.str().data()[0]), ss.str().size(), f);
    fflush(f);
    fclose(f);
}

bool IndexWrite::file_get_contents(const string& filename, vector<unsigned char>& buffer)
{
    ifstream file(filename.c_str(), ios_base::binary);

    if (file) {
        file.seekg(0, std::ios_base::end);
        std::streamsize size = file.tellg();

        if (size > 0) {
            file.seekg(0, std::ios_base::beg);
            buffer.resize(static_cast<std::size_t>(size));
            file.read((char*)&buffer[0], size);
        }
        return true;
    } else {
        return false;
    }
}
