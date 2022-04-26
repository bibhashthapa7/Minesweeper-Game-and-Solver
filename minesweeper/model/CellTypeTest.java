/**
 * testing Enumeration to represent what type a Cell on the gameboard can be.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class CellTypeTest {
    @Test
    public void toStringSafeChecked() {
        // setup
        String expected = "Safe Checked";

        // invoke
        String actual = CellType.SAFE_CHECKED.getType();

        // analyze
        assertEquals(expected, actual);
    }

    @Test
    public void toStringSafeUnchecked() {
        // setup
        String expected = "Safe Unchecked";

        // invoke
        String actual = CellType.SAFE_UNCHECKED.getType();

        // analyze
        assertEquals(expected, actual);
    }

    @Test
    public void toStringBombChecked() {
        // setup
        String expected = "Bomb Checked";

        // invoke
        String actual = CellType.BOMB_CHECKED.getType();

        // analyze
        assertEquals(expected, actual);
    }

    @Test
    public void toStringBombUnchecked() {
        // setup
        String expected = "Bomb Unchecked";

        // invoke
        String actual = CellType.BOMB_UNCHECKED.getType();

        // analyze
        assertEquals(expected, actual);
    }
}
