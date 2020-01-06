package com.libiao.libiaodemo.test;

import java.util.HashMap;
import java.util.Map;

public class test2 {

    public static void main(String[] args) {
        Map<String, Object> maps = new HashMap<>();
        Map<String, bean> strs = new HashMap<>();
        strs.put("11", new bean());
        maps.put("li", strs);
        Map<String, bean> o = (Map<String, bean>)maps.get("li");
        System.out.println(o.get("11").b);
    }



    public static class bean {
        public int a = 1;
        public String b = "2";

    }
}
