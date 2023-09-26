package algorithm.graph;

import algorithm.determiners.bridge.BridgeDeterminer;
import algorithm.determiners.connectivity.ConnectedGraphDeterminer;
import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.StopRecursionException;
import algorithm.finders.circuit.CircuitFinder;
import algorithm.finders.circuit.DFSCircuitFinder;
import algorithm.finders.threecut.ThreeCutFinder;
import algorithm.finders.twocut.TwoCutFinder;

import java.util.*;

/**
 * Class representing a cubic graph
 */
public class CubicGraph implements Graph {

    /**
     * List containing all the vertices of the graph
     */
    private final List<Vertex> vertices;

    /**
     * Set containing all the edges of the graph
     */
    private final Set<Edge> edges;

    /**
     * List containing all the circuits of the graph
     */
    private List<Circuit> circuits;


    /**
     * Circuit finder
     */
    private final CircuitFinder circuitFinder;

    /**
     * Constructor of the CubicGraph class with default circuit finder.
     */
    public CubicGraph() {
        this.vertices = new ArrayList<>();
        this.edges = new HashSet<>();
        circuitFinder = new DFSCircuitFinder();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getNumberOfVertices() {
        return this.vertices.size();
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Vertex> getVertices() {
        return this.vertices;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<Edge> getEdges() {
        return this.edges;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addVertex(Vertex vertex) throws InconsistentGraphException {
        if (this.vertices.contains(vertex)) {
            throw new InconsistentGraphException("This vertex is already in graph!");
        }
        this.vertices.add(vertex);
    }

    /**
     * Adds an edge to a graph
     *
     * @param edge edge to be added
     */
    @Override
    public void addEdge(Edge edge) throws InconsistentGraphException {
        if (!this.vertices.contains(edge.getFirst()) || !this.vertices.contains(edge.getSecond())) {
            throw new InconsistentGraphException("This edge doesn't exists!");
        }
        if (this.edges.contains(edge)) {
            throw new InconsistentGraphException("This edge is already in graph!");
        }
        this.edges.add(edge);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasBridge(BridgeDeterminer bridgeDeterminer) {
        return bridgeDeterminer.hasBridge(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isConnected(ConnectedGraphDeterminer connectedGraphDeterminer) {
        return connectedGraphDeterminer.isConnected(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<List<Edge>> get2Cuts(TwoCutFinder twoCutFinder) {
        return twoCutFinder.getTwoCuts(this);

    }

    /**
     * @inheritDoc
     */
    @Override
    public List<List<Edge>> get3Cuts(ThreeCutFinder threeCutFinder) {
        return threeCutFinder.getThreeCuts(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Circuit> getCircuits() {
        if (circuits == null) {
            circuits = circuitFinder.getCircuits(this);
        }
        return circuits;
    }

    @Override
    public String getRepresentationForVisualization() {
        StringBuilder answer = new StringBuilder();
        for (Vertex vertex : this.vertices) {
            for (Vertex neighbor : vertex.getNeighbors()) {
                if (vertex.getNumber() < neighbor.getNumber()) {
                    answer.append(vertex.getNumber());
                    answer.append(" ");
                    answer.append(neighbor.getNumber());
                    answer.append("\n");
                }
            }
        }
        answer.deleteCharAt(answer.length() - 1);
        return answer.toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isConsistent() {
        if (edges.size() != (3 * vertices.size()) / 2) {
            return false;
        }
        if (vertices.size() % 2 != 0) {
            return false;
        }
        for (Vertex vertex : getVertices()) {
            if (vertex.getNeighbors().size() != 3) {
                return false;
            }
            for (Vertex neighbor : vertex.getNeighbors()) {
                if (!edges.contains(new CubicEdge(vertex, neighbor))) {
                    return false;
                }
            }
        }
        for (Edge edge : getEdges()) {
            if (!vertices.contains(edge.getFirst())) {
                return false;
            }
            if (!vertices.contains(edge.getSecond())) {
                return false;
            }
        }
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isCycleOfTheGraph(Cycle cycle) {
        Set<Edge> cycleEdges = new HashSet<>();
        Set<Edge> edgesThatAreNotPartOfAnyCircuits = new HashSet<>(cycle.getEdges());
        List<Vertex> vertices = new ArrayList<>(cycle.getVertices());
        Map<Vertex, List<Vertex>> map = process(cycle);
        int size = 0;
        circuits = new ArrayList<>();
        while (!vertices.isEmpty()) {
            Map<Vertex, Boolean> check = new HashMap<>();
            for (Vertex vertex : vertices) {
                check.put(vertex, false);
            }
            try {
                DFS(vertices.get(0), vertices.get(0), vertices.get(0), map, new HashSet<>(), true, check);
            } catch (StopRecursionException ignored) {
            }

            if (circuits.size() != size) {
                size = circuits.size();
                Circuit newCircuit = circuits.get(size - 1);
                cycleEdges.addAll(newCircuit.getEdges());
                vertices.removeAll(newCircuit.getVertices());
            } else {
                vertices.remove(0);
            }
        }

        edgesThatAreNotPartOfAnyCircuits.removeAll(cycleEdges);

        if (edgesThatAreNotPartOfAnyCircuits.isEmpty() || cycle.getAddedEdges().containsAll(edgesThatAreNotPartOfAnyCircuits)) {
            for (Edge edge : edgesThatAreNotPartOfAnyCircuits) {
                cycle.removeEdgePermanently(edge);
            }
            return true;
        }
        return false;
    }

    /**
     * This method processed a cycle
     *
     * @param cycle the cycle to be processed
     * @return map of vertices of the cycles and corresponding neighbors
     */
    private Map<Vertex, List<Vertex>> process(Cycle cycle) {
        Map<Vertex, List<Vertex>> map = new HashMap<>();
        for (Vertex vertex : cycle.getVertices()) {
            List<Vertex> neighbors = new ArrayList<>();
            for (Edge edge : cycle.getEdges()) {
                if (edge.getFirst().equals(vertex)) {
                    neighbors.add(edge.getSecond());
                } else if (edge.getSecond().equals(vertex)) {
                    neighbors.add(edge.getFirst());
                }
            }
            map.put(vertex, neighbors);
        }
        return map;
    }

    private void DFS(Vertex first, Vertex previous, Vertex actual, Map<Vertex, List<Vertex>> map, Set<Edge> currentCircuit, boolean initial, Map<Vertex, Boolean> check) throws StopRecursionException {
        if (first.equals(actual) && !initial) {
            circuits.add(new CubicCircuit(currentCircuit));
            throw new StopRecursionException();
        }
        if (!check.containsKey(actual)) {
            return;
        }
        if (check.get(actual)) {
            return;
        }
        check.put(actual, true);
        for (Vertex neighbor : map.get(actual)) {
            if (neighbor.equals(previous)) {
                continue;
            }
            Edge edge = new CubicEdge(actual, neighbor);
            currentCircuit.add(edge);

            DFS(first, actual, neighbor, map, currentCircuit, false, check);
            currentCircuit.remove(edge);

        }
        check.put(actual, false);
    }

    @Override
    public void findCycles() {
        if (circuits == null) {
            circuits = circuitFinder.getCircuits(this);
        }

    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        for (Vertex vertex : this.vertices) {
            for (Vertex toPrintVertex : vertex.getNeighbors()) {
                answer.append(toPrintVertex.getNumber());
                answer.append(" ");
            }
            answer.deleteCharAt(answer.length() - 1);
            answer.append("\n");
        }
        return answer.toString();
    }
}

