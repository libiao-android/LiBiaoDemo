package com.libiao.libiaodemo.kotlin

var a : String = "string"
var a2 : String? = "a"
val a3 = true
fun main() {
    var data : String? = null
    val live = data as String?
    //println("live = " + live)

    //a?.let { a+a3.toString() }

    //println(a2?.plus(a3))

    println(String.format("%.2s",34.166).toDouble())


//    println("hello world")
//    val items = listOf<Int>(1, 2, 3, 4, 5)
//    val value = items.fold("Elements:", { acc, i -> acc + " " + i })
//
//    val str = items.fold(0, { acc, i -> acc + i })
//    println(str)
//
//    val stringPlus: (String, String) -> String = String::plus
//    val intPlus: Int.(Int) -> Int = Int::plus
//
//    println(stringPlus.invoke("<-", "->"))
//    println(stringPlus("Hello, ", "world!"))
//
//    println(intPlus.invoke(1, 1))
//    println(intPlus(1, 2))
//    println(2.intPlus(3)) // 类扩展调用
//    println("a = $a")
//    val aa : String? = format()
//    println(aa?.length)
}

fun format() : String? {
    return null
}

fun foo() {
    run loop@{
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@loop // 从传入 run 的 lambda 表达式非局部返回
            print(it)
        }
    }
    print(" done with nested loop")
}

fun <T, R> Collection<T>.fold(
        initial: R,
        combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}