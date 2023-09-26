package application.center.vboxes;

import algorithm.reading.GraphFileAnalysis;
import algorithm.reading.GraphFileFormatAnalyser;
import application.WindowBorderPane;

import io.github.palexdev.materialfx.controls.MFXProgressSpinner;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Class representing CenterVBox with the analysis of the file.
 */
public class AnalysisVBox extends CenterVBox {
    /**
     * Progress spinner for the analysis.
     */
    private final MFXProgressSpinner progressSpinner;

    /**
     * @inheritDoc
     */
    public AnalysisVBox(WindowBorderPane parent) {
        super(parent);
        setSpacing(15);
        setPadding(new Insets(0, 0, 15, 0));

        progressSpinner = new MFXProgressSpinner();
        progressSpinner.setPrefSize(200, 200);
        getChildren().add(progressSpinner);

        Text text = new Text("Analysing...");
        text.setFont(Font.font(20));
        getChildren().add(text);

        Task<GraphFileAnalysis> task = new Task<>() {
            @Override
            protected GraphFileAnalysis call() {
                GraphFileFormatAnalyser graphFileFormatAnalyser = new GraphFileFormatAnalyser();
                return graphFileFormatAnalyser.analyseFile(parent.getGraphFilePath(), 0);
            }
        };

        task.setOnSucceeded(event -> {
            getChildren().remove(progressSpinner);
            getChildren().remove(text);
            GraphFileAnalysis graphFileAnalysis = task.getValue();
            parent.setGraphFileAnalysis(graphFileAnalysis);
            graphFileAnalysis.getFormat().updateAnalysisVBox(this);
        });

        new Thread(task).start();
    }

}


