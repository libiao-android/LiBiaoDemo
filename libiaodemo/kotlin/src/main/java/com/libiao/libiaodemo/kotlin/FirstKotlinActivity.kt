package com.libiao.libiaodemo.kotlin

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.first_kotlin_main.*

/**
 * Description:
 * Data：2019/6/19-下午1:52
 * Author: libiao
 */
class FirstKotlinActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_kotlin_main)
        tv.text = "hello kotlin";
    }
}