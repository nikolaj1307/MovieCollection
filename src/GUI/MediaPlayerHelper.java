// MediaPlayerHelper.java
package GUI;


import BE.Movie;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class MediaPlayerHelper {
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    public MediaPlayerHelper(Media media) {
        this.mediaPlayer = new MediaPlayer(media);
        configureMediaPlayer();
    }

    private void configureMediaPlayer() {
        mediaPlayer.setOnError(new Runnable() {
            @Override
            public void run() {
                System.out.println("Media error: " + mediaPlayer.getError());
            }
        });
    }

    public void playMovie() {
        if (mediaPlayer != null && !isPlaying) {
            mediaPlayer.play();
            isPlaying = true;
        }
    }

    public void pauseMovie() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}
