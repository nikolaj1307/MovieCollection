// Import statements
package GUI.Util;

import BE.Movie;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

// Utility class for displaying various types of alerts in the GUI
public class Alerts {

    // Method to show a confirmation alert for deletion with custom message
    public static Alert showDeleteAlert(String message) {
        // Create a confirmation alert with YES and NO buttons
        Alert confirmationAlert = new Alert(Alert.AlertType.NONE, // Use NONE to remove the standard icon
                message,
                ButtonType.YES, ButtonType.NO);

        confirmationAlert.setAlertType(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setHeaderText(null); // Make it more simple
        confirmationAlert.setGraphic(null); // Remove the question mark
        return confirmationAlert;
    }

    // Method to show a general information alert with a specified title and content
    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Method to show an alert about an expiring movie
    public void showExpiringMovieAlert(Movie movie) {
        // Create a message about the expiring movie
        String expiringMessage = "The movie [ " + movie.getName() + " ] has not been seen in 2 years and has a personal rating under 6. Consider removing it from your collection.";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Movie is getting old");
        alert.setHeaderText(null);
        alert.setContentText(expiringMessage);
        alert.showAndWait();
    }
}
