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
 * Iterator for the format with snarks.
 */
public class SnarkFormatGraphIterator implements GraphIterator {
    /**
     * Number of graphs to be read
     */
    private int numberOfGraphs;
    /**
     * Number of vertices of the graphs to be read
     */
    private int numberOfVertices;
    /**
     * How many graphs were read
     */
    private int counter;
    /**
     * Scanner for reading
     */
    private BufferedReader bufferedReader;
    /**
     * if the vertices are denoted from 0.
     */
    private final boolean zeroFirst;
    /**
     * index of the first graph
     */
    private final int start;
    /**
     * index of the last graph
     */
    private final int end;
    /**
     * the excess from 0
     */
    private final int excess;


    /**
     * Constructor of the class SnarkFormatGraphIterator
     *
     * @param path      path of the file to read from
     * @param zeroFirst if the vertices are denoted from 0
     * @param excess    the excess from 0
     * @throws IOException wrong format of the graphs
     */
    public SnarkFormatGraphIterator(String path, boolean zeroFirst, int excess) throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(path));
        this.zeroFirst = zeroFirst;
        this.start = 0;
        preprocess(path);
        this.end = numberOfGraphs;
        this.excess = excess;

    }

    /**
     * Constructor of the class SnarkFormatGraphIterator
     *
     * @param path      path of the file to read from
     * @param start     index of the first graph
     * @param zeroFirst if the vertices are denoted from 0
     * @param excess    the excess from 0
     * @throws IOException wrong format of the graphs
     */
    public SnarkFormatGraphIterator(String path, int start, boolean zeroFirst, int excess) throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(path));
        this.zeroFirst = zeroFirst;
        this.start = start - 1;
        preprocess(path);
        readLines(this.start * 4);
        this.end = numberOfGraphs;
        this.excess = excess;
    }

    /**
     * Constructor of the class SnarkFormatGraphIterator
     *
     * @param path      path of the file to read from
     * @param start     index of the first graph
     * @param end       index of the last graph
     * @param zeroFirst if the vertices are denoted from 0
     * @param excess    the excess from 0
     * @throws IOException wrong format of the graphs
     */
    public SnarkFormatGraphIterator(String path, int start, int end, boolean zeroFirst, int excess) throws IOException {
        this.end = end;
        this.zeroFirst = zeroFirst;
        this.start = start - 1;
        this.excess = excess;
        preprocess(path);
        readLines(this.start * 4);

    }

    private void preprocess(String path) throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(path));
        BufferedReader numberOfVerticesBufferedReader = new BufferedReader(new FileReader(path));
        numberOfVerticesBufferedReader.readLine();
        String line = numberOfVerticesBufferedReader.readLine();
        line = line.replaceAll("[^0-9]+", " ");
        this.numberOfVertices = line.trim().split(" ").length;
        int numberOfLines;
        try (Stream<String> stream = Files.lines(Paths.get(path), Charset.defaultCharset())) {
            numberOfLines = (int) stream.count();
        } catch (Exception e) {
            throw new IOException();
        }
        this.numberOfGraphs = (numberOfLines + 1) / 4;
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
     * This method check whether there is another graph to be read;
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
     * @return Graph object read from the file
     */
    @Override
    public Graph next() throws IOException, InconsistentGraphException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Graph graph = new CubicGraph();
        for (int i = 0; i < numberOfVertices; i++) graph.addVertex(new CubicVertex(i + excess));
        bufferedReader.readLine();
        int[] first = new int[numberOfVertices];
        int[] second = new int[numberOfVertices];
        int[] third = new int[numberOfVertices];
        int[][] arrays = new int[][]{first, second, third};
        for (int[] array : arrays) {
            String line = bufferedReader.readLine().replaceAll("[^0-9]+", " ");
            String[] values = line.trim().split(" ");
            for (int i = 0; i < numberOfVertices; i++) {
                array[i] = (zeroFirst) ? Integer.parseInt(values[i]) : Integer.parseInt(values[i]) - 1;
            }
        }
        for (int i = 0; i < numberOfVertices; i++) {
            for (int[] array : arrays) {
                if (i + excess < array[i]) {
                    graph.getVertices().get(i).addNeighbor(graph.getVertices().get(array[i] - excess));
                    graph.addEdge(new CubicEdge(graph.getVertices().get(i), graph.getVertices().get(array[i] - excess)));
                }
            }
        }
        counter++;
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
