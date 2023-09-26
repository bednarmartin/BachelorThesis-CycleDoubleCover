package algorithm.finders.strongedge;

import algorithm.graph.*;

import java.util.*;

/**
 * Class representing an algorithm for determining strong edges by comparing the circuits that cover the end vertices of the edges.
 */
public class ComparisonStrongEdgesFinder implements StrongEdgesFinder {
    /**
     * Graph to be processed
     */
    private final Graph graph;
    /**
     * Map vertex -> cycles that cover the vertex
     */
    private final Map<Vertex, Set<Cycle>> circuitMap;

    /**
     * Constructor for the class ComparisonStrongEdgesFinder
     *
     * @param graph graph to be processed
     */
    public ComparisonStrongEdgesFinder(Graph graph) {
        this.graph = graph;
        this.circuitMap = new HashMap<>();
        for (Vertex vertex : graph.getVertices()) {
            circuitMap.put(vertex, new HashSet<>());
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<Edge> getStrongEdges(CycleDoubleCover cycleDoubleCover) {
        Set<Edge> strongEdges = new HashSet<>();
        process(cycleDoubleCover);
        for (Edge edge : graph.getEdges()) {
            if (!circuitMap.get(edge.getFirst()).equals(circuitMap.get(edge.getSecond()))) {
                strongEdges.add(edge);
            }
        }
        clear();
        return strongEdges;
    }

    /**
     * This method adds cycles that cover vertices into circuitMap
     *
     * @param cycleDoubleCover Cycle Double Cover to be processed
     */
    private void process(CycleDoubleCover cycleDoubleCover) {
        for (Cycle cycle : cycleDoubleCover.getCycles()) {
            for (Edge edge : cycle.getEdges()) {
                circuitMap.get(edge.getFirst()).add(cycle);
                circuitMap.get(edge.getSecond()).add(cycle);
            }
        }
    }

    /**
     * This method clears the circuitMap
     */
    private void clear() {
        for (Vertex vertex : graph.getVertices()) {
            circuitMap.put(vertex, new HashSet<>());
        }
    }
}
