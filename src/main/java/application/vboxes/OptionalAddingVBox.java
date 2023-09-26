package application.vboxes;

import application.hboxes.adding.AddingHBox;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Class representing a VBox with Adding HBox that is optionally activated.
 */
public class OptionalAddingVBox extends VBox {

    private final MFXToggleButton mfxToggleButton;

    /**
     * Constructor
     *
     * @param addingHBox optionally activated addingHBox
     * @param toggleText text to be shown above toggle button
     */
    public OptionalAddingVBox(AddingHBox addingHBox, String toggleText) {
        mfxToggleButton = new MFXToggleButton(toggleText);
        setAlignment(Pos.CENTER);
        setSpacing(5);
        setPadding(new Insets(0, 0, 10, 0));
        addingHBox.setActivated(false);
        mfxToggleButton.setOnAction(event -> addingHBox.setActivated(mfxToggleButton.isSelected()));
        getChildren().add(mfxToggleButton);
        getChildren().add(addingHBox);
    }
}
