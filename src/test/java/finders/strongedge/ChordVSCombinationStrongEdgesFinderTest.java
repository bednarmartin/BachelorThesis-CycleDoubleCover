package finders.strongedge;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.finders.doublecyclecover.CombinationCycleDoubleCoverFinder;
import algorithm.finders.doublecyclecover.CycleDoubleCoverFinder;
import algorithm.finders.strongedge.ChordsStrongEdgesFinder;
import algorithm.finders.strongedge.ComparisonStrongEdgesFinder;
import algorithm.finders.strongedge.StrongEdgesFinder;
import algorithm.graph.CycleDoubleCover;
import algorithm.graph.Edge;
import algorithm.graph.Graph;
import algorithm.reading.GraphFileFormatAnalyser;
import algorithm.reading.iterator.GraphIterator;
import algorithm.strategies.CDCStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests for ChordStrongEdgeFinder and CombinationStrongEdgeFinder
 */
public class ChordVSCombinationStrongEdgesFinderTest {

    @Test
    @DisplayName("Ensure that Chord and Comparison Strong Edges Finders return the same results for 4g3e.txt")
    public void test_4g3e() throws InconsistentGraphException, IOException, UnsupportedGraphFormatException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/4g3e.txt", 0).getGraphIterator();
        Graph graph = graphIterator.next();
        CycleDoubleCoverFinder circuitDoubleCoverFinder = new CombinationCycleDoubleCoverFinder(graph);
        circuitDoubleCoverFinder.find();
        Set<CycleDoubleCover> CDCs = circuitDoubleCoverFinder.getCycleDoubleCovers();

        StrongEdgesFinder comparisonStrongEdgesFinder = new ComparisonStrongEdgesFinder(graph);
        StrongEdgesFinder chordStrongEdgesFinder = new ChordsStrongEdgesFinder(graph);
        for (CycleDoubleCover cycleDoubleCover : CDCs) {
            Assertions.assertEquals(comparisonStrongEdgesFinder.getStrongEdges(cycleDoubleCover), chordStrongEdgesFinder.getStrongEdges(cycleDoubleCover));
        }
    }

    @Test
    @DisplayName("Ensure that Chord and Comparison Strong Edges Finders return the same results for other files")
    public void test_other() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        String[] files = {"6g3e.txt", "8g3e.txt", "10g3e.txt", "12g3e.txt", "14g3e.txt"};
        for (String file : files) {
            GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/" + file, 0).getGraphIterator();
            Graph graph = graphIterator.next();
            CycleDoubleCoverFinder circuitDoubleCoverFinder = new CombinationCycleDoubleCoverFinder(graph);
            circuitDoubleCoverFinder.find();
            Set<CycleDoubleCover> CDCs = circuitDoubleCoverFinder.getCycleDoubleCovers();

            StrongEdgesFinder comparisonStrongEdgesFinder = new ComparisonStrongEdgesFinder(graph);
            StrongEdgesFinder chordStrongEdgesFinder = new ChordsStrongEdgesFinder(graph);
            for (CycleDoubleCover cycleDoubleCover : CDCs) {
                Assertions.assertEquals(comparisonStrongEdgesFinder.getStrongEdges(cycleDoubleCover), chordStrongEdgesFinder.getStrongEdges(cycleDoubleCover));
            }
        }


    }

    @Test
    @DisplayName("Ensure that Chord and Comparison Strong Edges Finders return the same edges")
    public void test_edges() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        String[] files = {"4g3e.txt", "6g3e.txt", "8g3e.txt", "10g3e.txt"};
        for (String file : files) {
            GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/" + file, 0).getGraphIterator();
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                class EqualEdges implements CDCStrategy {
                    private Set<Edge> comparisonEdges = new HashSet<>();
                    private Set<Edge> chordEdges = new HashSet<>();

                    @Override
                    public String toString() {
                        return comparisonEdges.equals(chordEdges) ? "TRUE" : "FALSE";
                    }

                    @Override
                    public boolean processCDC(CycleDoubleCover cycleDoubleCover) {
                        comparisonEdges = new HashSet<>(cycleDoubleCover.getStrongEdges(new ComparisonStrongEdgesFinder(graph)));
                        chordEdges = new HashSet<>(cycleDoubleCover.getStrongEdges(new ChordsStrongEdgesFinder(graph)));
                        return true;
                    }

                    @Override
                    public List<CycleDoubleCover> getCDCs() {
                        throw new UnsupportedOperationException();
                    }


                }

                CycleDoubleCoverFinder circuitDoubleCoverFinder = new CombinationCycleDoubleCoverFinder(graph);
                CDCStrategy cdcStrategy = new EqualEdges();
                circuitDoubleCoverFinder.setCDCStrategy(cdcStrategy);
                circuitDoubleCoverFinder.find();
                Assertions.assertEquals("TRUE", cdcStrategy.toString());
            }

        }
    }
}


