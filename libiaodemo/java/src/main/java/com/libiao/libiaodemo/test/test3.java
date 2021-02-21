package com.libiao.libiaodemo.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class test3 {

    //约瑟夫环

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        System.out.println("please input totalnum: ");
        int totalNum = in.nextInt();
        System.out.println(new Integer(1).equals(new Long(1)));
        int num = in.nextInt();

        LinkedList<Integer> numList = new LinkedList<>();
        for(int i = 0; i< totalNum; i++) {
            numList.addLast(i+1);
        }

        int count = 0;
        while(numList.size() != 0) {
            int a = numList.poll();
            count++;
            if(count % num == 0) {
                System.out.print(a+" ");
            } else {
                numList.addLast(a);
            }
        }

        System.out.println();
        System.out.println("====================");

        Node head = new Node();
        Node cur = head;
        for(int i = 1; i<= totalNum; i++) {
            Node node = new Node(i);
            cur.next = node;
            cur = node;
        }
        cur.next = head.next;

        Node p = head.next;
        while(p.next != p) {
            for(int i = 1; i< num - 1; i++) {
                p = p.next;
            }
            System.out.print(p.next.data + " ");
            p.next = p.next.next;
            p = p.next;
        }
        System.out.print(p.data + " 1");
    }

    static class Node {
        int data;
        Node next;
        public Node(){}
        public Node(int data){this.data = data;}
    }
}
