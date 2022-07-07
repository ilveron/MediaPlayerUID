package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

public class PlaylistTemplateController {
    Playlist playlist;
    private boolean PlayPause=false;

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
    private Label LabelTime;
    @FXML
    private Label LabelBrani;

    @FXML
    void onDeletePlaylist(ActionEvent event) {
        if(SceneHandler.getInstance().showConfirmationAlert("Delete the playlist '"+playlist.getName()+"' ?")) {
            playlist.getMyList().clear();
            Playlists.getInstance().setDelete(findPlaylist());
        }
    }

    @FXML
    void onChange(ActionEvent event) {
        CreateNewPlaylist.getInstance().createPlaylist(playlist.getImage(), playlist.getName());
        playlist.setImage(CreateNewPlaylist.getInstance().getImage());
        playlist.setName(CreateNewPlaylist.getInstance().getName());

        labelName.setText(playlist.getName());
        imagePlaylist.setImage(new Image(playlist.getImage()));
    }

    @FXML
    void onPlayPlaylist(ActionEvent event) {
        if(playlist.getMyList().size()>0) {
            if (Playlists.getInstance().getTypePlaylist() == playlist.getName()) {
                refreshPlayQueue();
                if(Playlists.getInstance().isPlaying()){
                    System.out.println("setto a false (sono nell if dentro if)");
                    Playlists.getInstance().setPlaying(false);
                    Player.getInstance().pauseMedia();
                }else{
                    System.out.println("setto a true (sono nell else dentro if)");
                    Playlists.getInstance().setPlaying(true);
                    Player.getInstance().playMedia();
                }
            } else {
                initListPlayQueue();
                System.out.println("setto a true (sono nell else)");
                Playlists.getInstance().setPlaying(true);
                Playlists.getInstance().setTypePlaylist(playlist.getName());
            }
        }
    }
    @FXML
    void onAddToPlaylist(ActionEvent event) {
        DataExchangePlaylist.getInstance().setPlaylist(playlist);
        //DataExchangePlaylist.getInstance().setImage(playlist.getImage());
        //DataExchangePlaylist.getInstance().setName(playlist.getName());
        //DataExchangePlaylist.getInstance().setList(playlist.getMyList());
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("addMediaToPlaylist.fxml"));
        try{
           //Scene scene=new Scene(new AddMediaToPlaylist())
            Scene scene= new Scene(loader.load(),700,400);
            Stage stage=new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Create Playlist");
            stage.setMinHeight(700);
            stage.setMinWidth(400);
            stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("image/logoMediaPlayerUID-48x48.png")));
            //scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/style.css")).toExternalForm());
            //scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/"+ Settings.theme+".css")).toExternalForm());
            stage.setScene(scene);
            stage.showAndWait();
            //root.prefWidthProperty().bind(this.widthProperty());
        }catch(Exception ignoredException){ignoredException.printStackTrace(); return;}
        setLabel();
        if(Playlists.getInstance().isPlaying()&&Playlists.getInstance().getTypePlaylist()== playlist.getName()) {
            Playlists.getInstance().setUpdatePlayQueue(true);
        }
    }

    public void init(Playlist playlist) {
        this.playlist=playlist;
        // TODO: 02/07/2022 settare che non si resetta dopo lo switch di pagina , aggiornamento canzoni sbagliato
        //list= FXCollections.observableArrayList();
        setLabel();
        if(Player.getInstance().getIsRunning()){
            Playlists.getInstance().setPlaying(true);}
        else {Playlists.getInstance().setPlaying(false);}


        if(playlist.getImage()=="")
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

        imagePlaylist.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if(!ButtonAzione.isVisible())
                setButtonPlay(true);
        });
        ButtonAzione.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if(ButtonAzione.isVisible())
                setButtonPlay(newValue);
        });

        Playlists.getInstance().updatePlayQueueProperty().addListener(observable -> {
            if(Playlists.getInstance().getTypePlaylist()== playlist.getName() && Playlists.getInstance().getUpdatePlayQueue()) {
                if(playlist.getMyList().size()>0)
                    refreshPlayQueue();
                else
                    initListPlayQueue();
            }
        });

        Playlists.getInstance().playingProperty().addListener(observable ->changeIcon());

        Player.getInstance().isRunningProperty().addListener(observable -> Playlists.getInstance().setPlaying(Player.getInstance().getIsRunning()));

    }
    private void changeIcon(){
        System.out.println("cambio icona");
        if(Playlists.getInstance().isPlaying()) {
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

    private void refreshPlayQueue(){
        for(int i=0;i<playlist.getMyList().size();i++){
            boolean e=false;
            for(int j=0;j<PlayQueue.getInstance().getQueue().size();j++)
                if(playlist.getMyList().get(i)==PlayQueue.getInstance().getQueue().get(j))
                    e=true;
            if(!e)
                PlayQueue.getInstance().addFileToListFromOtherModel(playlist.getMyList().get(i));
        }
        Playlists.getInstance().setUpdatePlayQueue(false);
    }

    private boolean exist(MyMedia myMedia){
        for(int i=0;i<playlist.getMyList().size();i++){
            if(playlist.getMyList().get(i)==myMedia)
                return true;
        }
        return false;
    }

    private int findPlaylist(){
        for(int pos=0;pos<Playlists.getInstance().getPlayListsCollections().size();pos++){
            if(Playlists.getInstance().getPlayListsCollections().get(pos).equals(playlist))
                return pos;
        }
        return -1;
    }

    public void setDim(double size){
        anchorPanePlaylist.setPrefWidth(size);
    }

    public void  setButtonPlay(boolean visible){ButtonAzione.setVisible(visible);}




}
