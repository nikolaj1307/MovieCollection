package GUI.Controller;

import BE.Movie;
import BE.TMDBMovie;
import DAL.Rest.TMDBConnector;
import GUI.MediaPlayerHelper;
import GUI.Util.MovieExceptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class InfoAPIViewController {

    @FXML
    private Button playMovieBtn;

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
    private MediaViewController mediaViewController;

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

            if (tmdbMovie != null) {
                String posterPath = tmdbMovie.getPoster_path();
                String posterUrl = "https://image.tmdb.org/t/p/original" + posterPath;

                apiMoviePoster.setImage(new Image(posterUrl));
                overviewTxt.setText(tmdbMovie.getOverview());
                titleTxt.setText(selectedMovie.getName());
                genreTxt.setText(selectedMovie.getCatName());
                ratingTxt.setText(String.valueOf(selectedMovie.getRating()));
                personalRatingTxt.setText(String.valueOf(selectedMovie.getPersonalRating()));

            } else {
                overviewTxt.setText("Overview not available");
            }
            System.out.println("No movie is selected");
        }
    }

    public void onClickPlayMovieBtn(ActionEvent event) throws MovieExceptions {

        Movie selectedMovie = mainController.movieTblView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MediaView.fxml"));
                Parent root = loader.load();

                String mediaFilePath = "Data/Movies/" + selectedMovie.getFileLink();
                Media media = new Media(new File(mediaFilePath).toURI().toString());

                MediaViewController mediaViewController = loader.getController();
                mediaViewController.setMediaPlayerHelper(new MediaPlayerHelper(media));
                mediaViewController.setSelectedMovie(selectedMovie);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Media player");
                stage.show();

            } catch (Exception e) {
                throw new MovieExceptions(e);
            }

        }
    }
}

