package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.*;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class PlaylistSingleViewController {


    @FXML
    private AnchorPane AnchorPanAzione;
    @FXML
    private Button ButtonAzione;
    @FXML
    private HBox hBoxAzione;

    @FXML
    private FontIcon iconPlayPause;

    @FXML
    private TableColumn<MyMedia, String> album;

    @FXML
    private AnchorPane anchorPanePlaylist;

    @FXML
    private TableColumn<MyMedia, String> artist;

    @FXML
    private TableColumn<MyMedia, String> genre;

    @FXML
    private ImageView imagePlaylist;

    @FXML
    private Label labelName;

    @FXML
    private TableColumn<MyMedia, Double> length;

    @FXML
    private TableView<MyMedia> tableViewPlaylist;

    @FXML
    private TableColumn<MyMedia, String> title;

    @FXML
    private TableColumn<MyMedia, Integer> year;

    @FXML
    private VBox summaryVBox;

    @FXML
    private Label LabelTime;
    @FXML
    private Label LabelBrani;

    @FXML
    void onDeletePlaylist(ActionEvent event) {
        if(SceneHandler.getInstance().showConfirmationAlert("Delete the playlist '"+playlist.getName()+"' ?")) {
        //if(MyNotification.notifyConfirm("Confermi la tua scelta","Ok per confermare")){
            int index= PlaylistCollection.getInstance().getPlaylistWidthName(playlist.getName());
            PlaylistCollection.getInstance().deletePlaylist(index);
        }
    }

    @FXML
    void onChange(ActionEvent event) {
        SubStageHandler.getInstance().init("newPlaylist-view.fxml",400,300,"Create Playlist",false, playlist.getName());

        /*CreateNewPlaylist.getInstance().createPlaylist(playlist.getImage(), playlist.getName());
        playlist.setImage(CreateNewPlaylist.getInstance().getImage());
        playlist.setName(CreateNewPlaylist.getInstance().getName());

        labelName.setText(playlist.getName());
        imagePlaylist.setImage(new Image(playlist.getImage()));*/
    }

    @FXML
    void onPlayPlaylist(ActionEvent event) {
        if(!playlist.isPlaying()){
            playlist.setPlaying(true);
            playlist.playAll(false);
        }







        /*
        if(playlist.getMyList().size()>0) {
            if (PlaylistCollection.getInstance().getTypePlaylist() == playlist.getName()) {
                if(PlaylistCollection.getInstance().isPlaying()){
                    System.out.println("setto a false (sono nell if dentro if)");
                    PlaylistCollection.getInstance().setPlaying(false);
                    Player.getInstance().pauseMedia();
                }else{
                    System.out.println("setto a true (sono nell else dentro if)");
                    PlaylistCollection.getInstance().setPlaying(true);
                    Player.getInstance().playMedia();
                }
            } else {
                initListPlayQueue();
                System.out.println("setto a true (sono nell else)");
                PlaylistCollection.getInstance().setPlaying(true);
                PlaylistCollection.getInstance().setTypePlaylist(playlist.getName());
            }
        }*/
    }

    @FXML
    void onAddToPlaylist(ActionEvent event) {
        SubStageHandler.getInstance().init("addMediaToPlaylist.fxml",700,400,"Edit Playlist",true, playlist.getName());
        //PlaylistMedia.getInstance().changePlaylistMedia(playlist);
        setLabel();
    }

    Playlist playlist;
    private boolean PlayPause=false;
    private ContextMenuHandler contextMenuHandler;
    public void init(Playlist playlist) {
        this.playlist=playlist;
        // TODO: 02/07/2022 settare che non si resetta dopo lo switch di pagina , aggiornamento canzoni sbagliato
        //list= FXCollections.observableArrayList();
        setLabel();
        if(Player.getInstance().getIsRunning()){
            PlaylistCollection.getInstance().setPlaying(true);}
        else {
            PlaylistCollection.getInstance().setPlaying(false);}



        playlist.setImage("file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/iconaMusica.png");
        labelName.setText(playlist.getName());

        imagePlaylist.setImage(new Image(playlist.getImage()));
        tableViewPlaylist.setItems(playlist.getMyList());
        title.setCellValueFactory(new PropertyValueFactory<MyMedia, String>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<MyMedia, String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<MyMedia, String>("album"));
        genre.setCellValueFactory(new PropertyValueFactory<MyMedia, String>("genre"));
        year.setCellValueFactory(new PropertyValueFactory<MyMedia, Integer>("year"));
        length.setCellValueFactory(new PropertyValueFactory<MyMedia, Double>("length"));

        summaryVBox.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                //ContextMenuHandler contextMenuHandler = new ContextMenuHandler(null,playlist.getName(),"playlist");
                setOnContextMenuHandler(summaryVBox,contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY(),null,0);
            }
        });

        tableViewPlaylist.setRowFactory(tableView ->{
            final TableRow<MyMedia> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getButton().equals(MouseButton.SECONDARY)){
                    if(!row.isEmpty()){
                        MyMedia myMedia=row.getItem();
                        contextMenuHandler = new ContextMenuHandler(myMedia, playlist.getName(),"playlist",row.getIndex());
                        row.setContextMenu(contextMenuHandler);
                        row.getContextMenu();
                    }

                }
            });

            return row;
        });




        imagePlaylist.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if(!ButtonAzione.isVisible())
                setButtonPlay(true);
        });
        ButtonAzione.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if(ButtonAzione.isVisible())
                setButtonPlay(newValue);
        });
        /*
        Playlists.getInstance().updatePlayQueueProperty().addListener(observable -> {
            if(Playlists.getInstance().getTypePlaylist()== playlist.getName() && Playlists.getInstance().getUpdatePlayQueue()) {
                if(playlist.getMyList().size()>0)
                    refreshPlayQueue();
                else
                    initListPlayQueue();
            }
        });
        */
        //Playlists.getInstance().playingProperty().addListener(observable ->changeIcon());

        //Player.getInstance().isRunningProperty().addListener(observable -> Playlists.getInstance().setPlaying(Player.getInstance().getIsRunning()));

    }
    private void changeIcon(){
        System.out.println("cambio icona");
        if(PlaylistCollection.getInstance().isPlaying()) {
            iconPlayPause.setIconLiteral("fa-pause");
        }
        else {
            iconPlayPause.setIconLiteral("fa-play");
        }
    }

    private void initListPlayQueue(){
        for(int i=0;i<playlist.getMyList().size();i++){
            if(i==0)
                PlayQueue.getInstance().generateNewQueue(playlist.getMyList().get(i));
            else
                PlayQueue.getInstance().addFileToListFromOtherModel(playlist.getMyList().get(i));
        }

    }
    private void setLabel(){
        LabelBrani.setText("Brani: "+playlist.getSongs());
        LabelTime.setText("Time: "+playlist.getTotalDuration()); // TODO: 04/07/2022  da fare
    }



    private boolean exist(MyMedia myMedia){
        for(int i=0;i<playlist.getMyList().size();i++){
            if(playlist.getMyList().get(i)==myMedia)
                return true;
        }
        return false;
    }

    private int findPlaylist(){
        for(int pos = 0; pos< PlaylistCollection.getInstance().getPlayListsCollections().size(); pos++){
            if(PlaylistCollection.getInstance().getPlayListsCollections().get(pos).equals(playlist))
                return pos;
        }
        return -1;
    }

    public void setDim(double size){
        anchorPanePlaylist.setPrefWidth(size);
    }

    public void  setButtonPlay(boolean visible){ButtonAzione.setVisible(visible);}


    public void setOnContextMenuHandler(Node node, double x, double y, MyMedia myMedia,int row) {

        if(contextMenuHandler!=null && contextMenuHandler.isShowing())
            contextMenuHandler.hide();

        contextMenuHandler=new ContextMenuHandler(null, playlist.getName(), "playlist",row);
        contextMenuHandler.show(node,x,y);

    }
}
