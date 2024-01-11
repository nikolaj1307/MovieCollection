package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Movie Collection");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}