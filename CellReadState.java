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
 * This class models the cell parsing state of the parser. This class specifically
 * parses unquoted cell fields.
 * 
 * @author Patrick Ryan
 */
public class CellReadState implements CSVParserState {
    
    /**
     * Consume character, possibly terminating the cell and transition state
     * 
     * @param context calling context
     * @param character character to act upon
     */
    @Override
    public void parse(CSVParser context, char character) {
        if (character == ',') { // We've reached the end of the cell
            context.terminateCell();
            context.setState(new StripWhitespaceState());
        } else { // append the character to the context's buffer
            context.append(character);
        }
    }
}
