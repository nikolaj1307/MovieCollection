
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



    public class PersonalRatingController {

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

        public void setMainController(MainController mainController) {

            this.mainController = mainController;
        }

        public void OnClickPersonalRatingCancelBtn(ActionEvent event) {
            Stage stage = (Stage) PersonalRatingCancelBtn.getScene().getWindow();
            stage.close();
        }

        public void OnClickPersonalRatingSaveBtn(ActionEvent event) throws Exception {

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

                mainController.updateMovieTable();
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









