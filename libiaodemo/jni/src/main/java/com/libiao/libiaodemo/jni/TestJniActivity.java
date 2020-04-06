package com.libiao.libiaodemo.jni;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.libiao.libiaodemo.jni.demo.JNITest;

public class TestJniActivity extends Activity {

    private TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jni_main);
        tv = findViewById(R.id.tv);
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
        tv.setText(s);
    }

    public void format(View v) {
        JNITest test = new JNITest();
        String s = test.avformatInfo();
        Log.i("libiao", "avformatInfo = " + s);
        tv.setText(s);
    }
    public void codec(View v) {
        JNITest test = new JNITest();
        String s = test.avcodecInfo();
        Log.i("libiao", "avcodecInfo = " + s);
        tv.setText(s);
    }
    public void filter(View v) {
        JNITest test = new JNITest();
        String s = test.avfilterInfo();
        Log.i("libiao", "avfilterInfo = " + s);
        tv.setText(s);
    }
    public void config(View v) {
        JNITest test = new JNITest();
        String s = test.configurationInfo();
        Log.i("libiao", "configurationInfo = " + s);
        tv.setText(s);
    }

    private boolean start = false;
    public void run(View v) {
        final JNITest test = new JNITest();
        if(start) return;
        start = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String base = Environment.getExternalStorageDirectory().getPath();
                Log.i("libiao", base);
                String[] commands = new String[9];
                commands[0] = "ffmpeg";
                commands[1] = "-i";
                commands[2] = base + "/input.mp4";
                commands[3] = "-i";
                commands[4] = base + "/input.mp3";
                commands[5] = "-strict";
                commands[6] = "-2";
                commands[7] = "-y";
                commands[8] = base + "/merge.mp4";
                int result = test.run(commands);
                start = false;
                Log.i("libiao", "result = " + result + ", progress = " + test.getProgress());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(start) {
                        final int progress = test.getProgress();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText("progress = " + progress);
                            }
                        });
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void addWater(View v) {
        final JNITest test = new JNITest();
        String base = Environment.getExternalStorageDirectory().getPath();
        String srcFile = base + "/input.mp4";
        //String waterMark = "android:resource://" + getPackageName() + "/" + R.mipmap.watermark;
        String waterMark = base + "/watermark.png";
        Log.i("libiao", "waterMark = " + waterMark);
        String targetFile = base + "/merge_water.mp4";
        test.addWaterMark(srcFile, waterMark, targetFile);
    }

    public void concat(View v) {
        final JNITest test = new JNITest();
        String base = Environment.getExternalStorageDirectory().getPath();
        String srcFile = base + "/file.txt";
        String targetFile = base + "/input_concat.mp4";
        test.concat(srcFile, targetFile);
    }

    public void info(View v) {
        final JNITest test = new JNITest();
        String base = Environment.getExternalStorageDirectory().getPath();
        String srcFile = base + "/input.mp4";
        test.getVideoInfo(srcFile);
    }
}
