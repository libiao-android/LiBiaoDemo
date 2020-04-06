package com.libiao.libiaodemo.dfa;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DfaHelper {

    HashMap<String, Map<String, String>> sensitiveWordMap;

    public void createSensitiveWordMap(List<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());
        Map currentMap = null;
        Map<String, String> newWorMap = null;
        Iterator<String> iterator = keyWordSet.iterator();
        String key = null;
        int charCount = 0;
        while(iterator.hasNext()) {
            key = iterator.next().toLowerCase();
            //System.out.println(key);
            currentMap = sensitiveWordMap;
            for(int i = 0 ; i < key.length() ; i++) {
                char keyChar = key.charAt(i);
                Object wordMap = currentMap.get(keyChar);
                if(wordMap != null) {
                    currentMap = (Map) wordMap;
                } else {
                    newWorMap = new HashMap<String,String>();
                    currentMap.put(keyChar, newWorMap);
                    charCount++;
                    currentMap = newWorMap;
                }
                if(i == key.length() - 1) {
                    currentMap.put("isEnd", "Y");
                }
            }
        }
        System.out.println(charCount);
        long total = 0;
        total = charCount * (40 + 2);
        System.out.println("total = " + total);
        total += (keyWordSet.size() * (40 + 2 * 5 + 40 + 2 * 1));
        System.out.println("total = " + total);
    }

    public void checkSensitiveWord(String word) {
        char keyChar;
        word = word.replace(" ", "").toLowerCase();
        int num;
        StringBuilder builder;
        for(int i = 0; i < word.length(); i++){
            keyChar = word.charAt(i);
            if (sensitiveWordMap.containsKey(keyChar)) {
                builder = new StringBuilder();
                builder.append(keyChar);
                Map m =  sensitiveWordMap.get(keyChar);
                num = i + 1;
                while (true) {
                    if ("Y".equals(m.get("isEnd"))) {
                        System.out.println("check true = " + builder.toString());
                        return;
                    }
                    if (num < word.length()) {
                        keyChar = word.charAt(num++);
                        if (m.containsKey(keyChar)){
                            builder.append(keyChar);
                            m = (Map) m.get(keyChar);
                        } else {break;}
                    } else {break;}
                }
            }
        }
    }
}
