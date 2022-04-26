/**
 * Testing Class to represent a Location which holds where a cell is.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class LocationTest {
    @Test
    public void getRow() {
        // setup
        int expected = 4;
        int row = 4;
        int col = 5;

        // invoke
        Location actual = new Location(row, col);

        // analyze
        assertEquals(expected, actual.getRow());
    }

    @Test
    public void getCol() {
        // setup
        int expected = 5;
        int row = 4;
        int col = 5;

        // invoke
        Location actual = new Location(row, col);

        // analyze
        assertEquals(expected, actual.getCol());
    }

    @Test
    public void getEquals() {
        // setup
        int row = 4;
        int col = 5;
        Location expected = new Location(row, col);

        // invoke
        Location actual = new Location(row, col);

        // analyze
        assertEquals(expected, actual);
    }
}
