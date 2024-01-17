package GUI.Controller;

import BLL.CategoryManager;
import BLL.MovieManager;
import GUI.MediaPlayerHelper;
import GUI.Model.MovieModel;
import BE.Category;
import BE.Movie;
import GUI.Util.Alerts;
import GUI.Util.MovieExceptions;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // FXML elements for UI controls
    @FXML
    private BorderPane BorderPane;

    @FXML
    private MFXButton btnAddMovie;

    @FXML
    private MFXButton btnPersonalRating;

    @FXML
    private MFXButton btnRemove;

    @FXML
    private TableColumn<Category, String> colCategory;

    @FXML
    private TableColumn<Movie, Double> colImdb;

    @FXML
    private TableColumn<Movie, String> colName;

    @FXML
    private TableColumn<Movie, Double> colPersonalRating;

    @FXML
    private TableColumn<Movie, Integer> colViewHistory;

    @FXML
    private MFXLegacyComboBox<String> comBoxCategory;

    @FXML
    private MFXLegacyComboBox<Movie> comBoxRating;

    @FXML
    public MFXLegacyTableView<Movie> movieTblView;

    @FXML
    private MFXTextField searchField;

    MediaPlayerHelper mediaPlayerHelper;
    MediaViewController mediaViewController;

    private InfoAPIViewController infoAPIViewController;

    private MovieExceptions exceptions;

    // Model instance for accessing movie data
    private MovieModel movieModel;
    private Alerts alerts;

    private CategoryManager categoryManager;
    private MovieManager movieManager;

    // Constructor for initializing model instances
    public MainController() {
        try {
            movieManager = new MovieManager();
            categoryManager = new CategoryManager();
            movieModel = new MovieModel();
            alerts = new Alerts();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configure table columns with PropertyValueFactory
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("CatName"));
        colImdb.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        colPersonalRating.setCellValueFactory(new PropertyValueFactory<>("PersonalRating"));
        colViewHistory.setCellValueFactory(new PropertyValueFactory<>("LastView"));

        try {
            loadCategories();
        } catch (MovieExceptions e) {
        }


        // Set the table items from the observable list in the model
        movieTblView.setItems(movieModel.getObservableMovies());
        //SearchBar
        searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));

        // Delays the method
        Platform.runLater(() -> movieManager.movieExpiring(movieModel.getObservableMovies()));
    }

    // Method to update the movie table
    public void updateMovieTable() {
        movieTblView.refresh();
    }

    //Clears the selected movie
    public void clearSelection() {
        movieTblView.getSelectionModel().clearSelection();
    }

    @FXML
    public void onClickAddMovie(ActionEvent event) throws MovieExceptions {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddMovieView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add movie");
            stage.setResizable(false);
            stage.show();

            // Get the controller for the AddMovieView.fxml
            AddMovieController addMovieController = loader.getController();
            // Pass the reference to the main controller to allow communication between controllers
            addMovieController.setMainController(this);
        } catch (IOException e) {
            throw new MovieExceptions(e);
        }

    }

    // Method to add a movie to the view
    public void addMovieToView(Movie movie) {
        // Add the new movie to the table view
        movieTblView.getItems().add(movie);
    }


    public void onClickRemove(ActionEvent event) throws MovieExceptions {
        // Get the currently selected movie from the TableView
        Movie selectedMovie = movieTblView.getSelectionModel().getSelectedItem();
        // Check if a movie is selected
        if (selectedMovie != null) {

            // Create an alert message asking for confirmation to delete the selected movie
            String alertMessage = "Are you sure you want to delete '" +
                    selectedMovie.getName() + "'?";

            // Show a confirmation alert dialog with the specified message
            Alert confirmationAlert = alerts.showDeleteAlert(alertMessage);

            // Wait for the user's response in the dialog
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            // Check if the user clicked 'Yes' in the confirmation dialog
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    // Delete the selected movie from the model and data layer
                    movieModel.deleteMovie(selectedMovie);
                    // Remove the selected movie from the observable list in the model
                    movieModel.getObservableMovies().remove(selectedMovie);

                    // Clear the selection in the TableView
                    clearSelection();
                } catch (Exception e) {
                    throw new MovieExceptions(e);
                }
            }
        } else {
            if (selectedMovie == null) {
                alerts.showAlert("No movie selected", "Please select a movie for deletion");
            }
        }
    }

    public void onClickPersonalRating(ActionEvent event) throws MovieExceptions {
        try {
            Movie selectedMovie = movieTblView.getSelectionModel().getSelectedItem();

            btnPersonalRating.setDisable(true);

            if (selectedMovie != null) {
                btnPersonalRating.setDisable(false);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PersonalRatingView.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);

                stage.setTitle("Personal rating");

                PersonalRatingController personalRatingController = loader.getController();
                personalRatingController.setMainController(this);

                stage.show();

            } else {
                btnPersonalRating.setDisable(false);
            }
        } catch (IOException e) {
            throw new MovieExceptions(e);
        }
    }



    public void movieTblClick(MouseEvent mouseEvent) throws MovieExceptions {

        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            Movie selectedMovie = movieTblView.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                try {

                    // Get the current date
                    Date currentDate = new Date();

                    // Update the last view and pass the current date
                    movieModel.updateLastView(selectedMovie, currentDate);

                    updateMovieTable();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/InfoAPIView.fxml"));
                    Parent root = loader.load();

                    InfoAPIViewController infoAPIViewController = loader.getController();
                    infoAPIViewController.setMainController(this);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Movie Information");
                    stage.setResizable(false);
                    stage.show();

                    infoAPIViewController.changeData();

                    //clearSelection();

                } catch (Exception e) {
                    //exceptions.noAddMovie(e);
                    throw new MovieExceptions(e);
                }
            }
        }
    }

    public void onClickCatFilterBox(ActionEvent event) throws MovieExceptions {
        // Get the selected category from the ComboBox
        String selectedCategory = comBoxCategory.getValue();
        // Check if a category is selected
        if(selectedCategory != null && !selectedCategory.isEmpty()) {
            try {
                // Fetch movies based on the selected category
                List<Movie> moviesByCategory = movieModel.getMoviesByCategory(selectedCategory);

                // Update the TableView with the filtered movies
                movieTblView.setItems(FXCollections.observableArrayList(moviesByCategory));

            } catch (Exception e) {
                throw new MovieExceptions(e);
            }
        } else {
            // If no category is selected, show all movies
            movieTblView.setItems(movieModel.getObservableMovies());

        }
    }


    public void loadCategories() throws MovieExceptions{
        try {
            // Retrieve all categories from the CategoryManager
            List<Category> allCategories = categoryManager.getAllCategories();
            // Clear the existing items in the comBoxCategory (ComboBox)
            comBoxCategory.getItems().clear();
            // Iterate through the list of categories and add their names to the comBoxCategory
            for (Category category : allCategories) {
                comBoxCategory.getItems().add(category.getCatName());
            }
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }


}






