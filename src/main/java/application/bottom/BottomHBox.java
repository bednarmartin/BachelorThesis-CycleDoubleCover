package application.bottom;

import application.WindowBorderPane;
import application.center.vboxes.*;
import application.states.LoadWindowState;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.enums.ButtonType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * Class representing a HBox on the bottom of the WindowBorderPane.
 */
public class BottomHBox extends HBox {
    /**
     * MFXButton to cancel current operation and return to drag and drop.
     */
    private final MFXButton cancelButton;

    /**
     * Constructor for BottomHBox.
     *
     * @param parent parent WindowBorderPane
     */
    public BottomHBox(WindowBorderPane parent) {
        cancelButton = new MFXButton("Cancel");
        cancelButton.setPrefWidth(150);
        cancelButton.setButtonType(ButtonType.RAISED);
        cancelButton.setStyle("-fx-background-color: red");
        getChildren().addAll(cancelButton);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(0, 0, 30, 0));
        cancelButton.setOnAction(event -> {
            parent.update(new LoadWindowState());
            CircuitInformationVBox.clear();
            FilterGraphsVBox.clear();
            parent.clearAll();

        });
    }

    /**
     * This method grants access to the cancel button.
     *
     * @return the cancel button
     */
    public MFXButton getCancelButton() {
        return cancelButton;
    }

}
