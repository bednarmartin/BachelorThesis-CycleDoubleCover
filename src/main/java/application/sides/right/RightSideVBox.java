package application.sides.right;

import application.WindowBorderPane;
import application.sides.SideVBox;
import javafx.geometry.Insets;

/**
 * Class representing a SideVBox for the right side of the WindowBorderPane.
 */
public class RightSideVBox extends SideVBox {
    /**
     * Constructor for RightSideVBox class
     *
     * @param windowBorderPane parent WindowBorderPane.
     */
    public RightSideVBox(WindowBorderPane windowBorderPane) {
        super(windowBorderPane);
        button.setText("Next");
        setPadding(new Insets(0, 40, 20, 0));
    }

}
