package com.airbnb.lottie.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.R;

/**
 * Description:
 * Data：2019/4/22-下午7:52
 * Author: libiao
 */
public class LottieTestActivity extends Activity implements View.OnClickListener {

    private LottieAnimationView success;
    private LottieAnimationView fail;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lottie_main);
        initView();
    }

    private void initView() {
        success = (LottieAnimationView) findViewById(R.id.lottie_animation_view_success);
        fail = (LottieAnimationView) findViewById(R.id.lottie_animation_view_fail);

        success.setOnClickListener(this);
        fail.setOnClickListener(this);

        success.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i("libiao", "success min = " + success.getMinFrame());
                Log.i("libiao", "success max = " + success.getMaxFrame());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mHandler.postDelayed(mSuccessLottie, 1500);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        fail.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mHandler.postDelayed(mFailLottie, 1500);

            }

            @Override
            public void onAnimationStart(Animator animation) {
                //fail.setMinAndMaxFrame(96, 96 + 15);
                Log.i("libiao", "fail min = " + fail.getMinFrame());
                Log.i("libiao", "fail max = " + fail.getMaxFrame());
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.lottie_animation_view_fail) {
            fail.playAnimation();
        } else if (i == R.id.lottie_animation_view_success) {
            mHandler.post(mSuccessLottie);
        }
    }

    int state = 0;

    private Runnable mSuccessLottie = new Runnable() {

        @Override
        public void run() {
            if(state == 0) {
                state = 1;
                success.setMinAndMaxFrame(72, 72 + 15);
                success.playAnimation();
            } else if(state == 1) {
                state = 2;
                success.setImageAssetsFolder("search_coupon_fail/");
                success.setAnimation("search_coupon_fail.json");  //96 , 126
                success.setMinAndMaxFrame(96 + 15, 126);
                success.playAnimation();
            }
        }
    };

    private Runnable mFailLottie = new Runnable() {

        @Override
        public void run() {

            fail.playAnimation();
        }
    };
}
