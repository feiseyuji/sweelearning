package com.womenhz.swee.nio;


import io.undertow.Undertow;

public class UndertowDemo {

    public static void main(String[] args) {
        Undertow seerver = Undertow.builder().build();
    }

}
