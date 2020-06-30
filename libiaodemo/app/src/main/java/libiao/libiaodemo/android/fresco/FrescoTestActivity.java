package libiao.libiaodemo.android.fresco;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.CloseableAnimatedImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import libiao.libiaodemo.R;

public class FrescoTestActivity extends Activity {
    SimpleDraweeView draweeView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fresco_text_main);
        draweeView = findViewById(R.id.iv);
    }

    public void loadGif(View v) {
        commonLoad("http://img.huofar.com/data/jiankangrenwu/shizi.gif");
    }

    private void commonLoad(String url) {
        ControllerListener listener = new ControllerListener(){

            @Override
            public void onSubmit(String id, Object callerContext) {
                Log.i("libiao", "onSubmit id = " + id + ", callerContext = " + callerContext);
            }

            @Override
            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                Log.i("libiao", "onFinalImageSet id = " + id + ", imageInfo = " + imageInfo);
                if(imageInfo instanceof CloseableStaticBitmap) {
                    CloseableStaticBitmap staticBitmap = (CloseableStaticBitmap) imageInfo;
                    Log.i("libiao", "szie = " + staticBitmap.getSizeInBytes());
                    Log.i("libiao", "getWidth = " + staticBitmap.getWidth());
                    Log.i("libiao", "getHeight = " + staticBitmap.getHeight());
                    Log.i("libiao", "draweeView.getWidth = " + draweeView.getWidth());
                    Log.i("libiao", "draweeView.getHeight = " + draweeView.getHeight());
                } else if (imageInfo instanceof CloseableAnimatedImage) {
                    CloseableAnimatedImage animatedImage = (CloseableAnimatedImage) imageInfo;
                    Log.i("libiao", "szie = " + animatedImage.getSizeInBytes());
                    Log.i("libiao", "getWidth = " + animatedImage.getWidth());
                    Log.i("libiao", "getHeight = " + animatedImage.getHeight());
                    Log.i("libiao", "getFrameCount = " + animatedImage.getImage().getFrameCount());
                    Log.i("libiao", "draweeView.getWidth = " + draweeView.getWidth());
                    Log.i("libiao", "draweeView.getHeight = " + draweeView.getHeight());
                }
            }

            @Override
            public void onIntermediateImageSet(String id, Object imageInfo) {
                Log.i("libiao", "onIntermediateImageSet id = " + id + ", imageInfo = " + imageInfo);
            }

            @Override
            public void onIntermediateImageFailed(String id, Throwable throwable) {
                Log.i("libiao", "onIntermediateImageFailed id = " + id + ", throwable = " + throwable);
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                Log.i("libiao", "onFailure id = " + id + ", throwable = " + throwable);
            }

            @Override
            public void onRelease(String id) {
                Log.i("libiao", "onRelease id = " + id);
            }
        };

        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(draweeView.getWidth(), draweeView.getHeight()))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(draweeView.getController())
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .setControllerListener(listener)
                .build();
        draweeView.setController(controller);
    }

    public void load(View v) {
        commonLoad("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3627787715,4148989579&fm=26&gp=0.jpg");
    }

    public void check(View v) {
        Log.i("libiao", "getBitmapMemoryCacheCount = " + BitmapMemoryManager.get().getBitmapMemoryCacheCount());
        Log.i("libiao", "getBitmapMemoryCacheSize = " + BitmapMemoryManager.get().getBitmapMemoryCacheSize());
        Log.i("libiao", "getEncodeMemoryCacheCount = " + BitmapMemoryManager.get().getEncodeMemoryCacheCount());
        Log.i("libiao", "getEncodeMemoryCacheSize = " + BitmapMemoryManager.get().getEncodeMemoryCacheSize());
    }
}


/**
 *
 * BitmapMemory  app内存/4
 * EncodeMemory  4M
 *
 *
 * onMemoryCachePut 再 onBitmapCachePut
 *
 * onBitmapCacheHit 再 onMemoryCacheHit
 */

