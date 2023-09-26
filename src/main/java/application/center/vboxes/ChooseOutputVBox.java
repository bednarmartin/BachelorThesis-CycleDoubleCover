package application.center.vboxes;

import application.WindowBorderPane;
import application.sides.SideVBox;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class representing CenterVBox with the option to select the name of the output file.
 */
public class ChooseOutputVBox extends CenterVBox {

    /**
     * Constructor
     *
     * @param parent WindowBorderPane
     */
    public ChooseOutputVBox(WindowBorderPane parent, SideVBox sideVBox) {
        super(parent);
        AtomicBoolean nameIsGood = new AtomicBoolean(false);
        sideVBox.getButton().setDisable(true);
        Text text = new Text("Choose the output file name:");
        TextField textField = new TextField();
        textField.setMaxWidth(200);
        textField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        textField.setPromptText("Name of the output file");
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!validateStringFilenameUsingRegex(newValue)) {
                textField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                nameIsGood.set(false);
            } else {
                textField.setStyle("-fx-border-color: red ; -fx-border-width: 0px ;");
                nameIsGood.set(true);
            }
            sideVBox.getButton().setDisable(!nameIsGood.get());
        });
        sideVBox.getButton().addEventHandler(ActionEvent.ACTION, e -> {
            parent.setOutputFileName(textField.getText() + ".txt");
            parent.setOutputDirectory("./outputs");
        });
        getChildren().addAll(text, textField);
        setSpacing(20);
    }

    /**
     * This method makes sure that the name of the output file is correct.
     *
     * @param filename the name of the file
     * @return true if the filename is correct
     */
    private boolean validateStringFilenameUsingRegex(String filename) {
        if (Objects.equals(filename, "")) {
            return false;
        }
        String REGEX_PATTERN = "^[A-za-z0-9.]{1,255}$";
        return filename.matches(REGEX_PATTERN);
    }

}
