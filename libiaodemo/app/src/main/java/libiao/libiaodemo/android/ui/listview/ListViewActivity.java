package libiao.libiaodemo.android.ui.listview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import libiao.libiaodemo.R;

public class ListViewActivity extends Activity {

    private ListView mListView;
    private ListViewAdapter mAdapter;
    private String mCacheStrategy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_main);

        mCacheStrategy = getIntent().getStringExtra("cache");

        mListView = findViewById(R.id.list_view);
        mAdapter = new ListViewAdapter(this, mCacheStrategy);

        mListView.setAdapter(mAdapter);
        mListView.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                //Log.i("libiao", "onMovedToScrapHeap = " + ((ListViewAdapter.ViewHolder)view.getTag()).position);
            }
        });
    }
}
