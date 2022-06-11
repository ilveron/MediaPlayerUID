package it.unical.sadstudents.mediaplayeruid.thread;

import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import javafx.scene.media.MediaPlayer;

import java.util.Map;

public class MetaDataApply implements Runnable {
    private MyMedia myMedia;
    private MediaPlayer mediaPlayer;

    public MetaDataApply(MyMedia myMedia, MediaPlayer mediaPlayer) {
        this.myMedia = myMedia;
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void run() {
        Map<String, Object> metadata = mediaPlayer.getMedia().getMetadata();
        if (metadata != null && metadata.get("title") != null) {
            myMedia.setTitle(metadata.get("title").toString());
        }
        if (metadata != null && metadata.get("artist") != null) {
            myMedia.setArtist(metadata.get("artist").toString());
        }
        if (metadata != null && metadata.get("album") != null) {
            myMedia.setAlbum(metadata.get("album").toString());
        }
        if (metadata != null && metadata.get("genre") != null) {
            myMedia.setGenre(metadata.get("genre").toString());
        }
        if (metadata != null && metadata.get("year") != null) {
            myMedia.setYear((Integer) metadata.get("year"));
        }
        if (metadata != null && metadata.get("lenght") != null) {
            myMedia.setLength((Double) metadata.get("lenght"));
        }

        synchronized(ThreadManager.getInstance().getObj()){//this is required since mp.setOnReady creates a new thread and our loopp  in the main thread
            ThreadManager.getInstance().getObj().notify();// the loop has to wait unitl we are able to get the media metadata thats why use .wait() and .notify() to synce the two threads(main thread and MediaPlayer thread)
        }



    }
}
