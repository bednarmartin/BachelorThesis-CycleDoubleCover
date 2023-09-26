package algorithm.reading.iterator;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.graph.CubicEdge;
import algorithm.graph.CubicGraph;
import algorithm.graph.CubicVertex;
import algorithm.graph.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * This class represent an algorithm for reading files that represents graph as list of adjacent vertices of a vertex in a row.
 */
public class AdjacentFormatGraphIterator implements GraphIterator {

    /**
     * number of graphs to be read
     */
    private int numberOfGraphs;

    /**
     * number of vertices of the graphs to be read
     */
    private int numberOfVertices;

    /**
     * how many graphs were read
     */
    private int counter;

    /**
     * Buffered Reader for reading
     */
    private BufferedReader bufferedReader;

    /**
     * starting point for reading graphs
     */
    private final int start;

    /**
     * ending point for reading graphs
     */
    private final int end;
    /**
     * if the vertices are denoted from 0
     */
    private final boolean zeroFirst;
    /**
     * the excess from 0.
     */
    private final int excess;

    /**
     * Constructor of the class AdjacentFormatGraphIterator with just one parameter.
     *
     * @param path      path of the file to read from
     * @param zeroFirst if the vertices are denoted from 0
     * @param excess    the excess from 0.
     * @throws IOException - wrong format of the graphs
     */
    public AdjacentFormatGraphIterator(String path, boolean zeroFirst, int excess) throws IOException {
        preprocess(path);
        this.start = 0;
        this.end = numberOfGraphs;
        this.zeroFirst = zeroFirst;
        this.excess = excess;
    }

    /**
     * Constructor of the class AdjacentFormatGraphIterator with two parameters.
     *
     * @param path   path of the file to read from
     * @param start  staring point for reading graphs
     * @param excess the excess from 0.
     * @throws IOException - wrong format of the graphs
     */
    public AdjacentFormatGraphIterator(String path, int start, boolean zeroFirst, int excess) throws IOException {
        preprocess(path);
        this.start = start - 1;
        this.end = numberOfGraphs;
        this.zeroFirst = zeroFirst;
        this.excess = excess;
        readLines((start - 1) * (numberOfVertices + 1));
    }

    /**
     * Constructor of the class AdjacentFormatGraphIterator with three parameters.
     *
     * @param path      path of the file to read from
     * @param start     staring point for reading graphs
     * @param end       ending point for reading graphs
     * @param zeroFirst if the vertices are denoted from 0.
     * @param excess    the excess from 0.
     * @throws IOException - wrong format of the graphs
     */
    public AdjacentFormatGraphIterator(String path, int start, int end, boolean zeroFirst, int excess) throws IOException {
        preprocess(path);
        this.start = start - 1;
        this.end = end;
        this.zeroFirst = zeroFirst;
        this.excess = excess;
        readLines((start - 1) * (numberOfVertices + 1));
    }

    /**
     * This method preprocess file for reading
     *
     * @param path path of the file
     * @throws IOException wrong format of the graphs
     */
    private void preprocess(String path) throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(path));
        int numberOfLines;
        try (Stream<String> stream = Files.lines(Paths.get(path), Charset.defaultCharset())) {
            numberOfLines = (int) stream.count();
            this.numberOfVertices = Integer.parseInt(bufferedReader.readLine());
        } catch (Exception e) {
            throw new IOException();
        }
        this.numberOfGraphs = (numberOfLines + 2) / (numberOfVertices + 1);
        this.counter = 0;
    }

    /**
     * This method makes a BufferedReader read selected number of lines.
     *
     * @param numberOfLines number of lines to be read
     */
    private void readLines(int numberOfLines) throws IOException {
        for (int i = 0; i < numberOfLines; i++) {
            bufferedReader.readLine();
        }
    }

    /**
     * This method checks whether there is another graph to be read;
     *
     * @return True if there is a graph to be read
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
     * This method reads a graph.
     *
     * @return Graph object read from the file
     * @throws IOException                if reading fails
     * @throws InconsistentGraphException if graphs ic inconsistent
     */
    @Override
    public Graph next() throws IOException, InconsistentGraphException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Graph graph = new CubicGraph();
        for (int i = 0; i < numberOfVertices; i++) graph.addVertex(new CubicVertex(i + excess));
        for (int i = 0; i < numberOfVertices; i++) {
            String line = bufferedReader.readLine();
            String[] splitLine = line.split(" ");
            int countOfVertices = 0;
            for (String s : splitLine) {
                countOfVertices++;
                int toVertex = (zeroFirst) ? Integer.parseInt(s) : Integer.parseInt(s) - 1;
                if (i + excess < toVertex) {
                    graph.getVertices().get(i).addNeighbor(graph.getVertices().get(toVertex - excess));
                    graph.addEdge(new CubicEdge(graph.getVertices().get(i), graph.getVertices().get(toVertex - excess)));
                }
            }
            if (countOfVertices != 3) {
                throw new InconsistentGraphException("There has to be three neighbors!");
            }
        }
        counter++;
        if (hasNext()) {
            bufferedReader.readLine();
        }
        return graph;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getNumberOfGraphs() {
        return numberOfGraphs;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getNumberOfEdges() {
        return (3 * numberOfVertices) / 2;
    }
}
