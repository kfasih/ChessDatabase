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
 * 
 * This class models the whitespace consumption state for the CSV parser finite
 * state machine.
 * 
 * @author Patrick Ryan
 */
public class StripWhitespaceState implements CSVParserState {
    
    /**
     * Consume whitespace, or transition to next state
     * 
     * @param context calling context
     * @param character character to act upon
     */
    @Override
    public void parse(CSVParser context, char character) {
        if (character == '"') { // transition to enclosed cell parse.
            context.setState(new EnclosedRecordReadState());
        } else if (Character.isWhitespace(character)) { // continue consuming whitespace.
            return;
        } else if (character == ',') { // terminate the cell
            context.terminateCell();
        } else {  // All other characters enter cell parse state.
            context.append(character);
            context.setState(new CellReadState());
        }
    }
}
