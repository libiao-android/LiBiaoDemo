package com.libiao.libiaodemo.test;

import com.libiao.libiaodemo.dfa.DfaHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test2 {

    public static void main(String[] args) {
        Map<String, Object> maps = new HashMap<>();
        Map<String, bean> strs = new HashMap<>();
        strs.put("11", new bean());
        maps.put("li", strs);
        Map<String, bean> o = (Map<String, bean>)maps.get("li");
        //System.out.println(o.get("11").b);

        HashMap<String, String> map = new HashMap<>();

        map.put(null, null);
        boolean b = map.containsKey(null);
        //System.out.println(b); //不会异常，返回true

        Pattern p;
        Matcher m;
        p = Pattern.compile("^[0-9]{6,}$");
        m = p.matcher("8888888");
       // System.out.println(m.matches());

        Set<String> wordSet = new HashSet<>();
       /* List<String> list = new ArrayList<>();
        //list.addAll(null); //异常
        wordSet.add("2321");
        wordSet.add("32");
        wordSet.add("1001");
        wordSet.add("ABFC");
        wordSet.add("美国人");*/
        List<String> list = readFileContent(new File("sensitive_word_51601.txt"));
        long total = 0;
        for(String s : list) {
            total += (40 + 2 * s.length());
        }
        total += (list.size() * (40 + 2 * 5 + 40 + 2 * 1));
        System.out.println("total = " + total);
        DfaHelper helper = new DfaHelper();
        helper.createSensitiveWordMap(list);
        long begin = System.currentTimeMillis();
        helper.checkSensitiveWord("中国与海外疫情江氏形势的改变，不仅让两个月来高江氏政强度抗“疫”的中国得以暂舒一口气，还改变江氏政治了中国的国际疫情和舆情环境。中国的抗“疫”局面江氏政治委解刚稳，就派出医疗队伍携带物资援助其他国家江氏政治委员。");
        long end = System.currentTimeMillis();
        System.out.println("cost = " + (end - begin));
    }

    private static List<String> readFileContent(File file) {
        try {
            InputStream is = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            List<String> list = new ArrayList<>();
            String str;
            while((str = br.readLine()) != null){
                list.add(str);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static class bean {
        public int a = 1;
        public String b = "2";

    }
}
