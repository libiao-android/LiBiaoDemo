package libiao.libiaodemo.android.ui.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

public class Layout3 extends RecyclerView {

    private static final String TAG = "Layout3";

    public Layout3(Context context) {
        super(context);
    }

    public Layout3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Layout3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent = " + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    float x0;
    float y0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent = " + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                x0 = event.getX();
                y0 = event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if(x0 == 0 && y0 == 0) {
                    x0 = event.getX();
                    y0 = event.getY();
                }
                float disX = event.getX() - x0;
                float disY = event.getY() - y0;
                if (disY != 0f && (disX / disY > 3.0 || disX / disY < -3.0)) {
                    Log.i(TAG, "内部控制拦截，自己不消费事件，需要横向滑动，让父控件消费事件");
                    return false;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                x0 = 0f;
                y0 = 0f;
            }
        }
        return super.onTouchEvent(event);
    }
}
