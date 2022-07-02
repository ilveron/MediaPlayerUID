package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.model.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import org.kordamp.ikonli.javafx.FontIcon;

public class PlaylistTemplateController {
    private  ObservableList<MyMedia> list;
    private  String name="";
    private String image="";
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
    void onPlayPlaylist(ActionEvent event) {
        if(list.size()>0) {
            if (Playlists.getInstance().getTypePlaylist() == name) {
                refresh();
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
                initList();
                System.out.println("setto a true (sono nell else)");
                Playlists.getInstance().setPlaying(true);
                Playlists.getInstance().setTypePlaylist(name);
            }
        }
    }
    @FXML
    void onAddToPlaylist(ActionEvent event) {
        DataExchangePlaylist.getInstance().setImage(image);
        DataExchangePlaylist.getInstance().setName(name);
        DataExchangePlaylist.getInstance().setList(list);
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
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    //gestisci dati da inviare
                }
            });
            //root.prefWidthProperty().bind(this.widthProperty());
        }catch(Exception ignoredException){}
        Playlists.getInstance().setUpdate(true);
    }

    public void init(ObservableList<MyMedia> myMedia,String image,String name) {
        // TODO: 02/07/2022 settare che non si resetta dopo lo switch di pagina , aggiornamento canzoni sbagliato
        //list= FXCollections.observableArrayList();

        if(Player.getInstance().getIsRunning()){
            System.out.println("dio1");Playlists.getInstance().setPlaying(true);}
        else {System.out.println("dio2");Playlists.getInstance().setPlaying(false);}



        list=myMedia;
        this.name=name;
        if(image=="")
            image="file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/iconaMusica.png";
        this.image=image;
        labelName.setText(name);
        imagePlaylist.setImage(new Image(image));
        tableViewPlaylist.setItems(myMedia);
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

        Playlists.getInstance().updateProperty().addListener(observable -> {
            if(Playlists.getInstance().getTypePlaylist()==name && Playlists.getInstance().isUpdate())
                refresh();
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

    private void initList(){
        for(int i=0;i<list.size();i++){
            if(i==0)
                PlayQueue.getInstance().generateNewQueue(list.get(i));
            else
                PlayQueue.getInstance().addFileToListFromOtherModel(list.get(i));
        }
    }

    private void refresh(){
        for(int i=0;i<list.size();i++){
            boolean e=false;
            for(int j=0;j<PlayQueue.getInstance().getQueue().size();j++)
                if(list.get(i)==PlayQueue.getInstance().getQueue().get(j))
                    e=true;
            if(!e)
                PlayQueue.getInstance().addFileToListFromOtherModel(list.get(i));
        }
        Playlists.getInstance().setUpdate(false);
    }

    private boolean exist(MyMedia myMedia){
        for(int i=0;i<list.size();i++){
            if(list.get(i)==myMedia)
                return true;
        }
        return false;
    }

    public void setDim(double size){
        anchorPanePlaylist.setPrefWidth(size);
    }

    public void  setButtonPlay(boolean visible){ButtonAzione.setVisible(visible);}




}
