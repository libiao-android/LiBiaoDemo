package libiao.libiaodemo.android.ui.video.videoeditor.media;


public class AudioJniUtils {

    static {
        System.loadLibrary("native-lib");
    }
    public static native byte[] audioMix(byte[] sourceA, byte[] sourceB, byte[] dst, float firstVol, float secondVol);

}
