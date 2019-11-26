package com.womenhz.swee.data.trie;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class CommonTrieNode {

    private int path;

    private int end;

    private Map<String, Object> map;

    public CommonTrieNode() {
        this.path = 0;
        this.end = 0;
        this.map = new HashMap<>();
    }
}
