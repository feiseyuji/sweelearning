package com.womenhz.swee.data.map;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BSTMapTest {

    public static void main(String[] args) {
        BSTMap<String, String> map = new BSTMap<>();

        map.put("6", "tom");
        map.put("2", "jerry");
        map.put("7", "hanmeimei");

        log.info("map= "+map);

        map.remove("2");

        log.info("map= "+map);
    }
}
