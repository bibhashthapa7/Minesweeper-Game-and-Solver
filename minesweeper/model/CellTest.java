/**
 * Testing Class to represent a Cell on the Minesweeper gameboard.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class CellTest {
    @Test
    public void isAdjacent() {
        // setup
        Cell one = new Cell(new Location(2, 2), CellType.SAFE_UNCHECKED);
        Cell two = new Cell(new Location(2, 3), CellType.SAFE_UNCHECKED);
        Cell three = new Cell(new Location(3, 3), CellType.SAFE_UNCHECKED);
        Cell four = new Cell(new Location(1, 2), CellType.SAFE_UNCHECKED);
        

        // invoke
        boolean actual = one.isAdjacent(two); //TRUE
        boolean actual2 = one.isAdjacent(three); //TRUE
        boolean actual3 = one.isAdjacent(four); //TRUE
        boolean actual4 = four.isAdjacent(three); //FALSE
        boolean expected1 = true;
        boolean expected2 = true;
        boolean expected3 = true;
        boolean expected4 = false; 


        // analyze
        assertEquals(expected1, actual);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
        assertEquals(expected4, actual4);
    }
}
