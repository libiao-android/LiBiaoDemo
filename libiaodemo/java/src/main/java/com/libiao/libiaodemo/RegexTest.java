package com.libiao.libiaodemo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class RegexTest {

    public static void main(String[] args) {
       String value = "【开门迎财送红包喽！最低66元最高200元惊喜大红包等你来领！】 https:/actevo/201909-invite-packet/index.html?aid=20190926190926#/empty?urlToken=210268fec54c3f25a34c45af9172ee82 点击链接或者复制整段描述¥m_T0MqV¥后打开平安金管家APP";
       String value1= "【开门迎财送红包喽！最低66元最高200元惊喜大红包等你来领！】 https:/actevo/201909-invite-packet/index.html?aid=20190926190926#/empty?urlToken=9af9f1b1902fc4ead2f1b5b2b4317aaa 点击链接或者复制这段描述￥m_T0MqV￥后打开平安金管家APP";

       Pattern p = Pattern.compile("【(.*)】\\s(.*)\\s.*¥(.*)¥.*");
        Matcher m = p.matcher(value);
        System.out.println(m.matches());

        System.out.println(m.groupCount());
        System.out.println(m.group(1));
        System.out.println(m.group(2));
        System.out.println(m.group(3));
    }
}
