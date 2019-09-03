/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment4;

import model.Database;
import model.GameRecord;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.SaveType;

/**
 *
 * @author kd
 */
public class MainPage extends Application {

    @Override
    public void start(Stage primaryStage) {

        //Setting up the ListView
        ListView<GameRecord> gameRecord = new ListView<GameRecord>();
        Database db = new Database(SaveType.BINARY, "database/binaryDatabase.dat");
        db.readDatabase();
        ArrayList<GameRecord> records = db.getAllRecords();
        ObservableList<GameRecord> items
                = FXCollections.observableArrayList(records);
        gameRecord.setItems(items);

        //Pane
        GridPane pane = new GridPane();

        //Display Database
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(gameRecord);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxWidth(Double.MAX_VALUE);
        vbox.setPrefWidth(1350);

        GridPane paneForRecords = new GridPane();
        paneForRecords.setHgap(5);
        paneForRecords.setVgap(5);

        //Adding Buttons
        HBox paneForButtons = new HBox(20);
        paneForButtons.setPadding(new Insets(5, 0, 0, 0));
        paneForButtons.setAlignment(Pos.CENTER);
        Button btAdd = new Button("Add");
        Button btSearch = new Button("Search");
        Button btDelete = new Button("Delete");
        Button btEdit = new Button("Edit");
        Button btClear = new Button("Clear Search");
        paneForButtons.getChildren().addAll(btSearch, btAdd, btDelete, btEdit, 
                btClear);

        pane.add(vbox, 0, 0);
        pane.add(paneForButtons, 0, 10);
        pane.setMaxWidth(Double.MAX_VALUE);

        //Button Event Handling
        btAdd.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                stage.setTitle("Add Page");
                AddPage addPage = new AddPage(db, items);
                addPage.show(stage);
            }
        });

        btSearch.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                stage.setTitle("Search Page");
                SearchPage searchPage = new SearchPage(db, items);
                searchPage.show(stage);
            }
        });

        btEdit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                boolean isSelected = !gameRecord.getSelectionModel().isEmpty();
                if (!isSelected) {
                    Stage stage = new Stage();
                    stage.setTitle("No Items Selected");
                    NoItemSelected noSelection = new NoItemSelected();
                    noSelection.show(stage);
                } else {
                    //Get Selected Item
                    GameRecord toEdit = gameRecord.getSelectionModel().
                            getSelectedItem();
                    Stage stage = new Stage();
                    stage.setTitle("Edit Page");
                    EditWindow editPage = new EditWindow(toEdit, db, items);
                    editPage.show(stage);
                }
            }
        });

        btDelete.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                boolean isSelected = !gameRecord.getSelectionModel().isEmpty();
                if (!isSelected) {
                    Stage stage = new Stage();
                    stage.setTitle("No Items Selected");
                    NoItemSelected noSelection = new NoItemSelected();
                    noSelection.show(stage);
                } else {
                    //Get Selected Item
                    GameRecord toRemove = gameRecord.getSelectionModel().
                            getSelectedItem();
                    DeleteWindow dw = new DeleteWindow(toRemove, db, items);
                    Stage stage = new Stage();
                    stage.setTitle("Delete Page");
                    dw.show(stage);
                }
            }
        });
        
        btClear.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                items.clear();
                items.addAll(db.getAllRecords());
            }
        });

        //Stage
        Scene scene = new Scene(pane, 1360, 475);
        primaryStage.setTitle("Main Page"); // Set title
        primaryStage.setScene(scene); // Place the scene on the stage
        primaryStage.show(); // Display the stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}
