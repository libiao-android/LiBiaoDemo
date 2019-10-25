package com.libiao.libiaodemo.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class test1 {

    private static final String TAG = "PicUtils";
    private static final String PNG = "png";
    private static final String JPG = "jpg";
    private static final String JPEG = "jpeg";
    private static final String BMP = "bmp";
    // private static final String[] imgSuffixes = {PNG, JPG, JPEG, BMP};
    private static final List<String> fileSuffixes = Arrays.asList(PNG, JPG, JPEG, BMP);
    // 缓存文件头信息-文件头信息
    private static final Map<String, String> mFileTypes = new HashMap<String, String>();

    static {
        // images
        mFileTypes.put("FFD8FFE0", JPG);
        mFileTypes.put("89504E47", PNG);
        mFileTypes.put("424D5A52", BMP);
    }
    public static void main(String[] args) {
        String a = null;
        System.out.println("aa = " + (System.currentTimeMillis()));

        String key = "a.b.c";
        key = key.replace(".", "_");

        System.out.println("key = " + key);

        String head1 = getFileHeader("/Users/libiao657/Desktop/one.png");
        System.out.println("head = " + head1);

        String head2 = getFileHeader("/Users/libiao657/Desktop/one2.jpg");
        System.out.println("head = " + head2);

        String head3 = getFileHeader("/Users/libiao657/Desktop/two.png");
        System.out.println("head = " + head3);

        try {
            BufferedImage bufferedImage = ImageIO.read(new File("/Users/libiao657/Desktop/two.png"));
            if (bufferedImage != null && bufferedImage.getColorModel() != null && !bufferedImage.getColorModel().hasAlpha()) {
                System.out.println("没有alpha通道");
            }
            for (int i = 0; i < bufferedImage.getHeight(); i++) {
                for (int j = 0; j < bufferedImage.getWidth(); j++) {
                    if (isTransparent(bufferedImage, j, i)){
                        System.out.println("有alpha通道");
                        break;
                    }
                }
                System.out.println("wai cheng xun huan");
            }
            BufferedImage newBuffer = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        }catch (Exception e) {

        }
    }

    public static boolean isTransparent(BufferedImage image, int x, int y ) {
        int pixel = image.getRGB(x,y);
        return (pixel>>24) == 0x00; //透明通道在高8位，根据其是否为0判断是否包含透明通道
    }


    private static String getFileHeader(String filePath) {
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[4];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return "";
        }
        for (int i = 0; i < src.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            String hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }
}
