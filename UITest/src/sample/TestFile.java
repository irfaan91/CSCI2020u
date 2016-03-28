package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Irfaan on 28/03/2016.
 */
public class TestFile {
    private String fileName;

    public TestFile(String fName) {
        this.fileName = fName;
    }

    public String getFileName() {return fileName;}
    public void setFileName(String fName) {fileName = fName;}
}
