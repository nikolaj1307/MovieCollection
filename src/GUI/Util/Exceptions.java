package GUI.Util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Exceptions {


    private void showErrorAlert(Alert.AlertType alertType, String title, String headerText, Exception e) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(e.getMessage());
        alert.showAndWait();

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void noAddMovie(Exception e) {
        showErrorAlert(Alert.AlertType.ERROR, "Could not add the movie", e.getMessage(), e);
    }

    public void noDeleteMovie(Exception e) {
        showErrorAlert(Alert.AlertType.ERROR, "Could not delete the movie", e.getMessage(), e);
    }

    public void noAddCategory(Exception e) {
        showErrorAlert(Alert.AlertType.ERROR, "Could not add the category", e.getMessage(), e);
    }

    public void noSearchResults(Exception e) {
        showErrorAlert(Alert.AlertType.INFORMATION, "No movies found", e.getMessage(), e);
    }

    public void invalidRating(Exception e){
        showErrorAlert(Alert.AlertType.ERROR, "Invalid Rating", "The provided rating is not valid",e);
    }

    private static Alert showDeleteAlert(String message) {
        Alert confirmationAlert = new Alert(Alert.AlertType.NONE, //Use NONE inorder to get rid of the standard icon
                message,
                ButtonType.YES, ButtonType.NO);

        confirmationAlert.setAlertType(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setHeaderText(null); //inorder to make it more simple
        confirmationAlert.setGraphic(null); // Removes the questionmark
        return confirmationAlert;
    }
}