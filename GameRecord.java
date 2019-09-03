package model;

import java.io.Serializable;
import java.util.EnumMap;

/**
 * This class models a single game record in the database. Each GameRecord
 * contains all information pertaining to an individual game. This information
 * includes player names, Elo, the date of the game, and so on. All data can be
 * accessed in bulk with getFields(), or individually with getFieldData(). The
 * class also has a toCSVRecord() method to be used by the database for writing
 * to a file.
 * 
 * @author Patrick Ryan
 */
public class GameRecord implements Serializable {

    // Map containing field key/value pairs.
    private final EnumMap<RecordField, String> fields;

    // Single constructor
    public GameRecord(EnumMap<RecordField, String> fields) {
        this.fields = fields;
    }

    // Bulk field retrieval
    public EnumMap<RecordField, String> getFields() {
        return fields;
    }

    public void setField(RecordField field, String value) {
        fields.put(field, value);
    }
    
    // Single field retrieval
    public String getFieldData(RecordField field) {
        return fields.get(field);
    }

    /**
     * 
     * This class prepares a string representation of the game record suitable
     * for writing to a CSV file.
     * 
     * @param id database integer id for this game.
     * @return String representation of the record to be written to a csv file.
     */
    public String toCSVRecord(int id) {
        String str = "\"" + id + "\",";
        for (String s : fields.values()) {
            str = str.concat("\"" + s + "\",");
        }

        return str.substring(0, str.length() - 1) + "\n";
    }

    /**
     * Provide a human readable string representation of the game record.
     * @return String representation of instance.
     */
    @Override
    public String toString() {
        int[] pad_lengths = {20, 20, 20, 20, 4, 4, 3, 2, 2, 4, 55, 10, 28, 15};
        
        String formatString = "";
        // construct format string.
        for (int x : pad_lengths) {
            formatString += "%" + x + "s, ";
        }
        
        Object[] fieldsList = fields.values().toArray();
        
        String str = String.format(formatString, fieldsList);

        return str.substring(0, str.length() - 2);
    }
    
    /**
     * Equality function for GameRecord.
     * 
     * @param other GameRecord to compare against.
     * @return true if all fields are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        
        if (!(other instanceof GameRecord)) {
            return false;
        }
        
        GameRecord otherGame = (GameRecord)other;
        
        for (RecordField field : fields.keySet()) {
            // compare the elements of each field. If they are different, short
            // the search and return false.
            if (!fields.get(field).equals(otherGame.fields.get(field))) {
                return false;
            }
        }
        return true;
    }
}
