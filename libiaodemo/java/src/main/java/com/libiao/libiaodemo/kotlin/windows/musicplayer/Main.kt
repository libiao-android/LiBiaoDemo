package com.libiao.libiaodemo.kotlin.windows.musicplayer

fun main(args: Array<String>) {
    val user = User(1, "name", PlayerViewType.VIP())
    PlayerUI.get().showPlayer(user)
}