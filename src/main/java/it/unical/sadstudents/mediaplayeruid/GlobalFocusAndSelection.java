package it.unical.sadstudents.mediaplayeruid;

import it.unical.sadstudents.mediaplayeruid.model.Player;
import javafx.scene.control.Button;

public class GlobalFocusAndSelection {
    private String previousMenu ="";
    private String currentMenu = "home";

    //SINGLETON
    private static GlobalFocusAndSelection instance = null;
    private GlobalFocusAndSelection() { }
    public static GlobalFocusAndSelection getInstance(){
        if (instance==null)
            instance = new GlobalFocusAndSelection();
        return instance;
    }
    //END SINGLETON

    public void setAllNameButton(String current){
        previousMenu=currentMenu;
        currentMenu=current;
    }

    public String getPreviousMenu() {
        return previousMenu;
    }

    public void setStyle(Button previous,Button current){
        if(previous!=null)
            previous.getStyleClass().remove("focusedToolBarButton");

        current.getStyleClass().add("focusedToolBarButton");

    }
}
