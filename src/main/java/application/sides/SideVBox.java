package application.sides;

import application.WindowBorderPane;
import application.states.WindowState;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Abstract class representing a VBox on the left or right side of the WindowBorderPane.
 */
public abstract class SideVBox extends VBox {
    /**
     * MFXButton on the side.
     */
    protected MFXButton button;
    /**
     * parent WindowBorderPane
     */
    protected WindowBorderPane windowBorderPane;

    /**
     * Constructor
     *
     * @param windowBorderPane parent WindowBorderPane
     */
    protected SideVBox(WindowBorderPane windowBorderPane) {
        button = new MFXButton();
        this.windowBorderPane = windowBorderPane;
        setAlignment(Pos.CENTER);
        button.setDisable(true);
        button.setPrefSize(60, 30);
        setPadding(new Insets(0, 0, 20, 0));
        getChildren().add(button);

    }

    /**
     * This method allows activating the button
     *
     * @param windowState the new WindowState to set when button is clicked.
     */
    public void activateButton(WindowState windowState) {
        button.setDisable(false);
        button.setOnAction(event -> {
            windowBorderPane.update(windowState);
        });
    }

    /**
     * This method grant access to the MFXButton.
     *
     * @return the button.
     */
    public MFXButton getButton() {
        return button;
    }


}
