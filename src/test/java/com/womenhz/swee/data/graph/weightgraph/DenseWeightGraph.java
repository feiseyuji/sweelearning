package com.womenhz.swee.data.graph.weightgraph;

import java.util.Vector;

public class DenseWeightGraph<Weight extends Number & Comparable> implements WeightedGraph{

    private int n;

    private int m;

    private boolean directed;

    private Edge<Weight>[][] weight;

    public DenseWeightGraph(int n, boolean directed) {
        assert n >= 0;
        this.n = n;
        this.m = 0;
        this.directed = directed;
        this.weight = new Edge[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <n; j++) {
                weight[i][j] = null;
            }
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
    public void addEdge(Edge edge) {
        assert edge.a() >= 0 && edge.a() < n;
        assert edge.b() >= 0 && edge.b() < n;
        if (hasEdge(edge.a(), edge.b()))
            return;
        weight[edge.a()][edge.b()] = edge;
        if (!directed) {
            weight[edge.b()][edge.a()] = edge;
        }

    }

    @Override
    public boolean hasEdge(int a, int b) {
        assert a >= 0 && a < n;
        assert b >= 0 && b < n;
        return weight[a][b] != null;
    }

    @Override
    public Iterable<Edge> adj(int v) {
        assert v >= 0 && v < n;
        Vector<Edge> adj = new Vector<>();
        for (int i = 0; i< n; i++) {
            if (weight[v][i] != null) {
                adj.add(weight[v][i]);
            }
        }
        return adj;
    }
}
