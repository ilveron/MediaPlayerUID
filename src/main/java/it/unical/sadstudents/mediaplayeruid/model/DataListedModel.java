package it.unical.sadstudents.mediaplayeruid.model;

import java.io.File;
import java.util.ArrayList;

public interface DataListedModel {

    public void clearList();

    public void addFileToList(File file);

    public void addFolderToList(ArrayList<File> files);
}
