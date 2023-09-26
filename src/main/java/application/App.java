package application;

import application.states.LoadWindowState;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Class representing the JavaFX app
 */
public class App extends javafx.application.Application {
    /**
     * This method launches the JavaFX app
     *
     * @param args arguments
     */
    public void launchApplication(String[] args) {
        launch(args);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void start(Stage stage) {
        WindowBorderPane windowBorderPane = new WindowBorderPane(stage);
        windowBorderPane.update(new LoadWindowState());
        Scene root = new Scene(windowBorderPane);
        stage.setScene(root);
        setStage(stage);
        stage.show();
    }

    /**
     * This method sets parameters of the stage to default values
     *
     * @param stage stage of which parameters should be set
     */
    private void setStage(Stage stage) {
        try {
            stage.setTitle("Cycle Double Cover");
            stage.setWidth(600);
            stage.setHeight(500);
            stage.setResizable(false);
            Image icon = new Image(new FileInputStream("bin/icon.png"));
            stage.getIcons().add(icon);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

    }

}

