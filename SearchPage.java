/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment4;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Database;
import model.GameRecord;
import model.RecordField;

/**
 *
 * @author kd
 */
public class SearchPage {

    
    private Database db;
    private ObservableList<GameRecord> list;

    public SearchPage(Database db,
            ObservableList<GameRecord> list) {
        this.db = db;
        this.list = list;
    }
    
    /**
     * @param args the command line arguments
     */
    private TextField name = new TextField();
    private TextField elo = new TextField();
    private TextField length = new TextField();
    private TextField date = new TextField();
    private TextField site = new TextField();
    private Button search = new Button("Search");
    private Button clear = new Button("Clear");

    public void show(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(name, 1, 0);
        gridPane.add(new Label("ELO:"), 0, 1);
        gridPane.add(elo, 1, 1);
        gridPane.add(new Label("Length:"), 0, 2);
        gridPane.add(length, 1, 2);
        gridPane.add(new Label("Date:"), 0, 3);
        gridPane.add(date, 1, 3);
        gridPane.add(new Label("Location:"), 0, 4);
        gridPane.add(site, 1, 4);

        //Alignments
        gridPane.setAlignment(Pos.CENTER);
        name.setAlignment(Pos.BOTTOM_LEFT);
        elo.setAlignment(Pos.BOTTOM_LEFT);
        length.setAlignment(Pos.BOTTOM_LEFT);
        date.setAlignment(Pos.BOTTOM_LEFT);
        site.setAlignment(Pos.BOTTOM_LEFT);

        //Buttons
        HBox paneForButtons = new HBox(20);
        Button btSearch = new Button("Search");
        Button btCancel = new Button("Cancel");
        paneForButtons.getChildren().addAll(btSearch, btCancel);
        gridPane.add(paneForButtons, 1, 6);

        //Button Event Handling
        btCancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        btSearch.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                list.clear();
                list.addAll(search());
                primaryStage.close();
            }
        });

        //Scene
        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setTitle("Chess Search"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    public ArrayList<GameRecord> search() {
        ArrayList<RecordField> searchFields = new ArrayList<>();
        ArrayList<GameRecord> games = db.getAllRecords();

        if (!name.getText().isEmpty()) {
            String text = name.getText();
            searchFields.add(RecordField.WHITE_FIRST_NAME);
            searchFields.add(RecordField.WHITE_LAST_NAME);
            searchFields.add(RecordField.BLACK_FIRST_NAME);
            searchFields.add(RecordField.BLACK_LAST_NAME);
            games = db.search(games, searchFields, text);
            searchFields.clear();
        }
        if (!elo.getText().isEmpty()) {
            String text = elo.getText();
            searchFields.add(RecordField.WHITE_ELO);
            searchFields.add(RecordField.BLACK_ELO);
            games = db.search(games, searchFields, text);
            searchFields.clear();        
        }
        if (!length.getText().isEmpty()) {
            String text = length.getText();
            searchFields.add(RecordField.LENGTH);
            games = db.search(games, searchFields, text);
            searchFields.clear(); 
        }
        if (!date.getText().isEmpty()) {
            String text = date.getText();
            searchFields.add(RecordField.DATE);
            games = db.search(games, searchFields, text);
            searchFields.clear(); 
        }
        if (!site.getText().isEmpty()) {
            String text = site.getText();
            searchFields.add(RecordField.SITE);
            games = db.search(games, searchFields, text);
        }
        
        return games;    
    }
}
