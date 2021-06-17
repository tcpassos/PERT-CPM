package criticalpath;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Classe responsavel por realizar a busca em profundidade em um grafo.
 */
public class DepthFirstSearch {
    
    private DirectedGraph graph;
    private LinkedList<Vertex> topologicalOrder;
    
    public DepthFirstSearch(DirectedGraph graph) {
        this.graph = graph;
        this.topologicalOrder = new LinkedList<>();
    }
    
    /*
     * Executa DFS no grafo para encontrar a ordem topologica
     */
    public LinkedList<Vertex> getTopologicalOrder() {
        // Inicializa todos os vertices para a cor branca
        graph.getVertexes().stream()
                           .filter(Objects::nonNull)
                           .forEach(v -> v.setColor(Color.WHITE));
        topologicalOrder.clear();
        // Visita os vertices recursivamente
        graph.getVertexes().stream()
                           .filter(v -> Objects.nonNull(v) && v.getColor() == Color.WHITE)
                           .forEach(v -> dfsVisit(graph, v));
        return topologicalOrder;
    }

    /*
     * Visita a ramificacao de um vertice usando DFS
     */
    private void dfsVisit(DirectedGraph g,Vertex u) {
        // Muda a cor para cinza indicando que esta no vertice
        u.setColor(Color.GRAY);
        // Visita recursivamente os nodes que ainda nao foram visitados (brancos)
        u.getAdj().stream()
                  .map(e -> e.otherEnd(u))
                  .filter(v -> v != null && v.getColor() == Color.WHITE)
                  .forEach(v -> dfsVisit(g, v));
        // Muda a cor para preto indicando que ja foi visitado
        u.setColor(Color.BLACK);
        // Insere o node na primeira posicao da lista de ordem topologica
        topologicalOrder.addFirst(u);
    }
    
}
