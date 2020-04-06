package com.libiao.libiaodemo.kotlin.windows.musicplayer

class PlayerUI private constructor() {
    companion object {
        fun get(): PlayerUI {
            return Holder.instance
        }
    }

    private object Holder{
        var instance = PlayerUI()
    }

    fun showPlayer(user: User) {
        MediaPlayerView(getPlayerView(user.playerType)).showView()
    }
}