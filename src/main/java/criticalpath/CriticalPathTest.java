package criticalpath;

public class CriticalPathTest {
    
    public static void main(String[] args) {
        DirectedGraph g = DirectedGraph.readGraph();
        CriticalPaths cp = new CriticalPaths(g);
        cp.findCriticalPaths();
    }
    
}
