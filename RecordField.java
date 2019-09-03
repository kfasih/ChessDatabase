package model;

import java.io.Serializable;

/**
 * This enumeration represents the required fields for a GameRecord. The ordering
 * of this enum is identical to the mapping in the corresponding database CSV
 * file.
 *
 * @author Patrick Ryan
 */
public enum RecordField implements Serializable {
    WHITE_FIRST_NAME, WHITE_LAST_NAME, BLACK_FIRST_NAME, BLACK_LAST_NAME,
    WHITE_ELO, BLACK_ELO, RESULT, LENGTH, NUMBER_OF_VARIATIONS, ECO_CODE,
    OPENING_NAME, DATE, SITE, PGN_FILE_PATH;
}
