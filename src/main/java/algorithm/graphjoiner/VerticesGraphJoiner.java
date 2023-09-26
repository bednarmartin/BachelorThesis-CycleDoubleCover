package algorithm.graphjoiner;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.graph.*;

import java.util.*;

/**
 * Class representing a joiner of two graphs by selected edges to remove and add.
 */
public class VerticesGraphJoiner implements GraphJoiner {
    /**
     * Map of the edges to be added.
     */
    private final Map<Vertex, Vertex> toConnectVertices;
    /**
     * Map of the edges to be removed.
     */
    private final Map<Vertex, Vertex> toDisconnectVertices;

    /**
     * Constructor.
     *
     * @param toConnectVertices    Map of the edges to be added.
     * @param toDisconnectVertices Map of the edges to be removed.
     */
    public VerticesGraphJoiner(Map<Vertex, Vertex> toConnectVertices, Map<Vertex, Vertex> toDisconnectVertices) {
        this.toConnectVertices = toConnectVertices;
        this.toDisconnectVertices = toDisconnectVertices;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Graph joinGraphs(Graph graph1, Graph graph2) {
        int newNumberOfVertices = graph1.getNumberOfVertices() + graph2.getNumberOfVertices();
        Graph newGraph = new CubicGraph();
        Vertex[] newVerticesArray = new Vertex[newNumberOfVertices];
        Set<Edge> newEdges = new HashSet<>();
        for (Graph graphToCopy : Arrays.asList(graph1, graph2)) {
            for (Edge edge : graphToCopy.getEdges()) {
                Vertex newFirstVertex = new CubicVertex(edge.getFirst().getNumber());
                Vertex newSecondVertex = new CubicVertex(edge.getSecond().getNumber());

                if (newVerticesArray[newFirstVertex.getNumber()] == null) {
                    newVerticesArray[newFirstVertex.getNumber()] = newFirstVertex;
                } else {
                    newFirstVertex = newVerticesArray[newFirstVertex.getNumber()];
                }

                if (newVerticesArray[newSecondVertex.getNumber()] == null) {
                    newVerticesArray[newSecondVertex.getNumber()] = newSecondVertex;
                } else {
                    newSecondVertex = newVerticesArray[newSecondVertex.getNumber()];
                }

                Edge newEdge = new CubicEdge(newFirstVertex, newSecondVertex);
                newEdges.add(newEdge);
                try {
                    newFirstVertex.addNeighbor(newSecondVertex);
                } catch (InconsistentGraphException e) {
                    e.printStackTrace();
                }
            }
        }
        Set<Vertex> newVertices = new HashSet<>(Arrays.asList(newVerticesArray));
        for (Vertex vertex1 : toDisconnectVertices.keySet()) {
            Vertex vertex2 = toDisconnectVertices.get(vertex1);
            Optional<Edge> edgeToBeRemoved = newEdges.stream()
                    .filter(test -> test.equals(new CubicEdge(vertex1, vertex2)))
                    .findFirst();
            if (edgeToBeRemoved.isPresent()) {
                Edge removedEdge = edgeToBeRemoved.get();
                removedEdge.getFirst().getNeighbors().remove(removedEdge.getSecond());
                removedEdge.getSecond().getNeighbors().remove(removedEdge.getFirst());
                newEdges.remove(removedEdge);
            } else {
                throw new RuntimeException("The edge " + vertex1 + " " + vertex2 + " does not exist");
            }
        }
        for (Vertex vertex1 : toConnectVertices.keySet()) {
            Vertex vertex2 = toConnectVertices.get(vertex1);
            Optional<Vertex> firstVertexToAdd = newVertices.stream()
                    .filter(test -> test.equals(vertex1))
                    .findFirst();

            Optional<Vertex> secondVertexToAdd = newVertices.stream()
                    .filter(test -> test.equals(vertex2))
                    .findFirst();
            if (firstVertexToAdd.isPresent() && secondVertexToAdd.isPresent()) {
                Vertex firstVertex = firstVertexToAdd.get();
                Vertex secondVertex = secondVertexToAdd.get();
                Edge newEdge = new CubicEdge(firstVertex, secondVertex);
                newEdges.add(newEdge);
                firstVertex.getNeighbors().add(secondVertex);
                secondVertex.getNeighbors().add(firstVertex);
            } else {
                throw new RuntimeException("Such vertex does not exist");
            }

        }
        try {
            for (Vertex vertex : newVertices) {
                newGraph.addVertex(vertex);
            }
            for (Edge edge : newEdges) {
                newGraph.addEdge(edge);
            }
        } catch (InconsistentGraphException e) {
            e.printStackTrace();
        }
        return newGraph;
    }
}
