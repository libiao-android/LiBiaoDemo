package libiao.libiaodemo.android.ui.danmu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import libiao.libiaodemo.R;

public class DanmuView extends RelativeLayout {

    //1、动态计算item高度
    //2、stop

    private int mScreenWidth;
    public Map<Integer, LinkedList<View>> mDanmuItemMap;
    private boolean mIsWorking = false;
    private Context mContext;
    private int mRowNum = 2; //弹幕行数
    private int mDanmuItemHeight = 26; //弹幕高 dp
    private int mDanmuItemMarginTop = 13; // 弹幕之间的间隔 dp
    private int mDanmuSpeed = 200; //弹幕滑动速度，px/s
    private int lastRow = -1;
    private boolean isFirst = true;
    private int mDelayDuration = 500;
    private Random mRandom;
    private boolean mLayoutFinish = false;
    private boolean mStart = false;

    public static enum XCDirection {
        FROM_RIGHT_TO_LEFT,
        FORM_LEFT_TO_RIGHT
    }

    public enum XCAction {
        SHOW, HIDE
    }

    private XCDirection mDirection = XCDirection.FROM_RIGHT_TO_LEFT;

    public DanmuView(Context context) {
        super(context);
        init();
    }

    public DanmuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DanmuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = this.getChildCount();
        Log.i("libiao", "onLayout = " + childCount);
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            RelativeLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            if (lp.leftMargin <= 0) {
                if (mDirection == XCDirection.FORM_LEFT_TO_RIGHT) {
                    view.layout(-view.getMeasuredWidth(), lp.topMargin, 0, lp.topMargin + view.getMeasuredHeight());
                } else {
                    view.layout(mScreenWidth, lp.topMargin, mScreenWidth + view.getMeasuredWidth(),
                            lp.topMargin + view.getMeasuredHeight());
                }

            } else {
                continue;
            }
        }
        mLayoutFinish = true;
        check();
    }

    private void init() {
        mContext = getContext();
        if(getContext() instanceof Activity) {
            Activity activity = (Activity)getContext();
            Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            mScreenWidth = point.x;
        }
        mDanmuItemMap = new HashMap<>();
        mRandom = new Random();
    }

    public void initDanmuItemViews(List<DanmuBean> danmuContents) {
        for (DanmuBean bean : danmuContents) {
            createDanmuView(bean);
        }
    }

    public void setRowNum(int num) {
        mRowNum = num;
    }

    public void setDanmuItemHeight(int height) {
        mDanmuItemHeight = height;
    }

    public void setDanmuItemMarginTop(int marginTop) {
        mDanmuItemMarginTop = marginTop;
    }

    public void setDanmuSpeed(int time) {
        mDanmuSpeed = time;
    }

    public void createDanmuView(DanmuBean bean) {
        int heightPx = dip2px(mDanmuItemHeight);
        int marginPx = dip2px(mDanmuItemMarginTop);
        final TextView textView = new TextView(mContext);
        textView.setTextColor(Color.parseColor("#99FFFFFF"));
        textView.setText(bean.content);
        RelativeLayout.LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int row = mRandom.nextInt(mRowNum);
        while (row == lastRow && mRowNum > 1) {
            row = mRandom.nextInt(mRowNum);
        }
        lp.topMargin = row * heightPx + (row + 1) * marginPx;
        lastRow = row;
        textView.setLayoutParams(lp);
        textView.setPadding(dip2px(10), dip2px(4), dip2px(10), dip2px(4));
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.danmu_item_bg);
        drawable.setColor(Color.parseColor(bean.color));
        textView.setBackground(drawable);
        this.addView(textView);
        LinkedList<View> views =  mDanmuItemMap.get(row);
        if(views == null) {
            views = new LinkedList<>();
            mDanmuItemMap.put(row, views);
        }
        views.add(textView);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);

            int row = msg.what;
            View itemView = (View) msg.obj;

            ViewPropertyAnimator animator;
            int length = mScreenWidth + itemView.getWidth();
            int length_s = itemView.getWidth() + 100;
            if (mDirection == XCDirection.FROM_RIGHT_TO_LEFT) {
                animator = itemView.animate()
                        .translationXBy(-length);
            } else {
                animator = itemView.animate()
                        .translationXBy(length);
            }
            animator.setDuration(length *  1000 / mDanmuSpeed);
            animator.setInterpolator(new LinearInterpolator());
            animator.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                boolean first = true;
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float x = (float) animation.getAnimatedValue();
                    if(x * length >= length_s && first) {
                        first = false;
                        move(row);
                    }
                }
            });
            animator.setListener(new AnimatorListenerAdapter(){

                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    itemView.setTranslationX(0);//归位
                    add(row, itemView);
                }
            });
            animator.start();
        }
    };

    public void start() {
        mStart = true;
        check();
    }

    private void check() {
        if(mLayoutFinish && mStart) {
            switchAnimation(XCAction.SHOW);
            mIsWorking = true;
            if (isFirst) {
                for(Integer row : mDanmuItemMap.keySet()) {
                    move(row);
                }
                isFirst = false;
            }
        }
    }

    private Map<Integer, Boolean> recordMove = new HashMap<>();
    private void move(int row) {
        if(!isWorking()) {return;}
        LinkedList<View> viewList = mDanmuItemMap.get(row);
        if(viewList != null) {
            View info = viewList.poll();
            if(info != null) {
                Message message = Message.obtain();
                message.what = row;
                message.obj = info;
                mHandler.sendMessage(message);
            } else {
                Log.i("libiao", "等待循环 = " + row);
                recordMove.put(row, true);
            }
        }
    }

    private void add(int row, View view) {
        if(!isWorking()) {return;}
        LinkedList<View> viewList = mDanmuItemMap.get(row);
        if(viewList != null && view != null) {
            viewList.add(view);
        }
        if(recordMove.get(row) != null && recordMove.get(row)) {
            Log.i("libiao", "需要重新开始循环 = " + row);
            recordMove.put(row, false);
            move(row);
        }
    }

    public void hide() {
        switchAnimation(XCAction.HIDE);
        mIsWorking = false;
    }

    public void stop() {
        mIsWorking = false;
        mLayoutFinish = false;
        mStart = false;
        this.setVisibility(View.GONE);
        for(List<View> views : mDanmuItemMap.values()) {
            for(View view : views) {
                view.clearAnimation();
            }
        }
        mHandler.removeCallbacksAndMessages(null);
    }

    public boolean isWorking() {
        return mIsWorking;
    }

    private void switchAnimation(final XCAction action) {
        AlphaAnimation animation;
        if (action == XCAction.HIDE) {
            animation = new AlphaAnimation(1.0f, 0.0f);
            animation.setDuration(400);
        } else {
            animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(1000);
        }
        DanmuView.this.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (action == XCAction.HIDE) {
                    DanmuView.this.setVisibility(View.GONE);
                } else {
                    DanmuView.this.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public int dip2px(float dpValue) {
        if (mContext == null) {
            return 0;
        }
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public class DanmuMoveInfo {
        //第几行？第几个view？延时多少？
        public int row;
        public int pos;
        public int delay;

        public DanmuMoveInfo(int row, int pos, int delay) {
            this.row = row;
            this.pos = pos;
            this.delay = delay;
        }
    }
}
