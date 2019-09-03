package model;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * This class models the game database for the chess database application. The
 * class serves as an aggregate for GameRecords and provides basic database
 * functionality. The implemented functions include load and write to file, add
 * game, edit game, search for games, and remove games.
 *
 * @author Patrick Ryan
 */
public class Database {

    // list of games
    private ArrayList<GameRecord> recordList;
    private SaveType type;
    private String path;

    // Default constructor
    public Database(SaveType type, String path) {
        this.type = type;
        this.path = path;
        recordList = new ArrayList<>();
    }

    // Setters and getters
    public void setType(SaveType type) {
        this.type = type;
    }

    public SaveType getType() {
        return type;
    }

    public ArrayList<GameRecord> getAllRecords() {
        return recordList;
    }
    
    public int getId(GameRecord record) {
        return recordList.indexOf(record);
    }

    /**
     * Add the record into the database
     *
     * @param record to be added
     */
    public void add(GameRecord record) {
        recordList.add(record);
    }

    /**
     *
     * Return a game record.
     *
     * @param id integer id corresponding to the desired game record
     * @return specified game record.
     * @throws IllegalArgumentException if an invalid id is entered.
     */
    public GameRecord getRecordById(int id) {
        if (id >= recordList.size()) {
            throw new IllegalArgumentException("Invalid ID for record access.");
        }

        return recordList.get(id);
    }

    /**
     * Edit a game record in the database.
     *
     * @param id integer id corresponding to the desired game record
     * @param editedFields fields that will be changed in the GameRecord.
     * @return the edited record
     * @throws IllegalArgumentException if an invalid id is entered.
     */
    public GameRecord editRecord(int id, EnumMap<RecordField, String> editedFields) {

        if (id >= recordList.size()) {
            throw new IllegalArgumentException("Invalid ID for record access.");
        }

        for (RecordField field : editedFields.keySet()) {
            getRecordById(id).setField(field, editedFields.get(field));
        }
        
        return getRecordById(id);
    }

    /**
     * Remove the element at id from the database.
     *
     * @param id the id of the element to be removed.
     * @throws IllegalArgumentException if the id > size
     */
    public void remove(int id) {
        if (id >= recordList.size()) {
            throw new IllegalArgumentException("Invalid removal ID");
        }
        recordList.remove(id);
    }
    
    /**
     * Remove the specified element from the database, if it exists.
     * @param record the element to be removed.
     */
    public void remove(GameRecord record) {
        recordList.remove(record);
    }

    // Fuzzy search methods.
    /**
     * Search the database for <text> across all game's <fields> and return all
     * games whose specified fields contain <text>.
     *
     * @param fields fields to search across
     * @param text text to match
     * @return list of matching game records
     */
    public ArrayList<GameRecord> search(ArrayList<RecordField> fields,
            String text) {

        return search(recordList, fields, text);
    }

    /**
     *
     * Search the given games list for <text> across all games <fields> and
     * return all that match.
     *
     * @param games games to search across
     * @param fields fields to search across
     * @param text text to match
     * @return list of matching game records
     */
    public ArrayList<GameRecord> search(ArrayList<GameRecord> games,
            ArrayList<RecordField> fields, String text) {

        ArrayList<GameRecord> results = new ArrayList<>();

        // search each game for a match across each specified field.
        // search each game for a match across each specified field.
        for (GameRecord record : games) {
            for (RecordField field : fields) {
                // Get the string we will search across.
                String contentString = record.getFields().get(field);
                contentString = contentString.toLowerCase();
                if (contentString.contains(text.toLowerCase())) {
                    // only add if the record is not in the list.
                    if (!results.contains(record)) {
                        results.add(record);
                    }
                }
            }
        }
        return results;
    }

    // Database save/load methods
    // Binary searlialized read/write
    /**
     * Write the database contents in binary format to a database file.
     *
     * @param path path to the database file.
     */
    private void writeSerialized(String path) {
        try (
                ObjectOutputStream output = new ObjectOutputStream(
                        new FileOutputStream(path, false));) {
            output.writeObject(recordList);
        } catch (Exception ex) {
            System.out.println("Error writing database to file");
        }
    }

    /**
     * Reads the database contents in binary format from a database file.
     *
     * @param path path to the database file.
     * @throws java.io.IOException if a write error occurrs while reading the
     * file
     */
    private boolean readSerialized(String path) {
        try (
                ObjectInputStream output = new ObjectInputStream(
                        new FileInputStream(path));) {
            recordList = (ArrayList<GameRecord>) output.readObject();
        } catch (Exception ex) {
            System.out.println("Error reading database from file");
            return false;
        }

        return true;
    }

    // CSV read/write
    /**
     * Attempt to load the database corresponding to the file located at path.
     * If this file cannot be opened, or an IO error occurs during reading,
     * false is returned by this method.
     *
     * @param path Path to CSV file.
     * @return true if the path could be loaded and read successfully. Otherwise
     * false.
     */
    private boolean readCSV(String path) {
        CSVFileReader reader = null;

        try {
            reader = new CSVFileReader(path);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find database");
            return false;
        }

        GameRecord game = null;
        do {
            try {
                game = reader.readRecord();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                return false;
            }

            if (game == null) {
                break;
            } else {
                recordList.add(game);
            }

        } while (true);
        return true;
    }

    /**
     * Write the current database state to a file.
     *
     * @param csvFile the path to the file to be written.
     */
    private void writeCSV(String csvFile) {
        try (BufferedWriter writer
                = new BufferedWriter(new FileWriter(csvFile, false))) {

            int id = 0;
            for (GameRecord record : recordList) {
                writer.write(record.toCSVRecord(id), 0, record.toCSVRecord(id).length());
                ++id;
            }
        } catch (IOException ex) {
            System.out.println("Could not write database.");
        }
    }

    // public wrappers
    /**
     * Write the database to path in the format of this.type
     *
     * @param path path to file to be written
     */
    public void writeDatabase() {
        if (type == SaveType.CSV) {
            writeCSV(path);
        } else {
            writeSerialized(path);
        }
    }

    /**
     * Read the database from path in the format of this.type
     *
     * @param path path to file to be read from
     * @return true if read was successful, false otherwise.
     */
    public boolean readDatabase() {
        if (type == SaveType.CSV) {
            return readCSV(path);
        } else {
            return readSerialized(path);
        }
    }
}
