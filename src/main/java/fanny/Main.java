package fanny;

import java.io.IOException;

import fanny.gui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Fanny using FXML.
 */
public class Main extends Application {

    private Fanny fanny = new Fanny();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setScene(scene);
            stage.setTitle("Fanny");
            fxmlLoader.<MainWindow>getController().setFanny(fanny);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
