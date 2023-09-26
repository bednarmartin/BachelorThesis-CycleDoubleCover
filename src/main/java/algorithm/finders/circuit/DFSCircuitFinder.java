package algorithm.finders.circuit;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.graph.*;

import java.util.*;

/**
 * Class representing an algorithm for finding all the circuits of the graph using DFS algorithm.
 */
public class DFSCircuitFinder implements CircuitFinder {

    /**
     * Set of Sets of Edges in order to eliminate duplicates
     */
    private final Set<Set<Edge>> allCircuits = new HashSet<>();

    /**
     * @inheritDoc
     */
    @Override
    public List<Circuit> getCircuits(Graph graph){
        allCircuits.clear();
        List<Circuit> circuits = new ArrayList<>();
        for (int i = 3; i <= graph.getNumberOfVertices(); i++) {
            findCircuitsOfLength(i, graph);
        }
        for (Set<Edge> edges : allCircuits) {
            circuits.add(new CubicCircuit(new HashSet<>(edges)));
        }
        return circuits;
    }

    /**
     * This method finds circuits of particular length
     *
     * @param number length of the circuits to be found
     * @param graph  graph to be processed
     */
    private void findCircuitsOfLength(int number, Graph graph) {
        boolean[] marked = new boolean[Collections.max(graph.getVertices()).getNumber() + 1];
        for (int i = 0; i < graph.getNumberOfVertices() - (number - 1); i++) {
            List<Vertex> vertices = new ArrayList<>();
            vertices.add(graph.getVertices().get(i));
            dfs(marked, number - 1, graph.getVertices().get(i), graph.getVertices().get(i), vertices, graph);
            marked[graph.getVertices().get(i).getNumber()] = true;
        }
    }

    /**
     * DFS algorithm modified to find all the circuits in the graph
     *
     * @param marked   marked vertices
     * @param n        vertices remaining
     * @param vertex   actual vertex
     * @param start    starting vertex
     * @param vertices list of vertices
     * @param graph    graph to be processed
     */
    private void dfs(boolean[] marked, int n, Vertex vertex, Vertex start, List<Vertex> vertices, Graph graph) {
        marked[vertex.getNumber()] = true;
        if (n == 0) {
            marked[vertex.getNumber()] = false;
            if (vertex.getNeighbors().contains(start)) {
                allCircuits.add(makeSetOfEdges(vertices, start));
            }
            return;

        }
        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            if (!marked[graph.getVertices().get(i).getNumber()] && vertex.getNeighbors().contains(graph.getVertices().get(i))) {
                List<Vertex> newVertices = new ArrayList<>(vertices);
                newVertices.add(graph.getVertices().get(i));
                dfs(marked, n - 1, graph.getVertices().get(i), start, newVertices, graph);
            }
        }
        marked[vertex.getNumber()] = false;
    }

    /**
     * This method makes a Set of edges out of List of vertices
     *
     * @param vertices list of vertices to transform
     * @param start    first vertex of the circuit
     * @return Set of edges representing a circuit
     */

    private Set<Edge> makeSetOfEdges(List<Vertex> vertices, Vertex start) {
        vertices.add(start);
        Set<Edge> edges = new HashSet<>();
        try {
            for (int i = 1; i < vertices.size(); i++) {
                edges.add(new CubicEdge(vertices.get(i - 1), vertices.get(i)));
            }
            vertices.remove(vertices.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return edges;
    }

}
