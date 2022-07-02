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

public class PlaylistTemplateController {
    private  ObservableList<MyMedia> list;
    private  String name="";
    private String image="";

    @FXML
    private AnchorPane AnchorPanAzione;
    @FXML
    private Button ButtonAzione;
    @FXML
    private HBox hBoxAzione;

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

    }
    @FXML
    void onAddToPlaylist(ActionEvent event) {
        DataExchangePlaylist.getInstance().setImage(image);
        DataExchangePlaylist.getInstance().setName(name);
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
    }

    public void init(ObservableList<MyMedia> myMedia,String image,String name) {
        //list= FXCollections.observableArrayList();
        list=myMedia;
        this.name=name;
        // TODO: 03/06/2022 CARICAMENTO DA DATABASE
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
                setButton(true);
        });
        ButtonAzione.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if(ButtonAzione.isVisible())
                setButton(newValue);
        });
    }
    public void setDim(double size){
        anchorPanePlaylist.setPrefWidth(size);
    }

    public void  setButton(boolean visible){
        ButtonAzione.setVisible(visible);
    }


}
