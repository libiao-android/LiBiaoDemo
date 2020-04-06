package com.libiao.libiaodemo.kotlin;

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
}
