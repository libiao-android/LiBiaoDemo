package com.libiao.libiaodemo.algorithm;

import java.util.Stack;

public class Traversal {

    public void preOrder(Node root) {
        if(root != null) {
            System.out.print(root.value + " ");
            preOrder(root.left);
            preOrder(root.right);
        }
    }
    public void preOrder2(Node root) {
        Stack<Node> stack = new Stack<>();
        while(root != null || !stack.isEmpty()) {
            if(root != null) {
                System.out.print(root.value + " ");
                stack.push(root);
                root = root.left;
            } else {
                Node tmp = stack.pop();
                root = tmp.right;
            }
        }
    }

    public void inOrder(Node root) {
        if(root != null) {
            inOrder(root.left);
            System.out.print(root.value + " ");
            inOrder(root.right);
        }
    }
    public void inOrder2(Node root) {
        Stack<Node> stack = new Stack<>();
        while(root != null || !stack.isEmpty()) {
            if(root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                System.out.print(root.value + " ");
                root = root.right;
            }
        }
    }

    public void postOrder(Node root) {
        if(root != null) {
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.value + " ");
        }
    }
    public void postOrder2(Node root) {
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        Node cur, pre = null;
        while(!stack.isEmpty()) {
            cur = stack.peek();
            if(cur.left == null && cur.right == null || (pre != null && (pre == cur.left || pre == cur.right))) {
                System.out.print(cur.value + " ");
                stack.pop();
                pre = cur;
            } else {
                if(cur.right != null) {
                    stack.push(cur.right);
                }
                if(cur.left != null) {
                    stack.push(cur.left);
                }
            }
        }
    }
}
