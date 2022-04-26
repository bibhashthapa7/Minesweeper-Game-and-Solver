/**
 * Testing Class to represent a Board which holds the Minesweeper gameboard.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class BoardTest {
    
    @Test
    public void testIsRealLocation1(){
        Board board = new Board(5, 5);
        board.makeBoard();
        boolean expected = true;
        boolean actual = board.isRealLocation(new Location(0, 0));

        assertEquals(expected, actual);
    }

    @Test
    public void testIsRealLocation2(){
        Board board = new Board(5, 5);
        board.makeBoard();
        boolean expected = false;
        boolean actual = board.isRealLocation(new Location(6, 5));

        assertEquals(expected, actual);
    }


}
