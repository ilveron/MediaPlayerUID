module it.unical.sadstudents.mediaplayeruid {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens it.unical.sadstudents.mediaplayeruid to javafx.fxml;
    exports it.unical.sadstudents.mediaplayeruid;
    exports it.unical.sadstudents.mediaplayeruid.controller;
    opens it.unical.sadstudents.mediaplayeruid.controller to javafx.fxml;
}