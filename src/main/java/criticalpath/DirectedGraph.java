package criticalpath;

import core.InputReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Classe que representa um grafo direcionado
 */
public class DirectedGraph implements Iterable<Vertex> {
    
    private List<Vertex> vertexList;
    private int size;

    public DirectedGraph(int size) {
        this.size = size;
        this.vertexList = new ArrayList<>(size + 1);
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
     * Retorna os vértices do grafo
     *
     * @return {@code List<Vertex>}
     */
    public List<Vertex> getVertexes() {
        return vertexList;
    }

    /**
     * Retorna a quantidade de vertices do grafo
     *
     * @return {@code int}
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Retorna o vértice que representa o início do grafo
     *
     * @return {@code Vertex}
     */
    public Vertex start() {
        return vertexList.get(vertexList.size() - 2);
    }

    /**
     * Retorna o vértice que representa o final do grafo
     *
     * @return {@code Vertex}
     */
    public Vertex end() {
        return vertexList.get(vertexList.size() - 1);
    }
    
    /**
     * Adiciona uma aresta no grafo
     * 
     * @param source Vertice origem
     * @param destination Vertice destino
     */
    public void addEdge(Vertex source, Vertex destination) {
        Edge edge = new Edge(source, destination);
        source.link(edge);
        destination.reverseLink(edge);
    }

    @Override
    public Iterator<Vertex> iterator() {
        Iterator<Vertex> it = this.vertexList.iterator();
        it.next();  // Pula o indice 0
        return it;
    }
    
    /**
     * Cria um grafo direcionado a partir do input do usuario
     *
     * @return {@code DirectedGraph}
     */
    public static DirectedGraph readGraph() {
        // Faz a leitura da quantidade de arestas e vertices do grafo
        List<Integer> in = _readLineParams();
        int vertexCount = in.get(0);
        int edgeCount = in.get(1);
        DirectedGraph g = new DirectedGraph(vertexCount + 2);
        // Faz a leitura das arestas do grafo
        for (int i=0; i<edgeCount; i++) {
            in = _readLineParams();
            g.addEdge(g.getVertex(in.get(0)), g.getVertex(in.get(1)));
        }
        // Faz a leitura da duracao de cada vertice
        int count = 0;
        in = _readLineParams();
        for (int i=1; i<g.vertexList.size() - 2; i++) {
            g.getVertex(i).setDuration(in.get(count++));
        }
        // Cria vertices auxiliares representando o inicio e o fim do grafo
        int size = g.vertexList.size();
        Vertex s = g.vertexList.get(size - 2);
        Vertex t = g.vertexList.get(size - 1);
        s.setDuration(0);
        t.setDuration(0);
        // Conecta o vertice inicial aos demais vertices
        g.vertexList.stream()
                    .filter(v -> v != null && !v.hasTrailingEdges() && v != s && v != t)
                    .forEach((v) -> g.addEdge(s, v));
        // Conecta o último vértice aos demais vértices
        g.getVertexes().stream()
                       .filter(v -> v != null && v.getAdj().isEmpty() && v != s && v != t)
                       .forEach(v -> g.addEdge(v, t));
        return g;
    }
    
    private static List<Integer> _readLineParams() {
        String[] in = InputReader.readLine().split(" ");
        return Arrays.asList(in).stream().map(Integer::parseInt).collect(Collectors.toList());
    }

}
