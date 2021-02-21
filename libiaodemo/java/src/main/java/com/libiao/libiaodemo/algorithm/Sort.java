package com.libiao.libiaodemo.algorithm;

public class Sort {

    public int a[] = {2,4,5,3,8,1,9,7};

    public void maoPao() {
        for(int i = 0; i < a.length - 1; i++) {
            for(int j = 0; j < a.length - 1 - i; j++) {
                if(a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }
    }

    public void chaRu() {
        int j;
        for(int i = 1; i < a.length; i++) {
            int tmp = a[i];
            for(j = i; j>0 && a[j]<a[j-1]; j--) {
                a[j] = a[j-1];
            }
            a[j] = tmp;
        }
    }

    public void mergeSort() {
        int tmp[] = new int[a.length];
        int left = 0;
        int right = a.length - 1;
        mergeSort(a, tmp, left, right);
    }
    public void mergeSort(int[] a, int[] tmp, int left, int right) {
        if(left < right) {
            int center = (left + right) / 2;
            mergeSort(a, tmp, left, center);
            mergeSort(a, tmp, center + 1, right);
            merge(a, tmp, left, center + 1, right);
        }
    }
    private void merge(int[] a, int[] tmp, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int length = rightEnd - leftPos + 1;
        int tmpPos = leftPos;
        while(leftPos <= leftEnd && rightPos <= rightEnd) {
            if(a[leftPos] <= a[rightPos]) {
                tmp[tmpPos++] = a[leftPos++];
            } else {
                tmp[tmpPos++] = a[rightPos++];
            }
        }
        while(leftPos <= leftEnd) {
            tmp[tmpPos++] = a[leftPos++];
        }
        while(rightPos <= rightEnd) {
            tmp[tmpPos++] = a[rightEnd++];
        }
        for(int i = 0; i < length; i++, rightEnd --) {
            a[rightEnd] = tmp[rightEnd];
        }
    }

    public void quickSort(int a[], int leftIndex, int rightIndex) {
        if(leftIndex >= rightIndex) return;
        int left = leftIndex;
        int right = rightIndex;
        int key = a[left];
        while(left < right) {
            while(right > left && a[right] >= key) {right--;}
            while(right > left && a[left] <= key) {left++;}
            if(left < right) {
                int tmp = a[left];
                a[left] = a[right];
                a[right] = a[tmp];
            }
        }
        a[leftIndex] = a[left];
        a[left] = key;
        quickSort(a, leftIndex, left - 1);
        quickSort(a, right + 1, rightIndex);
    }

    public int binarySearch(int array[], int n) {
        int left = 0;
        int right = array.length - 1;
        while(left <= right) {
            int mid = (left + right) / 2;
            if(array[mid] == n) {
                return mid;
            } else if(array[mid] > n) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public int Fibonacci(int n) {
        if(n == 0) {return 0;}
        if(n == 1) {return 1;}
        int first = 0;
        int second = 1;
        int f = 0;
        for(int i = 2; i <= n; i++) {
            f = first + second;
            first = second;
            second = f;
        }
        return f;
    }
}
