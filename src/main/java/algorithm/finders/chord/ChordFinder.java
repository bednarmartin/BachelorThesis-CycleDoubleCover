package algorithm.finders.chord;

import algorithm.graph.Circuit;
import algorithm.graph.Edge;

import java.util.Set;


/**
 * Interface for finding chords of the graph
 */
public interface ChordFinder {
    /**
     * This method finds chords of the circuit.
     *
     * @param circuit circuit to be processed
     * @return set of chords
     */
    Set<Edge> getChords(Circuit circuit);
}
