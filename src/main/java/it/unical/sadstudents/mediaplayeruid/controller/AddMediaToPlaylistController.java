package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.SubStageHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddMediaToPlaylistController implements Initializable {

    private ArrayList<Integer> PosizioniSelezionate;
    private String tabSelezionata="MusicLibrary";

    @FXML
    private TableColumn<MyMedia,String> title, artist, album, genre;
    @FXML
    private TableColumn<MyMedia, Integer> year;
    @FXML
    private TableColumn<MyMedia, Double> length;


    @FXML
    private Button buttonMusicLibrary;


    @FXML
    private Button buttonVideoLibrary;

    @FXML
    private Button ButtonAddToPlayList;


    @FXML
    private ImageView imageMedia;

    @FXML
    private Label labelTitle;


    @FXML
    private TableView<MyMedia> tableView;

    @FXML
    private TextField textField;



    @FXML
    void onMusicLibrary(ActionEvent event) {
        reset();
        tabSelezionata="MusicLibrary";
        tableView.setItems(MusicLibrary.getInstance().getMusicLibrary());
    }


    @FXML
    void onVideoLibrary(ActionEvent event) {
        reset();
        tabSelezionata="VideoLibrary";
        tableView.setItems(VideoLibrary.getInstance().getVideoLibrary());
    }

    @FXML
    void onAddToPlaylist(ActionEvent event) {
        int index = PlaylistCollection.getInstance().getPlaylistWidthName(SubStageHandler.getInstance().getPlaylistName());
        for(int i=0;i<PosizioniSelezionate.size();i++){

            PlaylistCollection.getInstance().getPlayListsCollections().get(index).addMedia(tableView.getItems().get(PosizioniSelezionate.get(i)));
                //DataExchangePlaylist.getInstance().addPlaylist(tableView.getItems().get(PosizioniSelezionate.get(i)));
        }
        PlaylistCollection.getInstance().setUpdatePlayQueue(true);
        //Playlists.getInstance().setTypePlaylist();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    private int indexPlaylist;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PosizioniSelezionate=new ArrayList<Integer>(); //se pos >0  allora il pulsante diventa cliccabile
        //start tooltip
        setButtonAdd(true);
        //String image= DataExchangePlaylist.getInstance().getImage();
        indexPlaylist = PlaylistCollection.getInstance().returnPlaylist(SubStageHandler.getInstance().getPlaylistName());
        System.out.println(indexPlaylist);
        String image = PlaylistCollection.getInstance().getPlayListsCollections().get(indexPlaylist).getImage();
        //String image= PlaylistMedia.getInstance().getPlaylist().getImage();
        if(image!=null&&image!="")
            imageMedia.setImage(new Image(image));
        //labelTitle.setText(DataExchangePlaylist.getInstance().getName());
        labelTitle.setText(SubStageHandler.getInstance().getPlaylistName());

        tableView.setItems(MusicLibrary.getInstance().getMusicLibrary());
        title.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("album"));
        genre.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("genre"));
        year.setCellValueFactory(new PropertyValueFactory<MyMedia,Integer>("year"));
        length.setCellValueFactory(new PropertyValueFactory<MyMedia,Double>("length"));


        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(tabSelezionata=="MusicLibrary")
                    tableView.setItems(SearchForFile.getInstance().getSearch(newValue, MusicLibrary.getInstance().getMusicLibrary()));
                else
                    tableView.setItems(SearchForFile.getInstance().getSearch(newValue, VideoLibrary.getInstance().getVideoLibrary()));
            }
        });



        tableView.setRowFactory(tableView ->{
            final TableRow<MyMedia> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                //if(event.getClickCount() > 1 && !row.isEmpty()){
                //    DataExchangePlaylist.getInstance().addPlaylist(row.getItem());
                //}else
                if(event.getClickCount()==1 && !row.isEmpty()){
                    int index=presente(row.getIndex());
                    if(index!=-1){
                        row.setStyle(""); // TODO: 02/07/2022  vedere che colore mettere
                        row.setFocusTraversable(false);
                        PosizioniSelezionate.remove(index);
                    }else {
                        setButtonAdd(false);
                        row.setFocusTraversable(false);
                        PosizioniSelezionate.add(row.getIndex());
                        row.setStyle("-fx-background-color: RED;");
                    }

                }else if(row.isEmpty()){
                    System.out.println("clear");
                    reset();
                    row.setFocusTraversable(false);
                    PosizioniSelezionate.clear();
                    setButtonAdd(true);

                }

            });
            return row;
        });
    }
    public void setButtonAdd(boolean enable){
        ButtonAddToPlayList.setDisable(enable);
    }

    public int presente(int index){
        for(int i=0;i<PosizioniSelezionate.size();i++) {
            if (PosizioniSelezionate.get(i) == index)
                return i;
        }
        return -1;
    }


    public void reset(){
        // TODO: 02/07/2022  non funziona il reset 
        for(int i=0;i<PosizioniSelezionate.size();i++){
            TableRow<MyMedia> row=tableView.getRowFactory().call(tableView);
            row.setStyle("-fx-background-color: white;");
        }
    }
    /*
    public void setData(ObservableList<MyMedia> list,String image, String title){
        System.out.println("ok ok");
        imageMedia.setImage(new Image(image));
        labelTitle.setText(title);
    }*/

}

