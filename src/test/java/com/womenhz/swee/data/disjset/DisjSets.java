package com.womenhz.swee.data.disjset;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class DisjSets {

    private int[] s;

    public DisjSets(int numElements) {
        s = new int[numElements];
        for (int i = 0; i < numElements; i++) {
            s[i] = -1;
        }
    }

    public void union(int root1, int root2) {
        s[root2] = root1;
    }

    public int find(int x) {
        if (s[x] < 0) {
            return x;
        }else {
            return find(s[x]);
        }
    }

    public static void main(String[] args) {
        DisjSets disjSets = new DisjSets(10);
        log.info(disjSets.find(2));

        disjSets.union(2, 3);

        log.info(disjSets.find(3));
    }

}
