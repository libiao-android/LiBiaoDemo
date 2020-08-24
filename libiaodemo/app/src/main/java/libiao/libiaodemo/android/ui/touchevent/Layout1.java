package libiao.libiaodemo.android.ui.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class Layout1 extends RelativeLayout {

    private static final String TAG = "Layout1";

    public Layout1(Context context) {
        this(context, null);
    }

    public Layout1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Layout1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent = " + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent = " + event.getAction());
        return super.onTouchEvent(event);
    }
}
