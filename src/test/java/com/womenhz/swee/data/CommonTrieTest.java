package com.womenhz.swee.data;

import org.junit.Test;
import com.womenhz.swee.data.trie.CommonTrie;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommonTrieTest {

    @Test
    public void test(){
        CommonTrie commonTrie = new CommonTrie();
        log.info(commonTrie);
        commonTrie.insert("你");
        log.info(commonTrie);
        commonTrie.insert("你是小煞笔日本人");
        log.info(commonTrie);
        log.info(commonTrie.search("你"));

        String a = "小";
        String b = new String("小");
        log.info(a.hashCode() +" "+b.hashCode());
    }
}
