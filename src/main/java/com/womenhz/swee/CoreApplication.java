package com.womenhz.swee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication
{
    public static void main( String[] args )
    {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
    	SpringApplication.run(CoreApplication.class, args);
    	System.out.println("hello swee！");
	}
    
}
