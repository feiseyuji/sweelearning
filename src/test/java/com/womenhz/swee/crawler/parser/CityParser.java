package com.womenhz.swee.crawler.parser;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CityParser {

    public final static Pattern cityPattern = Pattern.compile("<a href=\"(http://www.zhenai.com/zhenghun/[0-9a-z]+)\"[^>]*>([^<]+)</a>");

    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static List<String> cityParser(List<String> seeds) {
        List<String> cityUrls = new CopyOnWriteArrayList<>();
        for (String seed : seeds) {
            Matcher matcher = cityPattern.matcher(seed);
            executorService.execute(() -> {
                while (matcher.find()) {
                    //log.info(matcher.group()+"\n");
                    log.info(matcher.group(1)+"\n");
                    cityUrls.add(matcher.group(1));
                    //log.info(matcher.group(2)+"\n");
                }
            });
        }
        return cityUrls;
    }
}
