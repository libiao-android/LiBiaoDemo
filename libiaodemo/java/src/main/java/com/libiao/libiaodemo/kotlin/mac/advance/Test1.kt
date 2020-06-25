package com.libiao.libiaodemo.kotlin.mac.advance

import com.libiao.libiaodemo.kotlin.JavaMain

// 解构：将一个类拆解，分别赋值
// operator：将一个函数标记为重载一个操作符或者实现一个约定

fun main(args: Array<String>) {
    val user = User(18, "libiao")
    val (age, name) = user
    println(age)
    println(name)

    val map = mapOf<String, String>("key" to "key", "value" to "value")
    for((k, v) in map) {
        println("$k---$v")
    }
    val list = ArrayList<User?>()
    list.add(User(1, "a"))
    list.add(null)
    list.add(User(1, "a"))
    for(str in JavaMain.getList()) {
        println("str: ${str.length}")
    }


}

class User(var age: Int, var name: String) {
    operator fun component1() = age
    operator fun component2() = name
}