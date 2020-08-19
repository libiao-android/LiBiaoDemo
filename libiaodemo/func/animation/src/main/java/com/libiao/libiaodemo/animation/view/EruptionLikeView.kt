package com.libiao.libiaodemo.animation.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.libiao.libiaodemo.animation.AnimElementPool
import com.libiao.libiaodemo.animation.AnimParamsProvide
import com.libiao.libiaodemo.animation.IAnimElement

class EruptionLikeView : FrameLayout {

    private var mPostLikeAnimElement : IAnimElement? = null
    private var mPostLikeTextElement : IAnimElement? = null
    private var mTextLayout : FrameLayout? = null
    private var mAnimLayout : FrameLayout? = null

    constructor(context: Context): this(context, null)

    constructor(@NonNull context: Context, @Nullable attrs: AttributeSet?): this(context, attrs, 0)

    constructor(@NonNull context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.i("libiao", "EruptionLikeView onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.i("libiao", "EruptionLikeView onDetachedFromWindow")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val pool = AnimElementPool(context)
        mAnimLayout = FrameLayout(context)
        mAnimLayout?.also {
            it.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            it.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    Log.i("libiao", "animLayout: ${it.width}, ${it.height}")
                    val provide = AnimParamsProvide(context)
                    mPostLikeAnimElement = pool.obtain(AnimElementPool.POST_LIKE_ANIM, provide, it)
                    it.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            })
            addView(it)
        }


        mTextLayout = FrameLayout(context)
        mTextLayout?.also {
            it.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            it.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    Log.i("libiao", "mTextLayout: ${it.width}, ${it.height}")
                    val provide = AnimParamsProvide(context)
                    mPostLikeTextElement = pool.obtain(AnimElementPool.POST_LIKE_TEXT, provide, it)
                    it.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            })
            addView(it)
        }
//        mTextLayout?.layoutParams = LayoutParams(this.width, this.height)
//        addView(mTextLayout)
        Log.i("libiao", "EruptionLikeView onFinishInflate")

    }

    fun showAnim(x: Int, y: Int, showText: Boolean, type: Int = 0) {
        Log.i("libiao", "showAnim")
        mPostLikeAnimElement?.onAnim(x, y, type)
        if(showText) {
            mPostLikeTextElement?.onAnim(x - 50, y - 200)
        }
    }

    fun stop() {
        mPostLikeAnimElement?.onDestroy()
        mPostLikeTextElement?.onDestroy()
    }
}