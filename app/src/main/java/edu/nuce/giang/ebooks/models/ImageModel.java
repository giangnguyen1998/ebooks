package edu.nuce.giang.ebooks.models;

import java.io.Serializable;
import java.util.List;

public class ImageModel implements Serializable {

    private String strFolder;
    private List<String> allImagePath;

    public String getStrFolder() {
        return strFolder;
    }

    public void setStrFolder(String strFolder) {
        this.strFolder = strFolder;
    }

    public List<String> getAllImagePath() {
        return allImagePath;
    }

    public void setAllImagePath(List<String> allImagePath) {
        this.allImagePath = allImagePath;
    }
}
