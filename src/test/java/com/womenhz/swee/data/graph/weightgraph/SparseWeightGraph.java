package com.womenhz.swee.data.graph.weightgraph;

import java.util.Vector;

public class SparseWeightGraph<Weight extends Number & Comparable> implements WeightedGraph {

    private int n;

    private int m;

    private boolean directed;

    private Vector<Edge<Weight>>[] g;

    public SparseWeightGraph(int n, boolean directed) {

        assert n >= 0;
        this.n = n;
        this.m = m;

        this.directed = directed;
        g = new Vector[n];
        for (int i = 0; i < n; i++) {
            g[i] = new Vector<>();
        }
    }

    @Override
    public int V() {
        return 0;
    }

    @Override
    public int E() {
        return 0;
    }

    @Override
    public void addEdge(Edge edge) {
        assert edge.b() >= 0 && edge.b() < n;
        assert edge.a() >= 0 && edge.a() < n;

        if (hasEdge(edge.a(), edge.b()))
            return;
        g[edge.a()].add(edge);
        if (edge.a() != edge.b() && !directed) {
            g[edge.b()].add(new Edge(edge.b(), edge.a(), edge.wt()));
        }
        m++;
    }

    @Override
    public boolean hasEdge(int a, int b) {
        assert a >= 0 && a < n;
        assert b >= 0 && b < n;

        for (int i = 0; i < g[a].size(); i++) {
            if (g[a].elementAt(i).other(a) == b)
                return true;
        }

        return false;
    }

    @Override
    public Iterable<Edge<Weight>> adj(int v) {
        assert v >= 0 && v < n;
        return g[v];
    }
}
