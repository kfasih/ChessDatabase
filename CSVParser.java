/*******************************************************************************
 * Copyright 2019 Patrick Ryan
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 ******************************************************************************/

package model;


/**
 * This class provides all functionality required for reading and writing CSV
 * data files for the database system. The parser for the reader does NOT allow
 * for nested quotes (") in data fields, and WILL fail if one is encountered.
 * 
 * Information is returned as an ArrayList of strings representing the contents
 * of cells.
 * 
 * @author Patrick Ryan
 */
import java.util.ArrayList;

public class CSVParser {
    private CSVParserState state;
    private final StringBuilder recordBuilder;
    private final ArrayList<String> cells;

    /**
     * Instantiate parser instance
     */
    public CSVParser() {
        state = new StripWhitespaceState();
        recordBuilder = new StringBuilder();
        cells = new ArrayList<>();
    }

    /**
     * Change internal state.
     * @param state to change to
     */
    public void setState(CSVParserState state) {
        this.state = state;
    }
    
    /**
     * Append character to current cell string builder
     * @param character to be appended
     */
    public void append(char character) {
        recordBuilder.append(character);
    }

    /**
     * End current cell, and add the result to the cells ArrayList.
     */
    public void terminateCell() {
        cells.add(recordBuilder.toString());
        recordBuilder.setLength(0); // wipe the builder for the next cell.
    }

    /**
     * Parse all cells in the input string. Return the cells as a list of strings.
     * @param csvRecord string to be parsed
     * @return list of cells represented as strings.
     */
    public ArrayList<String> parseCells(String csvRecord) {
        for (char c : csvRecord.toCharArray()) {
            state.parse(this, c);
        }
        terminateCell(); // Make sure to terminate the last cell!

        // wipe list after parsing.
        ArrayList<String> result = (ArrayList<String>)this.cells.clone();
        this.cells.clear();

        return result;
    }
}
