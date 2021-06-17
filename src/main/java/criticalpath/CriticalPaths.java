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
        LinkedList<Vertex> topologicalOrder = new DepthFirstSearch(graph).getTopologicalOrder();
        int size = graph.getVertexes().size();
        /**
         * Calculo do menor tempo de conclusao das tarefas
         */
        Vertex s = graph.getVertexes().get(size - 2);
        graph.getVertexes().stream().filter(Objects::nonNull)
                           .forEach(v -> v.setEarliestCompletionTime(Integer.MIN_VALUE));
        s.setEarliestCompletionTime(0);
        // Percorre os vertices atualizando o menor tempo de inicio de cada um
        topologicalOrder.forEach(u -> {
            u.getAdjVertexes().forEach(v -> v.updateEarliestCompletionTime(u.getEarliestCompletionTime() + v.getDuration()));
        });
        /**
         * Calculo do maior tempo de conclusao das tarefas
         */
        Vertex t = graph.getVertexes().get(size - 1);
        // Definine o valor do maior tempo de conclusao dos vertices como o menor tempo de conclusao das tarefas
        t.setLatestCompletionTime(t.getEarliestCompletionTime());
        graph.getVertexes().stream().filter(Objects::nonNull)
                           .forEach(u -> u.setLatestCompletionTime(t.getLatestCompletionTime()));
        // Inverte a ordem topologica e percorre novamente os caminhos calculando a LC dos vertices
        Iterator<Vertex> reverseIt = topologicalOrder.descendingIterator();
        while(reverseIt.hasNext()) {
            Vertex u = reverseIt.next();
            u.getRevAdjVertexes().forEach(v -> v.updateLatestCompletionTime(u.getLatestCompletionTime() - u.getDuration()));
        }
        /**
         * Calcula a folga subtraindo EC de LC
         */
        numberOfCriticalNodes = graph.getVertexes().stream().filter(Objects::nonNull)
                                     .peek(v -> v.setSlack(v.getLatestCompletionTime() - v.getEarliestCompletionTime()))
                                     .filter(v -> v.getSlack() == 0 && v != s && v != t)
                                     .count();
        // Armazena os caminhos criticos
        Vertex[] path = new Vertex[graph.getVertexes().size() - 1];
        _enumeratePaths(path, s, 0, t);
        // Imprime os resultados
        System.out.println("\nTarefa" + "\t" + "EC" + "\t" + "LC" + "\t" + "Folga");
        int task = 0;
        for (Vertex u : graph.getVertexes()) {
            if (u != null && u != s && u != t) {
                System.out.println(++task + "\t" + u.getEarliestCompletionTime() + "\t" + u.getLatestCompletionTime() + "\t" + u.getSlack());
            }
        }
        System.out.println("\nTempo de conclusao: " + t.getLatestCompletionTime());
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
    
    /**
     * Realiza a enumeracao dos caminhos criticos encontrados
     */
    private void _enumeratePaths(Vertex[] path, Vertex u, int index, Vertex t) {
        path[index] = u;
        if (u == t) {
            allPaths.add(path.clone());
        } else {
            u.getAdj().stream()
                      .map(e -> e.otherEnd(u))
                      .filter(v -> ((u.getLatestCompletionTime() == u.getEarliestCompletionTime()) &&
                                    (v.getLatestCompletionTime() == v.getEarliestCompletionTime()) &&
                                    (u.getLatestCompletionTime() + v.getDuration() == v.getLatestCompletionTime())))
                      .forEach(v -> _enumeratePaths(path, v, index + 1, t));
        }
    }

}