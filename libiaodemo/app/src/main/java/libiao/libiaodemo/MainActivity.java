package libiao.libiaodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.test.LottieTestActivity;
import com.libiao.libiaodemo.exoplayer.activity.TestExoplayerActivity;
import com.libiao.libiaodemo.jni.TestJniActivity;
import com.libiao.libiaodemo.kotlin.FirstKotlinActivity;
import com.libiao.libiaodemo.matrix.MatrixMainActivity;
import com.libiao.libiaodemo.okhttp.activity.TestOkhttpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import libiao.libiaodemo.android.activity.DeviceInfoActivity;
import libiao.libiaodemo.android.ui.video.VideoActivity;

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
        Log.i("libiao", "time = " + System.currentTimeMillis());

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
    }

    @Override
    protected void onResume() {
        super.onResume();
//        TextView tv = (TextView) findViewById(R.id.tv);
//        tv.getLayoutParams().width = 700;
    }

    public void okhttp(View view) {
        Intent in = new Intent(this, TestOkhttpActivity.class);
        startActivity(in);
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
        Intent in = new Intent(this, VideoActivity.class);
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
}
