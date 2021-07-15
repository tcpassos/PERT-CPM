package criticalpath;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Vertex {
    
    private int name;
    private int duration;
    private List<Edge> adj;
    private List<Edge> revAdj;
    private int earliestCompletionTime;
    private int latestCompletionTime;
    private int slack;
    private Color color;

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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Edge> getAdj() {
        return adj;
    }
    
    public List<Vertex> getAdjVertexes() {
        return adj.stream().map(e -> e.otherEnd(this)).collect(Collectors.toList());
    }

    public List<Edge> getRevAdj() {
        return revAdj;
    }
    
    public List<Vertex> getRevAdjVertexes() {
        return revAdj.stream().map(e -> e.otherEnd(this)).collect(Collectors.toList());
    }

    public void setEarliestCompletionTime(int earliestCompletionTime) {
        this.earliestCompletionTime = earliestCompletionTime;
    }
    
    /**
     * Atualiza o menor tempo de início caso o novo valor seja maior que o atual
     *
     * @param ec Novo valor para o menor tempo de início
     */
    public void updateEarliestCompletionTime(int ec) {
        this.earliestCompletionTime = Math.max(this.earliestCompletionTime, ec);
    }
    
    public int getEarliestCompletionTime() {
        return earliestCompletionTime;
    }

    public void setLatestCompletionTime(int latestCompletionTime) {
        this.latestCompletionTime = latestCompletionTime;
    }

    /**
     * Atualiza o maior tempo de comnclusao caso o novo valor seja menor que o atual
     *
     * @param lc Novo valor para o maior tempo de conclusao
     */
    public void updateLatestCompletionTime(int lc) {
        this.latestCompletionTime = Math.min(latestCompletionTime, lc);
    }

    public int getLatestCompletionTime() {
        return latestCompletionTime;
    }

    public void setSlack(int slack) {
        this.slack = slack;
    }

    public int getSlack() {
        return slack;
    }
    
    public boolean isCritical() {
        return latestCompletionTime == earliestCompletionTime;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

}
