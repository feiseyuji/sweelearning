package com.womenhz.swee.data.graph;

import java.util.Vector;

public class DenseGraph implements Graph {

    /**
     * 顶点数
     * */
    private int n;

    /**
     *边数
     * */
    private int m;

    /**
     * 是否为有向图
     * */
    private boolean directed;

    private boolean[][]g;

    public DenseGraph(int n, boolean directed) {
        assert n >= 0;
        this.n = n;
        this.m = 0;
        this.directed = directed;
        g = new boolean[n][n];
    }

    @Override
    public int V() {
        return n;
    }

    @Override
    public int E() {
        return m;
    }

    @Override
    public void addEdge(int v, int w) {
        assert v >= 0 && v < n;
        assert w >= 0 && w < n;

        if (hasEdge(v, w))
            return;
        g[v][w] = true;
        if (!directed)
            g[w][v] = true;

    }

    @Override
    public boolean hasEdge(int v, int w) {
        assert v >= 0 && v < n;
        assert w >= 0 && w < n;
        return g[v][w];
    }

    @Override
    public void show() {

    }

    @Override
    public Iterable<Integer> adj(int v) {
        assert v >= 0;
        Vector<Integer> adjV = new Vector<>();
        for (int i = 0; i < n; i++) {
            if (hasEdge(v, i))
                adjV.add(i);
        }
        return adjV;
    }
}
