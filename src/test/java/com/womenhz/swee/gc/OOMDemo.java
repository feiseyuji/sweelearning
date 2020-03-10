package com.womenhz.swee.gc;

import java.util.Random;

public class OOMDemo {

    public static void main(String[] args) {
        String string = "kkk";
        while (true) {
            string += string+new Random().nextInt(11111111)+new Random().nextInt(22222222);
            string.intern();
        }
    }

}
