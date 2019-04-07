package libiao.libiaodemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.libiao.libiaodemo.jni.TestJniActivity;
import com.libiao.libiaodemo.matrix.MatrixMainActivity;
import com.libiao.libiaodemo.okhttp.activity.TestOkhttpActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

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
}
