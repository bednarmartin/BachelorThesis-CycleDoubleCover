package application.center.vboxes;

import algorithm.reading.GraphFileAnalysis;
import application.WindowBorderPane;
import application.sides.SideVBox;
import application.states.ExcludeGraphsWindowState;
import application.states.LoadCircuitsWindowState;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class representing CenterVBox with information about the file.
 */
public class InformationVBox extends CenterVBox {

    /**
     * @inheritDoc
     */
    public InformationVBox(WindowBorderPane parent, SideVBox sideVBox) {
        super(parent);
        GraphFileAnalysis graphFileAnalysis = parent.getGraphFileAnalysis();
        setSpacing(20);
        Text textNumberOfGraphs = new Text("Number of Graphs: " + graphFileAnalysis.getNumberOfGraphs());
        Text textNumberOfVertices = new Text("Number of Vertices: " + graphFileAnalysis.getNumberOfVertices());
        textNumberOfGraphs.setFont(Font.font(20));
        textNumberOfVertices.setFont(Font.font(20));
        getChildren().addAll(textNumberOfGraphs, textNumberOfVertices);
        if (parent.getFileAnalysis().getNumberOfGraphs() == 1) {
            MFXToggleButton mfxToggleButton = new MFXToggleButton("Import fixed circuits");
            mfxToggleButton.setOnAction(e -> {
                parent.setImportFixedCircuits(mfxToggleButton.isSelected());
                if (mfxToggleButton.isSelected()) {
                    sideVBox.activateButton(new LoadCircuitsWindowState());
                } else {
                    sideVBox.activateButton(new ExcludeGraphsWindowState());
                }

            });
            getChildren().add(mfxToggleButton);
        } else {
            AtomicBoolean startGood = new AtomicBoolean(true);
            AtomicBoolean endGood = new AtomicBoolean(true);
            int numberOfGraphs = parent.getFileAnalysis().getNumberOfGraphs();
            TextField startTextFiled = new TextField("1");
            TextField endTextFiled = new TextField(String.valueOf(numberOfGraphs));
            startTextFiled.setPromptText("Start");
            endTextFiled.setPromptText("End");
            startTextFiled.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    startTextFiled.setText(newValue.replaceAll("[^\\d]", ""));
                } else {
                    startTextFiled.setText(startTextFiled.getText().replaceFirst("^0*", ""));
                    newValue = startTextFiled.getText();
                }
                if (!newValue.equals("")) {
                    if (Integer.parseInt(newValue) > numberOfGraphs) {
                        startGood.set(false);
                        startTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                    } else if (!endTextFiled.getText().equals("")) {
                        if (Integer.parseInt(newValue) > Integer.parseInt(endTextFiled.getText())) {
                            startGood.set(false);
                            endGood.set(false);
                            startTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                            endTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                        } else {
                            startGood.set(true);
                            endGood.set(true);
                            startTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 0px ;");
                            endTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 0px ;");
                        }
                    } else {
                        startGood.set(true);
                        startTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 0px ;");
                    }
                } else {
                    startGood.set(false);
                    startTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                }
                sideVBox.getButton().setDisable(!startGood.get() || !endGood.get());
            });
            endTextFiled.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    endTextFiled.setText(newValue.replaceAll("[^\\d]", ""));
                } else {
                    endTextFiled.setText(endTextFiled.getText().replaceFirst("^0*", ""));
                    newValue = endTextFiled.getText();
                }
                if (!newValue.equals("")) {
                    if (Integer.parseInt(newValue) > numberOfGraphs) {
                        endGood.set(false);
                        endTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                    } else if (!startTextFiled.getText().equals("")) {
                        if (Integer.parseInt(newValue) < Integer.parseInt(startTextFiled.getText())) {
                            startGood.set(false);
                            endGood.set(false);
                            endTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                            startTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                        } else {
                            startGood.set(true);
                            endGood.set(true);
                            endTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 0px ;");
                            startTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 0px ;");
                        }
                    } else {
                        endGood.set(true);
                        endTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 0px ;");
                    }
                } else {
                    endGood.set(false);
                    endTextFiled.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                }
                sideVBox.getButton().setDisable(!startGood.get() || !endGood.get());
            });
            sideVBox.getButton().addEventHandler(ActionEvent.ACTION, event -> {
                parent.setStart(Integer.parseInt(startTextFiled.getText()));
                parent.setEnd(Integer.parseInt(endTextFiled.getText()));

            });
            HBox rangeHBox = new HBox();
            rangeHBox.setAlignment(Pos.CENTER);
            rangeHBox.setSpacing(40);
            rangeHBox.getChildren().addAll(startTextFiled, endTextFiled);
            Text labelText = new Text("Select Range:");
            getChildren().addAll(labelText, rangeHBox);

        }

    }

}
