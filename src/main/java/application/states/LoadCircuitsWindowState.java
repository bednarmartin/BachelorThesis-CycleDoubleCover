package application.states;

import application.WindowBorderPane;
import application.bottom.BottomHBox;
import application.center.vboxes.DragAndDropVBox;
import javafx.scene.layout.HBox;

/**
 * Class representing a state of window for loading circuits.
 */
public class LoadCircuitsWindowState implements WindowState {
    /**
     * @inheritDoc
     */
    @Override
    public void update(WindowBorderPane windowBorderPane) {
        windowBorderPane.setCenter(new DragAndDropVBox(windowBorderPane, "circuits"));
        windowBorderPane.setBottom(new BottomHBox(windowBorderPane));
        windowBorderPane.setRight(new HBox());
        windowBorderPane.setLeft(new HBox());
        windowBorderPane.setTop(new HBox());
    }
}
