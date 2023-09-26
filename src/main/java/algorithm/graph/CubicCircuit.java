package algorithm.graph;

import java.util.Set;

/**
 * Class representing a circuit of the cubic graph.
 */
public class CubicCircuit extends CubicCycle implements Circuit {
    /**
     * Constructor of the class CubicCircuit
     *
     * @param edges edges of the circuit
     */
    public CubicCircuit(Set<Edge> edges) {
        super(edges);
        circuits.add(this);
    }

}
