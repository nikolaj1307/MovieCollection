package GUI.Controller;

import BE.Category;
import BE.Movie;
import GUI.Main;
import GUI.Model.CategoryModel;
import GUI.Model.MovieModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import GUI.Controller.MainController;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddMovieController {

    // FXML elements for UI controls
    @FXML
    private MFXButton AddMovieCancelBtn;

    @FXML
    private MFXComboBox<String> CategoryBox;

    @FXML
    private MFXButton AddMovieSaveBtn;

    @FXML
    private MFXButton FileChooserBtn;

    @FXML
    private TextField FilePathField;

    @FXML
    private TextField ImdbRatingField;

    @FXML
    private TextField MovieNameField;

    // Model instances for accessing data
    private CategoryModel categoryModel;
    private MovieModel movieModel;

    // Constructor for initializing model instances
    public AddMovieController() {
        try {
            movieModel = new MovieModel();
            categoryModel = new CategoryModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private MainController mainController;

    // Setter for MainController
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    @FXML
    public void OnClickAddMovieCancelBtn(ActionEvent event) {
        // Close the current stage
        Stage stage = (Stage) AddMovieCancelBtn.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void OnClickFileChooserBtn(ActionEvent event) {
        // Open a new stage for the file chooser
        Stage stage = new Stage();

        // Configure the file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("Data/Movies"));
        fileChooser.getExtensionFilters().addAll(
                // Determine which file extensions are allowed while inserting a file.
                new FileChooser.ExtensionFilter("Video files", "*.mp4", "*.mpeg4"));

        // Show the file chooser dialog and get the selected file
        File file = fileChooser.showOpenDialog(stage);

        // If a file is selected, set its name in the FilePathField
        if (file != null) {
            MovieNameField.setText((file.getName()));
            FilePathField.setText(file.getName());
        }
    }

    @FXML
    public void OnClickAddMovieSaveBtn(ActionEvent event) throws Exception {
        // Create a new movie using data from text fields
        movieModel.createMovie(MovieNameField.getText(), Double.parseDouble(ImdbRatingField.getText()), FilePathField.getText());

        // Retrieve data from text fields
        String name = MovieNameField.getText();
        double rating = Double.parseDouble(ImdbRatingField.getText());
        String fileLink = FilePathField.getText();

        // Create a new Movie object
        Movie newMovie = new Movie(name, rating, fileLink);

        // Add the new movie to the view in the main controller
        mainController.addMovieToView(newMovie);

        // Update the playlist table in the main controller
        mainController.updatePlaylistTable();

        // Close the current stage
        Stage stage = (Stage) AddMovieCancelBtn.getScene().getWindow();
        stage.close();
    }


    public void OnClickCategoryBox(ActionEvent event) {

    }
}