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

public class InfoAPIViewController {

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

    private MainController mainController;
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

    public void changeData() throws JSONException, IOException, URISyntaxException, InterruptedException {
        Movie selectedMovie = mainController.movieTblView.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            String getNameFromSelectedMovie = selectedMovie.getName();
            System.out.println(selectedMovie.getName());

            // Create a new TMDBConnector instance with the movie name
            TMDBConnector tmdbConnector = new TMDBConnector(getNameFromSelectedMovie);

            // Fetch the TMDBMovie data
            TMDBMovie tmdbMovie = tmdbConnector.getMovieFound();

            titleTxt.setText(selectedMovie.getName());
            genreTxt.setText(selectedMovie.getCatName());
            ratingTxt.setText(String.valueOf(selectedMovie.getRating()));
            personalRatingTxt.setText(String.valueOf(selectedMovie.getPersonalRating()));

            if (tmdbMovie != null) {
                String posterPath = tmdbMovie.getPoster_path();
                String posterUrl = "https://image.tmdb.org/t/p/original" + posterPath;

                apiMoviePoster.setImage(new Image(posterUrl));
                overviewTxt.setText(tmdbMovie.getOverview());

            } else {
                overviewTxt.setText("Overview not available");
                apiMoviePoster.setImage(new Image("Images/questionmark.png"));
            }
        }
    }

    public void onClickPlayMovieBtn(ActionEvent event) throws MovieExceptions {

        Movie selectedMovie = mainController.movieTblView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            try {

                // Get the current date
                Date currentDate = new Date();

                // Update the last view and pass the current date
                movieModel.updateLastView(selectedMovie, currentDate);

                mainController.updateMovieTable();

                // Close the (InfoAPIViewController's stage)
                Stage closeInfoView = (Stage) playMovieBtn.getScene().getWindow();
                closeInfoView.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MediaView.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Media player");
                stage.show();

                String mediaFilePath = "Data/Movies/" + selectedMovie.getFileLink();
                Media media = new Media(new File(mediaFilePath).toURI().toString());

                MediaViewController mediaViewController = loader.getController();
                mediaViewController.setMediaPlayerHelper(new MediaPlayerHelper(media));
                mediaViewController.setSelectedMovie(selectedMovie, (Stage) root.getScene().getWindow());

                mainController.clearSelection();

            } catch (Exception e) {
                throw new MovieExceptions(e);
            }

        }
    }
}

