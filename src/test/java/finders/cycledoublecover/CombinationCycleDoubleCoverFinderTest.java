package finders.cycledoublecover;

import algorithm.determiners.bridge.DFSBridgeDeterminer;
import algorithm.finders.doublecyclecover.CombinationCycleDoubleCoverFinder;
import algorithm.finders.doublecyclecover.CycleDoubleCoverFinder;
import algorithm.graph.*;
import algorithm.reading.iterator.AdjacentFormatGraphIterator;
import algorithm.reading.iterator.GraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests for CombinationDoubleCycleCoverFinder class
 */
public class CombinationCycleDoubleCoverFinderTest {

    @Test
    @DisplayName("Ensure the CDC Finder with 4-vertex cubic graph works correctly")
    public void test4g3eCDC() {
        try {
            GraphIterator graphIterator = new AdjacentFormatGraphIterator("src/test/resources/4g3e.txt", true, 0);
            Graph graph = graphIterator.next();

            CycleDoubleCoverFinder circuitDoubleCoverFinder = new CombinationCycleDoubleCoverFinder(graph);
            circuitDoubleCoverFinder.find();
            Set<CycleDoubleCover> CDCs = circuitDoubleCoverFinder.getCycleDoubleCovers();

            Assertions.assertEquals(2, CDCs.size());
            for (CycleDoubleCover cycleDoubleCover : CDCs) {
                for (Edge edge : graph.getEdges()) {
                    int counter = 0;
                    Set<Cycle> coverCycles = new HashSet<>();
                    for (Cycle cycle : cycleDoubleCover.getCycles()) {
                        for (Edge edgeOfCycle : cycle.getEdges()) {
                            if (edge.equals(edgeOfCycle)) {
                                coverCycles.add(cycle);
                                counter++;
                            }
                        }
                    }
                    Assertions.assertEquals(2, counter);
                    Assertions.assertEquals(2, coverCycles.size());
                }
            }


        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Ensure the CDC Finder with 6-vertex cubic graph works correctly")
    public void test6g3eCDC() {
        try {
            GraphIterator graphIterator = new AdjacentFormatGraphIterator("src/test/resources/6g3e.txt", true, 0);
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                CycleDoubleCoverFinder circuitDoubleCoverFinder = new CombinationCycleDoubleCoverFinder(graph);
                circuitDoubleCoverFinder.find();
                Set<CycleDoubleCover> CDCs = circuitDoubleCoverFinder.getCycleDoubleCovers();
                Assertions.assertFalse(CDCs.isEmpty());
                for (CycleDoubleCover cycleDoubleCover : CDCs) {
                    for (Edge edge : graph.getEdges()) {
                        int counter = 0;
                        Set<Cycle> coverCycles = new HashSet<>();
                        for (Cycle cycle : cycleDoubleCover.getCycles()) {
                            for (Edge edgeOfCycle : cycle.getEdges()) {
                                if (edge.equals(edgeOfCycle)) {
                                    coverCycles.add(cycle);
                                    counter++;
                                }
                            }
                        }
                        Assertions.assertEquals(2, counter);
                        Assertions.assertEquals(2, coverCycles.size());
                    }
                }
            }


        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Ensure the CDC Finder with 8-vertex cubic graph works correctly")
    public void test8g3eCDC() {
        try {
            GraphIterator graphIterator = new AdjacentFormatGraphIterator("src/test/resources/8g3e.txt", true, 0);
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                CycleDoubleCoverFinder circuitDoubleCoverFinder = new CombinationCycleDoubleCoverFinder(graph);
                circuitDoubleCoverFinder.find();
                Set<CycleDoubleCover> CDCs = circuitDoubleCoverFinder.getCycleDoubleCovers();
                Assertions.assertFalse(CDCs.isEmpty());
                for (CycleDoubleCover cycleDoubleCover : CDCs) {
                    for (Edge edge : graph.getEdges()) {
                        int counter = 0;
                        Set<Cycle> coverCycles = new HashSet<>();
                        for (Cycle cycle : cycleDoubleCover.getCycles()) {
                            for (Edge edgeOfCycle : cycle.getEdges()) {
                                if (edge.equals(edgeOfCycle)) {
                                    coverCycles.add(cycle);
                                    counter++;
                                }
                            }
                        }
                        Assertions.assertEquals(2, counter);
                        Assertions.assertEquals(2, coverCycles.size());
                    }
                }
            }


        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Ensure the CDC Finder with 10-vertex cubic graph works correctly")
    public void test10g3eCDC() {
        try {
            GraphIterator graphIterator = new AdjacentFormatGraphIterator("src/test/resources/10g3e.txt", true, 0);
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                CycleDoubleCoverFinder circuitDoubleCoverFinder = new CombinationCycleDoubleCoverFinder(graph);
                circuitDoubleCoverFinder.find();
                Set<CycleDoubleCover> CDCs = circuitDoubleCoverFinder.getCycleDoubleCovers();
                if (!graph.hasBridge(new DFSBridgeDeterminer())) {
                    Assertions.assertFalse(CDCs.isEmpty());
                }
                for (CycleDoubleCover cycleDoubleCover : CDCs) {
                    for (Edge edge : graph.getEdges()) {
                        int counter = 0;
                        Set<Cycle> coverCycles = new HashSet<>();
                        for (Cycle cycle : cycleDoubleCover.getCycles()) {
                            for (Edge edgeOfCycle : cycle.getEdges()) {
                                if (edge.equals(edgeOfCycle)) {
                                    coverCycles.add(cycle);
                                    counter++;
                                }
                            }
                        }
                        Assertions.assertEquals(2, counter);
                        Assertions.assertEquals(2, coverCycles.size());
                    }
                }
            }


        } catch (Exception e) {
            Assertions.fail();
        }
    }

}
