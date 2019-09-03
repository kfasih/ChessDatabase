/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment4;

import java.util.Collection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Database;
import model.GameRecord;

/**
 *
 * @author kd
 */
public class DeleteWindow {
    
    private Database db;
    private ObservableList<GameRecord> list;
    private GameRecord record;
    
    public DeleteWindow(GameRecord record, Database db, 
            ObservableList<GameRecord> list) {
        this.db = db;
        this.list = list;
        this.record = record;
    }

    public void show(Stage primaryStage) {

        //Buttons & Label
        HBox paneForButtons = new HBox(20);
        Label confirmRequest = new Label("Are You Sure?");
        Button btConfirm = new Button("Confirm");
        Button btCancel = new Button("Cancel");
        paneForButtons.getChildren().addAll(confirmRequest, btConfirm,
                btCancel);

        //Button Event Handling
        btCancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        btConfirm.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                delete();
                primaryStage.close();
                db.writeDatabase();
            }
        });

        //Adding to the Stage
        Scene scene = new Scene(paneForButtons, 300, 30);
        primaryStage.setTitle("Delete From Records"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

    }
    
    public void delete () {
        db.remove(record);
        list.remove(record);
    }
}
