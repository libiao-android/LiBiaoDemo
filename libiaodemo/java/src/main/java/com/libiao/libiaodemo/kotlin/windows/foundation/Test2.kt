package com.libiao.libiaodemo.kotlin.windows.foundation

//编译成了匿名内部类，且是单例对象

object Test2 {
    //@JvmStatic
    fun test2(str: String) {
        println("Test2: $str")
    }
}