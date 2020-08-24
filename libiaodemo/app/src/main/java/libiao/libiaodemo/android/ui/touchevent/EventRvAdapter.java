package libiao.libiaodemo.android.ui.touchevent;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import libiao.libiaodemo.R;
import libiao.libiaodemo.android.ui.recyclerview.GlideImageView;

public class EventRvAdapter extends RecyclerView.Adapter<EventRvAdapter.RecyclerViewHolder> {

    private Context mContext;

    private List<String> mData = new ArrayList<>();

    public EventRvAdapter(Context context) {
        mContext = context;
        mData.add("https://c-ssl.duitang.com/uploads/item/201208/30/20120830173930_PBfJE.thumb.700_0.jpeg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572053625157&di=ba97e24ca1814fbb25330586c4c58d0b&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D2968404150%2C3127104799%26fm%3D214%26gp%3D0.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572053646709&di=4ff79b0a81062ffcb11044329361c1b6&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D2574881099%2C1689606123%26fm%3D214%26gp%3D0.jpg");
        mData.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1699044021,3553217966&fm=26&gp=0.jpg");
        mData.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1376945104,1360683903&fm=26&gp=0.jpg");

        mData.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3328690371,1669009258&fm=26&gp=0.jpg");
        mData.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3133997230,82936750&fm=26&gp=0.jpg");
        mData.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=110361456,1700508643&fm=26&gp=0.jpg");
        mData.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=429337808,2880247801&fm=26&gp=0.jpg");
        mData.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=35586351,706837839&fm=26&gp=0.jpg");

        mData.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3627787715,4148989579&fm=26&gp=0.jpg");
        mData.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3751091939,2506584212&fm=26&gp=0.jpg");
        mData.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2069598706,2454908567&fm=26&gp=0.jpg");
        mData.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3057206859,4050346805&fm=26&gp=0.jpg");
        mData.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3183526200,1657950052&fm=26&gp=0.jpg");
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.event_rv_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        public int position = -1;
        public RecyclerViewHolder(View root){
            super(root);
//            root.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, "click = " + position, Toast.LENGTH_LONG).show();
//                    //notifyDataSetChanged();
//                    notifyItemChanged(position);
//                }
//            });
            tv = root.findViewById(R.id.tv);
        }
        void bind(String str, int position){
            this.position = position;
            //绑定逻辑搬移到这里将更加简洁
            tv.setText(str);
        }
    }
}
