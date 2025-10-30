
package graph.topo;
import graph.util.Graph;
import graph.util.Metrics;
import java.util.*;

public class KahnTopo {
    private final Graph g;
    public KahnTopo(Graph g){ this.g=g; }
    public List<Integer> topoOrder(Metrics metrics){
        metrics.start();
        int n = g.n;
        int[] indeg = new int[n];
        for (var e: g.edges) indeg[e.v]++;
        Deque<Integer> q = new ArrayDeque<>();
        for (int i=0;i<n;i++) if (indeg[i]==0) q.add(i);
        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()){
            int u = q.removeFirst();
            order.add(u);
            metrics.kahnOps++;
            for (var e: g.adj.get(u)){
                indeg[e.v]--;
                if (indeg[e.v]==0) q.add(e.v);
            }
        }
        metrics.stop();
        return order;
    }
}
