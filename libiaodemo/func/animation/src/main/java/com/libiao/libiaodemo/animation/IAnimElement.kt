package com.libiao.libiaodemo.animation

interface IAnimElement {
    fun onAnim(startX: Int, startY: Int, type: Int = 0)
    fun onDestroy()
}