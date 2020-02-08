package com.womenhz.swee.data.graph.weightgraph;

public interface WeightedGraph<Weight extends Number & Comparable> {

    int V();

    int E();

    void addEdge(Edge<Weight> edge);

    boolean hasEdge(int a, int b);

    Iterable<Edge<Weight>> adj(int v);
}
