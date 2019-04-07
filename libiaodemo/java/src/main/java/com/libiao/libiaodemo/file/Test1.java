package com.libiao.libiaodemo.file;

import java.io.File;

public class Test1 {

    public static void main(String[] args) {
        File f = new File("");
        // /Users/libiao657/Documents/libiaodemo/libiaodemo,得到的是工程的目录
        System.out.println(f.getAbsolutePath());

        // /Users/libiao657/Documents/libiaodemo/libiaodemo，得到的是工程的目录
        System.out.println(System.getProperty("user.dir"));

        // /Users/libiao657/Documents/libiaodemo/libiaodemo/java/build/classes/main/
        //build 目录下？？
        System.out.println(Test1.class.getResource("/").getPath());
        System.out.println(System.currentTimeMillis());
    }
}
