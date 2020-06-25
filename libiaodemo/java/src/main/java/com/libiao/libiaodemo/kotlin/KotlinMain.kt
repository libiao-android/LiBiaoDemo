package com.libiao.libiaodemo.kotlin

import kotlin.reflect.KClass

fun echo(name : String) {
    println("$name")
    Test1.sayMessage("hello")
    testClass(JavaMain::class.java)
    //testClass(Test1::class)
    JavaMain.`in`
}

object Test1 {
    @JvmStatic
    fun sayMessage(msg : String?) {
        println("$msg")
    }
}

fun testClass(clazz: Class<JavaMain>) {
    println(clazz.simpleName)
}

fun testClass(clazz: KClass<Test1>) {
    println(clazz.simpleName)
}

fun main() {
    val age = 18
    val name = "libiao"
    val r = (Math.random() * 8999).toInt() + 1000
    println(r)
    //test1(null)
}

fun test1(url : String?) {
    print(url?:"111")
}