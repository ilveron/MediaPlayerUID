package it.unical.sadstudents.mediaplayeruid.thread;

import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.print.attribute.standard.DialogOwner;
import javax.swing.text.Position;

public class MyNotification {
    public MyNotification instance = null;

    public MyNotification getInstance() {
        if(instance != null)
            instance = new MyNotification();
        return instance;
    }

    private MyNotification() { }

    public static void notifyInfo(String title, String text, Integer seconds){
        Notifications.create().title(title).text(text).hideAfter(Duration.seconds(seconds)).showInformation();
    }

    public static void notifyGeneric(String title, String text, Integer seconds){
        Notifications.create().title(title).text(text).hideAfter(Duration.seconds(seconds)).show();
    }

    public static void notifyError(String title, String text, Integer seconds){
        Notifications.create().title(title).text(text).hideAfter(Duration.seconds(seconds)).showError();
    }

    public static void notifyWarning(String title, String text, Integer seconds){
        Notifications.create().title(title).text(text).hideAfter(Duration.seconds(seconds)).showWarning();
    }

    public static boolean notifyConfirm(String title,String text){
        // TODO: 07/07/2022  da vedere
        Notifications.create().title(title).text(text).position(Pos.CENTER).showConfirm();
        return true;
    }
    //A questa funzione potremmo passare l'immagine da mostrare all'interno della modifica
    //public static void notifyCustom(String title, String text, Integer seconds)
}
