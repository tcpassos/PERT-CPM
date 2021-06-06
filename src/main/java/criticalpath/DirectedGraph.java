package criticalpath;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Classe que representa um grafo direcionado
 */
public class DirectedGraph {
    
    private List<Vertex> vertexList;
    private int size;

    public DirectedGraph(int size) {
        this.size = size;
        this.vertexList = new ArrayList<>();
        this.vertexList.add(0, null);
        IntStream.rangeClosed(1, size).forEach(i -> vertexList.add(i, new Vertex(i)));
    }
    
    /**
     * Retorna um vertice de indice n
     * 
     * @param n Indice do vertice
     * @return {@code Vertex}
     */
    public Vertex getVertex(int n) {
        return vertexList.get(n);
    }
    
    /**
     * Adiciona uma aresta no grafo
     * 
     * @param source Vertice origem
     * @param destination Vertice destino
     * @param weigth Peso
     */
    public void addEdge(Vertex source, Vertex destination, int weigth) {
        Edge edge = new Edge(source, destination, weigth);
        source.link(edge);
        destination.reverseLink(edge);
    }

}
