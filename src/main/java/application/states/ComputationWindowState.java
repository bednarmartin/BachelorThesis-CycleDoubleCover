package application.states;

import application.WindowBorderPane;
import application.bottom.BottomHBox;
import application.center.vboxes.ComputationVBox;
import application.top.TopHBox;
import javafx.scene.layout.HBox;

/**
 * Class representing a state of window with computation.
 */
public class ComputationWindowState implements WindowState {
    /**
     * @inheritDoc
     */
    @Override
    public void update(WindowBorderPane windowBorderPane) {
        TopHBox topHbox = new TopHBox();
        windowBorderPane.setTop(topHbox);
        BottomHBox bottomHBox = new BottomHBox(windowBorderPane);
        ComputationVBox computationVBox = new ComputationVBox(windowBorderPane, bottomHBox);
        windowBorderPane.setCenter(computationVBox);
        windowBorderPane.setRight(new HBox());
        windowBorderPane.setLeft(new HBox());
        windowBorderPane.setBottom(bottomHBox);
    }
}
