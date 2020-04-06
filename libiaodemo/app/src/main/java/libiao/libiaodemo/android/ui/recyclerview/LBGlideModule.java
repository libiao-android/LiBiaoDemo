package libiao.libiaodemo.android.ui.recyclerview;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

import libiao.libiaodemo.android.fresco.BitmapMemoryManager;

public class LBGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        LBLruResourceCache cache = LBLruResourceCache.get(LBMemorySizeCalculator.getMemoryCacheSize(context));
        BitmapMemoryManager.get().setGlideCache(cache);
        builder.setMemoryCache(cache);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
