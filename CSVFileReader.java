package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * This class provides all functionality required for reading and writing CSV
 * data files for the database system. The parser for the reader does NOT allow
 * for nested commas (,) in data fields, and WILL fail if one is encountered.
 *
 * Information can be returned as a set of strings or as a GameRecord
 * (preferred) to the calling environment. It is essential the caller catches
 * thrown IO exceptions.
 *
 * @author Patrick Ryan
 */
public class CSVFileReader {

    private final BufferedReader reader;
    private final CSVParser parser;

    /**
     *
     * Construct a reader and open a buffered reader instance to the file.
     *
     * @param path String representing the file path to a CSV file.
     * @throws FileNotFoundException If the passed file path does not exist.
     */
    public CSVFileReader(String path) throws FileNotFoundException {

        parser = new CSVParser();

        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("Could not find database");
        }
    }

    /**
     * Read and consume a line from the internal FileReader, returning the
     * column data as elements of an ArrayList. If an IO error occurs during
     * file reading, an IOException will be thrown. The CSV line is consumed
     * with this action.
     *
     * @return A GameRecord corresponding to a line in the CSV file.
     * @throws java.io.IOException if an IO error occurs
     */
    public GameRecord readRecord() throws IOException {

        // Read to EOF if necessary
        String line;
        try {
            line = readLine();
        } catch (IOException e) {
            throw new IOException("IO error encountered when reading file.");
        }

        // Parse the line and split it up on comma. Also remove enclosing quotes
        if (line == null) { // if line is null, we're at the end of the file.
            return null;
        }

        // parse the line
        ArrayList<String> cells = parser.parseCells(line);

        // remove the id, we don't need it
        cells.remove(0);
        EnumMap<RecordField, String> fields = mapLine(cells);

        return new GameRecord(fields);
    }

    /**
     *
     * Return the corresponding EnumMap<> for the given CSV record. The output
     * of this function should be used to create a GameRecord instance.
     *
     * @param list An ArrayList of strings parsed by the CSV reader.
     * @return a map containing a key-value mapping of data that can be used in
     * a GameRecord instance.
     */
    private EnumMap<RecordField, String> mapLine(ArrayList<String> list) {
        EnumMap<RecordField, String> fields = new EnumMap<>(RecordField.class);

        // names
        fields.put(RecordField.WHITE_FIRST_NAME, list.get(0));
        fields.put(RecordField.WHITE_LAST_NAME, list.get(1));
        fields.put(RecordField.BLACK_FIRST_NAME, list.get(2));
        fields.put(RecordField.BLACK_LAST_NAME, list.get(3));

        // ELO
        fields.put(RecordField.WHITE_ELO, list.get(4));
        fields.put(RecordField.BLACK_ELO, list.get(5));

        // Result 
        fields.put(RecordField.RESULT, list.get(6));

        // miscellaneous data
        fields.put(RecordField.LENGTH, list.get(7));
        fields.put(RecordField.NUMBER_OF_VARIATIONS, list.get(8));
        fields.put(RecordField.ECO_CODE, list.get(9));
        fields.put(RecordField.OPENING_NAME, list.get(10));
        fields.put(RecordField.DATE, list.get(11));
        fields.put(RecordField.SITE, list.get(12));
        fields.put(RecordField.PGN_FILE_PATH, list.get(13));

        return fields;
    }

    // Read a line from the buffer.
    private String readLine() throws IOException {
        return reader.readLine();
    }
}
