package libiao.libiaodemo.android.ui.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import libiao.libiaodemo.R;
import libiao.libiaodemo.android.ui.listview.ListViewAdapter;

public class RecyclerViewActivity extends Activity {

    private RecyclerView mRecyclerView;
    private String mCacheStrategy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_main);

        mCacheStrategy = getIntent().getStringExtra("cache");

        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(new RecyclerViewAdapter(this, mCacheStrategy));

    }
}
