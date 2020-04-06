package libiao.libiaodemo.android.ui.recyclerview;

import android.content.Context;

import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;

public class LBMemorySizeCalculator {

    public static int getMemoryCacheSize(Context context) {
        //return 10;
        return 10 * 1024 * 1024;
        //return new MemorySizeCalculator(context).getMemoryCacheSize();
    }
}
