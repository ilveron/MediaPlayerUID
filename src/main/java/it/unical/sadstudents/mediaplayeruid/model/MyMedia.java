package it.unical.sadstudents.mediaplayeruid.model;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.lang.Object;
import java.util.Objects;

public class MyMedia  {
    //VARIABLES-DATA
    private String title = "";
    private String artist = "";
    private String album = "";
    private String genre = "";
    private String path = "";
    private Double length;
    private Integer year;
    private String imageUrl;

    // TODO: 03/06/2022 funzioni per ordinamento nelle varie liste/library 
    // TODO:

    //CLASS CONSTRUCTOR
    public MyMedia() { }
    public MyMedia(File file) {
        path = file.toURI().toString();
        imageUrl = "src/main/resources/it/unical/sadstudents/mediaplayeruid/image/download.png";



        setTitle(file.getName());

        Media media = new Media(path);

        media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
            if(change.wasAdded()) {
                //System.out.println(media.getMetadata());
                if ("title".equals(change.getKey())) {
                    if (media.getMetadata().get("title").toString() != null)
                        setTitle(media.getMetadata().get("title").toString());
                }
                else if ("artist".equals(change.getKey())) {
                    setArtist(media.getMetadata().get("artist").toString());
                }
                else if ("album".equals(change.getKey())) {
                    setAlbum(media.getMetadata().get("album").toString());
                }
                else if ("genre".equals(change.getKey())) {
                    setGenre(media.getMetadata().get("genre").toString());
                }
                else if ("year".equals(change.getKey())) {
                    setYear(Integer.parseInt(media.getMetadata().get("year").toString()));
                }
                else if (media.getMetadata().get("length") != null){
                    setLength(Double.parseDouble(media.getMetadata().get("length").toString()));
                    System.out.println("Ho la length: "+ length);
                }
                /*else if (media.getMetadata().get("image") != null){
                    Object newImage = (media.getMetadata().get("image"));

                    setImage(String.valueOf(newImage.getClass().getResource() .toString());;
                    System.out.println(newImage.getClass().toString());


                }*/


            }
        });
    }
    //END CLASS CONSTRUCTOR

    //VARIABLES GETTERS AND SETTERS


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) { this.title = title; }
    public void setArtist(String artist) { this.artist = artist; }
    public void setAlbum(String album) { this.album = album; }
    public void setLength(Double length) { this.length = length; }
    public void setYear(Integer year) { this.year = year; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public Double getLength() { return length; }
    public Integer getYear() { return year; }

   @Override
   public String toString() {
        return  title + '\'' + artist + '\'' +album + '\'' +genre + '\'' +path + '\'' +length +'\''+year ;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyMedia myMedia = (MyMedia) o;
        return Objects.equals(title, myMedia.title) && Objects.equals(artist, myMedia.artist) && Objects.equals(album, myMedia.album) && Objects.equals(genre, myMedia.genre) && Objects.equals(path, myMedia.path) && Objects.equals(length, myMedia.length) && Objects.equals(year, myMedia.year);
    }*/

    //END VARIABLES GETTERS AND SETTERS


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyMedia)) return false;
        MyMedia myMedia = (MyMedia) o;
        return getPath().equals(myMedia.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath());
    }
}
