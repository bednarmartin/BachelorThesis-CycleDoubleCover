package reading;

import algorithm.reading.format.graph.AdjacentGraphFormat;
import algorithm.reading.format.graph.ErrorGraphFormat;
import algorithm.reading.GraphFileAnalysis;
import algorithm.reading.GraphFileFormatAnalyser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for GraphFileFormatAnalyser
 */
public class GraphFileFormatAnalyserTest {

    private GraphFileFormatAnalyser graphFileFormatAnalyser;

    @BeforeEach
    public void init() {
        graphFileFormatAnalyser = new GraphFileFormatAnalyser();
    }


    @Test
    @DisplayName("Test recognizing format of the 4g3e.txt file")
    public void test4g3e() {
        GraphFileAnalysis graphFileAnalysis = graphFileFormatAnalyser.analyseFile("src/test/resources/4g3e.txt",0 );
        Assertions.assertInstanceOf(AdjacentGraphFormat.class, graphFileAnalysis.getFormat());
        Assertions.assertEquals(4, graphFileAnalysis.getNumberOfVertices());
        Assertions.assertEquals(1, graphFileAnalysis.getNumberOfGraphs());
    }

    @Test
    @DisplayName("Test recognizing format of the 6g3e.txt file")
    public void test6g3e() {
        GraphFileAnalysis graphFileAnalysis = graphFileFormatAnalyser.analyseFile("src/test/resources/6g3e.txt", 0);
        Assertions.assertInstanceOf(AdjacentGraphFormat.class, graphFileAnalysis.getFormat());
        Assertions.assertEquals(6, graphFileAnalysis.getNumberOfVertices());
        Assertions.assertEquals(2, graphFileAnalysis.getNumberOfGraphs());
    }

    @Test
    @DisplayName("Test recognizing format of the FLOWER20.txt file")
    public void testFLOWER20() {
        GraphFileAnalysis graphFileAnalysis = graphFileFormatAnalyser.analyseFile("src/test/resources/FLOWER20.txt", 0);
        Assertions.assertInstanceOf(AdjacentGraphFormat.class, graphFileAnalysis.getFormat());
        Assertions.assertEquals(20, graphFileAnalysis.getNumberOfVertices());
        Assertions.assertEquals(1, graphFileAnalysis.getNumberOfGraphs());
    }

    @Test
    @DisplayName("Test recognizing format of the bad4g3e.txt file")
    public void testbad4g3e() {
        GraphFileAnalysis graphFileAnalysis = graphFileFormatAnalyser.analyseFile("src/test/resources/bad4g3e.txt", 0);
        Assertions.assertInstanceOf(ErrorGraphFormat.class, graphFileAnalysis.getFormat());
        Assertions.assertEquals(0, graphFileAnalysis.getNumberOfVertices());
        Assertions.assertEquals(0, graphFileAnalysis.getNumberOfGraphs());
    }

    @Test
    @DisplayName("Test recognizing format of the bad4g3e2.txt file")
    public void testbad4g3e2() {
        GraphFileAnalysis graphFileAnalysis = graphFileFormatAnalyser.analyseFile("src/test/resources/bad4g3e2.txt", 0);
        Assertions.assertInstanceOf(ErrorGraphFormat.class, graphFileAnalysis.getFormat());
        Assertions.assertEquals(0, graphFileAnalysis.getNumberOfVertices());
        Assertions.assertEquals(0, graphFileAnalysis.getNumberOfGraphs());
    }

    @Test
    @DisplayName("Test recognizing format of the bad4g3e3.txt file")
    public void testbad4g3e3() {
        GraphFileAnalysis graphFileAnalysis = graphFileFormatAnalyser.analyseFile("src/test/resources/bad4g3e3.txt", 0);
        Assertions.assertInstanceOf(ErrorGraphFormat.class, graphFileAnalysis.getFormat());
        Assertions.assertEquals(0, graphFileAnalysis.getNumberOfVertices());
        Assertions.assertEquals(0, graphFileAnalysis.getNumberOfGraphs());
    }

    @Test
    @DisplayName("Test recognizing format of the bad4g3e4.txt file")
    public void testbad4g3e4() {
        GraphFileAnalysis graphFileAnalysis = graphFileFormatAnalyser.analyseFile("src/test/resources/bad4g3e4.txt", 0);
        Assertions.assertInstanceOf(ErrorGraphFormat.class, graphFileAnalysis.getFormat());
        Assertions.assertEquals(0, graphFileAnalysis.getNumberOfVertices());
        Assertions.assertEquals(0, graphFileAnalysis.getNumberOfGraphs());
    }

}
