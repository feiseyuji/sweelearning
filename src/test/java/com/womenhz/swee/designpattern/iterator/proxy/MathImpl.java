package com.womenhz.swee.designpattern.iterator.proxy;

public class MathImpl implements IMath {

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
