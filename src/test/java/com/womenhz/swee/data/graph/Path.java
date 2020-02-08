package com.womenhz.swee.data.graph;

import java.util.Stack;
import java.util.Vector;

public class Path {

    private Graph graph;

    /**
     * 起始点
     * */
    private int s;

    private boolean[] visited;

    private int[] from;

    public Path(Graph graph, int s) {
        this.graph = graph;
        assert s >= 0 && s < graph.V();
        this.visited = new boolean[graph.V()];
        this.from = new int[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
             from[i] = -1;
             visited[i] = false;
        }
        this.s = s;
        dfs(s);
    }

    private void dfs(int v) {
        visited[v] = true;
        for (int i : graph.adj(v)) {
            if (!visited[i]) {
                from[i] = v;
                dfs(i);
            }
        }
    }

    /**
     *
     * s -> w是否有路径
     * */
    public boolean hasPath(int w) {
        assert w >= 0 && w < graph.V();
        return visited[w];
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
}
