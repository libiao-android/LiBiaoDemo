package com.libiao.libiaodemo.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Service
import android.os.*
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.libiao.libiaodemo.animation.view.EruptionLikeView
import com.libiao.libiaodemo.animation.view.FuncLayout
import com.sum.slike.BitmapProvider
import com.sum.slike.SuperLikeLayout


class AnimationActivity : AppCompatActivity() {

    private var mSuperLikeLayout: SuperLikeLayout? = null

    private var mReceiveLikeView: FrameLayout? = null

    private var mEruptionLikeView : EruptionLikeView? = null

    private var mLottieAnimationView: LottieAnimationView? = null

    private var rotationView: View? = null

    private var funcView: FuncLayout? = null

    private val mHandler = Handler()
    private var mVelocityTracker: VelocityTracker? = null

    private var vibrator: Vibrator? = null

    private fun initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        } else {
            mVelocityTracker?.clear()
        }
    }

    private fun initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
    }

    private fun recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker?.recycle()
            mVelocityTracker = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animation_main)
        mSuperLikeLayout = findViewById(R.id.super_like_layout)
        mReceiveLikeView = findViewById(R.id.receive_like_view)

        mEruptionLikeView = findViewById(R.id.eruption_like_view)

        mLottieAnimationView = findViewById(R.id.lottie_like)

        mLottieAnimationView?.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                Log.i("libiao", "onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.i("libiao", "onAnimationEnd")
            }
        })

        rotationView = findViewById(R.id.rotation)
        funcView = findViewById(R.id.func)

        vibrator = this.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
    }

    fun like(v: View) {


        val provider: BitmapProvider.Provider = BitmapProvider.Builder(this).build()
        mSuperLikeLayout?.provider = provider
        mSuperLikeLayout?.launch(200, 500)

        rotationView?.also {
            it.pivotX = 225f
            it.pivotY = 75f
            val animator = ObjectAnimator.ofFloat(it, "rotation", 0f, -45f, 0f, 45f, 0f, -45f, 0f, 45f, 0f)
            animator.duration = 1000
            animator.start()
        }

        funcView?.onMove(10)
    }

    fun receiveLike(v: View) {

        funcView?.onMove(-10)

//        mReceiveLikeView?.also { viewGroup ->
//            val elementPool = AnimElementPool(this)
//            val provide = AnimParamsProvide(this)
//            val element = elementPool.obtain(AnimElementPool.RECEIVE_LIKE_ANIM, provide, viewGroup)
//            val startX = viewGroup.width / 2 - 65
//            val startY = viewGroup.height
//            element?.also { e ->
//                Thread {
//                    for(i in 1 .. 100) {
//                        mHandler.post {
//                            e.onAnim(startX, startY)
//                        }
//                        Thread.sleep(100)
//                    }
//                }.start()
//
//            }
//        }
    }

    fun postLike(v: View) {
        Thread{
            for(i in 1 .. 100) {
                mHandler.post {
                    mEruptionLikeView?.showAnim(200, 1000, false)
                }
                Thread.sleep(80)

            }
        }.start()

       // mLottieAnimationView?.setMinFrame(80)
       // mLottieAnimationView?.playAnimation()


    }


    var lastTime = 0L
    var longPress = false
    var downX = 0F
    var downY = 0F
    var lastMove = 0F
    override fun onTouchEvent(event: MotionEvent): Boolean {
       // Log.i("libiao", "event?.action = ${event.action}")
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y

                mHandler.postDelayed(longPressRun, 200)

                initOrResetVelocityTracker()
                mVelocityTracker?.addMovement(event)
            }

            MotionEvent.ACTION_MOVE -> {
                initVelocityTrackerIfNotExists()
                mVelocityTracker?.addMovement(event)
                if(Math.abs(event.x - downX) > 50 || Math.abs(event.y - downY) > 50) {
                    mHandler.removeCallbacks(longPressRun)
                    if(longPress) {
                        //longPress = false
                        //mEruptionLikeView?.stop()
                    } else {
                        if(lastMove != 0F) {
                            funcView?.onMove((event.x - lastMove).toInt())
                        }
                        lastMove = event.x
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                mHandler.removeCallbacks(longPressRun)

                val now  = System.currentTimeMillis()
                if(now - lastTime < 200) {
                    mHandler.removeCallbacks(oneClick)
                    doubleClick(event.x, event.y)
                } else {
                    if(!longPress) {
                        mHandler.postDelayed(oneClick, 200)
                    }
                    longPress = false
                }
                lastTime = now

                lastMove = 0F

                mVelocityTracker?.computeCurrentVelocity(1000)
                val xVelocity = mVelocityTracker?.xVelocity
                funcView?.home(xVelocity ?: 0F)
            }
        }

        return super.onTouchEvent(event)
    }

    val longPressRun =  Runnable {
        Log.i("libiao", "longPress")
        longPress = true

        Thread{
            while(longPress) {
                mHandler.post {
                    mEruptionLikeView?.showAnim(downX.toInt(), downY.toInt() - 300, true)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator?.vibrate(VibrationEffect.createOneShot(50, 1))
                    } else {
                        //vibrator?.vibrate(200)
                    }
                }
                Thread.sleep(240)
            }
            mHandler.post {
                mEruptionLikeView?.stop()
            }
        }.start()
    }

    val oneClick =  Runnable {
        Log.i("libiao", "oneClick")
    }

    fun doubleClick(x: Float, y: Float) {
        Log.i("libiao", "doubleClick: $x, $y")
        Log.i("libiao", "doubleClick: ${mEruptionLikeView?.x}, ${mEruptionLikeView?.y}")

//        for(i in 0 .. 4) {
//
//        }
        mEruptionLikeView?.showAnim(x.toInt() - 100, y.toInt() - 300, false, 2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(50, 1))
        } else {
            //vibrator?.vibrate(200)
        }
    }
}