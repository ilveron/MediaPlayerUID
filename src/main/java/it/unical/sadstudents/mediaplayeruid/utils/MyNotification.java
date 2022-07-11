package it.unical.sadstudents.mediaplayeruid.utils;

import javafx.geometry.Pos;
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

    public static boolean notifyConfirm(String title,String text){
        // TODO: 07/07/2022  da vedere
        Notifications.create().title(title).text(text).position(Pos.CENTER).showConfirm();
        return true;
    }

}
