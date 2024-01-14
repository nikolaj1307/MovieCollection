package GUI.Controller;

// Importer de nødvendige klasser

import BE.Movie;
import GUI.MediaPlayerHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class MediaViewController implements Initializable {

    @FXML
    public MediaView mediaView;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnPause;
    @FXML
    private BorderPane borderPane;

    private MediaPlayerHelper mediaPlayerHelper;

    public void setMediaPlayerHelper(MediaPlayerHelper mediaPlayerHelper) {
        this.mediaPlayerHelper = mediaPlayerHelper;
    }

    @FXML
    public void handlePlay(ActionEvent event) {
        if (mediaPlayerHelper != null) {
            mediaPlayerHelper.playMovie();
        }
    }

    @FXML
    public void handlePause(ActionEvent event) {
        if (mediaPlayerHelper != null) {
            mediaPlayerHelper.pauseMovie();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // Tilføj yderligere initialisering eller metoder, hvis det er nødvendigt

    /*public void setSelectedMovie(Movie movie) {
        if (mediaPlayerHelper != null && mediaView != null) {
            String videoFileName = movie.getFileLink();
            String videoFilePath = "Data/Movies/" + videoFileName;

            File file = new File(videoFilePath);
            URI uri = file.toURI();

            Media media = new Media(uri.toString());
            //media.setOnError(this::handleMediaError);

            MediaPlayer mediaPlayer = new MediaPlayer(media);
            //mediaPlayer.setOnError(this::handleMediaPlayerError);

            mediaView.setMediaPlayer(new MediaPlayer(mediaPlayer));

            mediaPlayerHelper.setMediaPlayer(mediaPlayer);
        }
    }*/

    private void handleMediaPlayerError(MediaErrorEvent errorEvent) {
        System.out.println("MediaPlayer error: " + errorEvent);
    }

    private void handleMediaError(MediaErrorEvent errorEvent) {
        System.out.println("Media error: " + errorEvent);
    }


}
