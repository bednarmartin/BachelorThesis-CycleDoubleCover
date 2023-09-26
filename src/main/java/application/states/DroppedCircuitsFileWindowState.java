package application.states;

import application.WindowBorderPane;
import application.bottom.BottomHBox;
import application.center.vboxes.CircuitAnalysisVBox;
import application.top.TopHBox;
import javafx.scene.layout.HBox;

/**
 * Class representing a state of window with information whether the loading of the file was successful.
 */
public class DroppedCircuitsFileWindowState implements WindowState {
    /**
     * @inheritDoc
     */
    @Override
    public void update(WindowBorderPane windowBorderPane) {
        windowBorderPane.setCenter(new CircuitAnalysisVBox(windowBorderPane));
        TopHBox topHbox = new TopHBox();
        topHbox.setText("Fixed Circuits:");
        windowBorderPane.setBottom(new BottomHBox(windowBorderPane));
        windowBorderPane.setTop(topHbox);
        windowBorderPane.setRight(new HBox());
        windowBorderPane.setLeft(new HBox());

    }
}
