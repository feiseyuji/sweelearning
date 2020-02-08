package com.womenhz.swee.data;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

    public static void main(String[] args) {
        /*EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Welcome");
            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });*/

        int[] nums = {2, 7, 11, 15};
        int target = 9;

        int[] a = Solution.twoSum(nums, target);

        for (int b : a) {
            log.info(b);
        }
    }



    static class Solution {
        public static int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for(int i = 0; i < nums.length; i++) {
                map.put(target - nums[i], i);
            }

            int[] a = new int[2];
            for(int i = 0; i < nums.length; i++) {
                if(map.containsKey(nums[i]) && i != map.get(nums[i])) {
                    a[0] = i < map.get(nums[i]) ? i : map.get(nums[i]);
                    a[1] = i < map.get(nums[i]) ? map.get(nums[i]) : i;

                    break;
                }
            }
            return a;
        }
        Set<String> set = new HashSet<>();
    }

}
