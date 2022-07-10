module it.unical.sadstudents.mediaplayeruid {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires javafx.media;
    requires java.desktop;


    opens it.unical.sadstudents.mediaplayeruid to javafx.fxml;
    exports it.unical.sadstudents.mediaplayeruid;
    exports it.unical.sadstudents.mediaplayeruid.controller;
    opens it.unical.sadstudents.mediaplayeruid.controller to javafx.fxml;
    exports it.unical.sadstudents.mediaplayeruid.model;
    opens it.unical.sadstudents.mediaplayeruid.model to javafx.fxml;
    exports it.unical.sadstudents.mediaplayeruid.view;
    opens it.unical.sadstudents.mediaplayeruid.view to javafx.fxml;
    exports it.unical.sadstudents.mediaplayeruid.utils;
    opens it.unical.sadstudents.mediaplayeruid.utils to javafx.fxml;
}