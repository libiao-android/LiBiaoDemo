package com.libiao.libiaodemo.matrix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.libiao.libiaodemo.matrix.iocanary.TestIOActivity;

/**
 * Description:
 * Data：2019/4/5-下午10:28
 * Author: libiao
 */
public class MatrixMainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_main);
    }

    public void IoCanary(View view) {
        Intent in = new Intent(this, TestIOActivity.class);
        startActivity(in);
    }
}
