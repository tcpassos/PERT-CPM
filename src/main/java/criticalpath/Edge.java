package criticalpath;

public class Edge {
    
    private Vertex source;
    private Vertex destination;
    private int weight;
    
    Edge(Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    
}
