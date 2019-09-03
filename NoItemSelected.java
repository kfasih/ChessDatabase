/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment4;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author kd
 */
public class NoItemSelected {
    public void show(Stage primaryStage) {

        //Buttons & Label
        HBox paneForButtons = new HBox(20);
        paneForButtons.setPadding(new Insets(5, 0, 0, 0));
        paneForButtons.setAlignment(Pos.CENTER);
        HBox paneForLabel = new HBox(20);
        paneForLabel.setAlignment(Pos.CENTER);
        Label informUser = new Label("Please select a game first");
        Button btOk = new Button("OK");
        paneForLabel.getChildren().addAll(informUser);
        paneForButtons.getChildren().addAll(btOk);
        
        GridPane pane = new GridPane();
        pane.add(paneForLabel, 0, 10);
        pane.add(paneForButtons, 0, 30);

        //Button Event Handling
        btOk.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        
        //Scene
        Scene scene = new Scene(pane, 175, 60);
        primaryStage.setTitle("Add to Record"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
}
