package GUI.Controller;

import BE.Category;
import BE.Movie;
import BE.TMDBMovie;
import BLL.CategoryManager;
import DAL.DB.CategoryDAO_DB;
import DAL.Rest.TMDBConnector;
import GUI.Model.CategoryModel;
import GUI.Model.MovieModel;
import GUI.Util.Alerts;
import GUI.Util.MovieExceptions;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AddMovieController {

    // FXML elements for UI controls
    @FXML
    private MFXButton addCategoryBtn;

    @FXML
    private MFXButton removeCategoryBtn;

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

    private CategoryDAO_DB categoryDAO_db;


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

    public void initialize() throws MovieExceptions {
        try {
            loadCategories();
        } catch (MovieExceptions e) {
            throw new MovieExceptions(e);
        }
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
    public void onClickFileChooserBtn(ActionEvent event) throws MovieExceptions {
        try {
            // Open a new stage for the file chooser
            Stage stage = new Stage();

            // Configure the file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setInitialDirectory(new File("Data/Movies"));

            // Allow users to select only video files with specified extensions
            fileChooser.getExtensionFilters().addAll(
                    // Determine which file extensions are allowed while inserting a file.
                    new FileChooser.ExtensionFilter("Video files", "*.mp4", "*.mpeg4"));

            // Show the file chooser dialog and get the selected file
            File file = fileChooser.showOpenDialog(stage);

            // If a file is selected, set its name in the FilePathField
            if (file != null) {
                // Copy the selected file to the data folder
                copyFileToDataFolder(file);

                // Set the file name in the FilePathField
                String fileName = file.getName();
                FilePathField.setText(fileName);

                // Extract movie name from the file name (excluding extension)
                String movieName = fileName.substring(0, fileName.length() - 4);
                System.out.println(movieName);

                // Use TMDBConnector to fetch additional movie details from TMDB API
                TMDBConnector tmdbConnector = new TMDBConnector(movieName);
                tmdbMovie = tmdbConnector.getMovieFound();

                //Update UI with drama from TMDB
                if (tmdbMovie != null) {
                    MovieNameField.setText(tmdbMovie.getOriginal_title());
                    System.out.println(tmdbMovie.getOriginal_title());
                }
            }
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }

    public void addCategoriesToBox(Category category) {
        // Add the new movie to the table view
        categoryBox.getItems().add(String.valueOf(category));
    }


    @FXML
    public void onClickAddMovieSaveBtn(ActionEvent event) throws MovieExceptions {

        try {
            // Create a new movie using data from text fields
            movieModel.createMovie(MovieNameField.getText(), categoryBox.getValue(), Double.parseDouble(ImdbRatingField.getText()), FilePathField.getText());

            // Retrieve data from text fields
            String name = MovieNameField.getText();
            double rating = Double.parseDouble(ImdbRatingField.getText());
            String fileLink = FilePathField.getText();
            String category = categoryBox.getValue();


            // Validate the rating value
            if (rating < 0 || rating > 10) {
                alerts.showAlert("Error", "Please enter a valid IMDb rating between 0 and 10.");
                return; // Exit the method if there's an error
            }


            /*if (category == null) {
                alerts.showAlert("Error", "Please enter a valid category for the movie");
                return;
            }*/

            // Create a new Movie object
            Movie newMovie = new Movie(name, category, rating, fileLink);

            // Add the new movie to the view in the main controller
            mainController.addMovieToView(newMovie);

            // Update the playlist table in the main controller
            mainController.updateMovieTable();
            mainController.clearSelection();

            // Close the current stage
            Stage stage = (Stage) AddMovieCancelBtn.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            // Handle the case where the user didn't enter a valid number for IMDb rating
            alerts.showAlert("Error", "Please enter a valid numeric value for IMDb rating, AND pick a category for the movie.");
            throw new MovieExceptions("Please enter a valid numeric value between 0 and 10");
        }

    }

    public void onClickCategoryBox(ActionEvent event) throws MovieExceptions {

        System.out.println("Category selected " + categoryBox.getValue());
    }

    public void loadCategories() throws MovieExceptions {
        try {
            // Retrieve all categories from the CategoryManager
            List<Category> allCategories = categoryManager.getAllCategories();

            // Clear the existing items in the categoryBox (ComboBox)
            categoryBox.getItems().clear();
            for (Category category : allCategories) {
                categoryBox.getItems().add(category.getCatName());
            }
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }

    private void copyFileToDataFolder(File sourceFile) {
        try {
            // Create a Path for the destination file in the "Data/Movies" directory
            Path destinationPath = Paths.get("Data/Movies", sourceFile.getName());
            // Copy the source file to the destination path, replacing it if it already exists
            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickAddCategoryBtn(ActionEvent event) throws MovieExceptions {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CategoryAddRemoveView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add category");
            stage.setResizable(false);
            stage.show();


            CategoryAddRemoveViewController categoryAddRemoveViewController = loader.getController();
            // Pass the reference to the main controller to allow communication between controllers
            categoryAddRemoveViewController.setAddMovieController(this);
        } catch (IOException e) {
            throw new MovieExceptions(e);
        }

    }


    @FXML
    public void onClickRemoveCategoryBtn(ActionEvent event) throws MovieExceptions {
        // Get the selected category from the categoryBox
        String getSelectedCategory = categoryBox.getValue();

        // Check if a category is selected
        if (getSelectedCategory == null) {
            alerts.showAlert("Error", "Please select a category to remove.");
            return;
        }

        // Confirm with the user if they really want to delete the selected category
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Removal");
        confirmationAlert.setHeaderText("Remove Category");
        confirmationAlert.setContentText("Are you sure you want to remove the selected category? '" + getSelectedCategory + "'" );

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Call the appropriate method to delete the category from the database
                categoryManager.deleteCategory(new Category(getSelectedCategory));

                // Update the UI by reloading the categories
                loadCategories();

                categoryBox.clearSelection();
            } catch (Exception e) {
                throw new MovieExceptions(e);
            }
        }
    }
}



