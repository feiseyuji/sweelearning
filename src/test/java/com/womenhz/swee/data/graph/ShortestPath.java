package com.womenhz.swee.data.graph;

import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

public class ShortestPath {

    private Graph graph;

    private int s;

    private boolean[] visited;

    private int[] from;

    private int[] ord;

    public ShortestPath(Graph graph, int s) {
        this.graph = graph;
        assert s >= 0 && s < graph.V();
        visited = new boolean[graph.V()];
        ord = new int[graph.V()];
        from = new int[graph.V()];

        for (int i = 0; i < graph.V(); i++) {
            visited[i] = false;
            from[i] = -1;
            ord[i] = -1;
        }
        this.s = s;
        LinkedList<Integer> q = new LinkedList<>();

        q.push(s);
        visited[s] = true;
        ord[s] = 0;
        while (!q.isEmpty()) {
            int v = q.pop();
            for (int i : graph.adj(v)) {
                if (!visited[i]) {
                    q.push(i);
                    visited[i] = true;
                    from[i] = v;
                    ord[i] = ord[v] + 1;
                }
            }
        }
    }

    /**
     *
     * s -> w的路径
     * */
    public Vector<Integer> path(int w) {
        assert hasPath(w);
        Stack<Integer> stack = new Stack<>();
        int p = w;
        while (p != -1) {
            stack.push(p);
            p = from[p];
        }

        Vector<Integer> path = new Vector<>();
        while (!stack.isEmpty()) {
            path.add(stack.pop());
        }
        return path;
    }

    private boolean hasPath(int w) {
        assert w >= 0 && w < graph.V();
        return visited[w];
    }

    public int length(int w) {
        assert w >= 0 && w < graph.V();
        return ord[w];
    }

}
