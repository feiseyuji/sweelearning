package com.womenhz.swee.designpattern.iterator.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProxyUtil{

    private MathImpl mathImpl;

    public ProxyUtil(MathImpl mathImpl) {
        this.mathImpl = mathImpl;
    }

    public Object getProxy() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Class<?>[] clazz = mathImpl.getClass().getInterfaces();

        return Proxy.newProxyInstance(classLoader, clazz, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                log.info("start");
                Object res = method.invoke(mathImpl, args);
                log.info("res = "+res);
                log.info("end");
                return res;
            }
        });
    }

}
