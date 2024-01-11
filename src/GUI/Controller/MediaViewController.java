package GUI.Controller;

import BE.Movie;
import GUI.Model.MovieModel;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class MediaViewController implements Initializable {
    @FXML
    private Button btnPlay;
    @FXML
    private MediaView mediaView;
    @FXML
    private TableView<Movie> tblMovies;
    private ObservableList<Movie> moviesToBeViewed;

    private MFXLegacyTableView<Movie> movieTblView;
    private GUI.Controller.MediaPlayer mediaPlayer1;

    private Media media;
    private javafx.scene.media.MediaPlayer mediaPlayer;
    private Movie selectedMovie;
    private int currentMovieIndex = 0;
    private StringProperty currentMovieDetails = new SimpleStringProperty();

    private MovieModel movieModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMovieModel(MovieModel movieModel){
        this.movieModel = movieModel;
    }
    public void PlayMedia(ActionEvent actionEvent) {
        /*Movie selectedMovie = tblMovies.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            if (mediaPlayer1.isPlaying()) {
                btnPlay.setText("▶");
                mediaPlayer1.pauseMovie();
            } else {
                System.out.println("hello");
                playMediaMovie(selectedMovie);
                btnPlay.setText("⏸");
            }
        }
        mediaPlayer.play();*/
    }

    public void setSelectedMovie(Movie movie) {
        this.selectedMovie = movie;
        if (selectedMovie != null) {
            String videoFileName = selectedMovie.getFileLink();
            String videoFilePath = "Data/Movies/" + videoFileName;

            File file = new File(videoFilePath);
            URI uri = file.toURI();

            Media media = new Media(uri.toString());

            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
        }
    }

    private void playMediaMovie(Movie movie) {
        ObservableList<Movie> movies = movieModel.getObservableMovies();
        currentMovieIndex = movieModel.getObservableMovies().indexOf(movie);
        if (movie != null && mediaPlayer != null) {
            //mediaPlayer1.playMovie(movie.getFileLink());
            //mediaPlayer.duration(lblSongTimer);
            currentMovieDetails.set("Currently Playing: " + movie.getName());
            btnPlay.setText("⏸");
        }
    }
}
