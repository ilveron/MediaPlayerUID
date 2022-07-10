package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.ContextMenuHandler;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class PlayQueueController implements Initializable {

    private Timer timer;
    private TimerTask task;
    private boolean runningTimer;

    //TABLEVIEW (WORK WITH OBSERVABLE LIST IN PLAY QUEUE MODEL)
    @FXML
    private TableView<MyMedia> tableViewQueue;
    @FXML
    private TableColumn<MyMedia,String> title, artist, album, genre;
    @FXML
    private TableColumn<MyMedia, String> year;
    @FXML
    private TableColumn<MyMedia, String> length;
    //END TABLEVIEW

    @FXML
    private Button delete;

    @FXML
    private MenuItem addFileToMusicLibrary;

    @FXML
    private MenuItem addFileToPlaylist;


    //ACTION EVENT ON BUTTON INSIDE THE FXML ASSOCIATED FILE
    @FXML
    void addFilesToQueue(ActionEvent event) {
        PlayQueue.getInstance().addFilesToList(RetrievingEngine.getInstance().retrieveFile(0));
        colorSelectedRow();
    }

    @FXML
    void addFolderToQueue(ActionEvent event) {
        PlayQueue.getInstance().addFilesToList(RetrievingEngine.getInstance().retrieveFolder(0));
        colorSelectedRow();
    }

    @FXML
    void deleteQueue(ActionEvent event) {
        if(PlayQueue.getInstance().getQueue().size()>0) {
            if (Player.getInstance().getIsRunning())
                Player.getInstance().stop();
            PlayQueue.getInstance().clearQueue();
        }
    }

    @FXML
    void onAddFileToMusicLibrary(ActionEvent event) {

    }

    @FXML
    void onAddFileToPlaylist(ActionEvent event) {

    }

    //END ACTION EVENT

    private ContextMenuHandler contextMenuHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: 07/06/2022
        startToolTip();

        tableViewQueue.setItems(PlayQueue.getInstance().getQueue());
        title.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("album"));
        genre.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("genre"));
        year.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("year"));
        length.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("length"));
        //beginTimer();

        colorSelectedRow();

        focusTableView();

        PlayQueue.getInstance().currentMediaProperty().addListener(observable -> {
            Integer current = PlayQueue.getInstance().getCurrentMedia();
            tableViewQueue.getSelectionModel().select(current);
            tableViewQueue.scrollTo(current);
        });


        tableViewQueue.setRowFactory(tableView ->{
            final TableRow<MyMedia> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(!row.isEmpty() && !event.getButton().equals(MouseButton.SECONDARY)) {
                    MyMedia myMedia=row.getItem();
                    if (event.getClickCount() == 2) {
                        PlayQueue.getInstance().setCurrentMedia(row.getIndex());
                    }
                }
                else if(event.getButton().equals(MouseButton.SECONDARY)){
                    if(!row.isEmpty()){
                        MyMedia myMedia=row.getItem();
                        contextMenuHandler = new ContextMenuHandler(myMedia,"","playqueue",row.getIndex());
                        row.setContextMenu(contextMenuHandler);
                        row.getContextMenu();
                    }
                }
            });

            /*row.setOnMouseDragged(event ->{
                if(!row.isEmpty())
                    PlayQueue.getInstance().setCurrentMedia(row.getIndex());
            });*/
            return row;
        });

        tableViewQueue.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode key = keyEvent.getCode();
                if (key == KeyCode.ENTER)
                    PlayQueue.getInstance().setCurrentMedia(tableViewQueue.getSelectionModel().getSelectedIndex());
            }
        });
    }

    //aggiunta come fix
    public void colorSelectedRow(){
        if(PlayQueue.getInstance().getQueue().size() > 0)
            tableViewQueue.getSelectionModel().select(PlayQueue.getInstance().getCurrentMedia());
    }

    public void startToolTip(){
        // TODO: 07/06/2022
        delete.setTooltip(new Tooltip("Delete all the elements from the queue"));
    }

    private void focusTableView(){


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tableViewQueue.requestFocus();
            }
        });
    }

}
