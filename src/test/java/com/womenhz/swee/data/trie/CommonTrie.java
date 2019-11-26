package com.womenhz.swee.data.trie;

import java.util.Map;
import lombok.Data;

@Data
public class CommonTrie {

    private CommonTrieNode root;

    public CommonTrie() {
        this.root = new CommonTrieNode();
    }


    /**
     * insert
     * */
    public void insert(String word) {
        if (word != null && word != "") {
            int length = word.length();
            CommonTrieNode current = root;
            String index;
            for (int i = 0; i < length; i++) {
                index = String.valueOf(word.charAt(i));
                Map<String, Object> tempMap = current.getMap();
                if (tempMap.get(index) == null) {
                    tempMap.put(index, new CommonTrieNode());
                }
                current = (CommonTrieNode) tempMap.get(index);
                int path = current.getPath();
                current.setPath(++path);
            }
            int end = current.getEnd();
            current.setEnd(++end);
        }
    }

    /**
     * delete
     * */
    public void delete(String word) {
        if (search(word)) {//先查询
            int length = word.length();
            CommonTrieNode current = root;
            String index;
            for (int i = 0; i < length; i++) {
                index = String.valueOf(word.charAt(i));
                Map<String, Object> tempMap = current.getMap();
                int path = ((CommonTrieNode)tempMap.get(index)).getPath();
                if (path-- == 1) {
                    tempMap.put(index, null);
                }
                current = (CommonTrieNode) tempMap.get(index);
                current.setPath(--path);
            }
            int end = current.getEnd();
            current.setPath(--end);
        }
    }

    /**
     * search
     * */
    public boolean search(String word) {
        if (word != null && word != "") {
            int length = word.length();
            CommonTrieNode current = root;
            String index;
            for (int i = 0; i < length; i++) {
                index = String.valueOf(word.charAt(i));
                Map<String, Object> tempMap = current.getMap();
                if (tempMap.get(index) == null) {
                    return false;
                }
                current = (CommonTrieNode) tempMap.get(index);
            }
            int end = current.getEnd();
            return end != 0;
        }
        return false;
    }

    /**
     * prefixNum
     * */
    public int prefixNum(String word) {
        if (word != null && word != "") {//先查询
            int length = word.length();
            CommonTrieNode current = root;
            String index;
            for (int i = 0; i < length; i++) {
                index = String.valueOf(word.charAt(i));
                Map<String, Object> tempMap = current.getMap();
                if (tempMap.get(index) == null) {
                    return 0;
                }
                current = (CommonTrieNode) tempMap.get(index);
            }
            return current.getPath();
        }
        return 0;
    }

}
