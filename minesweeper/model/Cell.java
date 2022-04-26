/**
 * Class to represent a Cell on the Minesweeper gameboard.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

/**
 * Class to represent a Cell on the Minesweeper gameboard.
 * Can be a uncovered mine, covered mine, uncovered safe spot, and covered safe spot.
 */
public class Cell {
    private Location location;
    private CellType type;

    public Cell(Location location, CellType type) {
        this.location = location;
        this.type = type;
    }

    //------------- Getters and Setters --------------//

    public Location getLocation() {
        return this.location;
    }

    public CellType getType() {
        return this.type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    //Setter
    public void updateCellType(CellType newType){
        this.type = newType;
    }

    //------------- Special Methods -----------------//

    /**
     * Checks if a cell is adjacent by comparing both locations.
     * @param cell The cell to check if adjacent.
     * @return true if the cell is adjacent, false if not.
     */
    public boolean isAdjacent(Cell cell) {
        //Below middle square
        
        if (cell.getLocation().getRow() == location.getRow() - 1 &&
            cell.getLocation().getCol() == location.getCol()) {
            return true;
        }

        //Top middle square
        else if(cell.getLocation().getRow() == location.getRow() + 1 &&
                cell.getLocation().getCol() == location.getCol()) {
            return true;
        }

        //Right middle square
        else if(cell.getLocation().getRow() == location.getRow() &&
                cell.getLocation().getCol() == location.getCol() + 1) {
            return true;
        }

        //Left middle square
        else if(cell.getLocation().getRow() == location.getRow() &&
                cell.getLocation().getCol() == location.getCol() - 1) {
            return true;
        }

        //Top left square
        else if(cell.getLocation().getRow() == location.getRow() + 1 && 
                cell.getLocation().getCol() == location.getCol() - 1) {
            return true;
        }

        //Top right square
        else if(cell.getLocation().getRow() == location.getRow() + 1 &&
                cell.getLocation().getCol() == location.getCol() + 1) {
            return true;
        }

        //Bottom left square
        else if(cell.getLocation().getRow() == location.getRow() - 1 &&
                cell.getLocation().getCol() == location.getCol() - 1) {
            return true;
        }

        //Bottom right square
        else if(cell.getLocation().getRow() == location.getRow() - 1 &&
                cell.getLocation().getCol() == location.getCol() + 1) {
            return true;
        }

        else if(this == cell){
            return false;
        }

        else {
            return false;
        }
    }


    // public int getAdjacentMines(Cell cell){
        
    // }

    @Override 
    public String toString(){
        if(this.getType() == CellType.SAFE_UNCHECKED){
            return "-";
        }
        else if(this.getType() == CellType.BOMB_CHECKED || this.getType() == CellType.BOMB_UNCHECKED){
            return "M";
        }
        else{
            return "0";
        }
    }
}
