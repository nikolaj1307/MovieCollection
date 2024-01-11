
    package GUI.Controller;

import BE.Movie;
import GUI.Model.MovieModel;
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

        // Model instances for accessing data
        private MovieModel movieModel;


        // Constructor for initializing model instances
        public PersonalRatingController() {
            try {
                movieModel = new MovieModel();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        // Setter for MainController
        public void setMainController(MainController mainController) {
            this.mainController = mainController;
        }

        public void OnClickPersonalRatingCancelBtn(ActionEvent event) {
            Stage stage = (Stage) PersonalRatingCancelBtn.getScene().getWindow();
            stage.close();
        }

        public void OnClickPersonalRatingSaveBtn(ActionEvent event) throws Exception {
                 // Hust movieTblView er public, find ud af hvordan man kan access ved private.
                Movie selectedMovie = mainController.movieTblView.getSelectionModel().getSelectedItem();

                // HUSK.. at lave så der kommer fejl, hvis der ikke er indtastet en værdi og at værdien kun kan være fra 0-10
                Double pRating = Double.valueOf(PersonalRatingField.getText());

                movieModel.updatePersonalRating(selectedMovie, pRating);

                mainController.updateMovieTable();
                mainController.clearSelection();

                // Close the window
                Stage stage = (Stage) PersonalRatingSaveBtn.getScene().getWindow();
                stage.close();
            }

        }









