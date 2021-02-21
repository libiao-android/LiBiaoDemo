package com.libiao.libiaodemo.huaweimianshi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        List<Integer> array = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        if(line == null) {System.out.println(0);}
        String[] values = line.split(" ");
        for(int d = 0; d < values.length; d++) {
            String v = values[d].trim();
            try {
                array.add(Integer.parseInt(v));
            } catch (Exception e) {}
        }
        int maxAll = 0;
        int maxDay;
        for(int i = 0; i < array.size() - 1; i++) {
            maxDay = findMax(i+1, array) - array.get(i);
            if(maxDay > maxAll) {
                maxAll = maxDay;
            }
        }
        System.out.println(maxAll);
    }

    private static int findMax(int i, List<Integer> array) {
        int max = array.get(i);
        for(; i < array.size(); i++) {
            if(array.get(i) > max) {
                max = array.get(i);
            }
        }
        return max;
    }
}