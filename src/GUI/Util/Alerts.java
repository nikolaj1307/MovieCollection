package GUI.Util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerts {

    // Skal gøres private, men man skal stadig kunne bruge dem.

    public static Alert showDeleteAlert(String message) {
        Alert confirmationAlert = new Alert(Alert.AlertType.NONE, //Use NONE inorder to get rid of the standard icon
                message,
                ButtonType.YES, ButtonType.NO);

        confirmationAlert.setAlertType(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setHeaderText(null); //inorder to make it more simple
        confirmationAlert.setGraphic(null); // Removes the questionmark
        return confirmationAlert;
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
