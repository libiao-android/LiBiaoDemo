package libiao.libiaodemo.android.ui.listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import libiao.libiaodemo.R;

/**
 * 子view刷新会触发第一屏view刷新
 * notifyDataChange触发第一屏和当前屏刷新
 */

public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private DiskCacheStrategy mStrategy = DiskCacheStrategy.NONE;
    private boolean mSkipMemoryCache = true;

    private List<String> mData = new ArrayList<>();

    public ListViewAdapter(Context context, String cacheStrategy) {
        mContext = context;

        if("disk".equals(cacheStrategy)) {
            mStrategy = DiskCacheStrategy.RESULT;
        } else if("memory".equals(cacheStrategy)) {
            mSkipMemoryCache = false;
        }

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
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return mData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //分离绑定职责
        ViewHolder holder;
        Log.i("libiao", "getView : " + position);
        if (convertView == null) {
            //Log.i("libiao", "convertView == null : " + position);
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyDataSetChanged();
                }
            });

        } else {
           // Log.i("libiao", "convertView : " + convertView.toString());
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bind(getItem(position), position);
        return convertView;
    }
    public class ViewHolder {
        private ImageView iv;
        public int position = -1;
        ViewHolder(View root){//通过构造方法将子view的查找逻辑搬移到这里
            iv = root.findViewById(R.id.iv_listview_item);
        }
        void bind(String url, int position){
            this.position = position;
            //绑定逻辑搬移到这里将更加简洁
            Glide.with(mContext).load(url).skipMemoryCache(mSkipMemoryCache).diskCacheStrategy(mStrategy).into(iv);
        }
    }
}
