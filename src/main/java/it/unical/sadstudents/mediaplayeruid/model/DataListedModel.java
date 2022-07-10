package it.unical.sadstudents.mediaplayeruid.model;

import java.io.File;
import java.util.List;

public interface DataListedModel {

    public void clearList();

    public void addFileToListFromOtherModel(MyMedia myMedia);

    public void addFilesToList(List<File> files);

}
