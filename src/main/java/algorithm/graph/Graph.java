package algorithm.graph;

import algorithm.determiners.bridge.BridgeDeterminer;
import algorithm.determiners.connectivity.ConnectedGraphDeterminer;
import algorithm.exceptions.InconsistentGraphException;
import algorithm.finders.threecut.ThreeCutFinder;
import algorithm.finders.twocut.TwoCutFinder;

import java.util.List;
import java.util.Set;

/**
 * Interface for representing a graph
 */

public interface Graph {
    /**
     * This method grants access to the number of the vertices of the graph.
     *
     * @return number of vertices of the graph
     */
    int getNumberOfVertices();

    /**
     * This method grants access to the vertices of the graph.
     *
     * @return list of vertices of the graph
     */
    List<Vertex> getVertices();

    /**
     * This method grants access to the edges of the graph.
     *
     * @return set of edges of the graph
     */
    Set<Edge> getEdges();

    /**
     * This method adds a vertex to the graph.
     *
     * @param vertex vertex to be added
     */
    void addVertex(Vertex vertex) throws InconsistentGraphException;

    /**
     * This method adds an edge to the graph.
     *
     * @param edge edge to be added
     */
    void addEdge(Edge edge) throws InconsistentGraphException;

    /**
     * This method determines whether the graph has a bridge.
     *
     * @param bridgeDeterminer bridge determiner
     * @return true if there exists a bridge in the graph.
     */
    boolean hasBridge(BridgeDeterminer bridgeDeterminer);

    /**
     * This method determines whether the graph is connected.
     *
     * @param connectedGraphDeterminer connected graph determiner
     * @return true if the graph is connected.
     */
    boolean isConnected(ConnectedGraphDeterminer connectedGraphDeterminer);

    /**
     * This method finds 2-cuts of the graph
     *
     * @param twoCutFinder two cut finder
     * @return list of lists of the edges that make a 2-cut.
     */
    List<List<Edge>> get2Cuts(TwoCutFinder twoCutFinder);

    /**
     * This method finds 3-cuts of the graph
     *
     * @param threeCutFinder three cut finder
     * @return list of lists of the edges that make a 3-cut.
     */
    List<List<Edge>> get3Cuts(ThreeCutFinder threeCutFinder);

    /**
     * This method finds circuit of the graph
     *
     * @return list of the circuits of the graph
     */
    List<Circuit> getCircuits();

    /**
     * This method converts the string representation of the graph so it's convenient to visualize it via
     * <a href="https://csacademy.com/app/graph_editor/">https://csacademy.com/app/graph_editor/</a>
     */

    String getRepresentationForVisualization();

    /**
     * @return True if the graph is consistent
     */
    boolean isConsistent();

    /**
     * This method determine if the cycle exists in the graph.
     *
     * @param cycle considered cycle
     * @return True if the cycle exists in the graph
     */
    boolean isCycleOfTheGraph(Cycle cycle);

    /**
     * This method finds internally the cycles of the graph.
     */
    void findCycles();
}
