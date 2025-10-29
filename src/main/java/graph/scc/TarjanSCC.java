
package graph.scc;
import graph.util.Graph;
import graph.util.Metrics;
import java.util.*;

public class TarjanSCC {
    private final Graph g;
    private int time = 0;
    private int[] disc, low, comp;
    private boolean[] inStack;
    private Deque<Integer> stack = new ArrayDeque<>();
    private List<List<Integer>> sccs = new ArrayList<>();

    public TarjanSCC(Graph g){
        this.g = g;
        disc = new int[g.n]; Arrays.fill(disc, -1);
        low = new int[g.n]; Arrays.fill(low, -1);
        comp = new int[g.n]; Arrays.fill(comp, -1);
        inStack = new boolean[g.n];
    }

    public List<List<Integer>> getSCCs(Metrics metrics){
        metrics.start();
        for (int i=0;i<g.n;i++){
            if (disc[i]==-1) dfs(i, metrics);
        }
        metrics.stop();
        return sccs;
    }

    private void dfs(int u, Metrics metrics){
        disc[u]=low[u]=time++;
        stack.push(u); inStack[u]=true;
        metrics.dfsVisits++;
        for (var e: g.adj.get(u)){
            int v = e.v;
            metrics.dfsEdges++;
            if (disc[v]==-1){
                dfs(v, metrics);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
        if (low[u]==disc[u]){
            List<Integer> compList = new ArrayList<>();
            while (true){
                int v = stack.pop();
                inStack[v]=false;
                comp[v] = sccs.size();
                compList.add(v);
                if (v==u) break;
            }
            sccs.add(compList);
        }
    }

    public Graph buildCondensation(){
        int k = sccs.size();
        Graph cg = new Graph(k, true);
        for (var e: g.edges){
            int a = comp[e.u], b = comp[e.v];
            if (a!=b){
                cg.addEdge(a,b,e.w);
            }
        }
        return cg;
    }

    public List<Integer> expandComponentsInOrder(List<Integer> order){
        List<Integer> out = new ArrayList<>();
        for (int c: order){
            List<Integer> nodes = sccs.get(c);
            nodes.sort(Integer::compareTo);
            out.addAll(nodes);
        }
        return out;
    }

    public int componentOf(int v){ return comp[v]; }
}
