package application.center.vboxes;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.reading.CircuitFileAnalysis;
import algorithm.reading.CircuitFileFormatAnalyser;
import algorithm.reading.GraphFileFormatAnalyser;
import application.WindowBorderPane;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Class representing CenterVBox with progress spinner for analysing file with the circuits.
 */
public class CircuitAnalysisVBox extends CenterVBox {
    /**
     * Progress spinner for analysis.
     */
    private final MFXProgressSpinner progressSpinner;

    /**
     * @inheritDoc
     */
    public CircuitAnalysisVBox(WindowBorderPane parent) {
        super(parent);
        setSpacing(15);
        setPadding(new Insets(0, 0, 15, 0));

        progressSpinner = new MFXProgressSpinner();
        progressSpinner.setPrefSize(200, 200);
        getChildren().add(progressSpinner);

        Text text = new Text("Analysing...");
        text.setFont(Font.font(20));
        getChildren().add(text);

        Task<CircuitFileAnalysis> task = new Task<>() {
            @Override
            protected CircuitFileAnalysis call() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
                CircuitFileFormatAnalyser circuitFileFormatAnalyser = new CircuitFileFormatAnalyser();
                return circuitFileFormatAnalyser.analyseFile(parent.getCircuitFilePath(), new GraphFileFormatAnalyser().analyseFile(parent.getGraphFilePath(), 0).getGraphIterator(0).next());
            }
        };

        task.setOnSucceeded(event -> {
            getChildren().remove(progressSpinner);
            getChildren().remove(text);
            CircuitFileAnalysis circuitFileAnalysis = task.getValue();
            parent.setCircuitFileAnalysis(circuitFileAnalysis);
            circuitFileAnalysis.getCircuitFormat().updateAnalysisVBox(this);
        });

        new Thread(task).start();
    }

}
