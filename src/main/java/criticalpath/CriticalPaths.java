package criticalpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class CriticalPaths
{
    LinkedList<Vertex> topologicalOrder = new LinkedList<>();
    LinkedList<Vertex> criticalPath = new LinkedList<>();
    DirectedGraph g;

    Map<Integer, Integer> pathLength = new HashMap<>();
    List<Vertex[]> allPaths = new ArrayList<>();
    int numberOfCriticalNodes = 0;

    /*
     * Executa DFS no grafo para encontrar a ordem topologica
     */
    private void DFS(DirectedGraph graph) {
        // Inicializa todos os vertices para a cor branca
        graph.getVertexes().stream()
                           .filter(Objects::nonNull)
                           .forEach(u -> u.setColor('W'));
        graph.getVertexes().stream()
                           .filter(u -> Objects.nonNull(u) && u.getColor() == 'W')
                           .forEach(u -> dfsVisit(graph, u));
    }

    /*
     * Visita a ramificacao de um vertice usando DFS
     */
    private void dfsVisit(DirectedGraph g,Vertex u) {
        u.setColor('G');
        u.getAdj().stream()
                  .map(e -> e.otherEnd(u))
                  .filter(v -> v != null && v.getColor() == 'W')
                  .forEach(v -> dfsVisit(g, v));
        u.setColor('B');
        topologicalOrder.addFirst(u);
    }

    CriticalPaths(DirectedGraph g) {
        this.g = g;
    }

    /**
     * Realiza a enumeracao dos caminhos do inicio ate o final
     */
    private void enumeratePaths(Vertex[] path, Vertex u, int index, Vertex t) {
        path[index] = u;
        if (u != null && u == t) {
            visit(path, index);
        } else {
            u.getAdj().stream()
                      .map(e -> e.otherEnd(u))
                      .filter(v -> ((u.getLatestCompletionTime() == u.getEarliestCompletionTime()) &&
                                    (v.getLatestCompletionTime() == v.getEarliestCompletionTime()) &&
                                    (u.getLatestCompletionTime() + v.getDuration() == v.getLatestCompletionTime())))
                      .forEach(v -> enumeratePaths(path, v, index + 1, t));
        }
    }
    
    private void visit(Vertex[] path, int index) {
        Vertex[] tempPath = path.clone();
        pathLength.put(allPaths.size(), index);
        allPaths.add(tempPath);
    }

    /**
     * Encontra os caminhos críticos em um grafo
     */
    public void findCriticalPaths() {
        DFS(g);
        int size = g.getVertexes().size();
        // EARLIEST COMPLETION TIME
        Vertex s = g.getVertexes().get(size - 2);
        g.getVertexes().stream()
                       .filter(Objects::nonNull)
                       .forEach(u -> u.setEarliestCompletionTime(Integer.MIN_VALUE));
        s.setEarliestCompletionTime(0);
        // Percorre os vertices calculando a EC de cada
        topologicalOrder.forEach(u -> {
            u.getAdj().stream()
                      .map((e) -> e.otherEnd(u))
                      .forEach(v -> v.setEarliestCompletionTime(Math.max(v.getEarliestCompletionTime(),
                                                                         u.getEarliestCompletionTime() + v.getDuration())));
        });
        // LATEST COMPLETION TIME
        Vertex t = g.getVertexes().get(size - 1);
        t.setLatestCompletionTime(t.getEarliestCompletionTime()); // initial value of lc will be equal to ec
        g.getVertexes().stream()
                       .filter(Objects::nonNull)
                       .forEach(u -> u.setLatestCompletionTime(t.getLatestCompletionTime()));
        // Inverte a ordem topologica e percorre novamente os caminhos calculando a LC dos vertices
        Iterator<Vertex> reverseIt = topologicalOrder.descendingIterator();
        while(reverseIt.hasNext())
        {
            Vertex u = reverseIt.next();
            for(Edge e : u.getRevAdj()) {
                Vertex p = e.otherEnd(u);
                p.setLatestCompletionTime(Math.min(p.getLatestCompletionTime(), u.getLatestCompletionTime() - u.getDuration()));
            }
        }
        // Calcula a folga subtraindo EC de LC
        for (Vertex v : g.getVertexes()) {
            if (v != null) {
                v.setSlack(v.getLatestCompletionTime() - v.getEarliestCompletionTime());
                if (v.getSlack() == 0 && v != s && v != t) {
                    numberOfCriticalNodes++;
                }
            }
        }
        // Armazena o caminho critico
        Vertex[] path = new Vertex[g.getVertexes().size() - 1];
        enumeratePaths(path, s, 0, t);
        // Imprime o caminho critico
        System.out.println("\nDuracao de termino: " + t.getLatestCompletionTime());
        Iterator<Entry<Integer, Integer>> it1 = pathLength.entrySet().iterator();
        if (it1.hasNext()) {
            Map.Entry<Integer, Integer> pair = (Entry<Integer, Integer>) it1.next();
            Integer key = pair.getKey();
            int value = pair.getValue();
            Vertex[] temp = allPaths.get(key);
            for (int i = 0; i <= value; i++) {
                if (temp[i] != null && temp[i] != s && temp[i] != t) {
                    System.out.print(temp[i].getName() + " ");
                }
            }
            System.out.println("\n");
        }
        // ImprimeEC, LC and Slack
        System.out.println("Task" + "\t" + "EC" + "\t" + "LC" + "\t" + "Slack");
        int task = 0;
        for (Vertex u : g.getVertexes()) {
            if (u != null && u != s && u != t) {
                System.out.println(++task + "\t" + u.getEarliestCompletionTime() + "\t" + u.getLatestCompletionTime() + "\t" + u.getSlack());
            }
        }
        // Imprime o numero de nodes criticos e caminhos criticos
        System.out.println("\n" + numberOfCriticalNodes);
        System.out.println(allPaths.size());
        // Imprime o caminho critico
        Iterator<Entry<Integer, Integer>> it = pathLength.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> pair = (Entry<Integer, Integer>) it.next();
            Integer key = pair.getKey();
            int value = pair.getValue();
            Vertex[] temp = allPaths.get(key);
            for (int i = 0; i <= value; i++) {
                if (temp[i] != null && temp[i] != s && temp[i] != t) {
                    System.out.print(temp[i].getName() + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}