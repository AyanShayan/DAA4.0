
package graph;
import org.junit.Test;
import graph.util.Graph;
import graph.scc.TarjanSCC;
import graph.util.Metrics;
import static org.junit.Assert.*;

public class BasicTests {
    @Test
    public void testTarjanOnTasks() throws Exception {
        Graph g = Graph.fromJson("data/tasks.json");
        TarjanSCC t = new TarjanSCC(g);
        Metrics m = new Metrics();
        var sccs = t.getSCCs(m);
        boolean foundSize3 = sccs.stream().anyMatch(list -> list.size()==3);
        assertTrue(foundSize3);
    }
}
