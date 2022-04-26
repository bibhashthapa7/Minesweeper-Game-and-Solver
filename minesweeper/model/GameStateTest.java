/**
 * Testing Enumeration to represent what type of progress a current Game can be.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class GameStateTest {
    @Test
    public void toStringNotStarted() {
        // setup
        String expected = "Not Started";

        // invoke
        String actual = GameState.NOT_STARTED.getState();

        // analyze
        assertEquals(expected, actual);
    }

    @Test
    public void toStringInProgress() {
        // setup
        String expected = "In Progress";

        // invoke
        String actual = GameState.IN_PROGRESS.getState();

        // analyze
        assertEquals(expected, actual);
    }

    @Test
    public void toStringWon() {
        // setup
        String expected = "Won";

        // invoke
        String actual = GameState.WON.getState();

        // analyze
        assertEquals(expected, actual);
    }

    @Test
    public void toStringLost() {
        // setup
        String expected = "Lost";

        // invoke
        String actual = GameState.LOST.getState();

        // analyze
        assertEquals(expected, actual);
    }
}
