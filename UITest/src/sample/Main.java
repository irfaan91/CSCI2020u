package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;

public class Main extends Application {
    private BorderPane layout;
    private TableView<TestFile> table1 = new TableView();;
    private TableView<TestFile> table2 = new TableView();;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("File Transfer Client");

        //SPLIT PANE CONFIG
        SplitPane splitPane1 = new SplitPane();
        splitPane1.setPrefSize(500, 200);

        //TABLE PROPERTIES
        table1.setItems(Datasource.getClientFiles());
        table2.setItems(Datasource.getServerFiles());

        TableColumn clientColumn = null;
        clientColumn = new TableColumn("Client Content");
        clientColumn.setMinWidth(300);
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("FileName"));

        TableColumn serverColumn = null;
        serverColumn = new TableColumn("Server Content");
        serverColumn.setMinWidth(300);
        serverColumn.setCellValueFactory(new PropertyValueFactory<>("FileName"));

        //POPULATING TABLES
        table1.getColumns().add(clientColumn);
        table2.getColumns().add(serverColumn);

        //ADDING TABLE TO SPLIT PANE
        splitPane1.getItems().addAll(table1, table2);

        //BUTTON FUNCTIONALITY
        GridPane functionArea = new GridPane();
        Button up = new Button("Upload");
        Button down = new Button("Download");
        up.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent upload) {
                //BUTTON FUNCTIONALITY
                //UPLOADS SELECTED FILE
                TestFile selected = table1.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    table2.getItems().add(selected);
                }
            }
        });

        down.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent download) {
                //BUTTON FUNCTIONALITY
                //DOWNLOAD SELECTED FILE
                TestFile selected = table2.getSelectionModel().getSelectedItem();
                if (selected != null){
                    table1.getItems().add(selected);
                }
            }
        });

        functionArea.add(up, 1, 1);
        functionArea.add(down, 3, 1);

        layout = new BorderPane();
        layout.setTop(functionArea);
        layout.setCenter(splitPane1);

        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {launch(args);}
}
