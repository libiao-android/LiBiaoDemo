package com.libiao.libiaodemo.kotlin.windows.coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//通过提升CPU利用率，减少线程切换，进而提升程序运行效率

// 可控制性：启动和停止由代码控制，而非操作系统
// 轻量级：占用资源比线程还小
// 语法糖：多任务多线程切换不再使用回调语法

fun main(args: Array<String>) = runBlocking {
    val job = launch {
        repeat(1000) {
            println("gua qi zhong... $it - ${Thread.currentThread().id}")
            delay(500)
        }
    }
    println("1111111111")
    val job2 = async {
        delay(500)
        return@async "hello"
    }
    println("2222222222")
    println("job2 = ${job2.await()}")
    println("333333333")
    delay(1300)
    job.cancel()
    println("tui chu- ${Thread.currentThread().id}")
}