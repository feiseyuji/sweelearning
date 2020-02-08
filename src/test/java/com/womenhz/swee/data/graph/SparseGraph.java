package com.womenhz.swee.data.graph;

import java.util.Vector;

public class SparseGraph implements Graph {

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

    private Vector<Integer>[] g;

    public SparseGraph(int n, boolean directed) {
        assert n >= 0;
        this.n = n;
        this.m = m;

        this.directed = directed;
        for (int i = 0; i < n; i++) {
            g[i] = new Vector<>();
        }
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

        g[v].add(w);
        if (v != w && !directed) {
            g[w].add(v);
        }
        m++;
    }

    @Override
    public boolean hasEdge(int v, int w) {
        assert v >= 0 && v < n;
        assert w >= 0 && w < n;

        for (int i = 0; i < g[v].size(); i++) {
            if (g[v].elementAt(i) == w) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public Iterable<Integer> adj(int v) {
        assert v >= 0 && v < n;
        return g[v];
    }
}
