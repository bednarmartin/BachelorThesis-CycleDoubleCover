package algorithm.finders.strongedge;

import algorithm.finders.chord.ChordFinder;
import algorithm.finders.chord.CombinationChordFinder;
import algorithm.graph.Cycle;
import algorithm.graph.CycleDoubleCover;
import algorithm.graph.Edge;
import algorithm.graph.Graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing an algorithm for determining strong edges using chords.
 */
public class ChordsStrongEdgesFinder implements StrongEdgesFinder {

    /**
     * Graph to be processed.
     */
    private final Graph graph;

    /**
     * Algorithm for finding chords
     */
    private final ChordFinder chordFinder;

    /**
     * Constructor
     *
     * @param graph graph to be processed
     */
    public ChordsStrongEdgesFinder(Graph graph) {
        this.graph = graph;
        chordFinder = new CombinationChordFinder(graph);
    }

    /**
     * Constructor
     *
     * @param graph       graph to be processed
     * @param chordFinder chordFinder
     */
    public ChordsStrongEdgesFinder(Graph graph, ChordFinder chordFinder) {
        this.graph = graph;
        this.chordFinder = chordFinder;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<Edge> getStrongEdges(CycleDoubleCover cycleDoubleCover) {
        Set<Edge> allEdges = new HashSet<>(graph.getEdges());
        for (Cycle cycle : cycleDoubleCover.getCycles()) {
            for (Edge edge : cycle.getChords(chordFinder)) {
                allEdges.remove(edge);
            }
        }
        return allEdges;

    }
}
