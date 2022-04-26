/**
 * Testing Class to hold a MinesweeperException which is thrown when invalid moves are made.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class MinesweeperExceptionTest {
    @Test
    public void testMinesweeperException_MakeSelection1(){
        boolean expected = true;

        try{
            Board board = new Board(5, 5);
            Minesweeper game = new Minesweeper(5, 5, board);

            game.makeSelection(new Location(2, 1));
            boolean actual = false;
            assertEquals(expected, actual);
        }
        catch(MinesweeperException e){
            boolean actual = true;
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testMinesweeperException_MakeSelection2(){
        boolean expected = false;

        try{
            Board board = new Board(5, 5);
            board.makeBoard();
            Minesweeper game = new Minesweeper(5, 5, board);

            game.makeSelection(new Location(2, 1));
            boolean actual = false;
            assertEquals(expected, actual);
        }
        catch(MinesweeperException e){
            boolean actual = true;
            assertEquals(expected, actual);
        }
    }
}
