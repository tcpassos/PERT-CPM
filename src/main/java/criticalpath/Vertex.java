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
    
    /**
     * Indica se o vertice possui arestas adjacentes
     *
     * @return {@code boolean}
     */
    public boolean hasLeadingEdges() {
        return !adj.isEmpty();
    }

    /**
     * Indica se o vertice possui arestas reversamente adjacentes
     *
     * @return {@code boolean}
     */
    public boolean hasTrailingEdges() {
        return !revAdj.isEmpty();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    
}
