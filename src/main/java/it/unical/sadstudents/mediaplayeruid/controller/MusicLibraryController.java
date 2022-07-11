package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.utils.MyNotification;
import it.unical.sadstudents.mediaplayeruid.utils.RetrievingEngine;
import it.unical.sadstudents.mediaplayeruid.utils.SearchForFile;
import it.unical.sadstudents.mediaplayeruid.view.ContextMenuHandler;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MusicLibraryController implements Initializable {
    @FXML
    private TableView<MyMedia> tableViewMusicLibrary;
    @FXML
    private TableColumn<MyMedia,String> title, artist, album, genre;
    @FXML
    private TableColumn<MyMedia, String> year;
    @FXML
    private TableColumn<MyMedia, String> length;
    @FXML
    private MenuButton mbtAdd;
    @FXML
    private Label lblSongsNum;

    @FXML
    private TextField TextField;
    @FXML
    private Button btnAddLibraryToQueue, btnAddSongToQueue, btnRemove, btnClearAll;
    //END TABLEVIEW

    //ACTION EVENT ON BUTTON INSIDE THE FXML ASSOCIATED FILE
    @FXML
    void onAddFolder(ActionEvent event) {
        MusicLibrary.getInstance().addFilesToList(RetrievingEngine.getInstance().retrieveFolder(1));
        tableViewMusicLibrary.refresh();
    }
    @FXML
    void onAddMedia(ActionEvent event) {
        MusicLibrary.getInstance().addFilesToList(RetrievingEngine.getInstance().retrieveFile(1));
        tableViewMusicLibrary.refresh();
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
    void onRemoveSong(ActionEvent event) {

        MyMedia myMedia=tableViewMusicLibrary.getSelectionModel().getSelectedItem();
            if(myMedia==null){
                MyNotification.notifyInfo("","Select a song",3);
                return ;
            }

            MusicLibrary.getInstance().deleteStandard(myMedia);
            tableViewMusicLibrary.getItems().remove(myMedia);
            tableViewMusicLibrary.refresh();

            Integer size = MusicLibrary.getInstance().getMusicLibrary().size();
            lblSongsNum.setText(size.toString());
    }

    @FXML
    void onClearAll(ActionEvent event){
        MusicLibrary.getInstance().clearList();
        lblSongsNum.setText("0");
    }

    //END ACTION EVENT
    private MouseEvent mouseEvent1=null;
    private ContextMenuHandler contextMenuHandler;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startToolTip();
        activeButton();
        colorSelectedRow();

        SceneHandler.getInstance().getScene().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount()==1){
                    if(mouseEvent1!=null && !mouseEvent.getTarget().equals(mouseEvent1.getTarget())){
                        if (mouseEvent.getTarget()!=btnAddSongToQueue){
                            tableViewMusicLibrary.getSelectionModel().clearSelection();
                            mouseEvent1=null;
                        }
                    }
                }
            }
        });

        tableViewMusicLibrary.setItems(MusicLibrary.getInstance().getMusicLibrary());
        title.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("album"));
        genre.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("genre"));
        year.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("year"));
        length.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("length"));

        tableViewMusicLibrary.setRowFactory(tableView ->{
            final TableRow<MyMedia> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                mouseEvent1=event;
                if(!row.isEmpty() && !event.getButton().equals(MouseButton.SECONDARY)) {
                    MyMedia myMedia=row.getItem();
                    if (event.getClickCount() == 2) {
                        PlayQueue.getInstance().generateNewQueue(myMedia);
                    }
                } else if(event.getButton().equals(MouseButton.SECONDARY)){
                            if(!row.isEmpty()){
                                MyMedia myMedia=row.getItem();
                                contextMenuHandler = new ContextMenuHandler(myMedia,"","musicLibrary", row.getIndex());
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

        MusicLibrary.getInstance().refreshButtonProperty().addListener(observable -> {
            if(MusicLibrary.getInstance().isRefreshButton()){
                activeButton();
                MusicLibrary.getInstance().setRefreshButton(false);
            }
        });

        SceneHandler.getInstance().scaleTransition(btnAddLibraryToQueue);
        SceneHandler.getInstance().scaleTransition(btnAddSongToQueue);
        SceneHandler.getInstance().scaleTransition(btnRemove);
        SceneHandler.getInstance().scaleTransition(btnClearAll);
        SceneHandler.getInstance().scaleTransition(mbtAdd);

        //GESTIONE MULTILOADING
        mbtAdd.setDisable(SceneHandler.getInstance().getMediaLoadingInProgess());

        SceneHandler.getInstance().mediaLoadingInProgessProperty().addListener(observable -> {
            mbtAdd.setDisable(SceneHandler.getInstance().getMediaLoadingInProgess());
        });

        SceneHandler.getInstance().updateViewRequiredProperty().addListener(observable -> {
            if (SceneHandler.getInstance().isUpdateViewRequired() && SceneHandler.getInstance().getCurrentMidPane()=="music-library-view.fxml"){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        tableViewMusicLibrary.refresh();
                        Integer size = MusicLibrary.getInstance().getMusicLibrary().size();
                        lblSongsNum.setText(size.toString());
                        SceneHandler.getInstance().setUpdateViewRequired(false);
                    }
                });

            }
        });

        Player.getInstance().isRunningProperty().addListener(observable -> {
            if(Player.getInstance().getIsRunning())
                colorSelectedRow();

        });
        Integer size = MusicLibrary.getInstance().getMusicLibrary().size();
        lblSongsNum.setText(size.toString());
    }

    private void startToolTip() {
        // TODO: 07/06/
        btnAddLibraryToQueue.setTooltip(new Tooltip("Add entire library to queue"));
        btnRemove.setTooltip(new Tooltip("Delete selected media"));
        btnAddSongToQueue.setTooltip(new Tooltip("Add media to queue"));
        TextField.setTooltip(new Tooltip("Search for a media"));
        btnClearAll.setTooltip(new Tooltip("Clear all media(s)"));

    }
    public void activeButton(){
        if(MusicLibrary.getInstance().getMusicLibrary().size()>0) {
            TextField.setDisable(false);
            btnAddLibraryToQueue.setDisable(false);
            btnRemove.setDisable(false);
            btnClearAll.setDisable(false);
            btnAddSongToQueue.setDisable(false);
        }else{
            TextField.setDisable(true);
            btnAddLibraryToQueue.setDisable(true);
            btnRemove.setDisable(true);
            btnClearAll.setDisable(true);
            btnAddSongToQueue.setDisable(true);
        }
    }

    public void colorSelectedRow(){
        if(MusicLibrary.getInstance().getMusicLibrary().size() > 0 && Player.getInstance().getIsRunning()){
            MyMedia temp= PlayQueue.getInstance().getQueue().get(PlayQueue.getInstance().getCurrentMedia());
            tableViewMusicLibrary.getSelectionModel().select(temp);
            tableViewMusicLibrary.getSelectionModel().select(temp);
            tableViewMusicLibrary.scrollTo(temp);
        }
    }
}
