package libiao.libiaodemo.android.ui.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class Layout2 extends RelativeLayout {

    private static final String TAG = "Layout2";

    public Layout2(Context context) {
        super(context);
    }

    public Layout2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Layout2(Context context, AttributeSet attrs, int defStyleAttr) {
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
