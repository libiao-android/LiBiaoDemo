package libiao.libiaodemo.android.glide;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Util;

import libiao.libiaodemo.R;
import libiao.libiaodemo.android.fresco.BitmapMemoryManager;

public class GlideTestActivity extends Activity {

    ImageView iv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glide_text_main);
        iv = findViewById(R.id.iv);
    }

    public void loadGif(View v) {
        commomLoad("http://img.huofar.com/data/jiankangrenwu/shizi.gif");
    }

    private void commomLoad(String url) {
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        int size = 0;
                        Bitmap bitmap = null;
                        if(resource instanceof GifDrawable) {
                            GifDrawable gifDrawable = (GifDrawable)resource;
                            bitmap = gifDrawable.getFirstFrame();
                            Log.i("libiao", "glide getFrameCount = " + gifDrawable.getFrameCount());
                            Log.i("libiao", "glide getData().length = " + gifDrawable.getData().length);
                            size = gifDrawable.getData().length + Util.getBitmapByteSize(gifDrawable.getFirstFrame());
                        } else if(resource instanceof GlideBitmapDrawable) {
                            GlideBitmapDrawable bitmapDrawable = (GlideBitmapDrawable) resource;
                            bitmap = bitmapDrawable.getBitmap();
                            size = Util.getBitmapByteSize(bitmapDrawable.getBitmap());
                        }
                        Log.i("libiao", "glide size = " + size);
                        if(bitmap != null) {
                            Log.i("libiao", "bitmap.getWidth() = " + bitmap.getWidth());
                            Log.i("libiao", "bitmap.getHeight() = " + bitmap.getHeight());
                        }

                        Log.i("libiao", "iv.getWidth() = " + iv.getWidth());
                        Log.i("libiao", "iv.getHeight() = " + iv.getHeight());
                        return false;
                    }
                })
                .into(iv);
    }

    public void load(View v) {
        commomLoad("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3627787715,4148989579&fm=26&gp=0.jpg");
    }

    public void check(View v) {
        //Log.i("libiao", "getGlideCacheCurrentSize = " + BitmapMemoryManager.get().getGlideCacheCurrentSize());
    }
}
