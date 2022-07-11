package it.unical.sadstudents.mediaplayeruid.utils;

import it.unical.sadstudents.mediaplayeruid.model.MyMedia;

import java.io.File;
import java.util.List;

public interface ListedDataInterface {

    public void clearList();

    public void addFileToListFromOtherModel(MyMedia myMedia);

    public void addFilesToList(List<File> files);

}
