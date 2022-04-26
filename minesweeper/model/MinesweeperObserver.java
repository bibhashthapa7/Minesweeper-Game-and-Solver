/**
 * Minesweeper Observer class to check the cells.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

public interface MinesweeperObserver {
    public void cellUpdated(Location location);
}
