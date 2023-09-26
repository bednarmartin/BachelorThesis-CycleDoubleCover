package algorithm.reading.format.graph;

import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.reading.format.Format;
import algorithm.reading.iterator.GraphIterator;
import application.center.vboxes.AnalysisVBox;
import application.states.LoadWindowState;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.enums.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;

/**
 * Class representing a graph format that is not supported.
 */
public class ErrorGraphFormat implements GraphFormat {

    /**
     * @inheritDoc
     */
    @Override
    public GraphIterator getGraphIterator(String path, boolean zeroFirst, int excess) throws UnsupportedGraphFormatException {
        throw new UnsupportedGraphFormatException();
    }

    /**
     * @inheritDoc
     */
    @Override
    public GraphIterator getGraphIterator(String path, int start, boolean zeroFirst, int excess) throws UnsupportedGraphFormatException {
        throw new UnsupportedGraphFormatException();
    }

    /**
     * @inheritDoc
     */
    @Override
    public GraphIterator getGraphIterator(String path, int start, int end, boolean zeroFirst, int excess) throws UnsupportedGraphFormatException {
        throw new UnsupportedGraphFormatException();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateAnalysisVBox(AnalysisVBox analysisVBox) {
        try {
            analysisVBox.getWindowBorderPane().setBottom(new HBox());
            Format.setResult(analysisVBox,"./bin/error.png", Color.RED, toString());
            MFXButton mfxButton = new MFXButton("Try again");
            mfxButton.setButtonType(ButtonType.RAISED);
            mfxButton.setOnAction(event -> analysisVBox.getWindowBorderPane().update(new LoadWindowState()));
            analysisVBox.getChildren().add(mfxButton);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "Unsupported Graph Format";

    }
}
