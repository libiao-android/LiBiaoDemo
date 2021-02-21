package com.libiao.libiaodemo.huaweimianshi;

import java.util.LinkedList;

public class Test2 {

    //给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
    //
    //有效字符串需满足：
    //
    //左括号必须用相同类型的右括号闭合。
    //左括号必须以正确的顺序闭合。
    //注意空字符串可被认为是有效字符串。
    //
    //示例 1:
    //输入: "()"
    //输出: true
    //
    //示例 2:
    //输入: "()[]{}"
    //输出: true
    //
    //示例 3:
    //输入: "(]"
    //输出: false
    //
    //示例 4:
    //输入: "([)]"
    //输出: false
    //
    //示例 5:
    //输入: "{[]}"
    //输出: true

    public static void main(String[] args) {
        boolean valid = isValidString(" (  )".trim());
        System.out.println("valid: " + valid);
    }

    private static boolean isValidString(String str) {
        if(str == null || str.length() < 1) {return false;}
        LinkedList<Character> stack = new LinkedList<>();
        stack.push(str.charAt(0));
        for(int i = 1; i < str.length(); i++) {
            if(str.charAt(i) == ' ') continue;
            System.out.println(str.charAt(i));
            char c = stack.peek();
            System.out.println("c: " + c);
            if(isPair(c, str.charAt(i))) {
                stack.pop();
            } else {
                stack.addLast(str.charAt(i));
            }
        }
        if(stack.size() > 0) return false;
        return true;
    }

    private static boolean isPair(char a, char b) {
        if(a == '(' && b == ')') return true;
        if(a == '[' && b == ']') return true;
        if(a == '{' && b == '}') return true;
        return false;
    }
}
