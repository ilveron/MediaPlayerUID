package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.MusicLibrary;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.model.RetrievingEngine;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MusicLibraryController {
    private Timer timer;
    private TimerTask task;
    private boolean runningTimer;


    @FXML
    private TableView<MyMedia> tableViewMusicLibrary;

    @FXML
    private TableColumn<MyMedia,String> title;

    @FXML
    private TableColumn<MyMedia,String> artist;

    @FXML
    private TableColumn<MyMedia,String> album;

    @FXML
    private TableColumn<MyMedia,String> genre;

    @FXML
    private TableColumn<MyMedia, Integer> year;

    @FXML
    private TableColumn<MyMedia, Double> length;

    @FXML
    void onAddFolder(ActionEvent event) {
        MusicLibrary.getInstance().addFolderToMusicLibrary(RetrievingEngine.getInstance().retrieveFolder(1));
        beginTimer();
        System.out.println(MusicLibrary.getInstance().getKMusic());
    }

    @FXML
    void onAddMedia(ActionEvent event) {
        MusicLibrary.getInstance().addFileToMusicLibrary(RetrievingEngine.getInstance().retrieveFile(1));
        beginTimer();
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: 03/06/2022 CARICAMENTO DA DATABASE
        // caricare da database
        tableViewMusicLibrary.setItems(MusicLibrary.getInstance().getMusicLibrary());
        title.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("album"));
        genre.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("genre"));
        year.setCellValueFactory(new PropertyValueFactory<MyMedia,Integer>("year"));
        length.setCellValueFactory(new PropertyValueFactory<MyMedia,Double>("length"));
        beginTimer();

    }

    public void beginTimer(){

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runningTimer = true;
                tableViewMusicLibrary.refresh();
                System.out.println(tableViewMusicLibrary.getItems().size());
                if (MusicLibrary.getInstance().getMusicLibrary().size() == 0) {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task,0,500);

    }
    public void cancelTimer(){
        runningTimer = false;
        timer.cancel();
    }



}
