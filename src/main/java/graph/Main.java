
package graph;

import graph.scc.TarjanSCC;
import graph.topo.KahnTopo;
import graph.dagsp.Paths;
import graph.util.Graph;
import graph.util.Metrics;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        String in = args.length>0? args[0] : "data/dataset_9.json"; //you can choose any datasets from 1 to 9
        Graph g = Graph.fromJson(in);

        Metrics metrics = new Metrics();
        System.out.println("=== Input graph ===");
        System.out.println(g);

        TarjanSCC tarjan = new TarjanSCC(g);
        List<List<Integer>> sccs = tarjan.getSCCs(metrics);
        System.out.println("\nSCCs (Tarjan): " + sccs);
        System.out.println("SCC sizes: " + sccs.stream().map(List::size).collect(Collectors.toList()));

        Graph cond = tarjan.buildCondensation();
        System.out.println("\nCondensation DAG: ");
        System.out.println(cond);

        KahnTopo topo = new KahnTopo(cond);
        List<Integer> compOrder = topo.topoOrder(metrics);
        System.out.println("\nTopological order of components: " + compOrder);

        List<Integer> expanded = tarjan.expandComponentsInOrder(compOrder);
        System.out.println("Derived order of original tasks after SCC compression: " + expanded);

        int source = Graph.getSourceIndex(in);
        int compSource = tarjan.componentOf(source);
        System.out.println("\nSource (original): " + source + ", component source: " + compSource);

        Paths dsp = new Paths(cond);
        Map<Integer, Long> dist = dsp.shortestPathsFrom(compSource, metrics);
        System.out.println("\nShortest distances from component " + compSource + ": " + dist);

        Paths.PathResult longest = dsp.longestPathFrom(compSource, metrics);
        System.out.println("\nLongest (critical) path from component " + compSource + ": ");
        System.out.println("Length: " + longest.length + ", path (components): " + longest.path);

        System.out.println("\nMetrics summary: \n" + metrics);

    }

}
