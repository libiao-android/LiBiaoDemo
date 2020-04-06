package com.libiao.libiaodemo.kotlin.windows.musicplayer

import javax.swing.JOptionPane

val TITLE  = "TITLE"
val MESSAGE = "MESSAGE"
class VIPPlayer(var title: String?, var message: String?) : PlayerView {

    init {
        println(" main constructor")
        title = title ?: TITLE
        message = message ?: MESSAGE
    }

    constructor() : this(TITLE, MESSAGE) {

    }

    constructor(message: String?) : this(TITLE, message) {

    }

    override fun showView() {
        JOptionPane.showConfirmDialog(null, message, title, JOptionPane.DEFAULT_OPTION)
    }

    override fun getPlayButton() {
        println("VIP button")
    }
}