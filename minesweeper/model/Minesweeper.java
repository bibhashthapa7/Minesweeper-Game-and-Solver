/**
 * Class to represent a Minesweeper instance.
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Class to represent a Minesweeper instance.
 * Keeps track of the game data and controls the logic.
 * Used by MinesweeperGame.java to play a fully functional game of Minesweeper.
 */
public class Minesweeper {
    public static final char MINE = 'M';
    public static final char COVERED = '-';

    private int rows;
    private int cols;
    private int mineCount;
    private GameState state = GameState.NOT_STARTED;
    private Location current_pick;
    private int moveCount;
    private Board gameBoard;
    private MinesweeperObserver observer;
    private Collection<Location> allPossibleSelections;

    /**
     * Constructor which initializes MineSweeper with set rows, cols and mineCount
     * @param rows
     * @param cols
     * @param mineCount
     * @param board
     */
    public Minesweeper(int rows, int cols, int mineCount, Board board){
        this.rows = rows;
        this.cols = cols;
        this.mineCount = mineCount;
        this.moveCount = 0;
        this.gameBoard = board;
    }

    /**
     * Constructor which initializes Minesweeper with set rows and cols only
     * (mineCount = rows + cols)
     * @param rows
     * @param cols
     * @param board
     */
    public Minesweeper(int rows, int cols, Board board){
        this.rows = rows;
        this.cols = cols;
        this.mineCount = rows + cols;
        this.moveCount = 0;
        this.gameBoard = board;
    }

    /**
     * Constructor which makes deep copies of the Minesweeper implementation
     * @param minesweeper
     */
    public Minesweeper(Minesweeper minesweeper){
        this.mineCount = minesweeper.getMineCount();
        this.moveCount = minesweeper.getMoveCount();
        this.rows = minesweeper.getRows();
        this.cols = minesweeper.getCols();
        this.gameBoard = minesweeper.getBoard();
        this.allPossibleSelections = new ArrayList<>(minesweeper.getAllPossibleSelections());
        //What I'm trying to do right now except .length will not work
        //this.gameBoard = Arrays.copyOf(minesweeper.getBoard(), minesweeper.getBoard().length)


        this.observer = null;
        this.state = minesweeper.getGameState();

        List<Cell> cellArray = new ArrayList<>();
        for(Cell cell : minesweeper.getBoard().getTotalCells()){
            Cell newCell = new Cell(new Location(cell.getLocation().getRow(), cell.getLocation().getRow()), cell.getType());
            cellArray.add(newCell);
        }

        // this.board = Arrays.copyOf(original.board, original.board.length);
        // this.movesToSolve = original.movesToSolve;
        // this.size = original.size;
        // this.openIndex = original.openIndex;
        // this.moves = new ArrayList<>(original.moves);
        // this.configurations = new HashSet<>(original.configurations);
    }

    /**
     * Method which registers the observer 
     * @param observer
     */
    public void register(MinesweeperObserver observer){
        this.observer = observer;
    }

    /**
     * Notifies observers!
     * @param location
     */
    private void notifyObservers(Location location) {
        if(observer != null){
            observer.cellUpdated(location);
        }
    }

    /**
     * Choose a spot on the board to uncover.
     * @param location
     * @throws MinesweeperException
     */
    public void makeSelection(Location location) throws MinesweeperException{
        if(gameBoard.isRealLocation(location)){

            if(gameBoard.getCellAtLocation(location).getType() == CellType.SAFE_UNCHECKED){
                //Sets the location, leaves the method.
                current_pick = location;
                gameBoard.getCellAtLocation(current_pick).updateCellType(CellType.SAFE_CHECKED);
                this.moveCount += 1;
                notifyObservers(location);
            } 
            
            else if(gameBoard.getCellAtLocation(location).getType() == CellType.BOMB_UNCHECKED){
                // gameBoard.getCellAtLocation(current_pick).updateCellType(CellType.BOMB_CHECKED);
                this.moveCount += 1;
                System.out.println("You cannot make a selection on this cell");
            }            
        }

        else {
            throw new MinesweeperException("Something went wrong with your new selection, make sure " + location + " exists.");
        }
    }

    /**
     * Gets the safe possible selections around a uncovered cell.
     * Used for the hint function.
     * @return A list of the safe selections.
     */
    public Collection<Location> getPossibleSelections(){
        
        Collection<Location> safeSelection = new ArrayList<>();
        
        for(int i = 0; i<gameBoard.getCells().size(); i++){
            List<Cell> currentRow = gameBoard.getCells().get(i);
            for(Cell cell : currentRow){
                if(cell.getType() == CellType.SAFE_UNCHECKED){
                    safeSelection.add(cell.getLocation());
                }
            }
        }
        return safeSelection;
    }

    /**
     * GETS ALL POSSIBLE SELECTIONS EVEN ONES THAT WOULD RESULT IN GAME OVER....
     * Used for getSuccessors() by Jack
     * @return A list of the safe selections.
     */
    public Collection<Location> getAllPossibleSelections(){
        
        allPossibleSelections= new ArrayList<>();
        
        for(int i = 0; i<gameBoard.getCells().size(); i++){
            List<Cell> currentRow = gameBoard.getCells().get(i);
            for(Cell cell : currentRow){
                if(cell.getType() == CellType.SAFE_UNCHECKED || cell.getType() == CellType.BOMB_UNCHECKED){
                    allPossibleSelections.add(cell.getLocation());
                }
            }
        }
        return allPossibleSelections;
    }

    /**
     * Method to print the entire board uncovered.
     * Used for win or lose condition.
     * @return string containing the entire board uncovered.
     */
    public String uncoverBoard(GameState gState){
        String returnString = "\n";
        if (gState == GameState.LOST) {
            this.moveCount += 1;
        }
        
        for(int r = 0; r<rows; r++){
            for(int c = 0; c<cols; c++){
                try{
                    Cell currentCell = gameBoard.getCellAtLocation(new Location(r, c));

                    if(currentCell.getType() == CellType.BOMB_CHECKED){
                        returnString += MINE;

                    } else if(currentCell.getType() == CellType.BOMB_UNCHECKED){
                        returnString += MINE;

                    } else if(currentCell.getType() == CellType.SAFE_CHECKED){
                        int mines = calculateAdjacentMines(currentCell);
                        returnString += mines + "";

                    } else if(currentCell.getType() == CellType.SAFE_UNCHECKED){
                        int mines = calculateAdjacentMines(currentCell);
                        returnString += mines + "";
                        
                    } else {
                        returnString += COVERED;
                    }

                } catch(MinesweeperException e){
                    System.out.println(e);
                }
            }
            returnString += "\n";
        }
        return returnString + "\n" + "Moves: " + this.moveCount + "\n";
    }

    /**
     * Method which calculates the number of adjacent mines given a particular Cell of the Board
     * @param cell
     * @return number of adjacent mines
     * @author Bibhash
     */
    private int calculateAdjacentMines(Cell cell){
        int mines = 0;
        List<List<Cell>> allCells = gameBoard.getCells();
        for(List<Cell> cellRow : allCells){
            for(Cell otherCell : cellRow){
                if(cell.isAdjacent(otherCell)){
                    if(otherCell.getType() == CellType.BOMB_UNCHECKED){
                        mines++;
                    }
                }
            }
        }
        return mines;
    }

    /**
     * Method which checks if a cell, given its location, is covered or not
     * @param location
     * @return true if covered/unchecked, false if uncovered/unchecked
     * @throws MinesweeperException
     */
    public boolean isCovered(Location location) throws MinesweeperException{
        Cell cell = gameBoard.getCellAtLocation(location);
        if(cell.getType() == CellType.BOMB_UNCHECKED || cell.getType() == CellType.SAFE_UNCHECKED){
            return true;
        }
        return false;
    }

    //----------------- Getters and Setters ----------------//

        
    public int getMoveCount(){
        return this.moveCount;
    }

    public GameState getGameState(){
        return this.state;
    }

    public void setGameState(GameState state){
        this.state = state;
    }

    public int getMineCount() {
        return this.mineCount;
    }

    public int getRows(){
        return this.rows;
    }

    public int getCols(){
        return this.cols;
    }

    public Board getBoard(){
        return this.gameBoard;
    }

    public char getSymbol(Location location) throws MinesweeperException {
        Cell currentCell = gameBoard.getCellAtLocation(location);
            if(currentCell.getType() == CellType.BOMB_CHECKED){
                return MINE;
            }

            else if (currentCell.getType() == CellType.BOMB_UNCHECKED) {
                return COVERED;
            }

            else if (currentCell.getType() == CellType.SAFE_UNCHECKED) {
                return COVERED;
            }

            else {
                int numOfMines = calculateAdjacentMines(currentCell);
                System.out.println(numOfMines);
                return (char)(numOfMines + '0');
            }
    }

    //----------------- Special methods ----------------//

    @Override 
    public String toString(){
        String returnString = "\n";
        
        for(int r = 0; r<rows; r++){
            for(int c = 0; c<cols; c++){
                try{
                    Cell currentCell = gameBoard.getCellAtLocation(new Location(r, c));
                    if(currentCell.getType() == CellType.BOMB_CHECKED){
                        returnString+=MINE;
                    } else if(currentCell.getType() == CellType.SAFE_CHECKED){
                        int mines = calculateAdjacentMines(currentCell);
                        returnString+= mines+"";
                    } else {
                        returnString+=COVERED;
                    }
                } catch(MinesweeperException e){
                    System.out.println(e);
                }
            }
            returnString += "\n";
        }
        return returnString + "\n" + "Moves: " + this.moveCount + "\n";
    }
}