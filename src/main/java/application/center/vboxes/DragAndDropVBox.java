package application.center.vboxes;

import application.WindowBorderPane;
import application.states.DroppedCircuitsFileWindowState;
import application.states.DroppedFileWindowState;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Locale;

/**
 * Class representing CenterVBox with drag and drop area.
 */
public class DragAndDropVBox extends CenterVBox {
    /**
     * file path of the file dropped
     */
    private String filePath;
    /**
     * true if dialog from selecting file is open.
     */
    private boolean dialogOpen;
    /**
     * type of the file
     */
    private final String type;


    /**
     * @inheritDoc
     */
    public DragAndDropVBox(WindowBorderPane parent, String type) {
        super(parent);
        this.type = type;
        dialogOpen = false;
        String cssLayout = """
                -fx-border-insets: 5;
                -fx-border-width: 3;
                -fx-border-style: dashed;
                -fx-border-color: lightgray""";
        setStyle(cssLayout);
        Text text = new Text("DROP A FILE WITH " + type.toUpperCase(Locale.ROOT) + " HERE\n OR CLICK TO CHOOSE THE FILE");
        text.setFill(Color.LIGHTGRAY);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font(30));
        text.setRotate(30);
        getChildren().add(text);
        setOnDrag();
        setOnClick();

    }

    /**
     * This method implements the handlers for dragging over and dropping file.
     */
    private void setOnDrag() {
        try {
            setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    File draggedFile = db.getFiles().get(0);
                    String fileName = draggedFile.getName();
                    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, draggedFile.getName().length());
                    if (fileExtension.equals("txt")) {
                        event.acceptTransferModes(TransferMode.COPY);
                    }
                } else {
                    event.consume();
                }

            });

            setOnDragDropped(event -> {

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    filePath = db.getFiles().get(0).getAbsolutePath();
                    if (type.equals("graphs")) {
                        parent.setGraphFilePath(filePath);
                        parent.setGraphFileName(db.getFiles().get(0).getName());
                        parent.update(new DroppedFileWindowState());
                    } else if (type.equals("circuits")) {
                        parent.setCircuitFilePath(filePath);
                        parent.update(new DroppedCircuitsFileWindowState());
                    }

                }
                event.setDropCompleted(success);
                event.consume();


            });
        } catch (
                Exception ignored) {
        }

    }

    /**
     * This method implements the handler for selecting file on click.
     */
    private void setOnClick() {
        setOnMouseClicked(e -> {
            if (!dialogOpen) {
                dialogOpen = true;
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose file with " + type);
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt", "*.txt"));
                Stage stage = new Stage();
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    filePath = file.getAbsolutePath();
                    if (type.equals("graphs")) {
                        parent.setGraphFilePath(filePath);
                        parent.setGraphFileName(file.getName());
                        parent.update(new DroppedFileWindowState());
                    } else if (type.equals("circuits")) {
                        parent.setCircuitFilePath(filePath);
                        parent.update(new DroppedCircuitsFileWindowState());
                    }
                } else {
                    e.consume();
                }
                dialogOpen = false;
            } else {
                e.consume();
            }

        });
    }


}
