package application.center.vboxes;

import application.WindowBorderPane;
import application.hboxes.adding.AddingHBox;
import application.sides.SideVBox;
import application.vboxes.OptionalAddingVBox;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Class representing CenterVBox with filter options for graphs.
 */
public class FilterGraphsVBox extends FilterCenterVBox {
    /**
     * ToggleButton for graphs with bridge.
     */
    private final MFXToggleButton bridgeCheckBox;
    /**
     * ToggleButton for graphs with 2-cut
     */
    private final MFXToggleButton twoCutCheckBox;
    /**
     * ToggleButton for graphs with 3-cut
     */
    private final MFXToggleButton threeCutCheckBox;
    /**
     * instance of the class
     */
    private static FilterGraphsVBox instance;

    /**
     * Constructor
     *
     * @param parent WindowBorderPane
     */
    public FilterGraphsVBox(WindowBorderPane parent, SideVBox sideVBox) {
        super(parent);
        bridgeCheckBox = new MFXToggleButton("a bridge");
        twoCutCheckBox = new MFXToggleButton("a 2-cut");
        threeCutCheckBox = new MFXToggleButton("a nontrivial 3-cut");

        twoCutCheckBox.setOnAction(event -> {
            if (twoCutCheckBox.isSelected()) {
                bridgeCheckBox.setSelected(true);
                bridgeCheckBox.setDisable(true);
            } else {
                bridgeCheckBox.setDisable(false);
            }
        });
        threeCutCheckBox.setOnAction(event -> {
            if (threeCutCheckBox.isSelected()) {
                twoCutCheckBox.setDisable(true);
                twoCutCheckBox.setSelected(true);
                bridgeCheckBox.setDisable(true);
                bridgeCheckBox.setSelected(true);
            } else {
                twoCutCheckBox.setDisable(false);
            }
        });
        AddingHBox addingHBox = new AddingHBox(parent, "4", "5", "6", "7", "8", "9");
        addingHBox.setActivated(false);
        VBox girthVBox = new OptionalAddingVBox(addingHBox, "the girth less than:");
        setPadding(new Insets(0,0,-20,0));
        bridgeCheckBox.setSelected(true);
        parent.setFilterBridge(true);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().addAll(bridgeCheckBox, twoCutCheckBox, threeCutCheckBox);
        setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(0,0,0,130));
        getChildren().addAll(vBox, girthVBox);
        sideVBox.getButton().addEventHandler(ActionEvent.ACTION, e -> {
            parent.setFilterBridge(bridgeCheckBox.isSelected());
            parent.setFilter2Cut(twoCutCheckBox.isSelected());
            parent.setFilter3Cut(threeCutCheckBox.isSelected());
            if (addingHBox.activated()) {
                parent.setGirth(Integer.parseInt(addingHBox.getValue()));
            } else {
                parent.setGirth(3);
            }
        });
    }

    /**
     * This method grants access to the instance of the class.
     *
     * @param parent parent WindowBorderPane
     * @return instance of the class FilterCircuitsVBox
     */
    public static FilterGraphsVBox getInstance(WindowBorderPane parent, SideVBox sideVBox) {
        if (instance == null) {
            instance = new FilterGraphsVBox(parent, sideVBox);
        }
        return instance;
    }

    /**
     * This method nulls the instance.
     */
    public static void clear() {
        instance = null;
    }
}
