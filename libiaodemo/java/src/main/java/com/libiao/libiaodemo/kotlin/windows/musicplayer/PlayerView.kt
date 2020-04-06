package com.libiao.libiaodemo.kotlin.windows.musicplayer

import javax.swing.JOptionPane

interface PlayerView {
    fun showView()

    fun getPlayButton()
}

class GreenPlayerView : PlayerView {
    override fun showView() {
        JOptionPane.showConfirmDialog(null, "GREEN", "GREEN PLAYER", JOptionPane.DEFAULT_OPTION)
    }

    override fun getPlayButton() {
        println("Green button")
    }

}

class BluePlayerView : PlayerView {
    override fun showView() {
        JOptionPane.showConfirmDialog(null, "BLUE", "BLUE PLAYER", JOptionPane.DEFAULT_OPTION)
    }

    override fun getPlayButton() {
        println("Blue button")
    }

}

class MediaPlayerView(playerView: PlayerView) : PlayerView by playerView