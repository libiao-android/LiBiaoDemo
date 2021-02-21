package com.libiao.libiaodemo.huaweimianshi;

import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String value = in.nextLine();
        Map<Character, String> map = new TreeMap<>();
        for(int i = 0; i < value.length(); i++) {
            if(map.get(value.charAt(i)) == null) {
                map.put(value.charAt(i), String.valueOf(value.charAt(i)));
            } else {
                String a = map.get(value.charAt(i));
                map.put(value.charAt(i), a + value.charAt(i));
            }
        }

        StringBuilder builder = new StringBuilder();
        int len = value.length();
        for(int j = len; j > 0 ; j--) {
            builder.append(findMaxLen(map.values(), j));
        }
        System.out.println(builder.toString());
    }

    private static String findMaxLen(Collection<String> values, int len) {
        StringBuilder builder = new StringBuilder();
        for(String s : values) {
            if(s.length() == len) builder.append(s);
        }
        return builder.toString();
    }
}
