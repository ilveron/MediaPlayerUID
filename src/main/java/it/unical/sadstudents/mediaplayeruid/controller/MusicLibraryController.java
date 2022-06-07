package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MusicLibraryController implements Initializable {
    private Timer timer;
    private TimerTask task;
    private boolean runningTimer;

    //TABLEVIEW (WORK WITH OBSERVABLE LIST IN MUSIC LIBRARY MODEL)
    @FXML
    private TableView<MyMedia> tableViewMusicLibrary;
    @FXML
    private TableColumn<MyMedia,String> title, artist, album, genre;
    @FXML
    private TableColumn<MyMedia, Integer> year;
    @FXML
    private TableColumn<MyMedia, Double> length;
    //END TABLEVIEW

    //ACTION EVENT ON BUTTON INSIDE THE FXML ASSOCIATED FILE
    @FXML
    void onAddFolder(ActionEvent event) {
        MusicLibrary.getInstance().addFolderToList(RetrievingEngine.getInstance().retrieveFolder(1));
        beginTimer();
        //System.out.println(MusicLibrary.getInstance().getKMusic());
    }
    @FXML
    void onAddMedia(ActionEvent event) {
        MusicLibrary.getInstance().addFileToList(RetrievingEngine.getInstance().retrieveFile(1));
        beginTimer();
    }
    //END ACTION EVENT

    @FXML
    void onPlayLibrary(ActionEvent event) {
        // TODO: 07/06/2022 SELEZIONARE VARIE CANZONI DA RIPRODURRE
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        startToolTip();
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

        //Gestire se quandi clicchi su una canzone deve ricreare la playquee o aggiungere alla playquee
    }

    private void startToolTip() {
        // TODO: 07/06/2022
    }

    //TASK
    public void beginTimer(){

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runningTimer = true;
                MusicLibrary.getInstance().sortList();
                tableViewMusicLibrary.refresh();
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
    //END TASK



}
