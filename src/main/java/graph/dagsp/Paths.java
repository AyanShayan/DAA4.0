
package graph.dagsp;
import graph.util.Graph;

import java.util.*;


public class Paths {
    private final Graph g;
    public Paths(Graph g){ this.g=g; }

    public Map<Integer, Long> shortestPathsFrom(int src, graph.util.Metrics metrics){
        metrics.start();
        int n = g.n;
        int[] indeg = new int[n];
        for (var e: g.edges) indeg[e.v]++;
        Deque<Integer> q = new ArrayDeque<>();
        for (int i=0;i<n;i++) if (indeg[i]==0) q.add(i);
        List<Integer> topo = new ArrayList<>();
        while (!q.isEmpty()){ int u=q.removeFirst(); topo.add(u); for (var e: g.adj.get(u)){ indeg[e.v]--; if (indeg[e.v]==0) q.add(e.v); } }

        final long INF = Long.MAX_VALUE/4;
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[src]=0;
        for (int u: topo){
            if (dist[u]==INF) continue;
            for (var e: g.adj.get(u)){
                if (dist[e.v] > dist[u] + e.w){
                    dist[e.v] = dist[u] + e.w;
                    metrics.relaxations++;
                }
            }
        }
        metrics.stop();
        Map<Integer, Long> map = new HashMap<>();
        for (int i=0;i<n;i++) map.put(i, dist[i]==INF? null: dist[i]);
        return map;
    }

    public static class PathResult {
        public final long length;
        public final List<Integer> path;
        public PathResult(long length, List<Integer> path){ this.length=length; this.path=path; }
    }

    public PathResult longestPathFrom(int src, graph.util.Metrics metrics){
        metrics.start();
        int n = g.n;
        int[] indeg = new int[n];
        for (var e: g.edges) indeg[e.v]++;
        Deque<Integer> q = new ArrayDeque<>();
        for (int i=0;i<n;i++) if (indeg[i]==0) q.add(i);
        List<Integer> topo = new ArrayList<>();
        while (!q.isEmpty()){ int u=q.removeFirst(); topo.add(u); for (var e: g.adj.get(u)){ indeg[e.v]--; if (indeg[e.v]==0) q.add(e.v); } }

        final long NEG_INF = Long.MIN_VALUE/4;
        long[] dp = new long[n];
        int[] parent = new int[n];
        Arrays.fill(dp, NEG_INF);
        Arrays.fill(parent, -1);
        dp[src]=0;
        for (int u: topo){
            if (dp[u]==NEG_INF) continue;
            for (var e: g.adj.get(u)){
                long cand = dp[u] + e.w;
                if (cand > dp[e.v]){ dp[e.v]=cand; parent[e.v]=u; }
            }
        }
        long best = NEG_INF; int bestV = -1;
        for (int i=0;i<n;i++) if (dp[i]>best){ best=dp[i]; bestV=i; }
        List<Integer> path = new ArrayList<>();
        if (bestV!=-1){
            int cur = bestV;
            while (cur!=-1){ path.add(cur); cur=parent[cur]; }
            Collections.reverse(path);
        }
        metrics.stop();
        return new PathResult(best==NEG_INF? Long.MIN_VALUE: best, path);
    }
}
