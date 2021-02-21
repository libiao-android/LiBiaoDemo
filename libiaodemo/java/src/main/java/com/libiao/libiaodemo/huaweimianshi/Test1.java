package com.libiao.libiaodemo.huaweimianshi;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test1 {

    //给一个字符串数组words，给一个字符串chars，返回words中能由chars中存在的字符组成的所有字符串长度。例如：
    //输入：words=[“abc”,”bcd”,”cde”,”bb”], chars=”abcd”
    //输出：6
    //满足条件的有”abc”,”bcd”,一共长度是6.返回6.”bb”中存在两个b，chars中只有一个b，所以不满足。

    public static void main(String[] args) {
        String[] values = {"abc", "bcd", "ad", "bb"};
        String str = "abcd";
        int length = getLength(values, str);
        System.out.println("length: " + length);
    }

    private static int getLength(String[] values, String str) {
        if(values == null || str == null) {return -1;}
        int length = 0;
        for(int i = 0; i < values.length; i++) {
            String value = values[i];
            if(isInString(value, str)) {
                System.out.println(value);
                length += value.length();
            }
        }
        return length;
    }

    private static boolean isInString(String value, String str) {
        if(value == null || str == null) {return false;}
        char char1[] = value.toCharArray(); // abc
        char char2[] = str.toCharArray(); // abcd
        for(int i = 0; i< char1.length; i++) {
            char tmp = char1[i]; //a
            boolean isExist = false;
            for(int j = 0; j< char2.length; j++) {
                if(tmp == char2[j]) {
                    char2[j] = ' ';
                    isExist = true;
                    break ;
                }
            }
            if(isExist) {
                continue ;
            } else {
                return false;
            }
        }
        return true;
    }
}
