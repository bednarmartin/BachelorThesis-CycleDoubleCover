package application.sides.left;

import application.WindowBorderPane;
import application.sides.SideVBox;
import javafx.geometry.Insets;

/**
 * Class representing a SideVBox of the left side of WindowBorderPane.
 */
public class LeftSideVBox extends SideVBox {
    /**
     * Constructor for LeftSideVBox.
     *
     * @param windowBorderPane parent WindowBorderPane
     */
    public LeftSideVBox(WindowBorderPane windowBorderPane) {
        super(windowBorderPane);
        button.setText("Back");
        setPadding(new Insets(0, 0, 20, 40));
    }
}
