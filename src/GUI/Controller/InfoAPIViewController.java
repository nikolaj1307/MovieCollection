// Import statements
package GUI.Controller;

import BE.Movie;
import BE.TMDBMovie;
import DAL.Rest.TMDBConnector;
import GUI.MediaPlayerHelper;
import GUI.Model.MovieModel;
import GUI.Util.MovieExceptions;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

// Controller class for the InfoAPIView
public class InfoAPIViewController {

    // FXML elements for UI controls
    @FXML
    private MFXButton playMovieBtn;

    @FXML
    private ImageView apiMoviePoster;

    @FXML
    private Text titleTxt;

    @FXML
    private Text genreTxt;

    @FXML
    private Text ratingTxt;

    @FXML
    private Text overviewTxt;

    @FXML
    private Text personalRatingTxt;

    // Reference to the MainController for communication between controllers
    private MainController mainController;

    // Model instance for accessing data
    private MovieModel movieModel;

    // Constructor for initializing model instances
    public InfoAPIViewController() throws MovieExceptions {
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }

    // Setter for MainController
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Method to change displayed data based on the selected movie
    public void changeData() throws JSONException, IOException, URISyntaxException, InterruptedException {
        Movie selectedMovie = mainController.movieTblView.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            String getNameFromSelectedMovie = selectedMovie.getName();
            System.out.println(selectedMovie.getName());

            // Create a new TMDBConnector instance with the movie name
            TMDBConnector tmdbConnector = new TMDBConnector(getNameFromSelectedMovie);

            // Fetch the TMDBMovie data
            TMDBMovie tmdbMovie = tmdbConnector.getMovieFound();

            // Set text fields with information from the selected movie and TMDB API
            titleTxt.setText(selectedMovie.getName());
            genreTxt.setText(selectedMovie.getCatName());
            ratingTxt.setText(String.valueOf(selectedMovie.getRating()));
            personalRatingTxt.setText(String.valueOf(selectedMovie.getPersonalRating()));

            if (tmdbMovie != null) {
                // Display movie poster from TMDB API
                String posterPath = tmdbMovie.getPoster_path();
                String posterUrl = "https://image.tmdb.org/t/p/original" + posterPath;
                apiMoviePoster.setImage(new Image(posterUrl));
                overviewTxt.setText(tmdbMovie.getOverview());

            } else {
                // Display placeholder if no TMDB data is available
                overviewTxt.setText("Overview not available");
                apiMoviePoster.setImage(new Image("Images/questionmark.png"));
            }
        }
    }

    // Event handler for the Play Movie button
    public void onClickPlayMovieBtn(ActionEvent event) throws MovieExceptions {

        Movie selectedMovie = mainController.movieTblView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            try {

                // Get the current date
                Date currentDate = new Date();

                // Update the last view and pass the current date
                movieModel.updateLastView(selectedMovie, currentDate);

                // Update the movie table in the main controller
                mainController.updateMovieTable();

                // Close the InfoAPIViewController's stage
                Stage closeInfoView = (Stage) playMovieBtn.getScene().getWindow();
                closeInfoView.close();

                // Load the MediaView for playing the selected movie
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MediaView.fxml"));
                Parent root = loader.load();

                // Create a new stage for the MediaView
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Media player");
                stage.show();

                // Get the file path for the selected movie
                String mediaFilePath = "Data/Movies/" + selectedMovie.getFileLink();
                Media media = new Media(new File(mediaFilePath).toURI().toString());

                // Get the controller for the MediaView
                MediaViewController mediaViewController = loader.getController();
                mediaViewController.setMediaPlayerHelper(new MediaPlayerHelper(media));
                mediaViewController.setSelectedMovie(selectedMovie, (Stage) root.getScene().getWindow());

                // Clear the selection in the main controller's movie table
                mainController.clearSelection();

            } catch (Exception e) {
                throw new MovieExceptions(e);
            }

        }
    }
}
