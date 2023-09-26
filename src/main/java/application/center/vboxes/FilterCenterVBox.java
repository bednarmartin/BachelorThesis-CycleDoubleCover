package application.center.vboxes;

import application.WindowBorderPane;
import javafx.geometry.Pos;

/**
 * Abstract class for VBox with filter options
 */
public abstract class FilterCenterVBox extends CenterVBox {
    /**
     * Constructor
     *
     * @param parent WindowBorderPane
     */
    protected FilterCenterVBox(WindowBorderPane parent) {
        super(parent);
        setAlignment(Pos.CENTER);
        setSpacing(15);
    }

}
