package application.states;

import application.bottom.BottomHBox;
import application.WindowBorderPane;
import application.center.vboxes.AnalysisVBox;
import application.top.TopHBox;
import javafx.scene.layout.HBox;

/**
 * Class representing a state of window with information whether the loading of the file was successful.
 */
public class DroppedFileWindowState implements WindowState {
    /**
     * @inheritDoc
     */
    @Override
    public void update(WindowBorderPane windowBorderPane) {
        windowBorderPane.setTop(new TopHBox());
        windowBorderPane.setCenter(new AnalysisVBox(windowBorderPane));
        windowBorderPane.setLeft(new HBox());
        windowBorderPane.setRight(new HBox());
        windowBorderPane.setBottom(new BottomHBox(windowBorderPane));
    }
}
