package libiao.libiaodemo.android.ui.danmu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import libiao.libiaodemo.R;

public class DanmuActivity extends Activity {

    private DanmuView mDanmuView;
    private List<View> mViewList;
    private String[] mStrItems = {"搜狗", "百度", "腾讯", "360", "阿里巴巴", "搜狐", "网易", "新浪", "搜狗-上网从搜狗开始", "百度一下,你就知道", "必应搜索-有求必应", "好搜-用好搜，特顺手", "Android-谷歌", "IOS-苹果", "Windows-微软", "Linux"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danmu_2_main);
        initDanmuView();
    }

    private void initDanmuView() {
        mDanmuView = findViewById(R.id.danmu_view);
        List<DanmuBean> danmuBeans = new ArrayList<>();
        danmuBeans.add(new DanmuBean("11111111111", "#554455"));
        danmuBeans.add(new DanmuBean("3333333333", "#335533"));
        danmuBeans.add(new DanmuBean("44444444444444", "#333663"));
        danmuBeans.add(new DanmuBean("555555555555555555", "#331133"));
        danmuBeans.add(new DanmuBean("666666666", "#339993"));
        danmuBeans.add(new DanmuBean("777777777777", "#355533"));
        mDanmuView.initDanmuItemViews(danmuBeans);
        mDanmuView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDanmuView.stop();
    }
}
