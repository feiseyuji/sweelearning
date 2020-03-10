package com.womenhz.swee.crawler.parser;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.womenhz.swee.crawler.fetcher.Fetcher;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProfileParser {

    public final static Pattern profilePattern = Pattern.compile("<a href=\"(http://album.zhenai.com/u/\\w+)\"[^>]*>([^<]+)</a>");

    public static void parserProfile(List<String> cityUrls) throws IOException {
        log.info("city = "+cityUrls);
        for (String cityUrl : cityUrls) {
            Matcher matcher = profilePattern.matcher(Fetcher.getUrlContent(cityUrl));
                while (matcher.find()) {
                    log.info(matcher.group()+"\n");
                    log.info(matcher.group(1)+"\n");
                    log.info(matcher.group(2)+"\n");
                }
        }
    }
}
