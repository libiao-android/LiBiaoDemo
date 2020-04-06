package com.libiao.libiaodemo.kotlin.windows.foundation

import com.libiao.libiaodemo.kotlin.JavaMain

fun main() {
    //kotlin 中可以直接调用其它kotlin文件的方法
    test1("libiao")

    //匿名内部类
    Test2.test2("libiao")

    //空安全
    var str1 = JavaMain.format("")
    println(str1?.length)
    //val str2: String = JavaMain.format("")
    val str3: String? = JavaMain.format("")
    println(str3?.length)

    //kotlin不支持如下字符输出格式
    val age = 18
    val name = "libiao"
    //println("我叫%d, 今年%d", name, age)

    //val thread = Thread({ -> println("one")})
    //val thread = Thread({ println("one")}) //如果lambda没有参数可省略箭头符号
    //val thread = Thread(){ println("one")} //如果lambda是函数的最后一个参数，可以将大括号放到小括号外面
    val thread = Thread{ println("one")} //如果函数只有一个参数且是lambda，则可以省略小括号
    thread.start()


    //lambda闭包声明 lambda参数上限22
    val echo = {name: String -> println(name)}
    echo.invoke("libiao")
    echo("libiao")


    onlyIf(true) {
        println("gao jie han shu")
    }

    val run = Runnable { println("runnable") }
    val function : () -> Unit
    function = run::run
    onlyIf(true, function)
}

inline fun onlyIf(debug: Boolean, printLog: ()->Unit) { //inline修饰高阶函数，拆解函数调用为语句调用
    if(debug) printLog()
}