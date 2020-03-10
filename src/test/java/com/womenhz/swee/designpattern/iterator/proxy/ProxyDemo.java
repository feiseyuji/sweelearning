package com.womenhz.swee.designpattern.iterator.proxy;

public class ProxyDemo {

    public static void main(String[] args) {
        MathImpl math = new MathImpl();
        ProxyUtil proxyUtil = new ProxyUtil(math);
       IMath iMath = (IMath)proxyUtil.getProxy();
       iMath.add(2, 3);
    }

}
