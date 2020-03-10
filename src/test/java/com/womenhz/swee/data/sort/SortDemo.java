package com.womenhz.swee.data.sort;

import java.util.Arrays;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SortDemo {

    public static void main(String[] args) {

        int[] a1 = {2, 4, 5, 8, 9};
        int[] a2 = {1, 3, 4, 7 };
        int[] a = mergeArray(a1, a2);
        int[] arr = {6, 1, 3, 2, 4, 7};
        //int[] b = mergeSort(arr, 6);
        //quickSort(arr);
        //insertSort(arr);
        shellSort(arr);
       for (int i : arr) {
           log.info(i);
       }
    }

    public static int[] mergeArray(int[] a1, int[] a2){
       int[] arr = null;
       if (a1 == null) {
           arr = a2;
           return arr;
       }
       if (a2 == null) {
           arr = a1;
           return arr;
       }

       int len1 = a1.length;
       int len2 = a2.length;
       arr = new int[len1 + len2];
       int k1 = 0;
       int k2 = 0;
       int k = 0;
       while (k1 < len1 && k2 < len2) {
           if (a1[k1] < a2[k2]) {
               arr[k] = a1[k1];
               k++;
               k1++;
           }else {
               arr[k] = a2[k2];
               k++;
               k2++;
           }
       }

       while (k1 < len1) {
           arr[k++] = a1[k1++];
       }

       while (k2 < len2){
           arr[k++] = a2[k2++];
       }

       return arr;
    }

    public static int[] mergeSort(int[] a, int n) {
        if (a == null || n == 0) {
            return a;
        }
        int mid = n / 2;

        int[] arr1 = mergeSort1(a, 0, mid);
        int[] arr2 = mergeSort1(a, mid + 1, n - 1);
        return mergeArray(arr1, arr2);
    }

    private static int[] mergeSort1(int[] a, int le, int re) {
        if (le < re) {
            int mid = (re + le) / 2;
            return mergeArray(mergeSort1(a, le, mid), mergeSort1(a, mid + 1, re ));
        }
        if (le == re) {
            int[] arr = {a[le]};
            return arr;
        }
        return null;
    }

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return ;
        }
        quickSort1(arr, 0, arr.length - 1);
    }

    private static void quickSort1(int[] arr, int le, int re) {
        if (le >= re)
            return;
        int p = partion(arr, le, re);
        quickSort1(arr, le, p - 1);
        quickSort1(arr, p + 1, re);
    }

    private static int partion(int[] arr, int le, int re) {
        int p = le;
        int v = arr[le];
        for (int i = le + 1; i <= re ; i++) {
            if (arr[i] < v) {
                swap(arr,p + 1, i);
                p++;
            }
        }
        swap(arr, le, p);
        return p;
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    //insertSort
    public static void insertSort(int[] arr) {
        if(arr == null || arr.length == 0) {
            return;
        }
        //获取当前最大的有序数组的最后一个位置i，将i+1位置的数从i开始
        // 向前比较，找到第一个小于或等于的数i,放在i+1位置
        int length = arr.length;
        for (int i = 1; i < length; i++) {
                for (int j = i; j > 0; j--) {
                    if (arr[j] < arr[j - 1]){
                      swap(arr, j, j- 1);
                    }
                }
        }
    }

    //
    public static void shellSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int length = arr.length;
        int h = 0;
        while (h < length / 3) h = 3 * h + 1;
        while (h >= 1) {
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h; j -= h ) {
                    if (arr[j] < arr[j - h]) {
                        swap(arr, j, j - h);
                    }
                }
            }
            h = h / 3;
        }
    }


}
