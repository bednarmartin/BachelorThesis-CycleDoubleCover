package application.center.vboxes;

import algorithm.determiners.bridge.DFSBridgeDeterminer;
import algorithm.determiners.connectivity.DFSConnectedGraphDeterminer;
import algorithm.filters.circuit.CircuitFilter;
import algorithm.filters.circuit.InducedCircuitFilter;
import algorithm.filters.graph.*;
import algorithm.finders.chord.CombinationChordFinder;
import algorithm.finders.doublecyclecover.CombinationCycleDoubleCoverFinder;
import algorithm.finders.doublecyclecover.CycleDoubleCoverFinder;
import algorithm.finders.strongedge.ChordsStrongEdgesFinder;
import algorithm.finders.threecut.CombinationThreeCutFinder;
import algorithm.finders.twocut.CombinationTwoCutFinder;
import algorithm.graph.Graph;
import algorithm.reading.iterator.CircuitIterator;
import algorithm.reading.iterator.GraphIterator;
import algorithm.strategies.CDCStrategy;
import algorithm.strategies.NoCDCStrategy;
import algorithm.strategies.StrongEdgesPrintCDCStrategy;
import algorithm.strategies.AtMostCyclesFoundInducedPrintCDCStrategy;
import application.WindowBorderPane;
import application.bottom.BottomHBox;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Class representing CenterVBox that compute.
 */
public class ComputationVBox extends CenterVBox {
    /**
     * progress spinner
     */
    private final MFXProgressSpinner mfxProgressSpinner;
    /**
     * number of graphs tested
     */
    private int graphsTested;
    /**
     * whether to stop the computation
     */
    private boolean exit = false;
    /**
     * CDC finder
     */
    CycleDoubleCoverFinder cycleDoubleCoverFinder;

    FileWriter fileWriter;

    /**
     * Constructor
     *
     * @param parent WindowBorderPane
     */
    public ComputationVBox(WindowBorderPane parent, BottomHBox bottomHBox) {
        super(parent);
        setSpacing(15);
        setPadding(new Insets(0, 0, 15, 0));
        graphsTested = 0;
        mfxProgressSpinner = new MFXProgressSpinner();
        mfxProgressSpinner.setPrefSize(200, 200);
        int start = parent.getStart();
        int end = parent.getEnd();
        int numberOfGraphs = end - start + 1;
        fileWriter = null;
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    GraphIterator graphIterator = parent.getGraphFileAnalysis().getGraphIterator(start, end);
                    File file = new File(parent.getOutputDirectory(), parent.getOutputFileName());
                    file.createNewFile();
                    new PrintWriter(file).close();
                    fileWriter = new FileWriter(file, true);
                    while (graphIterator.hasNext()) {
                        if (exit) {
                            fileWriter.close();
                            break;
                        }
                        Graph graph = graphIterator.next();
                        fileWriter.write("GRAPH " + graphIterator.actualIndex() + "\n\n");
                        GraphFilter graphFilter = new NoGraphFilter();

                        if (parent.isFilter3Cut()) {
                            graphFilter = new ThreeCutGraphFilter(graphFilter, new CombinationThreeCutFinder(new DFSConnectedGraphDeterminer()));
                        } else if (parent.isFilter2Cut()) {
                            graphFilter = new TwoCutGraphFilter(graphFilter, new CombinationTwoCutFinder(new DFSConnectedGraphDeterminer()));
                        } else if (parent.isFilterBridge()) {
                            graphFilter = new BridgeGraphFilter(graphFilter, new DFSBridgeDeterminer());
                        }
                        if (parent.getGirth() != 3) {
                            graphFilter = new GirthGraphFilter(graphFilter, parent.getGirth());
                        }
                        if (!graphFilter.pass(graph)) {
                            fileWriter.write("EXCLUDED\n\n");
                            continue;
                        }
                        if (parent.isPrintCountOfStrongEdges()) {
                            cycleDoubleCoverFinder = new CombinationCycleDoubleCoverFinder(graph);
                            CDCStrategy cdcStrategy = new StrongEdgesPrintCDCStrategy(new ChordsStrongEdgesFinder(graph), graph);
                            cycleDoubleCoverFinder.setCDCStrategy(cdcStrategy);
                            if (parent.isImportFixedCircuits()) {
                                CircuitIterator circuitIterator = parent.getCircuitFileAnalysis().getCircuitFormat().getCircuitIterator(parent.getCircuitFilePath(), graph);
                                while (circuitIterator.hasNext()) {
                                    cycleDoubleCoverFinder.addFixedCycle(circuitIterator.next());
                                }
                            }
                            cycleDoubleCoverFinder.find();
                            if (!exit) {
                                fileWriter.write(cdcStrategy.toString());
                            }
                        }

                        if (parent.isPrintFound6CDC() || parent.isPrintFoundInduced7CDC()) {
                            CircuitFilter circuitFilter = new InducedCircuitFilter(new CombinationChordFinder(graph));
                            cycleDoubleCoverFinder = new CombinationCycleDoubleCoverFinder(graph);
                            cycleDoubleCoverFinder.setAny(true);
                            cycleDoubleCoverFinder.setCircuitFilter(circuitFilter);
                            CDCStrategy cdcStrategy = new NoCDCStrategy();

                            if (parent.isPrintFound6CDC()) {
                                cdcStrategy = new AtMostCyclesFoundInducedPrintCDCStrategy(cdcStrategy, graph, 6, false, true);
                            }

                            if (parent.isPrintFoundInduced7CDC()) {
                                cdcStrategy = new AtMostCyclesFoundInducedPrintCDCStrategy(cdcStrategy, graph, 7, true, true);
                            }


                            if (parent.isImportFixedCircuits()) {
                                CircuitIterator circuitIterator = parent.getCircuitFileAnalysis().getCircuitFormat().getCircuitIterator(parent.getCircuitFilePath(), graph);
                                while (circuitIterator.hasNext()) {
                                    cycleDoubleCoverFinder.addFixedCycle(circuitIterator.next());
                                }
                            }
                            cycleDoubleCoverFinder.setCDCStrategy(cdcStrategy);
                            cycleDoubleCoverFinder.find();
                            if (!exit) {
                                fileWriter.write(cdcStrategy.toString());
                            }
                        }
                        fileWriter.write("\n");
                        graphsTested++;
                    }
                    fileWriter.close();
                } catch (Exception ignored) {
                }

                return null;
            }
        };

        if (numberOfGraphs == 1) {
            Text label = new Text();
            label.setText(parent.getGraphFileName());
            label.setFont(Font.font(20));
            Text text = new Text("Testing...");
            text.setFont(Font.font(20));
            getChildren().addAll(label, mfxProgressSpinner, text);
            task.setOnSucceeded(event -> {
                mfxProgressSpinner.setProgress(1);
                text.setText("Done");
                bottomHBox.getCancelButton().setText("New File");
                bottomHBox.getCancelButton().setStyle("-fx-background-color: lightgreen");
                try {
                    Desktop.getDesktop().open(new File(parent.getOutputDirectory(), parent.getOutputFileName()));

                } catch (Exception ignored) {
                }
            });
            bottomHBox.getCancelButton().addEventHandler(ActionEvent.ACTION, event -> {
                if (cycleDoubleCoverFinder != null) {
                    cycleDoubleCoverFinder.stop();
                }
                try {
                    fileWriter.close();
                } catch (IOException ignored) {
                }
                if (!Objects.equals(text.getText(), "Done")) {
                    try {
                        Desktop.getDesktop().open(new File(parent.getOutputDirectory(), parent.getOutputFileName()));
                    } catch (Exception ignored) {
                    }
                }
                exit = true;

            });
            parent.getStage().setOnCloseRequest(e -> {
                if (cycleDoubleCoverFinder != null) {
                    cycleDoubleCoverFinder.stop();
                }
                try {
                    fileWriter.close();
                } catch (IOException ignored) {
                }
                if (!Objects.equals(text.getText(), "Done")) {
                    try {
                        Desktop.getDesktop().open(new File(parent.getOutputDirectory(), parent.getOutputFileName()));
                    } catch (Exception ignored) {
                    }
                }
                exit = true;

            });
        } else {
            HBox hBox = new HBox();
            Text label = new Text(parent.getGraphFileName());
            Text label2 = new Text(parent.getStart() + " - " + parent.getEnd());
            label.setFont(Font.font(20));
            label2.setFont(Font.font(20));
            hBox.getChildren().addAll(label, label2);
            hBox.setSpacing(20);
            hBox.setPadding(new Insets(5));
            hBox.setAlignment(Pos.CENTER);
            mfxProgressSpinner.setProgress(0);
            Text text = new Text("0/" + numberOfGraphs);
            text.setFont(Font.font(20));
            getChildren().addAll(hBox, mfxProgressSpinner, text);
            Timeline fiveSecondsWonder = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            event -> {
                                text.setText(graphsTested + "/" + numberOfGraphs);
                                double progress = (double) graphsTested / numberOfGraphs;
                                if (progress > 0.99 && graphsTested != numberOfGraphs) {
                                    progress = 0.99;
                                }
                                mfxProgressSpinner.setProgress(progress);

                            }));
            task.setOnSucceeded(event -> {
                fiveSecondsWonder.stop();
                mfxProgressSpinner.setProgress(1);
                text.setText("Done");
                bottomHBox.getCancelButton().setText("New File");
                bottomHBox.getCancelButton().setStyle("-fx-background-color: lightgreen");
                try {
                    Desktop.getDesktop().open(new File(parent.getOutputDirectory(), parent.getOutputFileName()));
                } catch (Exception ignored) {
                }
            });
            bottomHBox.getCancelButton().addEventHandler(ActionEvent.ACTION, event -> {
                fiveSecondsWonder.stop();
                if (cycleDoubleCoverFinder != null) {
                    cycleDoubleCoverFinder.stop();
                }
                try {
                    fileWriter.close();
                } catch (IOException ignored) {
                }
                if (!Objects.equals(text.getText(), "Done")) {
                    try {
                        Desktop.getDesktop().open(new File(parent.getOutputDirectory(), parent.getOutputFileName()));
                    } catch (Exception ignored) {
                    }
                }
                exit = true;
                parent.clearAll();
            });
            parent.getStage().setOnCloseRequest(e -> {
                fiveSecondsWonder.stop();
                if (cycleDoubleCoverFinder != null) {
                    cycleDoubleCoverFinder.stop();
                }
                try {
                    fileWriter.close();
                } catch (IOException ignored) {
                }
                if (!Objects.equals(text.getText(), "Done")) {
                    try {
                        Desktop.getDesktop().open(new File(parent.getOutputDirectory(), parent.getOutputFileName()));
                    } catch (Exception ignored) {
                    }
                }
                exit = true;
                parent.clearAll();
            });
            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();
        }

        Thread thread = new Thread(task);
        thread.start();

    }
}
