package com.womenhz.swee.data.string;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class StringDemo {

    /**
     * 回文字符串
     *
     * abcdedcba size 9 0 - 8
     * 0 1 2 3  8 7 6 5
     *
     * */
    public static boolean isPalindrome(String str){

        if (str == null || str.equals("")){
            return true;
        }
        int mid = str.length() / 2;
        char[] chars = str.toCharArray();
        int len = chars.length;

        for (int i = 0; i < mid ; i++) {
            if (chars[i] != chars[len - 1 -i]) {
                return false;
            }
        }
        return true;
    }

    public static int longestSubStr(String str) {
        if (str == null || str.equals(""))
            return 0;

        Map<Character, Integer> map = new HashMap<>();
        char[] chars = str.toCharArray();
        int left = 0;
        int len = 0;
        int res = 0;
        for (int i = 0; i < chars.length; i++) {
            if (map.get(chars[i]) == null) {
                len++;
                map.put(chars[i], i);
            }else {
                left = map.get(chars[i]) + 1;
                log.info("left = "+left+" char = "+chars[i] + " i= "+i);
                map.put(chars[i], i);
                res = Math.max(res, len);
                len = i - left + 1;
                log.info("res= "+res);
                log.info("===================================");
            }
        }

        log.info("map= "+map);
        return Math.max(res, len);
    }

    public static void main(String[] args) {
        String str = "abcedcbf";
        log.info(isPalindrome(str));
       //log.info("sub len = "+maxUnique1(str) +" str len = "+str.length());
    }

    public static int maxUnique(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int[] map = new int[256];
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }
        int len = 0;
        int pre = -1;
        int cur = 0;
        for (int i = 0; i != chars.length; i++) {
            pre = Math.max(pre, map[chars[i]]);
            cur = i - pre;
            len = Math.max(len, cur);
            map[chars[i]] = i;
        }
        return len;
    }

    public static int maxUnique1(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }
        char[] chars = str.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
       /* for (int i = 0; i < 256; i++) {
            map.put(chars[i], -1);
        }*/
        int len = 0;
        int pre = -1;
        int cur = 0;
        for (int i = 0; i != chars.length; i++) {
            if (map.get(chars[i]) == null) {
                map.put(chars[i], i);
            }else {
                pre = Math.max(pre, map.get(chars[i]));
                cur = i - pre;
                len = Math.max(len, cur);
                map.put(chars[i], i);
            }
        }
        return len;
    }

    public static int matehod(String str) {
        if (str == null || str.equals(""))
            return 0;

        Map<Character, Integer> map = new HashMap<>();
        char[] chars = str.toCharArray();
        int left = 0;
        int len = 0;
        int res = 0;
        for (int i = 0; i < chars.length; i++) {
            if (map.get(chars[i]) == null) {
                len++;
                map.put(chars[i], i);
            }else {
                left = map.get(chars[i]) + 1;
                map.clear();
                map.put(chars[i], i);
                res = Math.max(res, len);
                len = i - left + 1;
            }
        }
        return Math.max(res, len);
    }

}
