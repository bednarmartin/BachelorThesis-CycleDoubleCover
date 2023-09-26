package algorithm.reading.format;

import application.center.vboxes.CenterVBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Abstract class for Format
 */
public abstract class Format {
    /**
     * This method sets the result of file loading.
     *
     * @param centerVBox  The CenterVBox to be updated
     * @param path        path to the image
     * @param color       color of the text
     * @param displayText text to be displayed
     * @throws FileNotFoundException if the file does not exist
     */
    public static void setResult(CenterVBox centerVBox, String path, Paint color, String displayText) throws FileNotFoundException {
        ImageView imageView = new ImageView();
        Image image = new Image(new FileInputStream(path));
        imageView.setImage(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        centerVBox.getChildren().add(imageView);
        Text text = new Text(displayText);
        text.setFill(color);
        text.setFont(Font.font(20));
        centerVBox.getChildren().add(text);

    }
}
