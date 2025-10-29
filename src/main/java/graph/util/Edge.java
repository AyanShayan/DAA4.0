
package graph.util;
public class Edge {
    public final int u, v;
    public final long w;
    public Edge(int u, int v, long w){ this.u=u; this.v=v; this.w=w; }
    @Override public String toString(){ return String.format("%d->%d(%d)", u,v,w); }
}
