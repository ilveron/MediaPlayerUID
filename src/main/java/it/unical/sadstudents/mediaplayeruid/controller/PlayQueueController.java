package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
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
    private TableColumn<MyMedia,Double> duration;

    @FXML
    void addFileTo(ActionEvent event) {

    }

    @FXML
    void addFileToQueue(ActionEvent event) {

    }

    @FXML
    void addFolderToQueue(ActionEvent event) {

    }

    @FXML
    void deleteQueue(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewQueue.setItems(PlayQueue.getInstance().getQueue());
        title.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("album"));
        genre.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("genre"));

        //duration.setCellValueFactory(new PropertyValueFactory<MyMedia,Double>("duration"));
        beginTimer();



    }

    public void beginTimer(){

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("thread in funzione");
                System.out.println(PlayQueue.getInstance().getQueue().size());
                System.out.println(tableViewQueue.getItems().size());
                runningTimer = true;
                tableViewQueue.refresh();

                if (PlayQueue.getInstance().getQueue().size()==0 /*|| PlayQueue.getInstance().getQueue().size() == tableViewQueue.getItems().size()*/){
                    //System.out.println(PlayQueue.getInstance().getQueue().size());
                    //System.out.println(tableViewQueue.getItems().size());
                    //System.out.println("sto stoppando il thread");
                    //tableViewQueue.refresh();
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task,0,500);

    }

    public void cancelTimer(){
        System.out.println("thread stoppato");
        runningTimer = false;
        timer.cancel();


    }


}
