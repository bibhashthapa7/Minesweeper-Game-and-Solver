/**
 * Class to represent a Board which holds the Minesweeper gameboard.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to represent a Board which holds the Minesweeper gameboard.
 * Holds all cells in a list.
 * Holds all mines in a list.
 * Creates the mines.
 * Verifies cell location logic.
 * @author Bibhash
 */
public class Board {
    
    private final int row;
    private final int col;
    private final int mineCount;
    
    private List<List<Cell>> cells;
    private List<Cell> mineCells;
    private List<Cell> totalCells;
    
    /**
     * Constructor which initializes the row and columns of board, 2D list of cells, and an 
     * instance of the Minesweeper.
     * @param row of the board.
     * @param col of the board.
     * @author Bibhash
     */
    public Board(int row, int col){
        this.row = row;
        this.col = col;
        this.mineCount = row + col;
        this.cells = new ArrayList<>();
        this.mineCells = new ArrayList<>();
        this.totalCells = new ArrayList<>();
    }

    //------------- Getters and Setters --------------//

    /**
     * Method which returns the 2D list of cells of the board.
     * @return the 2d list of cells.
     * @author Bibhash
     */
    public List<List<Cell>> getCells(){
        return this.cells;
    }

    /**
     * Method which returns a List of mine cells.
     * @return list of mineCells.
     * @author Bibhash
     */
    public List<Cell> getMineCells(){
        return mineCells;
    }

    /**
     * Method which returns a total list of all cells of the board.
     * @return the list of cells.
     */
    public List<Cell> getTotalCells(){
        return this.totalCells;
    }

    //------------- Functional Methods -----------------//

    /**
     * Method which populates the board with cells.
     * @author Bibhash 
     */
    public void makeBoard(){
        List<Cell> cellRow = new ArrayList<>();

        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                Cell cell = new Cell(new Location(i, j), CellType.SAFE_UNCHECKED);
                cellRow.add(cell);
                totalCells.add(cell);
            }
            cells.add(cellRow);
            cellRow = new ArrayList<>();
        }

        mineCells = createMines(cells);
    }

    /**
     * Method which updates some cells of the board to mines and stores it into a List.
     * 
     * @param cells the 2d list of cells.
     * @return list of mines.
     * @author Bibhash
     */
    private List<Cell> createMines(List<List<Cell>> cells){

        while(mineCells.size() < mineCount){
            int mineRow = new Random().nextInt(row); 
            int mineCol = new Random().nextInt(col);
            cells.get(mineRow).get(mineCol).updateCellType(CellType.BOMB_UNCHECKED);
            Cell mineCell = cells.get(mineRow).get(mineCol);
            
            if(!mineCells.contains(mineCell)){
                mineCells.add(mineCell);
            }
        }

        return mineCells;
    }

    /**
     * Takes a location as an argument, returns whether it is a real location on the board.
     * DOES NOT CONFIRM WHETHER THIS LOCATION IS A VALID MOVE.
     * author: jack
     * @param location the location to check.
     * @return whether the location is real on the board or not.
     */
    public boolean isRealLocation(Location location){
        //Loop through all the cells, if it finds the location on the list, it's true, else it returns false.
        //This one is pretty simple, maybe it could be optimized.
        for(int i = 0; i<cells.size(); i++){
            List<Cell> currentRow = cells.get(i);
            for(Cell cell : currentRow){
                if(cell.getLocation().getRow() == location.getRow() && cell.getLocation().getCol() == location.getCol()){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * I might combine with the method above in the future
     * Gets cell at specific location.
     * author: jack
     * @param location the location to check.
     * @return the cell at this location.
     * @throws MinesweeperException
     */
    public Cell getCellAtLocation(Location location) throws MinesweeperException{
        //Loop through all the cells, if it finds the location on the list, it's true, else, return false.
        //This one is pretty simple, maybe it could be optimized.
        
        if(isRealLocation(location)){
            return cells.get(location.getRow()).get(location.getCol());
        } else {
            throw new MinesweeperException("That cell or location: " + location.toString() + " does not exist");
        }
    }

    /**
     * returns all adjacent cells to any given cell.
     * @author Jack Noble
     * @param cell
     * @return
     */
    public ArrayList<Cell> getAdjacentCells(Cell cell){
        Location location = cell.getLocation();
        ArrayList<Cell> cells = new ArrayList<>();
        
        try {
            //To the right -1
            if(isRealLocation(new Location(location.getRow()+1, location.getCol()))){
                cells.add(getCellAtLocation(new Location(location.getRow()+1, location.getCol())));
            }
            //To the left +1
            if(isRealLocation(new Location(location.getRow()-1, location.getCol()))){
                cells.add(getCellAtLocation(new Location(location.getRow()-1, location.getCol())));
            }
            //To the up
            if(isRealLocation(new Location(location.getRow(), location.getCol()-1))){
                cells.add(getCellAtLocation(new Location(location.getRow(), location.getCol()-1)));
            }
            //To the down
            if(isRealLocation(new Location(location.getRow(), location.getCol()+1))){
                cells.add(getCellAtLocation(new Location(location.getRow(), location.getCol()+1)));
            }
            //Top right
            if(isRealLocation(new Location(location.getRow()+1, location.getCol()-1))){
                cells.add(getCellAtLocation(new Location(location.getRow()+1, location.getCol()-1)));
            }
            //Top left
            if(isRealLocation(new Location(location.getRow()-1, location.getCol()-1))){
                cells.add(getCellAtLocation(new Location(location.getRow()-1, location.getCol()-1)));
            }
            //Bottom right
            if(isRealLocation(new Location(location.getRow()+1, location.getCol()+1))){
                cells.add(getCellAtLocation(new Location(location.getRow()+1, location.getCol()+1)));
            }
            //Bottom left
            if(isRealLocation(new Location(location.getRow()-1, location.getCol()+1))){
                cells.add(getCellAtLocation(new Location(location.getRow()-1, location.getCol()+1)));
            }
        } catch (MinesweeperException e) {
            e.printStackTrace();
        }
        
        return cells;
    }

    /**
     * Returns number of mines around any given cell.
     * @author Jack Noble
     * @param cell
     * @return
     */
    public int getMinesAroundCell(Cell cell){
        int mineCount = 0;
        if(isRealLocation(cell.getLocation())){
            ArrayList<Cell> cells = getAdjacentCells(cell);
            for(Cell e : cells){
                if(e.getType() == CellType.BOMB_CHECKED || e.getType() == CellType.BOMB_UNCHECKED){
                    mineCount++;
                }
            }
        } else {
            return 0;
        }

        return mineCount;
    }

    public List<Location> getSafeCells(){
        List<Location> safeCells = new ArrayList<>();
        for(Cell cell : totalCells){
            if(cell.getType() != CellType.BOMB_CHECKED || cell.getType() != CellType.BOMB_UNCHECKED){
                safeCells.add(cell.getLocation());
            }
        }
        return safeCells;
    }

    /**
     * Main function for testing all the functions of this class.
     * @param args
     * @author Bibhash
     */
    public static void main(String[] args) {
        Board board = new Board(3, 3);
        board.makeBoard();
        
        for(List<Cell> cell : board.getCells()){
            System.out.println(cell);
        }

        for(Cell mine: board.getMineCells()){
            System.out.println(mine.getLocation());
        }
    }
}