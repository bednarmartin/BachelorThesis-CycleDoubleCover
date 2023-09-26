package algorithm.reading.iterator;

import algorithm.exceptions.InconsistentCircuitException;
import algorithm.exceptions.InconsistentGraphException;
import algorithm.graph.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;

/**
 * This class represent an algorithm for reading files that represents circuits as list of vertices
 */
public class VerticesFormatCircuitIterator implements CircuitIterator {
    /**
     * number of circuits to be read
     */
    private int numberOfCircuits;
    /**
     * the graph of which circuits are read.
     */
    private final Graph graph;

    /**
     * how many circuits were read
     */
    private int counter;

    /**
     * Buffered Reader for reading
     */
    private BufferedReader bufferedReader;

    /**
     * starting point for reading circuits
     */
    private final int start;

    /**
     * ending point for reading circuits
     */
    private final int end;

    /**
     * Constructor without specified start or the end.
     *
     * @param path  path of the file
     * @param graph graph
     * @throws IOException - wrong format of the circuits
     */
    public VerticesFormatCircuitIterator(String path, Graph graph) throws IOException {
        start = 0;
        this.graph = graph;
        preprocess(path);
        end = numberOfCircuits;
    }

    /**
     * Constructor with specified start.
     *
     * @param path  path of the file
     * @param graph graph
     * @param start index of the first circuit to be read
     * @throws IOException - wrong format of the circuits
     */
    public VerticesFormatCircuitIterator(String path, Graph graph, int start) throws IOException {
        this.start = start - 1;
        this.graph = graph;
        preprocess(path);
        end = numberOfCircuits;
    }

    /**
     * Constructor with specified start and the end.
     *
     * @param path  path of the file
     * @param graph graph
     * @param start index of the first circuit to be read
     * @param end   index of the last circuit to be read
     * @throws IOException - wrong format of the circuits
     */
    public VerticesFormatCircuitIterator(String path, Graph graph, int start, int end) throws IOException {
        this.start = start - 1;
        this.end = end;
        this.graph = graph;
        preprocess(path);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getNumberOfCircuits() {
        return numberOfCircuits;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Circuit next() throws IOException, InconsistentCircuitException{
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Set<Edge> edges = new HashSet<>();
        Circuit circuit;
        String line = bufferedReader.readLine();
        String[] splitLine = line.split(" ");
        Vertex first = null;
        for (String s : splitLine) {
            Vertex second;
            int toVertex = Integer.parseInt(s);
            if (toVertex >= graph.getNumberOfVertices()) {
                throw new InconsistentCircuitException("Such vertex " + toVertex + " doesn't exist!");
            }
            if (first == null) {
                first = graph.getVertices().get(toVertex);
            } else {
                second = graph.getVertices().get(toVertex);
                Edge edge;
                edge = new CubicEdge(first, second);
                if (!graph.getEdges().contains(edge)) {
                    throw new InconsistentCircuitException("Edge " + edge + " doesn't exists!");
                }
                edges.add(edge);
                first = second;
            }

        }
        circuit = new CubicCircuit(edges);
        if (!graph.getCircuits().contains(circuit)) {
            throw new InconsistentCircuitException("Such circuit " + Arrays.toString(splitLine) + " doesn't exists");
        }
        counter++;
        return circuit;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasNext() {
        return start + counter < end;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int actualIndex() {
        return start + counter;
    }

    /**
     * This method preprocess file for reading
     *
     * @param path path of the file
     * @throws IOException wrong format of the circuits
     */
    private void preprocess(String path) throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(path));
        int numberOfLines;
        try (Stream<String> stream = Files.lines(Paths.get(path), Charset.defaultCharset())) {
            numberOfLines = (int) stream.count();
            this.numberOfCircuits = numberOfLines;
        } catch (Exception e) {
            throw new IOException();
        }
        this.counter = 0;
    }

}
