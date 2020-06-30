package libiao.libiaodemo.android.ui.video.videoeditor;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import libiao.libiaodemo.R;
import libiao.libiaodemo.android.ui.video.videoeditor.media.Constants;
import libiao.libiaodemo.android.ui.video.videoeditor.media.MediaMuxerRunnable;
import libiao.libiaodemo.android.ui.video.videoeditor.media.VideoClipper;
import libiao.libiaodemo.android.ui.video.videoeditor.media.VideoInfo;

public class VideoEditorActivity extends Activity {

    private String path1;
    private String path2;

    private TextView path1Tv;
    private TextView path2Tv;

    private TextView waterTv;
    private TextView concatTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_editor_activity_main);

        path1Tv = findViewById(R.id.tv_path1);
        path2Tv = findViewById(R.id.tv_path2);

        waterTv = findViewById(R.id.tv_water_result_path);
        concatTv = findViewById(R.id.tv_concat_result_path);
    }

    public void chooseVideo(View v) {
        VideoSelectActivity.openActivityForResult(this, 100);
    }
    public void choose2Video(View v) {
        VideoSelectActivity.openActivityForResult(this, 101);
    }

    public void addWater(View v) {
        if(path1 == null) {
            Toast.makeText(this, "请先选择一个视频", Toast.LENGTH_SHORT).show();
            return;
        }
        VideoClipper clipper = new VideoClipper();
        clipper.setInputVideoPath(path1);
        String outputPath = Constants.getPath("video/clip/", System.currentTimeMillis() + ".mp4");
        clipper.setOutputVideoPath(outputPath);
        clipper.setOnVideoCutFinishListener(new VideoClipper.OnVideoCutFinishListener() {
            @Override
            public void onFinish() {
                Log.i("libiao", "VideoCutFinish");
                //Toast.makeText(VideoEditorActivity.this, "水印添加完成", Toast.LENGTH_SHORT).show();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(VideoEditorActivity.this, "水印添加完成", Toast.LENGTH_SHORT).show();
                        waterTv.setText("Done: " + outputPath);
                    }
                });
            }
        });

        try {
            clipper.clipVideo(0);
        } catch (Exception e) {
            Log.i("libiao", "Exception = " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void concat(View v) {
        if(path1 == null || path2 == null) {
            Toast.makeText(this, "请先选择视频", Toast.LENGTH_SHORT).show();
            return;
        }
        String base = Environment.getExternalStorageDirectory().getPath();
        String waterMark = base + "/demo.jpg";
        setDataSource(new String[]{path1, path2});
    }


    public void setDataSource(String[] dataSource) {

        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        ArrayList<VideoInfo> infoList = new ArrayList<>();
        for (int i = 0; i < dataSource.length; i++) {
            VideoInfo info = new VideoInfo();
            String path = dataSource[i];
            retr.setDataSource(path);
            String rotation = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            String width = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            String height = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            String duration = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            info.path = path;
            info.rotation = Integer.parseInt(rotation);
            info.width = Integer.parseInt(width);
            info.height = Integer.parseInt(height);
            info.duration = Integer.parseInt(duration);

            infoList.add(info);
        }
        String outputPath = Constants.getPath("video/output/", System.currentTimeMillis() + ".mp4");
        concatTv.setText("视频拼接中");
        MediaMuxerRunnable instance = new MediaMuxerRunnable();
        instance.setVideoInfo(infoList, outputPath);
        instance.addMuxerListener(new MediaMuxerRunnable.MuxerListener() {
            @Override
            public void onStart() {
                Log.i("libiao", "视频拼接开始");
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(VideoEditorActivity.this," 拼接完成", Toast.LENGTH_SHORT).show();
                        concatTv.setText("Done: " + outputPath);
                    }
                });
            }
        });
        instance.start();

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 100) {
                path1 = data.getStringExtra("path");
                path1Tv.setText(path1);
            } else if (requestCode == 101) {
                path2 = data.getStringExtra("path");
                path2Tv.setText(path2);
            }
        }
    }
}
