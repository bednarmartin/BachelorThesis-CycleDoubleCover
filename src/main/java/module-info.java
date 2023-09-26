module application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    //requires MaterialFX;
    requires java.desktop;
    requires MaterialFX.materialfx.main;

    opens application to javafx.fxml;
    exports application;
}