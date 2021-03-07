package libiao.libiaodemo.flutter;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.flutter.embedding.android.FlutterFragment;
import libiao.libiaodemo.R;

public class FlutterTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutter);
        // 通过FlutterFragment引入Flutter编写的页面
        FlutterFragment flutterFragment = FlutterFragment.createDefault();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, flutterFragment)
                .commit();
    }
}

