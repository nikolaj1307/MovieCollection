package GUI.Controller;

// Importer de nødvendige klasser

import BE.Movie;
import GUI.MediaPlayerHelper;
import javafx.application.Platform;
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
    MediaPlayer mediaPlayer;
    private MediaPlayerHelper mediaPlayerHelper;

    public void setMediaPlayerHelper(MediaPlayerHelper mediaPlayerHelper) {
        this.mediaPlayerHelper = mediaPlayerHelper;
    }

    @FXML
    public void handlePlay(ActionEvent event) {
        Platform.runLater(() -> {
            if (mediaPlayerHelper != null) {
                mediaPlayerHelper.playMovie();
            }
        });
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

    public void setSelectedMovie(Movie movie) {
        try {
            // Check if MediaPlayerHelper and MediaView are initialized
            if (mediaPlayerHelper != null && mediaView != null) {
                // Get the file information for the selected movie
                String videoFileName = movie.getFileLink();
                String videoFilePath = "Data/Movies/" + videoFileName;
                System.out.println("video: " + videoFileName);

                // Create a File object from the file path
                File file = new File(videoFilePath);
                // Convert the file to a URI
                URI uri = file.toURI();
                System.out.println("Media URI: " + uri.toString());

                //virker ikke endnu...
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                    mediaPlayer = null;
                }
                // Create a new Media object from the URI
                Media media = new Media(uri.toString());

                //Create a new MediaPlayer instance for the selected media
                mediaPlayer = new MediaPlayer(media);
                /*mediaPlayer.setOnError(() -> {
                    System.out.println("Media Error: " + mediaPlayer.getError());
                });*/

                // Set the MediaPlayer for the MediaView
                //mediaView.setMediaPlayer(mediaPlayer);

                Platform.runLater(() -> {
                    mediaView.setMediaPlayer(mediaPlayer);
                });
                // Set the MediaPlayer for the MediaView
                mediaPlayerHelper.setMediaPlayer(mediaPlayer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
