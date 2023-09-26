package application.states;

import application.WindowBorderPane;
import application.center.vboxes.DragAndDropVBox;
import javafx.scene.layout.HBox;

/**
 * Class representing a state of window for file drag and dropping.
 */
public class LoadWindowState implements WindowState {
    /**
     * @inheritDoc
     */
    @Override
    public void update(WindowBorderPane windowBorderPane) {
        windowBorderPane.setTop(new HBox());
        windowBorderPane.setCenter(new DragAndDropVBox(windowBorderPane, "graphs"));
        windowBorderPane.setLeft(new HBox());
        windowBorderPane.setRight(new HBox());
        windowBorderPane.setBottom(new HBox());

    }
}
