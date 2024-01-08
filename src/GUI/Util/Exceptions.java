package GUI.Util;

import javafx.scene.control.Alert;

public class Exceptions {

    public void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public void noAddMovie(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Could not add the movie");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public void noDeleteMovie(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Could not delete the movie");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public void noAddCategory(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Could not add the category");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
}

