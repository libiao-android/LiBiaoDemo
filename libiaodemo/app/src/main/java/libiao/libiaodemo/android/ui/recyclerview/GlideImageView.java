package libiao.libiaodemo.android.ui.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class GlideImageView extends android.support.v7.widget.AppCompatImageView {

    private Target<GlideDrawable> mTarget;
    private int position = -1;
    private String url;

    public GlideImageView(Context context) {
        super(context);
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i("libiao", "onAttachedToWindow");
        if(url != null) {
            load(url, false, position);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i("libiao", "onDetachedFromWindow = " + position);

        if(url != null) {
            if(mTarget != null) {
                //mTarget.getRequest().clear();
            }
            setImageDrawable(null);
        }
    }

    public void load(String url, boolean mSkipMemoryCache, int position) {
        this.url = url;
        this.position = position;
        Glide.with(getContext()).load(url).skipMemoryCache(mSkipMemoryCache).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Log.i("libiao", "onException = " + position + ",e = " + e.getMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Log.i("libiao", "onResourceReady = " + position);
                mTarget = target;
                return false;
            }
        }).into(this);
    }
}
