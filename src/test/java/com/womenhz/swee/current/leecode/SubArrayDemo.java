package com.womenhz.swee.current.leecode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SubArrayDemo {

    public static void main(String[] args) throws InterruptedException {
        int[] arr = {0, 0, 0};//{-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0};//{-4,-2,-2,-2,0,1,2,2,2,3,3,4,4,6,6};
         log.info(threeSum(arr));

         Thread.sleep(Integer.MAX_VALUE);
    }

    public int maxSubArray(int[] nums) {


        return 0;
    }

    public int maxArea(int[] height) {
        if (height == null || height.length == 0){
            return 0;
        }
        int area = 0;
        for (int i = 0; i < height.length; i++) {
            for(int j = i + 1; j < height.length; j++) {
                area = Math.max(area, (j - i) * (Math.min(height[i], height[j])));
            }
        }
        return area;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return null;
        }
        List<List<Integer>> outList = new ArrayList<>();
        List<Integer> innerList = null;
        boolean flag = false;
        int count = 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                count++;
            }
            //log.info("i= "+i);
            for (int j = i + 1; j < nums.length; j++) {
               // log.info("j = "+j);
                 for (int k = j + 1; k < nums.length; k++) {
                     //log.info("k= "+k);
                     if (nums[i] + nums[j] + nums[k] == 0) {
                         log.info("i = "+i +" j= "+j+" k = "+k+" "+outList);
                         set.add(nums[i]);
                         set.add(nums[j]);
                         set.add(nums[k]);
                         for (List<Integer> list : outList) {
                             if (list.contains(nums[i]) && list.contains(nums[j]) && list.contains(nums[k])) {
                                 flag = true;
                             }
                         }
                         if (!flag && set.size() > 1) {
                             innerList = new ArrayList<>();
                             innerList.add(nums[i]);
                             innerList.add(nums[j]);
                             innerList.add(nums[k]);
                             outList.add(innerList);
                         }
                         flag = false;
                         set.clear();
                     }
                 }
            }
        }
        if (count > 2) {
            innerList = new ArrayList<>();
            innerList.add(0);
            innerList.add(0);
            innerList.add(0);
            outList.add(innerList);
        }
        return outList;
    }
}
