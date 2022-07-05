package it.unical.sadstudents.mediaplayeruid.thread;

import javafx.util.Duration;
import org.controlsfx.control.Notifications;

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

    //A questa funzione potremmo passare l'immagine da mostrare all'interno della modifica
    //public static void notifyCustom(String title, String text, Integer seconds)
}
