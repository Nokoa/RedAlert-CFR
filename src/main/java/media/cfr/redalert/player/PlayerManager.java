package media.cfr.redalert.player;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import media.cfr.redalert.util.CityUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Manages the playing of alert sounds submitted through the scraper
 */
public class PlayerManager {

    private MediaView mediaView;
    private PlayerState playerState;
    private List<String> playlist;
    private final int COOLDOWN_DURATION = 10000;
    private long cooldownEnd;

    // cache cities to not alert for them again for 60 seconds
    Cache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .removalListener(new RemovalListener<String, String>() {
                public void onRemoval(RemovalNotification<String, String> removal) {
                    //todo
                }
            })
            .build();

    /**
     * initializes the Player Manager
     */
    public PlayerManager() {
        JFXPanel fxPanel = new JFXPanel(); //required for JavaFX media player to work
        mediaView = new MediaView();
        this.playerState = PlayerState.IDLE;
        playlist = new ArrayList<>();


    }

    /**
     * Start the full alert process
     * Plays the Red Alert alarm
     */
    private void alert() {

        playAlarm(Alarm.REDALERT);


    }

    /**
     * Start the full alert chain process with custom initial alarm
     * This will automatically begin listing the cities affected if they are in the playlist
     * @param alarm the type of alarm to play initially
     */
    private void playAlarm(Alarm alarm) {
        setPlayerState(PlayerState.PLAYING_ALARM);
        loadAndPlayClip(alarm.getPath(), this::playAlertList);


    }

    /**
     * add a whole list of locations' audio file paths to start the alert chain process
     * @param locations
     */
    public void addLocationAlerts(List<String> locations) {
        for (String loc : locations) {
            addAlertLocationPath(CityUtil.getCityFilePath(loc));
        }
    }

    /**
     * Check if the alarm cooldown expired and reset its state
     */
    private void updateCooldown() {
        if (playerState == PlayerState.IDLE_COOLDOWN) {
            if (System.currentTimeMillis() > cooldownEnd)
                setPlayerState(PlayerState.IDLE);
        }
    }

    /**
     * Add a city audio file path name to begin the alerting chain automatically based on states.
     * This method will add the path to the cache
     * Adds the file path to the playlist
     * Begins the alerting with alaram chain
     * or begins the areas affected chain only, based on current state, and cooldown
     *
     * if there is a current alert playing, it will be added to the playlist, if it just ended, then it will start playing again but without the alarm
     * If cooldown expired it will start the whole alert process again
     * @param path the audio path to add to the playlist
     */
    public void addAlertLocationPath(String path) {
        updateCooldown();
        System.out.println("Received location path " + path);
        if (cache.asMap().containsKey(path)) {
            System.out.println("cache present");
            return;
        }
        System.out.println("Adding " + path);
        cache.put(path, path);
        playlist.add(path);
        if (getPlayerState() == PlayerState.IDLE) {
            System.out.println("Alert");
            alert();
        } else if (getPlayerState() == PlayerState.IDLE_COOLDOWN)
            playAlertList();


    }

    /**
     * Start the city alert list chain
     * This will play the header indicating areas affected, and then the cities in the playlist will be asked to play
     */
    private void playAlertList() {
        setPlayerState(PlayerState.PLAYING_HEADER);
        loadAndPlayClip(Alarm.ALERT_FOR.getPath(), this::playNext);
    }

    /**
     * gets the next city audio file path from the playlist, and remove it from the playlist
     * @return city audio file path
     */
    private String getNext() {
        System.out.println(playlist.size());
        if (playlist.size() > 0) {
            setPlayerState(PlayerState.PLAYING_LIST);
            return playlist.remove(0);
        }
        setPlayerState(PlayerState.IDLE_COOLDOWN);

        return null;
    }

    /**
     * Play the next city audio file from the playlist
     * Upon completion the next file in the playlist will automatically start playing until there are no more files in the playlist
     */
    private void playNext() {
        String path = getNext();
        if (path == null)
            return;

        setPlayerState(PlayerState.PLAYING_LIST);
        loadAndPlayClip(path, this::playNext);

    }

    /**
     * Set the state of the player
     * @param state the state to set the player
     */
    public void setPlayerState(PlayerState state) {
        this.playerState = state;
        if (state == PlayerState.IDLE_COOLDOWN)
            cooldownEnd = System.currentTimeMillis() + COOLDOWN_DURATION;
    }


    /**
     * Actual method that plays an audio file, and sets its completion event
     * @param musicFile
     * @param mediaEndRunnable
     */
    public void loadAndPlayClip(String musicFile, Runnable mediaEndRunnable) {

        disposePlayer();

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(mediaEndRunnable);
        mediaView.setMediaPlayer(mediaPlayer);


    }

    /**
     * disposes the media player to release resources.
     */
    private void disposePlayer() {
        MediaPlayer player = mediaView.getMediaPlayer();
        if (player != null) {
            player.dispose();
        }
    }

    /**
     * get the current player state
     * @return PlayerState the current state of the player
     */
    public PlayerState getPlayerState() {
        return playerState;
    }
}
