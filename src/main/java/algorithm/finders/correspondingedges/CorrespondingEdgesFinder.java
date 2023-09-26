package algorithm.finders.correspondingedges;

import algorithm.graph.Edge;
import algorithm.graph.Graph;

import java.util.Map;
import java.util.Set;

/**
 * Interface for a finder of corresponding edges.
 */
public interface CorrespondingEdgesFinder {
    /**
     * This method finds the corresponding edges
     *
     * @param newGraph  the new obtained graph
     * @param oldGraph1 the first graph
     * @param oldGraph2 the second graph
     * @return map of the corresponding edges
     */
    Map<Set<Edge>, Set<Edge>> findCorrespondingEdges(Graph newGraph, Graph oldGraph1, Graph oldGraph2);

}
