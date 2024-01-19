// Import statements
package GUI.Controller;

import BE.Movie;
import GUI.MediaPlayerHelper;
import GUI.Util.MovieExceptions;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

// Controller class for the MediaView
public class MediaViewController implements Initializable {

    // FXML elements for UI controls
    @FXML
    public MediaView mediaView;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Label volumeLabel;

    private int volumeValue;
    private MediaPlayer mediaPlayer;
    private MediaPlayerHelper mediaPlayerHelper;

    // Setter for MediaPlayerHelper
    public void setMediaPlayerHelper(MediaPlayerHelper mediaPlayerHelper) {
        this.mediaPlayerHelper = mediaPlayerHelper;
    }

    // Event handler for the Play button
    public void handlePlay(ActionEvent event) {
        Platform.runLater(() -> {
            if (mediaPlayerHelper != null) {
                mediaPlayerHelper.playMovie();
            }
        });
    }

    // Event handler for the Pause button
    public void handlePause(ActionEvent event) {
        if (mediaPlayerHelper != null) {
            mediaPlayerHelper.pauseMovie();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the volume slider and set its initial value
        volumeBar(volumeSlider);
        volumeSlider.setValue(100);
    }

    // Method to set the selected movie and initialize MediaPlayer
    public void setSelectedMovie(Movie movie, Stage stage) throws MovieExceptions {
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

                // Create a new Media object from the URI
                Media media = new Media(uri.toString());

                // Create a new MediaPlayer instance for the selected media
                mediaPlayer = new MediaPlayer(media);

                Platform.runLater(() -> {
                    mediaView.setMediaPlayer(mediaPlayer);
                });
                // Set the MediaPlayer for the MediaView
                mediaPlayerHelper.setMediaPlayer(mediaPlayer);

                // Stop and dispose of MediaPlayer when the stage is closed
                stage.setOnHidden(event -> {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.dispose();
                        mediaPlayer = null;
                    }
                });
            }
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }

    // Method to handle volume adjustments using the volume slider
    public void volumeBar(Slider volumeSlider) {
        volumeSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue() / 100.0);
                volumeValue = (int) volumeSlider.getValue();
                volumeLabel.setText(Integer.toString(volumeValue));
            }
        });
    }
}
