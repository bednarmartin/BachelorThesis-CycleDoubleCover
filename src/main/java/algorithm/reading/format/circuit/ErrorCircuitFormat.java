package algorithm.reading.format.circuit;

import algorithm.exceptions.UnsupportedCircuitFormatException;
import algorithm.graph.Graph;
import algorithm.reading.format.Format;
import algorithm.reading.iterator.CircuitIterator;
import application.center.vboxes.CircuitAnalysisVBox;
import application.states.LoadCircuitsWindowState;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.enums.ButtonType;
import javafx.scene.paint.Color;
import java.io.FileNotFoundException;

/**
 * Class representing a circuit format with an error.
 */
public class ErrorCircuitFormat implements CircuitFormat {
    /**
     * @inheritDoc
     */
    @Override
    public CircuitIterator getCircuitIterator(String path, Graph graph) throws UnsupportedCircuitFormatException {
        throw new UnsupportedCircuitFormatException();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateAnalysisVBox(CircuitAnalysisVBox circuitAnalysisVBox) {
        try {
            Format.setResult(circuitAnalysisVBox,"bin/error.png", Color.RED,toString());
            MFXButton mfxButton = new MFXButton("Try again");
            mfxButton.setButtonType(ButtonType.RAISED);
            mfxButton.setOnAction(event -> circuitAnalysisVBox.getWindowBorderPane().update(new LoadCircuitsWindowState()));
            circuitAnalysisVBox.getChildren().add(mfxButton);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "Something went wrong!";
    }
}

