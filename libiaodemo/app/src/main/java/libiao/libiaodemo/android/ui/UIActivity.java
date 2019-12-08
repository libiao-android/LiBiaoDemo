package libiao.libiaodemo.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import libiao.libiaodemo.R;
import libiao.libiaodemo.android.ui.listview.ListViewActivity;
import libiao.libiaodemo.android.ui.recyclerview.RecyclerViewActivity;
import libiao.libiaodemo.android.ui.video.VideoActivity;

public class UIActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
    }

    public void listview(View v) {
        Intent in = new Intent(this, ListViewActivity.class);
        startActivity(in);
    }

    public void listview_memory(View v) {
        Intent in = new Intent(this, ListViewActivity.class);
        in.putExtra("cache", "memory");
        startActivity(in);
    }

    public void listview_disk(View v) {
        Intent in = new Intent(this, ListViewActivity.class);
        in.putExtra("cache", "disk");
        startActivity(in);
    }


    public void recyclerview(View v) {
        Intent in = new Intent(this, RecyclerViewActivity.class);
        startActivity(in);
    }

    public void recyclerview_memory(View v) {
        Intent in = new Intent(this, RecyclerViewActivity.class);
        in.putExtra("cache", "memory");
        startActivity(in);
    }

    public void recyclerview_disk(View v) {
        Intent in = new Intent(this, RecyclerViewActivity.class);
        in.putExtra("cache", "disk");
        startActivity(in);
    }


    public void video(View v) {
        Intent in = new Intent(this, VideoActivity.class);
        startActivity(in);
    }
}
