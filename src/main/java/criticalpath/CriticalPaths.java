package criticalpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CriticalPaths {

    DirectedGraph graph;

    List<Vertex[]> allPaths;
    long numberOfCriticalNodes;

    CriticalPaths(DirectedGraph graph) {
        this.graph = graph;
        this.allPaths = new ArrayList<>();
        this.numberOfCriticalNodes = 0;
    }

    /**
     * Encontra os caminhos críticos em um grafo
     */
    public void findCriticalPaths() {
        // Monta a visualizacao da ordem topologica do grafo
        LinkedList<Vertex> topologicalOrder = new DepthFirstSearch(graph).getTopologicalOrder();
        // Calcula os tempos de conclusao das tarefas
        _calculateEarliestCompletionTime(topologicalOrder);
        _calculateLatestCompletionTime(topologicalOrder);
        // Calcula a folga das tarefas
        _calculateSlack();
        // Enumera os caminhos criticos 
        Vertex[] path = new Vertex[graph.getVertexes().size() - 1];
        _enumeratePaths(path, graph.start(), 0, graph.end());
        // Imprime os resultados do algoritmo
        _printResults();
    }
    
    /**
     * Calcula o menor tempo de conclusao das tarefas
     */
    private void _calculateEarliestCompletionTime(LinkedList<Vertex> topologicalOrder) {
        graph.getVertexes().stream().filter(Objects::nonNull)
                           .forEach(v -> v.setEarliestCompletionTime(Integer.MIN_VALUE));
        graph.start().setEarliestCompletionTime(0);
        // Percorre os vertices atualizando o menor tempo de inicio de cada um
        topologicalOrder.forEach(u -> {
            u.getAdjVertexes().forEach(v -> v.updateEarliestCompletionTime(u.getEarliestCompletionTime() + v.getDuration()));
        });
    }

    /**
     * Calcula o maior tempo de conclusao das tarefas
     */
    private void _calculateLatestCompletionTime(LinkedList<Vertex> topologicalOrder) {
        // Definine o valor do maior tempo de conclusao dos vertices como o menor tempo de conclusao das tarefas
        graph.getVertexes().stream().filter(Objects::nonNull)
                           .forEach(u -> u.setLatestCompletionTime(graph.end().getEarliestCompletionTime()));
        // Inverte a ordem topologica e percorre novamente os caminhos calculando a LC dos vertices
        Iterator<Vertex> reverseIt = topologicalOrder.descendingIterator();
        while(reverseIt.hasNext()) {
            Vertex u = reverseIt.next();
            u.getRevAdjVertexes().forEach(v -> v.updateLatestCompletionTime(u.getLatestCompletionTime() - u.getDuration()));
        }
    }

    /**
     * Calcula a folga subtraindo EC de LC
     */
    private void _calculateSlack() {
        numberOfCriticalNodes = graph.getVertexes().stream().filter(Objects::nonNull)
                                     .peek(v -> v.setSlack(v.getLatestCompletionTime() - v.getEarliestCompletionTime()))
                                     .filter(v -> v.isCritical() && v != graph.start() && v != graph.end())
                                     .count();
    }
    
    /**
     * Realiza a enumeracao dos caminhos criticos encontrados
     */
    private void _enumeratePaths(Vertex[] path, Vertex u, int index, Vertex end) {
        path[index] = u;
        if (u == end) {
            allPaths.add(path.clone());
        } else {
            u.getAdjVertexes()
             .stream()
             .filter(v -> (u.isCritical() && v.isCritical() &&
                          (u.getLatestCompletionTime() + v.getDuration() == v.getLatestCompletionTime())))
             .forEach(v -> _enumeratePaths(path, v, index + 1, end));
        }
    }
    
    /**
     * Imprime os resultados do algoritmo
     */
    private void _printResults() {
        System.out.println("\nTarefa" + "\t" + "EC" + "\t" + "LC" + "\t" + "Folga");
        int task = 0;
        for (Vertex u : graph.getVertexes()) {
            if (u != null && u != graph.start() && u != graph.end()) {
                System.out.println(++task + "\t" + u.getEarliestCompletionTime() + "\t" + u.getLatestCompletionTime() + "\t" + u.getSlack());
            }
        }
        System.out.println("\nTempo de conclusao: " + graph.end().getLatestCompletionTime());
        System.out.println("Nodes criticos: " + numberOfCriticalNodes);
        System.out.println("Caminhos criticos: " + allPaths.size());
        allPaths.forEach(p -> {
            Vertex[] fmtPath = Arrays.stream(p)
                                     .filter(Objects::nonNull)
                                     .skip(1)
                                     .toArray(Vertex[]::new);
            fmtPath = Arrays.copyOf(fmtPath, fmtPath.length - 1);
            System.out.println(Arrays.toString(fmtPath));
        });
    }

}