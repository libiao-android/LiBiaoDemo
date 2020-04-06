package com.libiao.libiaodemo.jni.demo;

import android.util.Log;

public class JNITest {

    static {
        System.loadLibrary("jnilib");
    }

    public native String getString();

    public native String avformatInfo();
    public native String avcodecInfo();
    public native String avfilterInfo();
    public native String configurationInfo();
    public native int run(String[] commands);
    public native int getProgress();

    public native byte[] audioMix(byte[] sourceA, byte[] sourceB, byte[] dst, float firstVol, float secondVol);


    public void addWaterMark(String srcFile, String waterMark, String targetFile) {
        Log.i("libiao", "addWaterMark begin");
        String waterMarkCmd = "ffmpeg -d -i %s -i %s -filter_complex overlay=100:100 %s";
        waterMarkCmd = String.format(waterMarkCmd, srcFile, waterMark, targetFile);
        int result = run(waterMarkCmd.split(" "));//以空格分割为字符串数组
        Log.i("libiao", "addWaterMark done = " + result);
    }


    /**
     * 关于视频合成的一些问题
     *
     * 1. 前后2个视频的video fps不一样，有关系吗？
     * 没有关系。底层decoder不会因为fps有变化，就不解码。做render的时候，是根据pts做显示，不会用fps。(fps一般用于pts没有，或者出错时，decoder根据fps重新计算pts，送给render。)
     *
     * 2. 前后2个视频的video codec不一样，有关系吗？
     * 有关系。底层decoder会根据不同的codec建立真正的decoder实例，如AVCDecoder, HEVCDecoder，到解码完成前，这个无法实时切换。
     *
     * 3. 前后2个视频的video 分辨率不一样，有关系吗？
     * 有关系。底层decoder解码时，会根据分辨率等参数，创建解码后的Output Buffer, 假设分辨率突然变大了，那buffer大小就不够，无法存储，会崩溃。
     *
     * 4. Audio sample rate /channel number不一样有关系吗？
     * 有关系。因为这2个参数，在播放音频时，底层建立decoder，就需要设置这2个参数，如果解码到一半，突然变了，一声道变成2声道了，或者sample rate从44.1k变成32k了，
     * 那有些厂商如果不支持sample rate动态变化，就会出错，最终效果，很可能是播放时变成杂音。
     */



    public void concat(String src, String target) {

        //首先第一个问题是就不应该考虑 ffmpeg 的 concat。ffmpeg 的 concat 是非常原始的连接，对于容器格式不是很适用。
        //然后要合并文件，要考虑的是目标文件格式支持哪些特性。
        //比如说你要合并成 mp4，那么流的格式就有限定（比如 ass 字幕就不可能合并），流本身也有限定，比如把一个 avc 的视频和一个 hevc 的视频接在一起大概率是要崩的。不同参数编码出来的视频放在一起也有很大概率会崩。

        //ffmpeg -f concat -i filelist.txt -c copy output.mkv

        Log.i("libiao", "concat begin");
        String concatCmd = "ffmpeg -f concat -safe 0 -i %s -c copy %s";
        concatCmd = String.format(concatCmd, src, target);
        int result = run(concatCmd.split(" "));//以空格分割为字符串数组
        Log.i("libiao", "concat done = " + result);
    }

    public void getVideoInfo(String path) {
        String cmd = "ffmpeg -i %s";
        cmd = String.format(cmd, path);
        //run(cmd.split(" "));
    }
}
