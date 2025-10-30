
package graph.util;
public class Metrics {
    private long startTime = 0;
    private long timeNs = 0;
    public long dfsVisits = 0;
    public long dfsEdges = 0;
    public long kahnOps = 0;
    public long relaxations = 0;

    public void start(){ startTime = System.nanoTime(); }
    public void stop(){ timeNs += System.nanoTime() - startTime; startTime = 0; }
    public void addTime(long ns){ timeNs += ns; }
    @Override public String toString(){
        return String.format("time(ns)=%d, dfsVisits=%d, dfsEdges=%d, kahnOps=%d, relaxations=%d", 
            timeNs, dfsVisits, dfsEdges, kahnOps, relaxations);
    }
}
