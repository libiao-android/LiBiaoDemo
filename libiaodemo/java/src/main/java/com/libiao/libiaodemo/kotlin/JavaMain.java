package com.libiao.libiaodemo.kotlin;

import java.util.ArrayList;
import java.util.List;

public class JavaMain {

    public static final int in = 2;
    public static void main(String[] args) {
        KotlinMainKt.echo("libiao");
        Test1.INSTANCE.sayMessage("world");
        Test1.sayMessage(null);
    }

    public static String format(String s) {
        return s.isEmpty() ? null : s;
    }

    public static List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add(null);
        list.add("12");
        return list;
    }
}
