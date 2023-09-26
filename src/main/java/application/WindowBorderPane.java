package application;

import algorithm.reading.CircuitFileAnalysis;
import algorithm.reading.GraphFileAnalysis;
import application.states.WindowState;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Class representing a root scene of the stage.
 */
public class WindowBorderPane extends BorderPane {

    private String graphFileName;

    private String graphFilePath;

    private String circuitFilePath;

    private GraphFileAnalysis graphFileAnalysis;

    private boolean filterBridge = true;

    private boolean filter2Cut;

    private boolean filter3Cut;

    private int start = 1;

    private int end = 1;

    private boolean printFoundInduced6CDC;

    private boolean printCountOfStrongEdges;

    private boolean printFound6CDC;

    private CircuitFileAnalysis circuitFileAnalysis;

    private String outputDirectory;

    String outputFileName;

    private boolean importFixedCircuits;

    private int girth = 3;

    private final Stage stage;


    /**
     * Constructor for the class WindowBorderPane.
     */
    public WindowBorderPane(Stage stage) {
        setPrefHeight(400);
        setPrefWidth(500);
        this.stage = stage;

    }

    public String getGraphFileName() {
        return graphFileName;
    }

    public void setGraphFileName(String graphFileName) {
        this.graphFileName = graphFileName;
    }

    public void setGraphFilePath(String path) {
        graphFilePath = path;
    }

    public String getGraphFilePath() {
        return graphFilePath;
    }

    public void setGraphFileAnalysis(GraphFileAnalysis graphFileAnalysis) {
        this.graphFileAnalysis = graphFileAnalysis;
    }

    public GraphFileAnalysis getGraphFileAnalysis() {
        return graphFileAnalysis;
    }

    public void update(WindowState windowState) {
        windowState.update(this);
    }

    public boolean isPrintFoundInduced7CDC() {
        return printFoundInduced6CDC;
    }

    public void setPrintFoundInduced6CDC(boolean printFoundInduced6CDC) {
        this.printFoundInduced6CDC = printFoundInduced6CDC;
    }

    public CircuitFileAnalysis getCircuitFileAnalysis() {
        return circuitFileAnalysis;
    }

    public void setCircuitFileAnalysis(CircuitFileAnalysis circuitFileAnalysis) {
        this.circuitFileAnalysis = circuitFileAnalysis;
    }

    public GraphFileAnalysis getFileAnalysis() {
        return graphFileAnalysis;
    }

    public boolean isFilterBridge() {
        return filterBridge;
    }

    public void setFilterBridge(boolean filterBridge) {
        this.filterBridge = filterBridge;
    }

    public boolean isFilter2Cut() {
        return filter2Cut;
    }

    public void setFilter2Cut(boolean filter2Cut) {
        this.filter2Cut = filter2Cut;
    }

    public boolean isFilter3Cut() {
        return filter3Cut;
    }

    public void setFilter3Cut(boolean filter3Cut) {
        this.filter3Cut = filter3Cut;
    }

    public int getGirth() {
        return girth;
    }

    public void setGirth(int girth) {
        this.girth = girth;
    }

    public boolean isImportFixedCircuits() {
        return importFixedCircuits;
    }

    public void setImportFixedCircuits(boolean importFixedCircuits) {
        this.importFixedCircuits = importFixedCircuits;
    }

    public String getCircuitFilePath() {
        return circuitFilePath;
    }

    public void setCircuitFilePath(String circuitFilePath) {
        this.circuitFilePath = circuitFilePath;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isPrintFound6CDC() {
        return printFound6CDC;
    }

    public void setPrintFound6CDC(boolean printFound6CDC) {
        this.printFound6CDC = printFound6CDC;
    }

    public boolean isPrintCountOfStrongEdges() {
        return printCountOfStrongEdges;
    }

    public void setPrintCountOfStrongEdges(boolean printCountOfStrongEdges) {
        this.printCountOfStrongEdges = printCountOfStrongEdges;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * This method sets the application to initial state.
     */
    public void clearAll() {
        graphFilePath = null;
        circuitFilePath = null;
        graphFileAnalysis = null;
        filterBridge = true;
        filter2Cut = false;
        filter3Cut = false;
        start = 1;
        end = 1;
        printFoundInduced6CDC = false;
        printCountOfStrongEdges = false;
        printFound6CDC = false;
        girth = 3;
        importFixedCircuits = false;
        outputFileName = null;
        outputDirectory = null;
        circuitFileAnalysis = null;
        graphFileName = null;
    }
}
