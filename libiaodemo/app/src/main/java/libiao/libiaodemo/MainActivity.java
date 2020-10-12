package libiao.libiaodemo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;

import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.airbnb.lottie.test.LottieTestActivity;
import com.facebook.common.util.ByteConstants;
import com.libiao.libiaodemo.animation.AnimationActivity;
import com.libiao.libiaodemo.component.ComponentUI;
import com.libiao.libiaodemo.exoplayer.activity.TestExoplayerActivity;
import com.libiao.libiaodemo.jni.TestJniActivity;
import com.libiao.libiaodemo.kotlin.FirstKotlinActivity;
import com.libiao.libiaodemo.matrix.MatrixMainActivity;
import com.libiao.libiaodemo.okhttp.activity.TestOkhttpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import libiao.libiaodemo.android.activity.DeviceInfoActivity;
import libiao.libiaodemo.android.fresco.BitmapMemoryManager;
import libiao.libiaodemo.android.fresco.FrescoTestActivity;
import libiao.libiaodemo.android.glide.GlideTestActivity;
import libiao.libiaodemo.android.glide.MemorySizeCalculator;
import libiao.libiaodemo.android.ui.UIActivity;
import libiao.libiaodemo.android.utils.Base64Utils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_modules);
        float a = getResources().getDimension(R.dimen.activity_horizontal_margin);
        Log.i("libiao", "a = " + a);
        String s = "4" + "/" + "15";
        float v = 4/15;
        //Float f = Float.parseFloat(s);
        //Log.i("libiao", "f = " + v);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        //String time = formatter.format
        Log.i("libiao", "getMaxCacheSize() = " + getMaxCacheSize());
        Log.i("libiao", "getEncodeMaxCacheSize() = " + getEncodeMaxCacheSize());

        JSONObject one = new JSONObject();
        try {
            String string = "{\"ulinkRefer\":\"4060415\",\"wxAPPId\":\"wxe1eed2808f25aa15\",\"Drainage\":null,\"ActivityID\":\"20190320703800\",\"medium\":\"WX\"}";
            String s2 = "";
            JSONObject object = new JSONObject(s2);
            Log.i("libiao", "object = " + object);
            String str = object.get("Drainage").toString();
            Log.i("libiao", "str = " + str);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("libiao", "Build.MODEL = " + Build.MODEL);

        //initMonitor();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        TextView tv = (TextView) findViewById(R.id.tv);
//        tv.getLayoutParams().width = 700;
    }

    private int getMaxCacheSize() {
        ActivityManager activityManager = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        int maxMemory = Math.min(activityManager.getMemoryClass() * 1048576, 2147483647);
        Log.i("libiao", "getMaxCacheSize maxMemory = " + maxMemory);
        if (maxMemory < 33554432) {
            return 4194304;
        } else if (maxMemory < 67108864) {
            return 6291456;
        } else {
            return Build.VERSION.SDK_INT < 11 ? 8388608 : maxMemory / 4;
        }
    }

    private int getEncodeMaxCacheSize() {
        final int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), Integer.MAX_VALUE);
        Log.i("libiao", "getEncodeMaxCacheSize maxMemory = " + maxMemory);
        if (maxMemory < 16 * ByteConstants.MB) {
            return 1 * ByteConstants.MB;
        } else if (maxMemory < 32 * ByteConstants.MB) {
            return 2 * ByteConstants.MB;
        } else {
            return 4 * ByteConstants.MB;
        }
    }

    DfaHelper dfaHelper;
    public void okhttp(View view) {
        Intent in = new Intent(this, TestOkhttpActivity.class);
        //startActivity(in);
        //new MemorySizeCalculator(this);

//        dfaHelper = new DfaHelper();
//        List<String> list = readLoaclTxt(this);
//        dfaHelper.createSensitiveWordMap(list);

        String str = Base64Utils.decodeToString("asdf");
        Log.i("libiao", "str = " + str);
        String data = Base64Utils.encodeToString(str);
        Log.i("libiao", "data = " + data);
    }

    public void jni(View view) {
        Intent in = new Intent(this, TestJniActivity.class);
        startActivity(in);
    }

    public void matrix(View view) {
        Intent in = new Intent(this, MatrixMainActivity.class);
        startActivity(in);
    }

    public void lottie(View view) {
        Intent in = new Intent(this, LottieTestActivity.class);
        startActivity(in);
    }

    public void ui(View view) {
        Intent in = new Intent(this, UIActivity.class);
        startActivity(in);
    }

    public void exoplayer(View view) {
        Intent in = new Intent(this, TestExoplayerActivity.class);
        startActivity(in);
    }

    public void kotlin(View view) {
        Intent in = new Intent(this, FirstKotlinActivity.class);
        startActivity(in);
    }

    public void deviceInfo(View view) {
        Intent in = new Intent(this, DeviceInfoActivity.class);
        startActivity(in);
    }

    public void fresco(View view) {
        Intent in = new Intent(this, FrescoTestActivity.class);
        startActivity(in);
    }

    public void frescoCacheDetail(View v) {
        Log.i("libiao", "getBitmapMemoryCacheCount = " + BitmapMemoryManager.get().getBitmapMemoryCacheCount());
        Log.i("libiao", "getBitmapMemoryCacheSize = " + BitmapMemoryManager.get().getBitmapMemoryCacheSize());
        Log.i("libiao", "getEncodeMemoryCacheCount = " + BitmapMemoryManager.get().getEncodeMemoryCacheCount());
        Log.i("libiao", "getEncodeMemoryCacheSize = " + BitmapMemoryManager.get().getEncodeMemoryCacheSize());
    }

    public void glide(View view) {
        Intent in = new Intent(this, GlideTestActivity.class);
        startActivity(in);
    }

    public void glideCacheDetail(View v) {
        Log.i("libiao", "getGlideCacheCurrentSize = " + BitmapMemoryManager.get().getGlideCacheCurrentSize());
    }


    private static List<String> readLoaclTxt(Context context) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("sensitive_word_51601.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            List<String> list = new ArrayList<>();
            String str;
            while((str = br.readLine())!=null){
                list.add(str);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void component(View view) {
        Intent in = new Intent(this, ComponentUI.class);
        startActivity(in);
    }

    public void animation(View view) {
        Intent in = new Intent(this, AnimationActivity.class);
        startActivity(in);
    }

    @RequiresApi(
            api = 21
    )
    ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {

    };

    private void initMonitor() {
        Log.i("libiao", "Build.VERSION.SDK_INT = " + Build.VERSION.SDK_INT);
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService("connectivity");
        if (Build.VERSION.SDK_INT >= 26) {
            connectivityManager.registerDefaultNetworkCallback(this.networkCallback);
        } else if (Build.VERSION.SDK_INT >= 21) {
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            connectivityManager.registerNetworkCallback(request, this.networkCallback);
        } else {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        }

    }
}
