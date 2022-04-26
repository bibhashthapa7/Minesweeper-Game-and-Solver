/**
 * Enumeration to represent what type of progress a current Game can be.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

/**
 * Enumeration to represent what type of progress a current Game can be.
 */
public enum GameState {
    NOT_STARTED("Not Started"), // No moves have been made yet.
    IN_PROGRESS("In Progress"), // At least one selection has been made and game has not yet ended.
    WON("Won"), // Game played to completion and won.
    LOST("Lost"); // Game ended becaue mine was uncovered.

    private final String state;

    private GameState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}