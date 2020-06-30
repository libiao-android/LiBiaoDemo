package libiao.libiaodemo.android.ui.video;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import libiao.libiaodemo.R;

/**
 * Description:
 * Data：2019/5/27-下午1:50
 * Author: libiao
 */
public class VideoActivity extends Activity implements View.OnClickListener {

    private ImageView mCloseVideoIv;
    private View mPauseVideoCenterView;
    private ImageView mPlayIv;
    private TextView mPlayTimeTv;
    private TextView mPlayTotalTimeTv;

    private VideoView mVideoView;

    private SeekBar mSeekBar;

    private Handler mHandler = new Handler();

    private int mTotalTime = 0; //单位秒
    private int mPlayTime = 0;
    private boolean mBuffering = false;
    private boolean mOnPaused = false;
    private int mCurrentPosition = 0;

    private String path1 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private String path2 = "https://www.apple.com/105/media/cn/home/2018/da585964_d062_4b1d_97d1_af34b440fe37/films/behind-the-mac/mac-behind-the-mac-tpl-cn_848x480.mp4";
    private String path3 = "http://www.w3school.com.cn/example/html5/mov_bbb.mp4";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity_main);

        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mVideoView.isPlaying()) {
            pause();
            mOnPaused = true;
            mVideoView.pause();
            mCurrentPosition = mVideoView.getCurrentPosition();
            Log.i("libiao", "onPause mVideoView.getCurrentPosition() = " + mVideoView.getCurrentPosition());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mOnPaused) {
            mVideoView.seekTo(mCurrentPosition);
            mVideoView.start();
            Log.i("libiao", "onResume mVideoView.getCurrentPosition() = " + mVideoView.getCurrentPosition());
            mBuffering = false;
            mOnPaused = false;
        }
    }

    private void initView() {
        mPlayTimeTv = (TextView) findViewById(R.id.tv_video_time_ing);
        mPlayTotalTimeTv = (TextView) findViewById(R.id.tv_video_time_total);
        mPlayTotalTimeTv.setText(formatTime(mTotalTime));
        mCloseVideoIv = (ImageView) findViewById(R.id.iv_video_close);
        mCloseVideoIv.setOnClickListener(this);
        mPauseVideoCenterView = findViewById(R.id.view_video_pause);
        mPauseVideoCenterView.setOnClickListener(this);
        mPlayIv = (ImageView) findViewById(R.id.iv_video_play);
        mPlayIv.setOnClickListener(this);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar_video);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updatePlayTime(progress * mTotalTime / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("libiao", "onStartTrackingTouch");
                if(mVideoView.canSeekForward() && mVideoView.canSeekBackward()) {
                    Log.i("libiao", "canSeek");
                    mVideoView.pause();
                    pause();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("libiao", "onStopTrackingTouch");
                if(mVideoView.canSeekForward() && mVideoView.canSeekBackward()) {
                    Log.i("libiao", "canSeek");
                    mVideoView.seekTo(seekBar.getProgress() * mTotalTime * 10);
                    mPlayTime = seekBar.getProgress() * mTotalTime / 100;
                    mVideoView.start();
                    begin();
                }
            }
        });
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mVideoView.setVideoPath(path1);
        mVideoView.requestFocus();
        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i("libiao", "onPrepared = " + mVideoView.getDuration());
                mTotalTime = mVideoView.getDuration() / 1000;
                begin();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i("libiao", "onCompletion = ");
                VideoActivity.this.finish();
            }
        });
        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Log.i("libiao", "onInfo what = " + what);
                if(what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    mBuffering = true;
                    pause();
                } else if(what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    mBuffering = false;
                    begin();
                }
                return false;
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i("libiao", "onError what = " + what);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.iv_video_play) {
            if(mBuffering) return;
            if(mVideoView.isPlaying()) {
                mVideoView.pause();
                pause();
            } else {
                mVideoView.start();
                begin();
            }
        } else if(id == R.id.view_video_pause) {
            if(mBuffering) return;
            mVideoView.start();
            begin();
        } else if(id == R.id.iv_video_close) {
            this.finish();
        }
    }

    private void pause() {
        mPlayIv.setImageResource(R.mipmap.video_play);
        mPauseVideoCenterView.setVisibility(View.VISIBLE);
        mHandler.removeCallbacks(mUpdateProgress);
    }

    private void begin() {
        mPlayTotalTimeTv.setText(formatTime(mTotalTime));
        mPlayIv.setImageResource(R.mipmap.video_pause);
        mPauseVideoCenterView.setVisibility(View.GONE);
        mHandler.post(mUpdateProgress);
    }

    private Runnable mUpdateProgress = new Runnable() {
        @Override
        public void run() {
            mPlayTime++ ;
            updateProgress(mVideoView.getCurrentPosition());
            mHandler.postDelayed(this, 1000);
        }
    };

    private void updateProgress(int time) {
        int progress = time / (mTotalTime * 10);
        mSeekBar.setProgress(progress);
    }

    private void updatePlayTime(int time) {
        mPlayTimeTv.setText(formatTime(time));
    }

    private String formatTime(int playTime) {
        if(playTime < 10) {
            return "00:0" + playTime;
        }
        if(playTime < 60) {
            return "00:" + playTime;
        }
        int min = playTime / 60;
        int sec = playTime % 60;
        String minS = String.valueOf(min);
        if(min < 10) {
            minS = "0" + min;
        }
        String secS = String.valueOf(sec);
        if(sec < 10) {
            secS = "0" + sec;
        }
        return minS + ":" + secS;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mVideoView.stopPlayback();
        mVideoView = null;
    }
}
