package libiao.libiaodemo.android.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.DrawableMarginSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import libiao.libiaodemo.R;
import libiao.libiaodemo.android.ui.danmu.DanmuActivity;
import libiao.libiaodemo.android.ui.listview.ListViewActivity;
import libiao.libiaodemo.android.ui.recyclerview.RecyclerViewActivity;
import libiao.libiaodemo.android.ui.video.videoeditor.VideoEditorActivity;

public class UIActivity extends Activity {

    private TextView tv;
    Handler handler;
    private View view1;
    private View view2;
    private View view3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        tv = findViewById(R.id.live_func_chat_new_count);
        handler = new Handler(Looper.getMainLooper());
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
    }

    public void listview(View v) {
        Intent in = new Intent(this, ListViewActivity.class);
        startActivity(in);
    }

    public void listview_memory(View v) {
        Intent in = new Intent(this, ListViewActivity.class);
        in.putExtra("cache", "memory");
        startActivity(in);
    }

    public void listview_disk(View v) {
        Intent in = new Intent(this, ListViewActivity.class);
        in.putExtra("cache", "disk");
        startActivity(in);
    }


    public void recyclerview(View v) {
        Intent in = new Intent(this, RecyclerViewActivity.class);
        startActivity(in);
    }

    public void recyclerview_memory(View v) {
        Intent in = new Intent(this, RecyclerViewActivity.class);
        in.putExtra("cache", "memory");
        startActivity(in);
    }

    public void recyclerview_disk(View v) {
        Intent in = new Intent(this, RecyclerViewActivity.class);
        in.putExtra("cache", "disk");
        startActivity(in);
    }


    public void video(View v) {
        Intent in = new Intent(this, VideoEditorActivity.class);
        startActivity(in);
    }

    public void danmu(View v) {
        Intent in = new Intent(this, DanmuActivity.class);
        startActivity(in);
    }

    public void constraint(View v) {

        Drawable d = getResources().getDrawable(R.mipmap.anchor);
        Log.i("libiao", "d.getIntrinsicWidth() = " + d.getIntrinsicWidth());
        Log.i("libiao", "d.getIntrinsicHeight() = " + d.getIntrinsicHeight());
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());

        AbsoluteSizeSpan imageSpan = new AbsoluteSizeSpan(20, true);
        SpannableString spannableString = new SpannableString(" 李彪  啊啊啊啊啊啊啊啊啊啊啊");
        spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(span, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan span2 = new ForegroundColorSpan(Color.YELLOW);
        spannableString.setSpan(span2, 6, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv.setText(spannableString);

    }
    String msg;
    public void changeTvLength(View v) {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i = 0; i < 200; i++) {
//                    msg = "阿斯顿法国红酒可领取";
//                    msg = msg.substring(i%msg.length());
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            tv.setText(msg);
//                        }
//                    });
//                    try {
//                        Thread.sleep(300);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();

        int w1 = view1.getWidth();
        Log.i("libiao", "w1 = " + w1);
        ValueAnimator animator = ValueAnimator.ofInt(0, w1, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int w = (int) animation.getAnimatedValue();
                Log.i("libiao", "w = " + w);
                view1.getLayoutParams().width = (int) animation.getAnimatedValue();
                view1.requestLayout();
                view1.invalidate();
            }
        });
        animator.setDuration(1600);
        animator.setRepeatCount(-1);
        animator.start();


        int w3 = view3.getWidth();
        ValueAnimator animator1 = ValueAnimator.ofInt(0, w3, 0);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int w = (int) animation.getAnimatedValue();
                Log.i("libiao", "w = " + w);
                view3.getLayoutParams().width = (int) animation.getAnimatedValue();
                view3.requestLayout();
                view3.invalidate();
            }
        });
        animator1.setDuration(1600);
        animator1.setRepeatCount(-1);
        animator1.start();
    }
}
