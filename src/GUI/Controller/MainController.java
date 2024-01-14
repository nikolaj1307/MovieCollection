package GUI.Controller;

import GUI.MediaPlayerHelper;
import GUI.Model.MovieModel;
import BE.Category;
import BE.Movie;
import GUI.Util.Alerts;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
    public MFXLegacyTableView<Movie> movieTblView;

    @FXML
    private MFXTextField searchField;

    private Exceptions exceptions;

    // Model instance for accessing movie data
    private MovieModel movieModel;
    private Alerts alerts;

    // Constructor for initializing model instances
    public MainController() {
        try {
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
        colImdb.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        colPersonalRating.setCellValueFactory(new PropertyValueFactory<>("PersonalRating"));
        colViewHistory.setCellValueFactory(new PropertyValueFactory<>("LastView"));

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
    public void onClickAddMovie(ActionEvent event) {
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
            Alert confirmationAlert = alerts.showDeleteAlert(alertMessage);

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    movieModel.deleteMovie(selectedMovie);
                    movieModel.getObservableMovies().remove(selectedMovie);
                    clearSelection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (selectedMovie == null) {
                alerts.showAlert("No movie selected", "Please select a movie for deletion");
            }
        }
    }

    public void onClickPersonalRating(ActionEvent event) throws IOException {
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
    }

    public void movieTblClick(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            Movie selectedMovie = movieTblView.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                try {

                    // Get the current date
                    Date currentDate = new Date();

                    // Update the last view and pass the current date
                    movieModel.updateLastView(selectedMovie, currentDate);

                    updateMovieTable();
                    clearSelection();

                    String mediaFilePath = "Data/Movies/" + selectedMovie.getFileLink();
                    Media media = new Media(new File(mediaFilePath).toURI().toString());

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MediaView.fxml"));
                    Parent root = loader.load();

                    MediaViewController mediaViewController = loader.getController();
                    mediaViewController.setMediaPlayerHelper(new MediaPlayerHelper(media));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Add movie");
                    stage.setResizable(false);
                    stage.show();

                } catch (IOException e) {
                    //exceptions.noAddMovie(e);
                    e.printStackTrace();
                }
            }
        }
    }
}






