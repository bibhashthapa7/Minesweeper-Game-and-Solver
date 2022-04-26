/**
 * Enumeration to represent what type a Cell on the gameboard can be.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

/**
 * Enumeration to represent what type a Cell on the gameboard can be.
 */
public enum CellType {
    BOMB_UNCHECKED("Bomb Unchecked"), // It's a bomb but hasn't been discovered.
    BOMB_CHECKED("Bomb Checked"), // It's a bomb but it's been discovered.
    SAFE_UNCHECKED("Safe Unchecked"), // It's a safe square but hasn't been discovered.
    SAFE_CHECKED("Safe Checked"); // It's a safe square but it's been discovered.

    private final String type;

    private CellType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
