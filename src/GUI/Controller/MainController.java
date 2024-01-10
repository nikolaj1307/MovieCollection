package GUI.Controller;

import GUI.Model.MovieModel;
import BE.Category;
import BE.Movie;
import GUI.Util.Exceptions;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    private MFXLegacyComboBox<Category> comBoxCategory;

    @FXML
    private MFXLegacyComboBox<Movie> comBoxRating;

    @FXML
    private MFXLegacyTableView<Movie> movieTblView;

    @FXML
    private MFXTextField searchField;
    private Exceptions exceptions;


    // Model instance for accessing movie data
    private MovieModel movieModel;

    // Constructor for initializing model instances
    public MainController() {
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configure table columns with PropertyValueFactory
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colImdb.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        colPersonalRating.setCellValueFactory(new PropertyValueFactory<>("PersonalRating"));
        colViewHistory.setCellValueFactory(new PropertyValueFactory<>("LastView"));

        // Set the table items from the observable list in the model
        movieTblView.setItems(movieModel.getObservableMovies());
        //SearchBar
        searchField.textProperty().addListener(((observable, oldValue, newValue) ->{
            try {
                movieModel.searchMovie(newValue);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }));
    }


    // Method to update the playlist table
    public void updatePlaylistTable() {
        movieTblView.refresh();
    }

    @FXML
    public void onClickAddMovie(ActionEvent event)  {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddMovieView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add movie");
            stage.setResizable(false);
            stage.show();

            // Get the controller for the AddMovieView.fxml
            addMovieController addMovieController = loader.getController();
            // Pass the reference to the main controller to allow communication between controllers
            addMovieController.setMainController(this);
        }catch (IOException e){
            exceptions.noAddMovie(e);
            e.printStackTrace();
        }

    }

    // Method to add a movie to the view
    public void addMovieToView(Movie movie) {
        // Add the new movie to the table view
        movieTblView.getItems().add(movie);
    }

    public void onClickRemove(ActionEvent event) {
        Movie selectedMovie = movieTblView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {

            String alertMessage = "Are you sure you want to delete '" +
                    selectedMovie.getName() + "'?";
            Alert confirmationAlert = showDeleteAlert(alertMessage);

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    movieModel.deleteMovie(selectedMovie);
                    movieModel.getObservableMovies().remove(selectedMovie);
                    System.out.println("MainController");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (selectedMovie == null) {
                showAlert("No movie selected", "Please select a movie for deletion");
            }
        }
    }

    public void onClickPersonalRating(ActionEvent event) {

    }

    private static Alert showDeleteAlert(String message) {
        Alert confirmationAlert = new Alert(Alert.AlertType.NONE, //Use NONE inorder to get rid of the standard icon
                message,
                ButtonType.YES, ButtonType.NO);

        confirmationAlert.setAlertType(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setHeaderText(null); //inorder to make it more simple
        confirmationAlert.setGraphic(null); // Removes the questionmark
        return confirmationAlert;
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}






