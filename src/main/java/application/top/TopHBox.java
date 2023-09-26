package application.top;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Class representing HBox for the top of  the WindowBorderPane
 */
public class TopHBox extends HBox {
    /**
     * Text of the top part
     */
    private final Text text;

    /**
     * Constructor for the TopHBox class
     *
     */
    public TopHBox() {
        text = new Text();
        text.setTextAlignment(TextAlignment.CENTER);
        getChildren().add(text);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(15, 0, 0, 0));
    }

    /**
     * This method allows setting text of the top part of the WindowBorderPane
     *
     * @param text text to be set
     */
    public void setText(String text) {
        this.text.setText(text);
    }
}
