/**
 * Class to play a real game of Minesweeper using everything we wrote. 
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.view;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import backtracker.Backtracker;
import backtracker.Configuration;
import minesweeper.model.Board;
import minesweeper.model.Cell;
import minesweeper.model.CellType;
import minesweeper.model.GameState;
import minesweeper.model.Location;
import minesweeper.model.Minesweeper;
import minesweeper.model.MinesweeperException;
import minesweeper.model.MinesweeperSolver;

/**
 * Class to play a real game of Minesweeper using everything we wrote.
 * Has commands and plays a CLI version.
 * @author Michael McI.
 */
public class MSGTest {
    
    /**
     * Checks if game input is an integer or not.
     * @param input the game input.
     * @return whether it's an integer or not.
     */
    private static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Helper function to print everything needed in the help command.
     * @author Michael McI.
     */
    private static void printHelp() {
        System.out.println("Commands: ");
        System.out.println("        help - this help message.");
        System.out.println("        pick <row> <col> - uncovers cell at <row> <col>.");
        System.out.println("        hint - displays a safe selection.");
        System.out.println("        solve - executes all the moves to solve the game.");
        System.out.println("        reset - resets to a new game.");
        System.out.println("        quit - quits the game.");
        System.out.println();
    }

    /**
     * Function to check if the game has been won yet.
     * @author Michael McI.
     * @param gameBoard Current gameboard of cells to be passed in.
     * @return true if game is won, false if not.
     */
    private static boolean isWin(Board gameBoard) {
        List<Cell> totalCells = gameBoard.getTotalCells();
        Set<Boolean> gameWinning = new HashSet<>();

        for(int i = 0; i < totalCells.size(); i++){
            if (totalCells.get(i).getType() == CellType.SAFE_UNCHECKED) {
                return false;
            }

            else if (totalCells.get(i).getType() == CellType.SAFE_CHECKED) {
                boolean flag = true;
                gameWinning.add(flag);
            }
        }

        if (gameWinning.contains(false)) {
            return false;
        }

        else {
            return true;
        }
    }

    /**
     * Plays a game of Minesweeper! Contains all the play logic.
     * @author Michael McI.
     * @param rows rows on the board.
     * @param columns columns on the board.
     * @param scanner handles user input.
     * @throws MinesweeperException
     */
    private static void playGame(int rows, int columns, Scanner scanner) throws MinesweeperException {
        
        Board gameBoard = new Board(rows, columns);
        Minesweeper game = new Minesweeper(rows, columns, gameBoard);
        gameBoard.makeBoard();

        System.out.println("Mines: " + game.getMineCount());

        printHelp();

        System.out.println(game);

        System.out.print("Enter command: ");
        String input = scanner.nextLine();
        String[] commands = input.trim().split(" ");
        
        // while game isn't a loss or win, continue.
        while (game.getGameState() != GameState.LOST || game.getGameState() != GameState.WON) {
            boolean exitCondition = false;
            if (commands[0].equals("help")) {
                printHelp();

                System.out.println(game);
                System.out.print("Enter command: ");
                input = scanner.nextLine();
                commands = input.trim().split(" ");
            }

            else if (commands[0].equals("hint")) {
                // return a valid location that isn't a mine.
                // System.out.println("Give " + (0, 2) + "a try.");
                Collection<Location> hints = game.getPossibleSelections();
                if (hints.iterator().hasNext()) {
                    Location first = hints.iterator().next();
                    System.out.println("Give " + first + " a try.");
                }
                
                else {
                    System.out.println("No available hints.");
                }

                System.out.println(game);
                System.out.print("Enter command: ");
                input = scanner.nextLine();
                commands = input.trim().split(" ");
            }

            else if (commands[0].equals("reset")) {
                game.setGameState(GameState.NOT_STARTED);
                // save the number of mines that was generated and create a board of the same size.
                // 6 x 6 board that had 5 mines on it should be recreated.
                // Randomized mine locations again.
                Board newBoard = new Board(rows, columns);
                Minesweeper newGame = new Minesweeper(rows, columns, newBoard);

                //set old board to newly created one
                gameBoard = newBoard;
                game = newGame;
                gameBoard.makeBoard();
                System.out.println("Resetting to a new game.");

                System.out.println(game);
                System.out.print("Enter command: ");
                input = scanner.nextLine();
                commands = input.trim().split(" ");
            }

            else if (commands[0].equals("quit")) {
                game.setGameState(GameState.LOST);
                System.out.println("Goodbye!");
                break;
            }

            else if (commands[0].equals("pick")) {
                if (isInt(commands[1]) == true && isInt(commands[2]) == true) {
                    int row = Integer.parseInt(commands[1]);
                    int col = Integer.parseInt(commands[2]);

                    //This line wasn't compiling for me without the extra minesweeper.model stuff, dunno why.
                    minesweeper.model.Location location = new minesweeper.model.Location(row, col);

                    //call isValid board function next and use it accordingly.
                    //if true, makeSelection.
                    //if selection is a bomb or wins the game, print the right message.
                    //if false, print error message, loop back, and ask again for valid input.
                    try{
                        if(gameBoard.isRealLocation(location)){
                            if(gameBoard.getCellAtLocation(location).getType() == CellType.SAFE_UNCHECKED){
                                game.makeSelection(location);
                                game.setGameState(GameState.IN_PROGRESS);
                            } else if(gameBoard.getCellAtLocation(location).getType() == CellType.BOMB_UNCHECKED){
                                game.setGameState(GameState.LOST);
                            }
                        }
                        // ADDED THIS TO TEST GET SYMBOL. CAN BE REMOVED.
                        System.out.println(game.getSymbol(location));
                    } catch(MinesweeperException e){
                        System.out.println("That is invalid " + e);
                    }

                    //check if gameState won or lost.
                    if(game.getGameState() == GameState.WON || isWin(gameBoard) == true) {
                        System.out.println("Congratulations!");
                        System.out.println(game.uncoverBoard(game.getGameState()));
                        
                        System.out.print("Enter command: ");
                        input = scanner.nextLine();
                        commands = input.trim().split(" ");

                        boolean flag = false;
                        while (flag != true) {
                            if (commands[0].equals("help")) {
                                printHelp();
                
                                System.out.println(game);
                                System.out.print("Enter command: ");
                                input = scanner.nextLine();
                                commands = input.trim().split(" ");
                            }
    
                            else if (commands[0].equals("reset")) {
                                game.setGameState(GameState.NOT_STARTED);
                                Board newBoard = new Board(rows, columns);
                                Minesweeper newGame = new Minesweeper(rows, columns, newBoard);
    
                                gameBoard = newBoard;
                                game = newGame;
                                gameBoard.makeBoard();
                                System.out.println("Resetting to a new game.");
                                flag = true;
                            }
    
                            else if (commands[0].equals("quit")) {
                                game.setGameState(GameState.LOST);
                                flag = true;
                                exitCondition = true;
                                break;
                            }
                        }
                    }
                    else if (game.getGameState() == GameState.LOST) {
                        System.out.println("BOOM! Better luck next time!");
                        System.out.println(game.uncoverBoard(game.getGameState()));

                        System.out.print("Enter command: ");
                        input = scanner.nextLine();
                        commands = input.trim().split(" ");

                        boolean flag = false;
                        while (flag != true) {
                            if (commands[0].equals("help")) {
                                printHelp();
                
                                System.out.println(game);
                                System.out.print("Enter command: ");
                                input = scanner.nextLine();
                                commands = input.trim().split(" ");
                            }
    
                            else if (commands[0].equals("reset")) {
                                game.setGameState(GameState.NOT_STARTED);
                                Board newBoard = new Board(rows, columns);
                                Minesweeper newGame = new Minesweeper(rows, columns, newBoard);
    
                                gameBoard = newBoard;
                                game = newGame;
                                gameBoard.makeBoard();
                                System.out.println("Resetting to a new game.");
                                flag = true;
                            }
    
                            else if (commands[0].equals("quit")) {
                                game.setGameState(GameState.LOST);
                                flag = true;
                                exitCondition = true;
                                break;
                            }
                        }
                    }
                }
                
                if (exitCondition != true) {
                    System.out.println(game);
                    System.out.print("Enter command: ");
                    input = scanner.nextLine();
                    commands = input.trim().split(" ");
                }
            }
            else if(commands[0].equals("solve")){
                if(game.getGameState() == GameState.NOT_STARTED) {
                    System.out.println("Cannot solve without making any moves");
                    continue;
                }
                Backtracker backtracker = new Backtracker(true);
                Configuration config = new MinesweeperSolver(game);
                MinesweeperSolver solution = (MinesweeperSolver) backtracker.solve(config);
                if(solution == null){
                    System.out.println("no solution");
                } 
                else{
                    for(Location move : solution.getPreviousMoves()){
                        // System.out.println("Selection: " + move);
                        game.makeSelection(move);
                        // System.out.println(game);
                    }
                }
            }

            else {
                //Not a valid command! Prints error message.
                System.out.println("Not a valid command. Please try again.");
            }
            
            exitCondition = false;
        }
    }

    /**
     * Main function to play the game.
     * @author Michael McI.
     * @param args parameters to play.
     * @throws MinesweeperException
     */
    public static void main(String[] args) throws MinesweeperException {
        //hardcoded instances of rows and cols to be used.
        //change these if you want a different board size.
        int rows = 5;
        int columns = 5;

        Board gameBoard = new Board(rows, columns);
        Minesweeper game = new Minesweeper(rows, columns, gameBoard);
        gameBoard.makeBoard();

        Location location = new Location(1, 1);

        // Printing out a freshly created board.
        System.out.println("Printing out a freshly created board.");
        System.out.println(game);

        // Making a copy of the board and making a change.
        System.out.println("Making a copy of the board and making a change.");
        Minesweeper game2 = new Minesweeper(game);
        try {
            game2.makeSelection(location);
        } catch (MinesweeperException mse) {
            System.out.println("ERROR!");
        }
        
        // Printing out the new change on the DEEP COPIED BOARD
        System.out.println("Printing out the new change on the DEEP COPIED BOARD");
        System.out.println(game2);

        // Printing out the original board which should be UNTOUCHED
        System.out.println("Printing out the original board which should be UNTOUCHED");
        System.out.println(game);
    }
}
