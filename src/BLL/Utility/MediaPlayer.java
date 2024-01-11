package BLL.Utility;

import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;

import java.io.File;

public class MediaPlayer {

    private javafx.scene.media.MediaPlayer mediaPlayer;

    private String folder = "Movies" + File.separator;

    private boolean isPlaying = false;

    private String curretSongFilePath = "";


    public void playMovie(String filePath) {
        File mediaFile = new File(folder + filePath);

        if (mediaFile.exists()) {
            if (mediaPlayer != null) {
                if (isPlaying) {
                    mediaPlayer.pause();
                    //mediaPlayer.getStatus();
                    isPlaying = false;
                }
            }

            Media media = new Media(mediaFile.toURI().toString());
            if (mediaPlayer == null || !filePath.equals(curretSongFilePath)) {
                mediaPlayer = new javafx.scene.media.MediaPlayer(media);
            }

            mediaPlayer.play();
            isPlaying = true;
            curretSongFilePath = filePath;
        } else {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        }
    }
    public void pauseMovie(){
        if (mediaPlayer != null){
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public boolean isPlaying() {
        return isPlaying();
    }
}