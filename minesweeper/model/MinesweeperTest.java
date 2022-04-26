/**
 * Testing Class to represent a Minesweeper instance.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class MinesweeperTest {
    @Test
    public void testGameState1(){
        GameState expected = GameState.NOT_STARTED;
        Minesweeper game = new Minesweeper(5, 5, new Board(5, 5));

        GameState actual = game.getGameState();

        assertEquals(expected, actual);
    }

    @Test
    public void testMoveCount(){
        int expected = 0;
        Minesweeper game = new Minesweeper(5, 5, new Board(5, 5));

        int actual = game.getMoveCount();

        assertEquals(expected, actual);
    }

    @Test
    public void testMineCount(){
        int rows = 8;
        int cols = 6;
        Minesweeper game = new Minesweeper(rows, cols, new Board(rows, cols));

        int expected = rows + cols;

        int actual = game.getMineCount();

        assertEquals(expected, actual);
    }

    @Test
    public void testGameStateDeepCopy(){
        Minesweeper game = new Minesweeper(5, 5, new Board(5, 5));
        String expected = game.toString();

        Location location = new Location(1, 1);

        

        System.out.println(game.toString());

        Minesweeper game2 = new Minesweeper(game);
        try {
            game2.makeSelection(location);
        } catch (MinesweeperException mse) {
            System.out.println("ERROR!");
        }
        

        System.out.println(game);
        System.out.println("DEEP COPY BELOW");
        System.out.println(game2);

        String actual = game2.toString();

        assertEquals(expected, actual);
    }
}
