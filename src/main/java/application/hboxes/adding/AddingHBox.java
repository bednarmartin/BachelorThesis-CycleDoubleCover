package application.hboxes.adding;

import application.WindowBorderPane;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Class representing a HBox with two buttons and one text for increasing / decreasing things.
 */
public class AddingHBox extends HBox {
    /**
     * Button for decreasing.
     */
    protected MFXButton lessButton;
    /**
     * Button for increasing.
     */
    protected MFXButton moreButton;
    /**
     * increased / decreased text.
     */
    protected Text text;
    /**
     * Options for text.
     */
    protected final List<String> optionsList;
    /**
     * whether the buttons are activated
     */
    protected boolean activated;
    /**
     * WindowBorderPane
     */
    WindowBorderPane windowBorderPane;

    /**
     * Constructor
     *
     * @param windowBorderPane window border pane
     */
    public AddingHBox(WindowBorderPane windowBorderPane, String... options) {
        this.windowBorderPane = windowBorderPane;
        activated = true;
        optionsList = List.of(options);
        text = new Text();
        setPadding(new Insets(0, 0, 0, 3));
        text.setText(optionsList.get(0));
        lessButton = new MFXButton();
        lessButton.setText("-");
        moreButton = new MFXButton();
        moreButton.setText("+");

        lessButton.setOnAction(event -> {
            int index = optionsList.indexOf(text.getText());
            if (index > 0) {
                text.setText(optionsList.get(--index));
            }
        });

        moreButton.setOnAction(event -> {
            int index = optionsList.indexOf(text.getText());
            if (index < optionsList.size() - 1) {
                text.setText(optionsList.get(++index));
            }
        });

        this.getChildren().addAll(lessButton, text, moreButton);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
    }

    /**
     * This method allows accessing the value of the text.
     *
     * @return value of the text
     */
    public String getValue() {
        return text.getText();
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
        lessButton.setDisable(!activated);
        moreButton.setDisable(!activated);
        text.setText((!activated) ? " " : optionsList.get(0));
    }

    /**
     * This method allows checking whether the buttons are activated.
     *
     * @return true if the buttons are activated
     */
    public boolean activated() {
        return activated;
    }

}
