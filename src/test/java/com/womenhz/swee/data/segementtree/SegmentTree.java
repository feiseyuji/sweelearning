package com.womenhz.swee.data.segementtree;

import lombok.Data;

@Data
public class SegmentTree<E> {

    private E[] element;

    private E[] tree;

    private Merge<E> merge;

    public SegmentTree(E[] arr, Merge<E> merge){
        this.merge = merge;
        element = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            element[i] = arr[i];
        }
        tree = (E[]) new Object[4 * arr.length];
        buildSegmentTree(0, 0, element.length - 1);
    }

    private void buildSegmentTree(int treeIndex, int l, int r) {
        if (l == r) {
            tree[treeIndex] = element[l];
            return;
        }

        int lIndex = getLeftIndex(treeIndex);
        int rIndex = getRightIndex(treeIndex);
        int mid = l + (r - l) / 2;

        buildSegmentTree(lIndex, l, mid);
        buildSegmentTree(rIndex, mid + 1, r);

        tree[treeIndex] = merge.merge(tree[lIndex], tree[rIndex]);
    }

    public E select(int l, int r) {
        if (l < 0 || l > element.length || r < 0 || r > element.length || l > r)
            throw new IllegalArgumentException("index is illegal.");
        return select(0, 0, element.length - 1, l, r);
    }

    private E select(int treeIndex, int l, int r, int sL, int sR) {
        if (l == sL && r == sR)
            return tree[treeIndex];

        int lIndex = getLeftIndex(treeIndex);
        int rIndex = getRightIndex(treeIndex);
        int mid = l + (r - l) / 2;

        if (sL > mid)
            return select(rIndex, mid + 1, r, sL, sR);
        else if (sR <mid +1)
            return select(lIndex, 0, mid, sL, sR);

        E lResult = select(lIndex, l, mid, sL, mid);
        E rRsult = select(rIndex, mid +1, r, mid + 1, sR);

        return merge.merge(lResult, rRsult);
    }

    public void set(int index, E e) {
        if (index < 0 || index > element.length) {
            throw new IllegalArgumentException("index is illegal.");
        }
        element[index] = e;
        set(0, 0, element.length -1, index, e);
    }

    private void set(int treeIndex, int l, int r, int index, E e) {
      if (l == r) {
          tree[treeIndex] = e;
          return;
      }
      int lIndex = getLeftIndex(treeIndex);
      int rIndex = getRightIndex(treeIndex);
      int mid = l + (r - l) / 2;

      if (index > mid) {
          set(rIndex, mid + 1, r, index, e);
      }else {
          set(lIndex, l, mid, index, e);
      }

      tree[treeIndex] = merge.merge(tree[lIndex], tree[rIndex]);
    }

    public int getSize() {
        return element.length;
    }

    public E get(int index) {
        if (index < 0 || index > element.length) {
            throw new IllegalArgumentException("index is illegal.");
        }
        return element[index];
    }

    public int getLeftIndex(int index) {
        return 2 * index +1;
    }

    public int getRightIndex(int index) {
        return 2 * index + 2;
    }

}
