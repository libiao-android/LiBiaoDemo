package libiao.libiaodemo;

import android.app.Application;

import com.libiao.libiaodemo.matrix.MatrixInit;

/**
 * Description:
 * Data：2019/4/5-下午10:57
 * Author: libiao
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MatrixInit.init(this);
    }
}
