package GUI.Controller;

import BE.Category;
import BE.Movie;
import BE.TMDBMovie;
import BLL.CategoryManager;
import DAL.Rest.TMDBConnector;
import GUI.Model.CategoryModel;
import GUI.Model.MovieModel;
import GUI.Util.Alerts;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class AddMovieController {

    // FXML elements for UI controls
    @FXML
    private MFXButton AddMovieCancelBtn;

    @FXML
    private MFXComboBox<String> categoryBox;

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
    private TMDBMovie tmdbMovie;

    private CategoryManager categoryManager;

    private Alerts alerts;


    // Constructor for initializing model instances
    public AddMovieController() {
        try {
            movieModel = new MovieModel();
            categoryModel = new CategoryModel();
            categoryManager = new CategoryManager();
            alerts = new Alerts();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize () {
        loadCategories();
    }

    private MainController mainController;

    // Setter for MainController
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    @FXML
    public void onClickAddMovieCancelBtn(ActionEvent event) {
        // Close the current stage
        Stage stage = (Stage) AddMovieCancelBtn.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void onClickFileChooserBtn(ActionEvent event) throws JSONException, IOException, URISyntaxException, InterruptedException {
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
            //MovieNameField.setText((file.getName()));
            String fileName = file.getName();
            FilePathField.setText(fileName);

            String movieName = fileName.substring(0, fileName.length()-4);
            System.out.println(movieName);
            TMDBConnector tmdbConnector = new TMDBConnector(movieName);
            tmdbMovie = tmdbConnector.getMovieFound();

            if(tmdbMovie != null) {
                MovieNameField.setText(tmdbMovie.getOriginal_title());
                System.out.println(tmdbMovie.getOriginal_title());
            }
        }
    }

    @FXML
    public void onClickAddMovieSaveBtn(ActionEvent event) throws Exception {

        try {
            // Create a new movie using data from text fields
            movieModel.createMovie(MovieNameField.getText(), Double.parseDouble(ImdbRatingField.getText()), FilePathField.getText());

            // Retrieve data from text fields
            String name = MovieNameField.getText();
            double rating = Double.parseDouble(ImdbRatingField.getText());
            String fileLink = FilePathField.getText();

            // Validate the rating value
            if (rating < 0 || rating > 10) {
                alerts.showAlert("Error", "Please enter a valid IMDb rating between 0 and 10.");
                return; // Exit the method if there's an error
            }

            // Create a new Movie object
            Movie newMovie = new Movie(name, rating, fileLink);

            // Add the new movie to the view in the main controller
            mainController.addMovieToView(newMovie);

            // Update the playlist table in the main controller
            mainController.updateMovieTable();
            mainController.clearSelection();

            // Close the current stage
            Stage stage = (Stage) AddMovieCancelBtn.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            // Handle the case where the user didn't enter a valid number for IMDb rating
            alerts.showAlert("Error", "Please enter a valid numeric value for IMDb rating.");
        }
    }

    public void onClickCategoryBox(MouseEvent event) {
    }

    public void loadCategories() {
        try {
            List<Category> allCategories = categoryManager.getAllCategories();
            categoryBox.getItems().clear();
            for (Category category : allCategories) {
                categoryBox.getItems().add(category.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}