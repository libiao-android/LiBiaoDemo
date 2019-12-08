package libiao.libiaodemo.android.ui.listview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class ListItemView extends LinearLayout {

    public ListItemView(Context context) {
        super(context);
    }

    public ListItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ListItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
       // Log.i("libiao", "onDetachedFromWindow = " + ((ListViewAdapter.ViewHolder)getTag()).position);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
       // Log.i("libiao", "onAttachedToWindow = " + ((ListViewAdapter.ViewHolder)getTag()).position);
    }
}
