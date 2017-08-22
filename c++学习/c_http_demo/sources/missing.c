/* a crippled implementation of memmove for systems that lack it */
/* this does not handle overlapping strings neither returns the original */
/* but we don't need it */
memmove (dst, src, len)
char *dst;
char *src;
unsigned int len;
{
    unsigned int i;
    for (i = 0; i < len; i++)
	dst[i] = src[i];
    return 0;
}
