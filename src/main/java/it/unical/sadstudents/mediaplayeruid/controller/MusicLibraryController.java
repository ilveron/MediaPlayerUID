package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.ContextMenuHandler;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;

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
    private TableColumn<MyMedia, String> year;
    @FXML
    private TableColumn<MyMedia, String> length;

    @FXML
    private TextField TextField;
    @FXML
    private Button btnAddLibraryToQueue, btnAddSongToQueue, btnDelete;
    //END TABLEVIEW

    //ACTION EVENT ON BUTTON INSIDE THE FXML ASSOCIATED FILE
    @FXML
    void onAddFolder(ActionEvent event) {
        MusicLibrary.getInstance().addFilesToList(RetrievingEngine.getInstance().retrieveFolder(1));
        tableViewMusicLibrary.refresh();
        if(MusicLibrary.getInstance().getMusicLibrary().size()>0){
            setObject(false);
        }
        //System.out.println(MusicLibrary.getInstance().getKMusic());
    }
    @FXML
    void onAddMedia(ActionEvent event) {
        MusicLibrary.getInstance().addFilesToList(RetrievingEngine.getInstance().retrieveFile(1));
        tableViewMusicLibrary.refresh();
        if(MusicLibrary.getInstance().getMusicLibrary().size()>0){
            setObject(false);
        }
    }
    void setObject(boolean t){
        btnAddLibraryToQueue.setDisable(t);
        btnDelete.setDisable(t);
        btnAddSongToQueue.setDisable(t);
    }
    // TODO: 08/06/2022 stesso problema sotto, passo un myMedia non un file 
    @FXML
    void onAddLibraryToQueue(ActionEvent event) {
        for(MyMedia myMedia : MusicLibrary.getInstance().getMusicLibrary()){
            PlayQueue.getInstance().addFileToListFromOtherModel(myMedia);
        }
        SceneHandler.getInstance().setCurrentMidPane("play-queue-view.fxml");
    }

    @FXML
    void onAddMediaPlayQueue(ActionEvent event) {
        MyMedia myMedia=tableViewMusicLibrary.getSelectionModel().getSelectedItem();
        if(myMedia!=null){
            PlayQueue.getInstance().addFileToListFromOtherModel(myMedia);
        }
    }
    @FXML
    void onDeleteSong(ActionEvent event) {
        int index=tableViewMusicLibrary.getSelectionModel().getSelectedIndex();
        MusicLibrary.getInstance().deleteWithIndex(index);
        tableViewMusicLibrary.refresh();
        /*if(myMedia!=-1){
            for(int i=0;i<Home.getInstance().getRecentMedia().size();i++) {
                if (MusicLibrary.getInstance().getMusicLibrary().get(myMedia).equals(Home.getInstance().getRecentMedia().get(i))){
                    Home.getInstance().removeItem(i);
                    break;
                }
            }
            Playlists.getInstance().deleteMediaCompletely(MusicLibrary.getInstance().getMusicLibrary().get(myMedia).getPath());
            //PlayQueue.getInstance().getQueue().remove(MusicLibrary.getInstance().getMusicLibrary().get(myMedia).getPath());
            DatabaseManager.getInstance().deleteMedia(MusicLibrary.getInstance().getMusicLibrary().get(myMedia).getPath(),"MyMedia");
            MusicLibrary.getInstance().getMusicLibrary().remove(myMedia);
            tableViewMusicLibrary.refresh();

        }*/
    }

    //END ACTION EVENT

    private ContextMenuHandler contextMenuHandler;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startToolTip();
        if(MusicLibrary.getInstance().getMusicLibrary().size()>0) setObject(false);

        /*tableViewMusicLibrary.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
               // tableViewMusicLibrary.(myMediaSingleBox,contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());
            }
        });*/
        // TODO: 03/06/2022 CARICAMENTO DA DATABASE
        // caricare da database
        tableViewMusicLibrary.setItems(MusicLibrary.getInstance().getMusicLibrary());
        title.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("album"));
        genre.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("genre"));
        year.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("year"));
        length.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("length"));
        //MusicLibrary.getInstance().sortList();

        tableViewMusicLibrary.setRowFactory(tableView ->{
            final TableRow<MyMedia> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(!row.isEmpty() && !event.getButton().equals(MouseButton.SECONDARY)) {
                    MyMedia myMedia=row.getItem();
                    if (event.getClickCount() > 1) {
                        if(MusicLibrary.getInstance().isPresent(myMedia,MusicLibrary.getInstance().getSelection())!=-1)
                            MusicLibrary.getInstance().getSelection().remove(myMedia);// TODO: 05/07/2022  baggato apre a caso un altro file pero video X/
                        System.out.println(myMedia.getPath());
                        PlayQueue.getInstance().addFileToListFromOtherModel(myMedia);
                    }/*else if (event.getClickCount() == 1 ) {
                        if (MusicLibrary.getInstance().isPresent(myMedia,MusicLibrary.getInstance().getSelection())!=-1) {
                            row.setStyle("");
                            row.setFocusTraversable(false);
                            MusicLibrary.getInstance().getSelection().remove(myMedia);
                        } else {
                            row.setFocusTraversable(false);
                            MusicLibrary.getInstance().getSelection().add(myMedia);
                            row.setStyle("-fx-background-color: RED;");
                        }

                    }*/ else if (row.isEmpty()) {
                        row.setFocusTraversable(false);
                        MusicLibrary.getInstance().getSelection().clear();

                    }
                }
                else if(event.getButton().equals(MouseButton.SECONDARY)){
                            if(!row.isEmpty()){
                                MyMedia myMedia=row.getItem();
                                contextMenuHandler = new ContextMenuHandler(myMedia,"","musicLibrary");
                                row.setContextMenu(contextMenuHandler);
                                row.getContextMenu();
                            }
                }
            });

            return row;
        });
        TextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tableViewMusicLibrary.setItems(SearchForFile.getInstance().getSearch(newValue, MusicLibrary.getInstance().getMusicLibrary()));
            }
        });
        //Gestire se quandi clicchi su una canzone deve ricreare la playquee o aggiungere alla playquee
    }

    private void startToolTip() {
        // TODO: 07/06/
        btnAddLibraryToQueue.setTooltip(new Tooltip("Add entire library to queue"));
        btnDelete.setTooltip(new Tooltip("Delete selected music"));
        btnAddSongToQueue.setTooltip(new Tooltip("Add song to queue"));
        TextField.setTooltip(new Tooltip("Research the Music"));

    }


    // TODO: 08/06/2022 inizialmente la lista deve essere ordinata usando il metodo sortList pero non funziona quando ci sono troppe canzoni dato che non si aggiorna
    //TASK
    /*public void beginTimer(){

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runningTimer = true;
                if(MusicLibrary.getInstance().sortTF>0&&MusicLibrary.getInstance().getMusicLibrary().size()>0){
                    MusicLibrary.getInstance().sortList();
                    MusicLibrary.getInstance().sortTF--;
                }
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
    }*/
    //END TASK



}
