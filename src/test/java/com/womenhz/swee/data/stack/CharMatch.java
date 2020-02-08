package com.womenhz.swee.data.stack;


import java.util.Stack;
import org.apache.commons.lang3.StringUtils;

public class CharMatch {

    private static void parenthesisMatching(String str) {
        if (StringUtils.isNotBlank(str)) {
            Stack<Character> stack = new Stack<>();
            char[] strchar = str.toCharArray();
            for (int i = 0; i < str.length(); i ++ ) {
                if (strchar[i] == '(' || strchar[i] == '[' || strchar[i] == '{') {
                    stack.push(strchar[i]);
                }else {
                    if (stack.isEmpty()) {
                        return;
                    }
                    char start = stack.peek();
                    stack.pop();
                    char match;
                    if (strchar[i] == ')') {
                        match = '(';
                    }else if (strchar[i] == ']') {
                        match = '[';
                    }else {
                        assert strchar[i] == '}';
                        match = '{';
                    }

                    if (start != match) {
                        return;
                    }
                }
            }

            if (!stack.isEmpty()) {
                return;
            }
        }
    }

}
