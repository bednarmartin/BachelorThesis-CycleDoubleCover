package algorithm.finders.chord;

import algorithm.graph.Circuit;
import algorithm.graph.Edge;
import algorithm.graph.Graph;

import java.util.HashSet;
import java.util.Set;

/**
 * ChordFinder for finding chords via combination.
 */
public class CombinationChordFinder implements ChordFinder {
    /**
     * Graph to be processed
     */
    private final Graph graph;

    /**
     * Constructor
     *
     * @param graph graph to be processed
     */
    public CombinationChordFinder(Graph graph) {
        this.graph = graph;

    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<Edge> getChords(Circuit circuit) {
        Set<Edge> chordEdges = new HashSet<>();
        for (Edge edge : graph.getEdges()) {
            if (circuit.getEdges().contains(edge)) {
                continue;
            }
            boolean first = false;
            boolean second = false;
            for (Edge edgeToCheck : circuit.getEdges()) {
                if (hasFirst(edge, edgeToCheck)) {
                    first = true;
                }
                if (hasSecond(edge, edgeToCheck)) {
                    second = true;
                }
                if (first && second) {
                    chordEdges.add(edge);
                    break;
                }
            }
        }
        return chordEdges;
    }

    /**
     * This method determines whether the first vertex of the first edge is the same as a vertex of second edge.
     *
     * @param first  first edge
     * @param second second edge
     * @return true if the first vertex of the first edge is the same as a vertex of second edge
     */
    private boolean hasFirst(Edge first, Edge second) {
        return first.getFirst().equals(second.getFirst()) || first.getFirst().equals(second.getSecond());
    }

    /**
     * This method determines whether the second vertex of the first edge is the same as a vertex of second edge.
     *
     * @param first  first edge
     * @param second second edge
     * @return true if the second vertex of the first edge is the same as a vertex of second edge
     */
    private boolean hasSecond(Edge first, Edge second) {
        return first.getSecond().equals(second.getFirst()) || first.getSecond().equals(second.getSecond());
    }

}

