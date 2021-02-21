package com.libiao.libiaodemo.huaweimianshi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

//3
//AYL888,04-03 08:30:00,25
//AYL888,03-03 08:30:00,25
//ZAL888,03-02 18:27:08,34
//ZAL887,03-02 18:27:08,34
//ZAL885,03-02 18:27:08,34
//ZAL889,03-02 18:27:08,34
//ZAL,03-02 18:27:08,34
//ALZ,03-02 18:27:08,34

public class Main2 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int month = in.nextInt();
        //in.nextLine();
        List<CarInfo> infoList = new ArrayList<>();
        while(in.hasNextLine()) {
            String line = in.nextLine();
            if(line == null || line.equals("")) continue;
            String[] values = line.split(",");
            if(values.length == 3) {
                try{
                    String monthInfo = values[1];
                    String m = monthInfo.substring(0, 2);
                    if(month == Integer.parseInt(m)) {
                        String name = values[0];
                        String t = values[2];
                        CarInfo info = findCar(name, infoList);
                        if(info != null) {
                            info.count++;
                            info.time += Integer.parseInt(t);
                        } else {
                            CarInfo carInfo = new CarInfo();
                            carInfo.carName = name;
                            carInfo.time = Integer.parseInt(t);
                            carInfo.count = 1;
                            infoList.add(carInfo);
                        }
                    }
                }catch (Exception e) {}
            }

            Collections.sort(infoList, new Comparator<CarInfo>() {
                @Override
                public int compare(CarInfo car1, CarInfo car2) {
                    if(car1.time > car2.time) {
                        return -1;
                    } else if(car1.time < car2.time) {
                        return 1;
                    } else {
                        if(car1.count > car2.count) {
                            return -1;
                        } else if(car1.count < car2.count) {
                            return 1;
                        } else {
                            for(int i = 0; i < car1.carName.length() && i< car2.carName.length(); i++) {
                                if(car1.carName.charAt(i) > car2.carName.charAt(i)) {
                                    return 1;
                                } else if(car1.carName.charAt(i) < car2.carName.charAt(i)){
                                    return -1;
                                }
                            }
                            if(car1.carName.length() > car2.carName.length()) {
                                return 1;
                            } else if(car1.carName.length() < car2.carName.length()){
                                return -1;
                            }
                            return 0;
                        }
                    }
                }
            });
            int len = infoList.size();
            if(len > 5) {
                len = 5;
            }
            for(int i = 0; i < len; i++) {
                System.out.println(infoList.get(i));
            }
        }
    }

    private static CarInfo findCar(String name, List<CarInfo> infoList) {
        for(CarInfo info : infoList) {
            if(info.carName.equals(name)) return info;
        }
        return null;
    }

    static class CarInfo {
        public String carName;
        public int time;
        public int count;
        public CarInfo(){}

        @Override
        public String toString() {
            return carName + "," + time + "," + count;
        }
    }
}
