// Import statements
package GUI.Controller;

import BE.Movie;
import GUI.Model.MovieModel;
import GUI.Util.Alerts;
import GUI.Util.MovieExceptions;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// Controller class for the PersonalRatingView
public class PersonalRatingController {

    // FXML elements for UI controls
    @FXML
    private MFXButton PersonalRatingCancelBtn;

    @FXML
    private MFXButton PersonalRatingSaveBtn;

    @FXML
    private TextField PersonalRatingField;

    private MainController mainController;

    private MovieModel movieModel;
    private Alerts alerts;

    // Constructor for initializing model instances
    public PersonalRatingController() throws MovieExceptions {
        try {
            movieModel = new MovieModel();
            alerts = new Alerts();
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }

    // Setter for MainController
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Event handler for the Cancel button
    public void OnClickPersonalRatingCancelBtn(ActionEvent event) {
        // Close the current stage
        Stage stage = (Stage) PersonalRatingCancelBtn.getScene().getWindow();
        stage.close();
    }

    // Event handler for the Save button
    public void OnClickPersonalRatingSaveBtn(ActionEvent event) throws Exception {
        // Get the selected movie from the TableView in the main controller
        Movie selectedMovie = mainController.movieTblView.getSelectionModel().getSelectedItem();

        try {
            // Validate the personal rating value
            Double pRating = Double.valueOf(PersonalRatingField.getText());

            if (pRating < 0 || pRating > 10) {
                // Display an alert if the value is outside the valid range
                alerts.showAlert("Error", "Personal rating must be between 0 and 10.");
                return; // Exit the method if there's an error
            }

            // Update personal rating if validation passes
            movieModel.updatePersonalRating(selectedMovie, pRating);

            // Update the movie table in the main controller
            mainController.updateMovieTable();
            // Clear the selection in the TableView
            mainController.clearSelection();

            // Close the window
            Stage stage = (Stage) PersonalRatingSaveBtn.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            // Handle the case where the user didn't enter a valid number
            alerts.showAlert("Error", "Please enter a valid numeric value for personal rating.");
        }
    }
}
