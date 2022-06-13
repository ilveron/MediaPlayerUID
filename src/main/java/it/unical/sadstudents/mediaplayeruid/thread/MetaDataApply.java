package it.unical.sadstudents.mediaplayeruid.thread;

import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.List;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

public class MetaDataApply extends Task<List<MyMedia>> {
    private List<MyMedia> myMediaList;
    private final Object obj= new Object();

    public MetaDataApply(List<MyMedia> myMediaList) {
        this.myMediaList = myMediaList;

    }



    @Override
    protected List<MyMedia> call()  {
        long startTime = currentTimeMillis();
        System.out.println("inizio a: "+startTime);
        int cont=0;
        Media media = new Media(myMediaList.get(0).getPath());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        for (MyMedia myMedia : myMediaList) {
            try{
                media = new Media(myMedia.getPath());
                mediaPlayer = new MediaPlayer(media);
            }catch(MediaException mediaException){
                mediaException.printStackTrace();
                long finale = currentTimeMillis()-startTime;
                System.out.println("Tempo esecuzione metadata: "+finale);
                System.out.println("trovati n metadata: "+cont);
            }

            synchronized(obj){
                try {
                    obj.wait(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Map<String, Object> metadata = mediaPlayer.getMedia().getMetadata();
            if (metadata != null && metadata.get("title") != null) {
                myMedia.setTitle(metadata.get("title").toString());
                cont++;
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
            if (metadata != null && metadata.get("length") != null) {
                myMedia.setLength((Double) metadata.get("length"));
            }
            /*else if (media.getMetadata().get("image") != null){
                // TODO: 12/06/2022 vedere come salvare immagini sul disco
                myMedia.setImage((Image) metadata.get("image"));


            }*/

            synchronized(ThreadManager.getInstance().getObj()){//this is required since mp.setOnReady creates a new thread and our loopp  in the main thread
                ThreadManager.getInstance().getObj().notify();// the loop has to wait unitl we are able to get the media metadata thats why use .wait() and .notify() to synce the two threads(main thread and MediaPlayer thread)
            }
            mediaPlayer.dispose();

        }
        myMediaList.clear();
        long finale = currentTimeMillis()-startTime;
        System.out.println("Tempo esecuzione metadata: "+finale);
        System.out.println("trovati n metadata: "+cont);
        return null;
    }
}
