package com.libiao.libiaodemo.animation.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewParent
import android.widget.RelativeLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable

class FuncLayout : RelativeLayout {

    companion object {
        private const val TAG = "FuncLayout"
    }

    private var mWidth = -1
    private var mLeft = 0
    private var mRight = 0

    constructor(context: Context): this(context, null)

    constructor(@NonNull context: Context, @Nullable attrs: AttributeSet?): this(context, attrs, 0)

    constructor(@NonNull context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //i(TAG, "onMeasure: $widthMeasureSpec, $heightMeasureSpec")
    }
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.i("libiao", "onLayout: $changed, $l, $t, $r, $b")
        super.onLayout(changed, l, t, r, b)
        if(mWidth == -1) {
            mWidth = r - l
            mLeft = l
            mRight = r
        } else {
            left = mLeft
            right = mRight
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //i(TAG, "onDraw")
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        //i(TAG, "draw")
    }

    override fun invalidate() {
        //i(TAG, "invalidate")
        super.invalidate()
    }

    override fun requestLayout() {
        //i(TAG, "requestLayout")
        super.requestLayout()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun onMove(x: Int) {
        if(left + x < 0 || left + x > mWidth) { return }

        Log.i("libiao", "left: $left")
        Log.i("libiao", "right: $right")

        left += x
        right += x
        //scrollTo(x, 0)
    }

    fun home(xVelocity: Float) {
        if(left in 1 until mWidth) {
//            Log.i("libiao", "home left: $left")
//            Log.i("libiao", "home right: $right")
//            Log.i("libiao", "home width: $width")
//            Log.i("libiao", "home mWidth: $mWidth")
            Log.i("libiao", "home xVelocity: $xVelocity")
            var begin: Int
            var end: Int
            if(xVelocity > 3000) {
                //往右
                begin = left
                end = mWidth
            } else if(xVelocity < -3000) {
                //往左
                begin = left
                end = 0
            } else {
                if(left > mWidth/2) {
                    begin = left
                    end = mWidth
                } else {
                    begin = left
                    end = 0
                }
            }

            val animator = ValueAnimator.ofInt(begin, end)
            animator.addUpdateListener {
                val value = it.animatedValue as Int
                //Log.i("libiao", "home value: $value")
                left = value
                right = mWidth + left
            }
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    mLeft = left
                    mRight = right

                    Log.i("libiao", "home anim end  mLeft: $mLeft, mRight: $mRight")
                }
            })
            var duration = Math.abs(end - begin) * 300 / mWidth
            Log.i("libiao", "duration: $duration")
            if(duration < 50) {
                duration = 50
            }
            animator.duration = duration.toLong()
            animator.start()
        }
    }
}
