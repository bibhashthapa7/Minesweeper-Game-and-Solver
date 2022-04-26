/**
 * Class to represent a Location which holds where a cell is.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

/**
 * Class to represent a Location which holds where a cell is.
 * Every cell on the Minesweeper board can be addressed using its row and column location.
 */
public class Location {

    private int row;
    private int col;

    public Location(int row, int col){
        this.row = row;
        this.col = col;
    }  

    //------------- Getters and Setters --------------//

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    //------------- Special Methods -----------------//

    @Override
    public boolean equals(Object o){
        if(o instanceof Location){
            Location other = (Location)o;
            return other.getRow() == this.getRow() && other.getCol() == this.getCol();
        }
        return false;
    }

    @Override
    public int hashCode(){
        return row * 100 + col;
    }

    @Override
    public String toString(){
        return "(" + this.row + ", " + this.col + ")";
    }
}
