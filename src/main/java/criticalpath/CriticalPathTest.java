package criticalpath;

public class CriticalPathTest {
    
    public static void main(String[] args) {
        DirectedGraph g = new DirectedGraph(2);
        g.addEdge(new Vertex(1), new Vertex(2), 2);
    }
    
}
