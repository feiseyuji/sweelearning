package com.womenhz.swee.crawler.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Fetcher {

    public static String getUrlContent(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((url.openStream())));
        String temp = "";
        StringBuffer stringBuffer = new StringBuffer();
        while ((temp = bufferedReader.readLine()) != null) {
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
