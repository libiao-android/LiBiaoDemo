package com.libiao.libiaodemo.jni.demo;

public class JNITest {

    static {
        System.loadLibrary("jnilib");
    }

    public native String getString();
}
