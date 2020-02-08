package com.womenhz.swee.data.graph.weightgraph;

public class Edge<Weight extends Number & Comparable> implements Comparable<Edge<Weight>> {

    private int a;

    private int b;

    private Weight weight;

    public Edge(int a, int b, Weight weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    public Edge(Edge<Weight> edge) {
        this.a = edge.a();
        this.b = edge.b();
        this.weight = edge.weight;
    }

    public int a(){return a;}

    public int b(){return b;}

    public int other(int s) {
        assert a == s || b == s;
        return s == a ? a : b;
    }

    public Weight wt() {
        return this.weight;
    }

    @Override
    public int compareTo(Edge<Weight> o) {
        if (o.weight.compareTo(this.weight) < 0) {
            return -1;
        }else if (o.weight.compareTo(this.weight) > 0) {
            return 1;
        }else {
            return 0;
        }
    }
}
