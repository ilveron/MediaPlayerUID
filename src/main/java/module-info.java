module it.unical.sadstudents.mediaplayeruid {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens it.unical.sadstudents.mediaplayeruid to javafx.fxml;
    exports it.unical.sadstudents.mediaplayeruid;
}