package com.womenhz.swee.data.trie;

import org.junit.Test;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CharTest {


    @Test
    public void testChar(){
        char a = 'a';
        log.info(--a);
        for (int i = 0; i < 26; i++) {
            a++;
            log.info("char= "+a);
        }
    }


    @Data
    class TrieNode {

        private int path;

        private int end;

        private TrieNode[] map;

        public TrieNode(){

            this.end = 0;

            this.path = 0;

            this.map = new TrieNode[26];
        }
    }

    @Data
    class Trie {

        private TrieNode root;

        public Trie(){
            this.root = new TrieNode();
        }

        void insert(String word) {
            if (word != null && word != "") {
                char[] chars = word.toCharArray();
                int index = 0;
                TrieNode current = root;
                for (char i : chars) {
                    index = i - 'a';
                    if (current.map[index] == null) {
                        current.map[index] = new TrieNode();
                    }
                    current = current.map[index];
                    current.path++;
                }
                current.end++;
            }
        }

        void delete(String word) {
            if (search(word)) {
                char[] chars = word.toCharArray();
                int index = 0;
                TrieNode current = root;
                for (char i : chars) {
                    index = i - 'a';
                    if (current.map[index].path-- == 1) {
                        current.map[index] = null;
                        return;
                    }
                    current = current.map[index];
                    current.path--;
                }
                current.end--;
            }
        }

        boolean search(String word) {
            if (word != null && word != "") {
                char[] chars = word.toCharArray();
                int index = 0;
                TrieNode current = root;
                for (char i : chars) {
                    index = i - 'a';
                    if (current.map[index] == null) {
                        return false;
                    }
                    current = current.map[index];
                }
                return current.end != 0;
            }
            return false;
        }


        public int prefixNum(String prefix){
            if (prefix != null && prefix != "") {
                char[] chars = prefix.toCharArray();
                int index = 0;
                TrieNode current = root;
                for (char i : chars) {
                    index = i - 'a';
                    if (current.map[index] == null) {
                          return 0;
                    }
                    current = current.map[index];
                }
                return current.path;
            }
            return 0;
        }


    }


    @Test
    public void testTrie() {
        Trie trie = new Trie();
        log.info(trie);
        //trie.insert("abc");
       // trie.insert("abcd");
        String word = "小煞笔";
        log.info(word.charAt(0));
        //log.info(trie.prefixNum("a"));
        //trie.delete("abc");

       // log.info(trie);
    }
}
