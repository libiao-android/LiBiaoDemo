package com.libiao.libiaodemo.matrix;

import android.app.Application;

import com.tencent.matrix.Matrix;
import com.tencent.matrix.iocanary.IOCanaryPlugin;
import com.tencent.matrix.iocanary.config.IOConfig;

/**
 * Description:
 * Data：2019/4/5-下午10:58
 * Author: libiao
 */
public class MatrixInit {

    public static void init(Application context) {
        Matrix.Builder builder = new Matrix.Builder(context);
        ioCanary(builder);
        Matrix.init(builder.build());
    }

    private static void ioCanary(Matrix.Builder builder) {
        DynamicConfigImpl dynamicConfig = new DynamicConfigImpl();
        IOConfig ioConfig = new IOConfig.Builder().dynamicConfig(dynamicConfig).build();
        IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(ioConfig);
        builder.plugin(ioCanaryPlugin);
    }
}
