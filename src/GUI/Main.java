package GUI;

import GUI.Util.MovieExceptions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws MovieExceptions {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/MainView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Movie Collection");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }
}