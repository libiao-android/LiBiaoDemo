package com.libiao.libiaodemo.kotlin.mac.advance

// Kotlin 集合操作符

fun main(args: Array<String>) {
    val a = arrayOf("4", "0", "7", "i", "f", "w", "0", "9")
    val index = arrayOf(5,3,9,4,8,3,1,9,2,1,7)

    index.filter {
        it < a.size
            }
            .map {
                a[it]
            }
            .reduce { acc, s ->  "$acc$s"}
            .also {
                println("密码是$it")
            }

    myOperator()
}

fun myOperator() {
    val list: List<Int> = listOf(1,2,3,4,5)
    list.convert {
        "A"
    }.forEach { println(it) }
}


inline fun <T, E> Iterable<T>.convert(action: (T) -> E): MutableList<E> {
    val list: MutableList<E> = mutableListOf()
    for(item in this) list.add(action(item))
    return list
}