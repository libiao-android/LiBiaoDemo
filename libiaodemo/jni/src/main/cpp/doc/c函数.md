###1、fgets

从文件结构体指针stream中读取数据，每次读取一行。读取的数据保存在buf指向的字符数组中，每次最多读取bufsize-1个字符（第bufsize个字符赋'\0'），
如果文件中的该行，不足bufsize-1个字符，则读完该行就结束。如若该行（包括最后一个换行符）的字符数超过bufsize-1，则fgets只返回一个不完整的行，
但是，缓冲区总是以NULL字符结尾，对fgets的下一次调用会继续读该行。函数成功将返回buf，失败或读到文件结尾返回NULL。
因此我们不能直接通过fgets的返回值来判断函数是否是出错而终止的，应该借助feof函数或者ferror函数来判断。

###2、char *strchr(const char *str, int c)

该函数返回在字符串 str 中第一次出现字符 c 的位置，如果未找到该字符则返回 NULL。


###3、char *strstr(const char *haystack, const char *needle)

该函数返回在 haystack 中第一次出现 needle 字符串的位置，如果未找到则返回 null。

###4、unsigned long int strtoul(const char *str, char **endptr, int base)

该函数返回转换后的长整数，如果没有执行有效的转换，则返回一个零值。

str -- 要转换为无符号长整数的字符串

endptr -- 对类型为 char* 的对象的引用，其值由函数设置为 str 中数值后的下一个字符。

base -- 基数，必须介于 2 和 36（包含）之间，或者是特殊值 0。

###5、void *memset(void *s, int ch, size_t n);

将s中当前位置后面的n个字节 （typedef unsigned int size_t ）用 ch 替换并返回 s 。
作用是将某一块内存中的内容全部设置为指定的值， 这个函数通常为新申请的内存做初始化工作。