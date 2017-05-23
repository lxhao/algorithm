#include "StringUtil.h"

void StringUtil::toLowerCase(char *s) {
    for(int i = 0; s[i] != '\0'; i++) {
        if(s[i] >= 'a' && s[i] <= 'z')
        {
            continue;
        }
        else if(s[i] >= 'A' && s[i] <= 'Z')
        {
            s[i] += 32;
        }
        else
        {
            s[i] = '\0';
            break;
        }
    }
}
