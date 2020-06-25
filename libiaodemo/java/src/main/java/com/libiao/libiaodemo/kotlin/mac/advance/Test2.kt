package com.libiao.libiaodemo.kotlin.mac.advance

// Kotlin 的循环

fun main(args: Array<String>) {
    for(i in 1..10) {
        print("$i,") // 1,2,3,4,5,6,7,8,9,10,
    }

    println()
    for(i in 1 until 10) {
        print("$i,") // 1,2,3,4,5,6,7,8,9,
    }

    println()
    for(i in 10 downTo 1) {
        print("$i,") // 10,9,8,7,6,5,4,3,2,1,
    }

    println()
    for(i in 1..10 step 2) {
        print("$i,") // 1,3,5,7,9,
    }

    println()
    repeat(10) {
        print("$it,") // 0,1,2,3,4,5,6,7,8,9,
    }

    println()
    val list = arrayListOf<String>("a", "b", "c", "d")
    for(str in list) {
        print("$str,") // a,b,c,d,
    }
    println()
    for((index, str) in list.withIndex()) {
        println("第${index}个元素是$str")
    }
}