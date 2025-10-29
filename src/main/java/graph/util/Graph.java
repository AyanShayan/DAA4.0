
package graph.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import com.google.gson.*;

public class Graph {
    public final int n;
    public final boolean directed;
    public final List<Edge> edges;
    public final List<List<Edge>> adj;
    public int source = 0;

    public Graph(int n, boolean directed) {
        this.n = n;
        this.directed = directed;
        this.edges = new ArrayList<>();
        this.adj = new ArrayList<>();
        for (int i=0;i<n;i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, long w){
        Edge e = new Edge(u,v,w);
        edges.add(e);
        adj.get(u).add(e);
        if (!directed) {
            adj.get(v).add(new Edge(v,u,w));
        }
    }

    public static Graph fromJson(String path) throws IOException {
        String txt = Files.readString(Paths.get(path));
        JsonObject obj = JsonParser.parseString(txt).getAsJsonObject();
        boolean directed = obj.get("directed").getAsBoolean();
        int n = obj.get("n").getAsInt();
        Graph g = new Graph(n, directed);
        JsonArray arr = obj.getAsJsonArray("edges");
        for (JsonElement el: arr) {
            JsonObject e = el.getAsJsonObject();
            int u = e.get("u").getAsInt();
            int v = e.get("v").getAsInt();
            long w = e.get("w").getAsLong();
            g.addEdge(u,v,w);
        }
        if (obj.has("source")) {
            g.source = obj.get("source").getAsInt();
        }
        return g;
    }

    public static int getSourceIndex(String path) throws IOException {
        String txt = Files.readString(Paths.get(path));
        JsonObject obj = JsonParser.parseString(txt).getAsJsonObject();
        return obj.has("source") ? obj.get("source").getAsInt() : 0;
    }

    @Override
    public String toString() {
        return String.format("Graph(n=%d, directed=%b, m=%d)", n, directed, edges.size());
    }
}
