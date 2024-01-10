
    package GUI.Controller;

import BE.Movie;
import GUI.Model.CategoryModel;
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
            Stage stage = (Stage) PersonalRatingSaveBtn.getScene().getWindow();
            stage.close();
        }
    }






