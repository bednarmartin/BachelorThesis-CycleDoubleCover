package application.center.vboxes;

import application.WindowBorderPane;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Abstract class representing a VBox in the center of WindowBorderPane
 */
public abstract class CenterVBox extends VBox {
    /**
     * WindowBorderPane
     */
    protected WindowBorderPane parent;

    /**
     * Constructor
     *
     * @param parent WindowBorderPane
     */
    protected CenterVBox(WindowBorderPane parent) {
        this.parent = parent;
        setAlignment(Pos.CENTER);

    }

    /**
     * This method grant access to the WindowBorderPane
     *
     * @return window border pane
     */
    public WindowBorderPane getWindowBorderPane() {
        return parent;
    }

}
