/**
 * Class to hold a MinesweeperException which is thrown when invalid moves are made.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

/**
 * Class to hold a MinesweeperException which is thrown when invalid moves are made.
 */
public class MinesweeperException extends Exception {
    
    /**
     * MinesweeperException denotes an error specific to our minesweeper game.
     * @param error String error messege.
     */
    public MinesweeperException(String message){
        //Calls superclass constructor inside of Exception class.
        super(message);
    }
}
