package com.womenhz.swee.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.assertj.core.util.Lists;
import com.womenhz.swee.crawler.fetcher.Fetcher;
import com.womenhz.swee.crawler.parser.CityParser;
import com.womenhz.swee.crawler.parser.ProfileParser;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CrawlerMain {

    //单体爬虫
    /**
     * 1. 传入种子url，获取页面所有其他url保存
     * 2. 遍历循环获取到url拿到所有数据
     *
     *
     * */



   // <a href="http://www.zhenai.com/zhenghun/huangshi" data-v-5e16505f>黄石</a>

    //public final static  Pattern cityPattern1 = Pattern.compile("<a href=\".+?\"")
    //并发爬虫
    public static void main(String[] args) throws IOException {
        String content = Fetcher.getUrlContent("https://www.zhenai.com/zhenghun");
        log.info("content= "+content );
        List<String> seeds = Lists.newArrayList();
        seeds.add(content);
        ProfileParser.parserProfile(CityParser.cityParser(seeds));
        Collections.synchronizedList(com.google.common.collect.Lists.newArrayList());
       // Collections.synchronizedMap();
    }


}
