package libiao.libiaodemo.android.ui.recyclerview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.util.LruCache;

import java.util.Collection;

public class LBLruResourceCache extends LruCache<Key, Resource<?>> implements MemoryCache{

    private ResourceRemovedListener listener;
    private static volatile LBLruResourceCache sInstance;

    /**
     * Constructor for LruResourceCache.
     *
     * @param size The maximum size in bytes the in memory cache can use.
     */
    private LBLruResourceCache(int size) {
        super(size);
    }

    public static LBLruResourceCache get(int size) {
        if(sInstance == null) {
            synchronized (LBLruResourceCache.class) {
                if(sInstance == null) {
                    Log.i("libiao", "size = " + size);
                    sInstance = new LBLruResourceCache(size);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void setResourceRemovedListener(ResourceRemovedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onItemEvicted(Key key, Resource<?> item) {
        if (listener != null) {
            listener.onResourceRemoved(item);
        }
    }

    @Override
    protected int getSize(Resource<?> item) {
        return item.getSize();
    }

    @SuppressLint("InlinedApi")
    @Override
    public void trimMemory(int level) {
        if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            // Nearing middle of list of cached background apps
            // Evict our entire bitmap cache
            clearMemory();
        } else if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            // Entering list of cached background apps
            // Evict oldest half of our bitmap cache
            trimToSize(getCurrentSize() / 2);
        }
    }

    @Override
    public Resource<?> put(Key key, Resource<?> item) {
        Log.i("libiao", "put = " + getCurrentSize());
        return super.put(key, item);
    }

}
