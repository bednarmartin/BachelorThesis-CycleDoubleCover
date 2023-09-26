package algorithm.finders.correspondingedges;

import algorithm.graph.Edge;
import algorithm.graph.Graph;
import algorithm.graph.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class representing an algorithm to find corresponding edges via checking all combinations.
 */
public class CombinationCorrespondingEdgesFinder implements CorrespondingEdgesFinder {

    /**
     * @inheritDoc
     */

    public Map<Set<Edge>, Set<Edge>> findCorrespondingEdges(Graph newGraph, Graph oldGraph1, Graph oldGraph2) {
        Map<Set<Edge>, Set<Edge>> correspondingEdges = new HashMap<>();
        Set<Edge> oldEdges = new HashSet<>();
        oldEdges.addAll(oldGraph1.getEdges());
        oldEdges.addAll(oldGraph2.getEdges());

        Set<Edge> removedEdges = new HashSet<>(oldEdges);
        removedEdges.removeAll(newGraph.getEdges());

        Set<Edge> addedEdges = new HashSet<>(newGraph.getEdges());
        addedEdges.removeAll(oldEdges);

        for (Edge removedEdge1 : removedEdges) {
            for (Edge removedEdge2 : removedEdges) {
                if (removedEdge1 != removedEdge2) {
                    if ((!(oldGraph1.getEdges().contains(removedEdge1) && oldGraph1.getEdges().contains(removedEdge2))) && (!(oldGraph2.getEdges().contains(removedEdge1) && !oldGraph2.getEdges().contains(removedEdge1)))) {
                        Set<Vertex> removalVertices = new HashSet<>();
                        removalVertices.add(removedEdge1.getFirst());
                        removalVertices.add(removedEdge1.getSecond());
                        removalVertices.add(removedEdge2.getFirst());
                        removalVertices.add(removedEdge2.getSecond());
                        for (Edge addedEdge1 : addedEdges) {
                            for (Edge addedEdge2 : addedEdges) {
                                if (addedEdge1 != addedEdge2) {
                                    Set<Vertex> additionVertices = new HashSet<>();
                                    additionVertices.add(addedEdge1.getFirst());
                                    additionVertices.add(addedEdge1.getSecond());
                                    additionVertices.add(addedEdge2.getFirst());
                                    additionVertices.add(addedEdge2.getSecond());
                                    if (additionVertices.equals(removalVertices)) {
                                        Set<Edge> removalEdges = new HashSet<>();
                                        removalEdges.add(removedEdge1);
                                        removalEdges.add(removedEdge2);
                                        Set<Edge> additionEdges = new HashSet<>();
                                        additionEdges.add(addedEdge1);
                                        additionEdges.add(addedEdge2);
                                        correspondingEdges.put(removalEdges, additionEdges);
                                    }
                                }
                            }
                        }
                    }

                }
            }

        }
        return correspondingEdges;
    }
}
