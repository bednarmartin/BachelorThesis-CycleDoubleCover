package algorithm.graph;

import algorithm.finders.chord.ChordFinder;
import algorithm.finders.chord.CombinationChordFinder;

import java.util.*;

/**
 * Class representing a cycle in a cubic graph.
 */
public class CubicCycle implements Circuit {
    /**
     * circuits of the cycle
     */
    final Set<Circuit> circuits;

    /**
     * edges of the cycle
     */
    private final Set<Edge> edges;

    /**
     * vertices of the cycle
     */
    private final Set<Vertex> vertices;
    /**
     * Map of the removed edges and circuits that contain them
     */
    private final Map<Edge, Circuit> removedEdges;
    /**
     * Set of added edges
     */
    private final Set<Edge> addedEdges;


    /**
     * Constructor for cycle.
     *
     * @param edges    edges of the cycle
     * @param circuits set of circuits that the cycle comprises.
     */
    public CubicCycle(Set<Edge> edges, Set<Circuit> circuits) {
        this.edges = edges;
        this.vertices = processEdges(edges);
        this.circuits = circuits;
        this.removedEdges = new HashMap<>();
        this.addedEdges = new HashSet<>();
    }

    /**
     * Constructor
     *
     * @param cycles cycles
     */
    public CubicCycle(Cycle... cycles) {
        this.edges = new HashSet<>();
        this.circuits = new HashSet<>();
        for (Cycle cycle : cycles) {
            edges.addAll(cycle.getEdges());
            circuits.addAll(cycle.getCircuits());
        }
        this.vertices = processEdges(edges);
        this.removedEdges = new HashMap<>();
        this.addedEdges = new HashSet<>();
    }

    /**
     * Constructor for circuit.
     *
     * @param edges edges of the circuits
     */
    protected CubicCycle(Set<Edge> edges) {
        this.edges = edges;
        this.vertices = processEdges(edges);
        this.circuits = new HashSet<>();
        this.removedEdges = new HashMap<>();
        this.addedEdges = new HashSet<>();

    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<Edge> getEdges() {
        return edges;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<Edge> getChords(ChordFinder chordFinder) {
        return chordFinder.getChords(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<Vertex> getVertices() {
        return this.vertices;
    }


    @Override
    public String toString() {
        List<Edge> copy = new ArrayList<>(getEdges());
        StringBuilder output = new StringBuilder();
        Edge firstEdge = copy.get(0);
        Vertex firstVertex = firstEdge.getFirst();
        output.append(firstVertex.toString()).append(" ");
        copy.remove(firstEdge);
        Vertex nextVertex = firstEdge.getSecond();
        output.append(nextVertex.toString()).append(" ");
        while (!copy.isEmpty()) {
            for (Edge edge : getEdges()) {
                if (!copy.contains(edge)) {
                    continue;
                }

                if (edge.getFirst().equals(nextVertex)) {
                    copy.remove(edge);
                    output.append(edge.getSecond().toString()).append(" ");
                    nextVertex = edge.getSecond();
                } else if (edge.getSecond().equals(nextVertex)) {
                    copy.remove(edge);
                    output.append(edge.getFirst().toString()).append(" ");
                    nextVertex = edge.getFirst();
                }
                if (nextVertex.equals(firstVertex)) {
                    output.deleteCharAt(output.length() - 1);
                    output.append("; ");
                    if (copy.isEmpty()) {
                        break;
                    } else {
                        firstVertex = copy.get(0).getFirst();
                        output.append(firstVertex.toString()).append(" ");
                        nextVertex = copy.get(0).getSecond();
                        copy.remove(0);
                    }

                }

            }

        }
        return output.toString();
    }


    @Override
    public int hashCode() {
        return edges.hashCode();
    }

    @Override
    public boolean equals(Object objectToCompare) {
        if (this == objectToCompare) {
            return true;
        }
        if (objectToCompare == null) {
            return false;
        }
        if (getClass() != objectToCompare.getClass()) {
            return false;
        } else {
            return ((Cycle) objectToCompare).getEdges().equals(this.getEdges());
        }
    }

    /**
     * This method extracts vertices of the edges.
     *
     * @param edges - edges to extract vertices from
     * @return Set of vertices of the cycle
     */
    private Set<Vertex> processEdges(Set<Edge> edges) {
        Set<Vertex> vertices = new HashSet<>();
        for (Edge edge : edges) {
            vertices.addAll(Arrays.asList(edge.getFirst(), edge.getSecond()));
        }
        return vertices;
    }

    /**
     * @inheritDoc
     */
    public boolean isInduced(Graph graph) {
        return getChords(new CombinationChordFinder(graph)).isEmpty() && determineWhetherIsInduced();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<Circuit> getCircuits() {
        return new HashSet<>(circuits);
    }

    /**
     * This method determine whether the cycle is induced.
     *
     * @return true if the cycle is induced
     */
    private boolean determineWhetherIsInduced() {
        for (Circuit circuit1 : circuits) {
            for (Circuit circuit2 : circuits) {
                if (!circuit1.equals(circuit2)) {
                    for (Vertex vertex : circuit1.getVertices()) {
                        if (!Collections.disjoint(vertex.getNeighbors(), circuit2.getVertices())) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * This method removed the edge from the cycle.
     *
     * @param edge the edge to be removed
     */
    public void removeEdge(Edge edge) {
        if (edges.contains(edge)) {
            edges.remove(edge);
            removedEdges.put(edge, this);
            for (Circuit circuit : circuits) {
                if (circuit.getEdges().contains(edge)) {
                    circuit.getEdges().remove(edge);
                    removedEdges.put(edge, circuit);
                }

            }
        }
    }

    /**
     * This method removes the edge from the cycle permanently.
     *
     * @param edge the edge to be removed permanently
     */
    public void removeEdgePermanently(Edge edge) {
        if (edges.contains(edge)) {
            edges.remove(edge);
            for (Circuit circuit : circuits) {
                circuit.getEdges().remove(edge);

            }
        }
    }

    /**
     * This method reverses the removal of edges
     */
    public void undoRemoveEdge() {
        for (Edge edge : removedEdges.keySet()) {
            Circuit circuit = removedEdges.get(edge);
            circuit.getEdges().add(edge);
            edges.add(edge);
        }
        removedEdges.clear();
    }

    /**
     * This method allows to add an edge to the cycle.
     *
     * @param edge the edge to be added
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
        addedEdges.add(edge);
    }

    /**
     * This method grants access to the added edges.
     *
     * @return set of the added edges
     */
    public Set<Edge> getAddedEdges() {
        Set<Edge> addedEdges2 = new HashSet<>(addedEdges);
        for (Cycle cycle : circuits) {
            if (!cycle.equals(this)) {
                addedEdges2.addAll(cycle.getAddedEdges());
            }
        }
        return addedEdges2;
    }

    /**
     * This method reverses the addition of the edges.
     */
    public void undoAddedEdge() {
        for (Edge edge : getAddedEdges()) {
            edges.remove(edge);
        }
        addedEdges.clear();
    }

    /**
     * This method grants access to the removed edges.
     *
     * @return set of the removed edges
     */
    public Set<Edge> getRemovedEdges() {
        return removedEdges.keySet();
    }

}
