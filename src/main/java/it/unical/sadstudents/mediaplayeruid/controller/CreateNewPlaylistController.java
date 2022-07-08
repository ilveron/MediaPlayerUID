package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.view.CreateNewPlaylist;
import it.unical.sadstudents.mediaplayeruid.model.DatabaseManager;
import it.unical.sadstudents.mediaplayeruid.model.Playlists;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewPlaylistController implements Initializable {
    private String previousName="";

    @FXML
    private Button ButtonImageChange;

    @FXML
    private Button ButtonSave;
    @FXML
    private Label labelErrore;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField textTitle;

    @FXML
    void onImageChange(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file to add");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image File","*.png","*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        imageView.setImage(new Image(fileChooser.showOpenDialog(new Stage()).getPath()));
    }

    @FXML
    void onSave(ActionEvent event) {
        // TODO: 04/07/2022 non far inserire caratteri speciali?
        String text=textTitle.getText().trim();
        if(findName(text)) {
            CreateNewPlaylist.getInstance().setName(text);
            CreateNewPlaylist.getInstance().setImage(imageView.getImage().getUrl());
            DatabaseManager.getInstance().changePlaylist(text,previousName,imageView.getImage().getUrl());
            ((Node)(event.getSource())).getScene().getWindow().hide();
            return ;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String image=CreateNewPlaylist.getInstance().getImage();
        //if(image=="") System.out.println("->"+image);
        imageView.setImage(new Image(image));
        previousName=CreateNewPlaylist.getInstance().getName();
        textTitle.setText(previousName);
        textTitle.setFocusTraversable(false);

        imageView.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if(!ButtonImageChange.isVisible())
                setButtonChange(newValue);
        });

        ButtonImageChange.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if(ButtonImageChange.isVisible())
                setButtonChange(newValue);
        });

        textTitle.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if(!findName(newValue.trim())){ error();}
                else {labelErrore.setVisible(false);}
            }
        });

    }

    private void error(){
        labelErrore.setText("Playlist name already in use");
        labelErrore.setVisible(true);
    }

    private boolean findName(String name){

        // TODO: 04/07/2022  utilizzare regex per i spazi vuoti 
        for(int pos=0;pos<Playlists.getInstance().getPlayListsCollections().size();pos++){
            if(Playlists.getInstance().getPlayListsCollections().get(pos).getName().equals(name)&&!previousName.equals(name)) {
                error();
                return false;
            }
        }
        return true;
    }

    private void setButtonChange(boolean visible){
        ButtonImageChange.setVisible(visible);
    }
}

