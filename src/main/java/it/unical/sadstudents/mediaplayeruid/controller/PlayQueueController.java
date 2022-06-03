package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import it.unical.sadstudents.mediaplayeruid.model.RetrievingEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class PlayQueueController implements Initializable {

    private Timer timer;
    private TimerTask task;
    private boolean runningTimer;


    @FXML
    private TableView<MyMedia> tableViewQueue;

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
    void addFileTo(ActionEvent event) {

    }

    @FXML
    void addFileToQueue(ActionEvent event) {
        PlayQueue.getInstance().addFileToQueue(RetrievingEngine.getInstance().retrieveFile(0));
        beginTimer();
    }

    @FXML
    void addFolderToQueue(ActionEvent event) {
        PlayQueue.getInstance().addFolderToQueue(RetrievingEngine.getInstance().retrieveFolder(0));
        beginTimer();
    }

    @FXML
    void deleteQueue(ActionEvent event) {
        PlayQueue.getInstance().getQueue().clear();
        Player.getInstance().stop();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableViewQueue.setItems(PlayQueue.getInstance().getQueue());
        title.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("album"));
        genre.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("genre"));
        year.setCellValueFactory(new PropertyValueFactory<MyMedia,Integer>("year"));
        length.setCellValueFactory(new PropertyValueFactory<MyMedia,Double>("length"));
        beginTimer();

        tableViewQueue.setRowFactory(tableView ->{
            final TableRow<MyMedia> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() > 1 && !row.isEmpty()){
                    PlayQueue.getInstance().setCurrentMedia(row.getIndex());
                    System.out.println(row.getIndex());
                }
            });
            return row;
        });
    }

    public void beginTimer(){

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runningTimer = true;
                tableViewQueue.refresh();

                if (PlayQueue.getInstance().getQueue().size()==0 /*|| PlayQueue.getInstance().getQueue().size() == tableViewQueue.getItems().size()*/){
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
