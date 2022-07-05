package it.unical.sadstudents.mediaplayeruid.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static java.lang.String.format;

public class DataExchangePlaylist {

    private Playlist playlist;


    private static DataExchangePlaylist instance = null;
    private DataExchangePlaylist(){
        playlist=new Playlist();
    }
    public static DataExchangePlaylist getInstance(){
        if (instance==null)
            instance = new DataExchangePlaylist();
        return instance;
    }
    public void addPlaylist(MyMedia m){
        for (MyMedia myMedia1: playlist.getMyList()){
            if (m.equals(myMedia1))
                return;
        }
        durationCalculation(m.getLength());
        playlist.setSongs(playlist.getSongs()+1);
        DatabaseManager.getInstance().setPlaylistSong(playlist.getSongs(), playlist.getTotalDuration(), playlist.getName());
        playlist.getMyList().add(m);
        //System.out.println("salvo : "+playlist.getName()+" il path :"+m.getPath());
        DatabaseManager.getInstance().addMyMediaInPlaylist(m.getPath(), playlist.getName());
    }
    private void durationCalculation(String duration){



        int hour=Integer.parseInt(duration.substring(0,2));
        int minute=Integer.parseInt(duration.substring(3,5));
        int second=Integer.parseInt(duration.substring(6,8));

        int h=Integer.parseInt(playlist.getTotalDuration().substring(0,2));
        int m=Integer.parseInt(playlist.getTotalDuration().substring(3,5));
        int s=Integer.parseInt(playlist.getTotalDuration().substring(6,8));


        int ts=(s+second)%60;
        int tm=(m+minute+((s+second)/60))%60;
        int to=(h+hour+((m+minute+((s+second)/60)))/60);

        /*if(to>=100)
            playlist.setTotalDuration(format("+%02d:%02d:%02d",to,tm,ts));
        else
            playlist.setTotalDuration(format("%02d:%02d:%02d",to,tm,ts));

         */
        playlist.setTotalDuration(format("%02d:%02d:%02d",to,tm,ts));

    }



    public void setPlaylist(Playlist playlist){this.playlist=playlist;}
    public Playlist getPlaylist(){return playlist;}

    public String getImage() {
        return playlist.getImage();
    }
    public void setImage(String image) {playlist.setImage( image);}

    public String getName() {return playlist.getName();}
    public void setName(String name) {playlist.setName(name);}

    public ObservableList<MyMedia> getList() {
        return getPlaylist().getMyList();
    }

    /*public void setList(ObservableList<MyMedia> list) {
        playlist.set = list;
    }*/
}
