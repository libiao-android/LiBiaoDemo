package com.libiao.libiaodemo.kotlin.mac.advance

//作用域函数

fun main(args: Array<String>) {
    val user = UserTest("libiao")

    //let和run都会返回闭包的执行结果，let有闭包参数，run没有闭包参数

    val letResult = user.let {
        it.name
    }
    println("let: $letResult")

    val runResult = user.run {
        this.name
    }
    println("run: $runResult")

    //also 和 apply不会返回闭包的执行结果，返回执行对象，可链式调用。区别在于also有闭包参数，apply没有闭包参数

    user.also {
        println("also: ${it.name}")
    }.name = "yuanting"
    println("also: ${user.name}")

    user.apply {
        println("apply: ${this.name}")
    }.name = "libiao"
    println("apply: ${user.name}")

    //takeIf的闭包返回一个判断结果，为false时，takeIf返回空，ture返回执行对象.takeUnless相反

    user.takeIf {
        it.name.isNotEmpty()
    }?.also {
        println("takeIf: ${it.name}")
    }?: println("name is null")

    user.takeUnless {
        it.name.isNotEmpty()
    }?.also {
        println("takeIf: ${it.name}")
    }?: println("name is null")

    //repeat 重复执行n次
    repeat(5) {
        println(it)
    }

    //with，不是以扩展函数存在，而是一个顶级函数
    with(user) {
        this.name = ""
    }

}


data class UserTest(var name: String)