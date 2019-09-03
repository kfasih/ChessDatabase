/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment4;

import java.util.EnumMap;
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
public class EditWindow {

    /**
     * @param args the command line arguments
     */
    
    private Database db;
    private ObservableList<GameRecord> list;
    private GameRecord record;
    
    public EditWindow(GameRecord record, Database db, 
            ObservableList<GameRecord> list) {
        this.db = db;
        this.list = list;
        this.record = record;
    }
    
    
    private TextField whiteFirstName = new TextField();
    private TextField whiteLastName = new TextField();
    private TextField blackFirstName = new TextField();
    private TextField blackLastName = new TextField();
    private TextField whiteElo = new TextField();
    private TextField blackElo = new TextField();
    private TextField result = new TextField();
    private TextField length = new TextField();
    private TextField ecoCode = new TextField();
    private TextField openingName = new TextField();
    private TextField date = new TextField();
    private TextField numVariations = new TextField();
    private TextField site = new TextField();
    private TextField pgnPathName = new TextField();
    private Button clear = new Button("Clear");

    public void show(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(new Label("What would you like to edit?"), 0, 0);
        gridPane.add(new Label("White First Name:"), 0, 1);
        gridPane.add(whiteFirstName, 1, 1);
        gridPane.add(new Label("White Last Name:"), 0, 2);
        gridPane.add(whiteLastName, 1, 2);
        gridPane.add(new Label("Black First Name:"), 0, 3);
        gridPane.add(blackFirstName, 1, 3);
        gridPane.add(new Label("Black Last Name:"), 0, 4);
        gridPane.add(blackLastName, 1, 4);
        gridPane.add(new Label("White ELO:"), 0, 5);
        gridPane.add(whiteElo, 1, 5);
        gridPane.add(new Label("Black ELO:"), 0, 6);
        gridPane.add(blackElo, 1, 6);
        gridPane.add(new Label("Result:"), 0, 7);
        gridPane.add(result, 1, 7);
        gridPane.add(new Label("Length:"), 0, 8);
        gridPane.add(length, 1, 8);
        gridPane.add(new Label("ECO Code:"), 0, 9);
        gridPane.add(ecoCode, 1, 9);
        gridPane.add(new Label("Opening Name:"), 0, 10);
        gridPane.add(openingName, 1, 10);
        gridPane.add(new Label("Number of Variations:"), 0, 11);
        gridPane.add(numVariations, 1, 11);
        gridPane.add(new Label("Date:"), 0, 12);
        gridPane.add(date, 1, 12);
        gridPane.add(new Label("Location:"), 0, 13);
        gridPane.add(site, 1, 13);
        gridPane.add(new Label("PGN Path Name:"), 0, 14);
        gridPane.add(pgnPathName, 1, 14);

        //Alignments
        gridPane.setAlignment(Pos.CENTER);
        whiteFirstName.setAlignment(Pos.BOTTOM_LEFT);
        whiteLastName.setAlignment(Pos.BOTTOM_LEFT);
        blackFirstName.setAlignment(Pos.BOTTOM_LEFT);
        blackLastName.setAlignment(Pos.BOTTOM_LEFT);
        whiteElo.setAlignment(Pos.BOTTOM_LEFT);
        blackElo.setAlignment(Pos.BOTTOM_LEFT);
        result.setAlignment(Pos.BOTTOM_LEFT);
        length.setAlignment(Pos.BOTTOM_LEFT);
        ecoCode.setAlignment(Pos.BOTTOM_LEFT);
        openingName.setAlignment(Pos.BOTTOM_LEFT);
        date.setAlignment(Pos.BOTTOM_LEFT);
        numVariations.setAlignment(Pos.BOTTOM_LEFT);
        site.setAlignment(Pos.BOTTOM_LEFT);
        pgnPathName.setAlignment(Pos.BOTTOM_LEFT);

        //Buttons
        HBox paneForButtons = new HBox(20);
        Button btEdit = new Button("Edit");
        Button btCancel = new Button("Cancel");
        paneForButtons.getChildren().addAll(btEdit, btCancel);
        gridPane.add(paneForButtons, 1, 15);

        
        //Button Event Handling
        btCancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        
        btEdit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                edit();
                primaryStage.close();
                db.writeDatabase();
            }
        });

        //Scene
        Scene scene = new Scene(gridPane, 400, 550);
        primaryStage.setTitle("Edit Record"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
    
    public void edit(){
        EnumMap<RecordField, String> editedFields = new EnumMap<>(RecordField.class);
        
        if (!whiteFirstName.getText().isEmpty())
            editedFields.put(RecordField.WHITE_FIRST_NAME, whiteFirstName.
                    getText());
        if (!whiteLastName.getText().isEmpty())
            editedFields.put(RecordField.WHITE_LAST_NAME, whiteLastName.
                    getText());
        if (!blackFirstName.getText().isEmpty())
            editedFields.put(RecordField.BLACK_FIRST_NAME, blackFirstName.
                    getText());
        if (!blackLastName.getText().isEmpty())
            editedFields.put(RecordField.BLACK_LAST_NAME, blackLastName.
                    getText());
        if (!whiteElo.getText().isEmpty())
            editedFields.put(RecordField.WHITE_ELO, whiteElo.getText());
        if (!blackElo.getText().isEmpty())
            editedFields.put(RecordField.BLACK_ELO, blackElo.getText());
        if (!result.getText().isEmpty())
            editedFields.put(RecordField.RESULT, result.getText());
        if (!length.getText().isEmpty())
            editedFields.put(RecordField.LENGTH, length.getText());
        if (!ecoCode.getText().isEmpty())
            editedFields.put(RecordField.ECO_CODE, ecoCode.getText());
        if (!openingName.getText().isEmpty())
            editedFields.put(RecordField.OPENING_NAME, openingName.getText());
        if (!date.getText().isEmpty())
            editedFields.put(RecordField.DATE, date.getText());
        if (!numVariations.getText().isEmpty())
            editedFields.put(RecordField.NUMBER_OF_VARIATIONS, numVariations.
                    getText());
        if (!site.getText().isEmpty())
            editedFields.put(RecordField.SITE, site.getText());
        if (!pgnPathName.getText().isEmpty())
            editedFields.put(RecordField.PGN_FILE_PATH, pgnPathName.getText());
        
        GameRecord editedRecord = db.editRecord(db.getId(record), editedFields);
        list.set(list.indexOf(record), editedRecord);   
    }
    
}
