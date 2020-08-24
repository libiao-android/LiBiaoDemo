package libiao.libiaodemo.android.ui.touchevent;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import libiao.libiaodemo.R;

public class EventActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new EventRvAdapter(this));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("Layout_activity", "onTouchEvent = " + event.getAction());
        return super.onTouchEvent(event);
    }
}
