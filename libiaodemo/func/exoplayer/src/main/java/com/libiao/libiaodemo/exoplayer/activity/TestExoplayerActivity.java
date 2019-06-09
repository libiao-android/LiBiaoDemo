package com.libiao.libiaodemo.exoplayer.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.libiao.libiaodemo.exoplayer.R;

/**
 * Description:
 * Data：2019/6/1-下午3:09
 * Author: libiao
 */
public class TestExoplayerActivity extends Activity {

    private PlayerView mPlayerView;
    private ExoPlayer mPlayer;
    private String path = "https://www.apple.com/105/media/cn/home/2018/da585964_d062_4b1d_97d1_af34b440fe37/films/behind-the-mac/mac-behind-the-mac-tpl-cn_848x480.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exoplayer_main);
        initView();
    }

    private void initView() {
        mPlayerView = (PlayerView) findViewById(R.id.player_view);
        mPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultRenderersFactory(this), new DefaultTrackSelector(), new DefaultLoadControl());
        mPlayerView.setPlayer(mPlayer);
        MediaSource source = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab")).createMediaSource(Uri.parse(path));
        mPlayer.prepare(source, false, true);
    }
}
