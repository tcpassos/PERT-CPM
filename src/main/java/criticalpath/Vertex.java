package criticalpath;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    
    private int name;
    private int duration;
    private List<Edge> adj;
    private List<Edge> revAdj;
    private int earliestCompletionTime;
    private int latestCompletionTime;
    private int slack;

    public Vertex(int n) {
        this.name = n;
        this.duration = Integer.MAX_VALUE;
        this.adj = new ArrayList<>();
        this.revAdj = new ArrayList<>();
    }
    
    /**
     * Vincula uma aresta adjacente
     * 
     * @param edge Aresta
     */
    public void link(Edge edge) {
        this.adj.add(edge);
    }
    
    /**
     * Vincula uma aresta adjacente inversamente
     * 
     * @param edge Aresta
     */
    public void reverseLink(Edge edge) {
        this.revAdj.add(edge);
    }
    
}
