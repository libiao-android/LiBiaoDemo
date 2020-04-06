package libiao.libiaodemo.android.fresco;

import com.facebook.imagepipeline.cache.CountingMemoryCache;

import libiao.libiaodemo.android.ui.recyclerview.LBLruResourceCache;

public class BitmapMemoryManager {

    private static volatile BitmapMemoryManager memoryManager;

    private CountingMemoryCache<?, ?> bitmapMemoryCache;
    private CountingMemoryCache<?, ?> encodedMemoryCache;

    private LBLruResourceCache glideCache;

    private BitmapMemoryManager() {}

    public static BitmapMemoryManager get() {
        if(memoryManager == null) {
            synchronized (BitmapMemoryManager.class) {
                if(memoryManager == null) {
                    memoryManager = new BitmapMemoryManager();
                }
            }
        }
        return memoryManager;
    }

    public void setBitmapMemoryCache(CountingMemoryCache<?, ?> bitmapMemoryCache) {
        this.bitmapMemoryCache = bitmapMemoryCache;
    }

    public void setEncodedMemoryCache(CountingMemoryCache<?, ?> encodedMemoryCache) {
        this.encodedMemoryCache = encodedMemoryCache;
    }

    public int getBitmapMemoryCacheSize() {
        if(bitmapMemoryCache != null) {
            return bitmapMemoryCache.getSizeInBytes();
        }
        return 0;
    }

    public int getBitmapMemoryCacheCount() {
        if(bitmapMemoryCache != null) {
            return bitmapMemoryCache.getCount();
        }
        return 0;
    }

    public int getEncodeMemoryCacheSize() {
        if(bitmapMemoryCache != null) {
            return encodedMemoryCache.getSizeInBytes();
        }
        return 0;
    }

    public int getEncodeMemoryCacheCount() {
        if(bitmapMemoryCache != null) {
            return encodedMemoryCache.getCount();
        }
        return 0;
    }

    public void setGlideCache(LBLruResourceCache cache) {
        glideCache = cache;
    }

    public int getGlideCacheCurrentSize() {
        if(glideCache != null) {
            return glideCache.getCurrentSize();
        }
        return 0;
    }
}
