package algorithm.filters.circuit;

import algorithm.finders.chord.ChordFinder;
import algorithm.graph.Circuit;

import java.util.ArrayList;
import java.util.List;

/**
 * Circuit Filter for filtering circuits that are not induced.
 */
public class InducedCircuitFilter extends CircuitFilterDecorator {

    private final ChordFinder chordFinder;

    /**
     * Constructor
     *
     * @param chordFinder Chord Finder
     */
    public InducedCircuitFilter(ChordFinder chordFinder) {
        super();
        this.chordFinder = chordFinder;
    }

    /**
     * Constructor
     *
     * @param circuitFilter Circuit Filter
     * @param chordFinder   Chord Finder
     */
    public InducedCircuitFilter(CircuitFilter circuitFilter, ChordFinder chordFinder) {
        super(circuitFilter);
        this.chordFinder = chordFinder;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Circuit> getFilteredCircuits(List<Circuit> circuits) {
        List<Circuit> newCircuits = new ArrayList<>((super.getFilteredCircuits(circuits) == null) ? circuits : super.getFilteredCircuits(circuits));
        newCircuits.removeIf(circuit -> !circuit.getChords(chordFinder).isEmpty());
        return newCircuits;

    }

}
