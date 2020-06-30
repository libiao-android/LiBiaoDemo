package com.libiao.libiaodemo.animation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sum.slike.BitmapProvider
import com.sum.slike.SuperLikeLayout

class AnimationActivity : AppCompatActivity() {

    private var mSuperLikeLayout: SuperLikeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animation_main)
        mSuperLikeLayout = findViewById(R.id.super_like_layout)
    }

    fun like(v: View) {
        val provider: BitmapProvider.Provider = BitmapProvider.Builder(this).build()
        mSuperLikeLayout?.provider = provider
        mSuperLikeLayout?.launch(100, 100)
    }
}