package criticalpath;

public class Edge {
    
    private Vertex source;
    private Vertex destination;
    
    Edge(Vertex source, Vertex destination) {
        this.source = source;
        this.destination = destination;
    }
    
    /**
     * Retorna o vértice da outra ponta da aresta
     *
     * @param u : Vertex
     * @return {@code Vertex}
     */
    public Vertex otherEnd(Vertex u) {
        return source == u ? destination : source;
    }

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }
    
}
