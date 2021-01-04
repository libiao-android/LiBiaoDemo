package libiao.libiaodemo;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ContextThemeWrapper;

import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.libiao.libiaodemo.matrix.MatrixInit;
import com.qihoo360.replugin.RePlugin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import libiao.libiaodemo.android.fresco.BitmapMemoryManager;

/**
 * Description:
 * Data：2019/4/5-下午10:57
 * Author: libiao
 */
public class MainApplication extends Application {

    public static Application sApplication;

    public static Context getContext() {
        return sApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        RePlugin.App.attachBaseContext(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RePlugin.App.onCreate();
        sApplication = this;
        MatrixInit.init(this);
        RequestListener requestListener = new RequestListener() {
            @Override
            public void onRequestStart(ImageRequest request, Object callerContext, String requestId, boolean isPrefetch) {
                Log.i("libiao", "onRequestStart");
            }

            @Override
            public void onRequestSuccess(ImageRequest request, String requestId, boolean isPrefetch) {
                Log.i("libiao", "onRequestSuccess");
            }

            @Override
            public void onRequestFailure(ImageRequest request, String requestId, Throwable throwable, boolean isPrefetch) {
                Log.i("libiao", "onRequestFailure");
            }

            @Override
            public void onRequestCancellation(String requestId) {
                Log.i("libiao", "onRequestCancellation");
            }

            @Override
            public void onProducerStart(String requestId, String producerName) {
                Log.i("libiao", "onProducerStart = " + producerName);
            }

            @Override
            public void onProducerEvent(String requestId, String producerName, String eventName) {
                Log.i("libiao", "onProducerEvent = " + producerName + ", eventName = " + eventName);
            }

            @Override
            public void onProducerFinishWithSuccess(String requestId, String producerName, Map<String, String> extraMap) {
                Log.i("libiao", "onProducerFinishWithSuccess = " + producerName);
            }

            @Override
            public void onProducerFinishWithFailure(String requestId, String producerName, Throwable t, Map<String, String> extraMap) {
                Log.i("libiao", "onProducerFinishWithFailure = " + producerName);
            }

            @Override
            public void onProducerFinishWithCancellation(String requestId, String producerName, Map<String, String> extraMap) {
                Log.i("libiao", "onProducerFinishWithCancellation = " + producerName);
            }

            @Override
            public boolean requiresExtraMap(String requestId) {
                return false;
            }
        };
        Set<RequestListener> requestListenerSet = new HashSet<>();
        requestListenerSet.add(requestListener);

        final int maxCacheSize = 4 * ByteConstants.MB;
        final int maxCacheEntrySize = maxCacheSize / 8;



        ImagePipelineConfig pipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)  // 必须和ImageRequest的ResizeOptions一起使用，作用就是在图片解码时根据ResizeOptions所设的宽高的像素进行解码，这样解码出来可以得到一个更小的Bitmap。
                .setResizeAndRotateEnabledForNetwork(true)// 对网络图片进行resize处理，减少内存消耗
                .setBitmapsConfig(Bitmap.Config.RGB_565) // 没有透明图片显示要求，设置为RGB_565，减少内存开销
                .setImageCacheStatsTracker(new ImageCacheStatsTracker() {
                    @Override
                    public void onBitmapCachePut() {
                        Log.i("libiao", "onBitmapCachePut");
                    }

                    @Override
                    public void onBitmapCacheHit() {
                        Log.i("libiao", "onBitmapCacheHit");
                    }

                    @Override
                    public void onBitmapCacheMiss() {
                        Log.i("libiao", "onBitmapCacheMiss");
                    }

                    @Override
                    public void onMemoryCachePut() {
                        Log.i("libiao", "onMemoryCachePut");
                    }

                    @Override
                    public void onMemoryCacheHit() {
                        Log.i("libiao", "onMemoryCacheHit");
                    }

                    @Override
                    public void onMemoryCacheMiss() {
                        Log.i("libiao", "onMemoryCacheMiss");
                    }

                    @Override
                    public void onStagingAreaHit() {
                        Log.i("libiao", "onStagingAreaHit");
                    }

                    @Override
                    public void onStagingAreaMiss() {
                        Log.i("libiao", "onStagingAreaMiss");
                    }

                    @Override
                    public void onDiskCacheHit() {
                        Log.i("libiao", "onDiskCacheHit");
                    }

                    @Override
                    public void onDiskCacheMiss() {
                        Log.i("libiao", "onDiskCacheMiss");
                    }

                    @Override
                    public void onDiskCacheGetFail() {
                        Log.i("libiao", "onDiskCacheGetFail");
                    }

                    @Override
                    public void registerBitmapMemoryCache(CountingMemoryCache<?, ?> bitmapMemoryCache) {
                        Log.i("libiao", "registerBitmapMemoryCache");
                        BitmapMemoryManager.get().setBitmapMemoryCache(bitmapMemoryCache);
                    }

                    @Override
                    public void registerEncodedMemoryCache(CountingMemoryCache<?, ?> encodedMemoryCache) {
                        Log.i("libiao", "registerEncodedMemoryCache");
                        BitmapMemoryManager.get().setEncodedMemoryCache(encodedMemoryCache);
                    }
                })
                .setRequestListeners(requestListenerSet)
                .setBitmapMemoryCacheParamsSupplier(() -> new MemoryCacheParams(10 * ByteConstants.MB, 256, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE))
                .setEncodedMemoryCacheParamsSupplier(() -> new MemoryCacheParams(maxCacheSize,
                        Integer.MAX_VALUE,
                        maxCacheSize,
                        Integer.MAX_VALUE,
                        maxCacheEntrySize))
                .build();

        pipelineConfig.getMemoryTrimmableRegistry().registerMemoryTrimmable(new MemoryTrimmable() {
            @Override
            public void trim(MemoryTrimType trimType) {
                Log.i("libiao", "trim");
            }
        });
        Fresco.initialize(this.getApplicationContext(), pipelineConfig);
    }
}
