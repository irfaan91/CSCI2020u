package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Irfaan on 28/03/2016.
 */
public class Datasource {
    public static ObservableList<TestFile> getClientFiles() {
        ObservableList<TestFile> data1 = FXCollections.observableArrayList(
                new TestFile("test.txt"),
                new TestFile("contacts.txt"),
                new TestFile("to-do_list.txt"),
                new TestFile("untitled.txt")
        );
        return data1;
    }
    public static ObservableList<TestFile> getServerFiles() {
        ObservableList<TestFile> data2 = FXCollections.observableArrayList(
                new TestFile("server.txt"),
                new TestFile("content.txt")
        );
        return data2;
    }
}
