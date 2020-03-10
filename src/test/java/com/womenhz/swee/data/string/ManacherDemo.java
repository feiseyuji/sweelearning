package com.womenhz.swee.data.string;

public class ManacherDemo {


    public char[] manacherString(String str) {
        char[] chars = str.toCharArray();
        char[] result = new char[chars.length * 2 + 1];
        int index = 0;
        for (int i = 0; i < result.length; i++) {
             result[i] = (i & 1) == 0 ? '#' : chars[index++];
        }
        return result;
    }
}
