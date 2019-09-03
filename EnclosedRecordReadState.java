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
 * This class models the quoted cell field parsing state of the parser.
 * 
 * @author Patrick Ryan
 */
public class EnclosedRecordReadState implements CSVParserState{
    
    /**
     * 
     * Consume character, or change state if a closing quote is encountered.
     * 
     * @param context calling context
     * @param character the character to act upon
     */
    @Override
    public void parse(CSVParser context, char character) {
        if (character == '"') {// our enclosure is finished, now strip whitespace until , is encountered.
            context.setState(new StripWhitespaceState());
        } else { // append everything else.
            context.append(character);
        }
    }
}
