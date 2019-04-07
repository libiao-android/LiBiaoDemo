package com.libiao.libiaodemo.jni;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.libiao.libiaodemo.jni.demo.JNITest;

public class TestJniActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jni_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        testJni();
    }

    private void testJni() {
        JNITest test = new JNITest();
        String s = test.getString();
        Log.i("libiao", "s = " + s);
    }
}
