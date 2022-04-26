/**
 * Class to play a real game of Minesweeper using everything we wrote in GUI Format. 
 * @author Jack
 * @author Michael McI.
 * @author Bibhash
 */

package minesweeper.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import minesweeper.model.Board;
import minesweeper.model.Cell;
import minesweeper.model.CellType;
import minesweeper.model.GameState;
import minesweeper.model.Location;
import minesweeper.model.Minesweeper;
import minesweeper.model.MinesweeperException;

/**
 * Class to play a real game of Minesweeper using everything we wrote.
 * Has buttons and plays a GUI version.
 * @author Michael McI, Jack, Bibhash.
 */
public class MinesweeperGUI extends Application {

    //Static final references to all of our images...
    private static final String IMAGE_PATH = "file:media/images/";
    private static final Image COVERED_IMG = new Image(IMAGE_PATH + "COVERED.png");
    private static final Image HINT_IMG = new Image(IMAGE_PATH + "hint_img.png");
    private static final Image BLANK_IMG = new Image(IMAGE_PATH + "BLANK.png");
    //private static final Image FLAG_IMG = new Image(IMAGE_PATH + "FLAG.png");
    private static final Image MINE_IMG = new Image(IMAGE_PATH + "MINE.png");
    private static final Image ONE_IMG = new Image(IMAGE_PATH + "uncovered-1.png");
    private static final Image TWO_IMG = new Image(IMAGE_PATH + "uncovered-2.png");
    private static final Image THREE_IMG = new Image(IMAGE_PATH + "uncovered-3.png");
    private static final Image FOUR_IMG = new Image(IMAGE_PATH + "uncovered-4.png");
    private static final Image FIVE_IMG = new Image(IMAGE_PATH + "uncovered-5.png");
    private static final Image SIX_IMG = new Image(IMAGE_PATH + "uncovered-6.png");
    private static final Image SEVEN_IMG = new Image(IMAGE_PATH + "uncovered-7.png");
    private static final Image EIGHT_IMG = new Image(IMAGE_PATH + "uncovered-8.png");

    private Minesweeper game;
    private Board gameBoard;

    private final int ROW_COUNT = 10;
    private final int COL_COUNT = 10;
    private Label movesLabel;
    private Label statusLabel;
    private GridPane grid;
    private Button hintButton;

    @Override
    public void start(Stage stage) throws Exception {

        gameBoard = new Board(ROW_COUNT, COL_COUNT);
        game = new Minesweeper(ROW_COUNT, COL_COUNT, gameBoard);
        gameBoard.makeBoard();

        //Create VBOX - holds whole game
        VBox gameBox = new VBox();

        //Holds game information, moves made, ect.
        HBox gameInfo = new HBox();

        //Creating labels which show the Mines and Moves count
        Label minesLabel =  makeLabel("Mines: " + game.getMineCount(), Color.BLACK, Color.GREY, new Font("Courier New", 15));;
        minesLabel.setPrefWidth(120);
        
        movesLabel = makeLabel("Moves: " + game.getMoveCount(), Color.BLACK, Color.GREY, new Font("Courier New", 15));
        movesLabel.setPrefWidth(120);

        gameInfo.getChildren().addAll(minesLabel, movesLabel);
        
        //Grid where the player makes moves
        grid = new GridPane();

        for(int i = 0; i<ROW_COUNT; i++){
            for(int j = 0; j<COL_COUNT; j++){
                grid.add(makeGameSquare(i,j, COVERED_IMG), i, j);
            }
        }

        //Used to control reset and get hints.
        HBox gameButtons = new HBox();

        //Creating a button for Hint
        hintButton = new Button("Hint");
        hintButton.setStyle("-fx-font-size: 2em; ");
        hintButton.setPrefWidth(120);

        ArrayList<Location> safeCells = (ArrayList<Location>) game.getPossibleSelections();

        //Implementing the event handler when "Hint" button is pressed
        //Provides a hint to the user by highlighting a safe cell
        hintButton.setOnAction(value -> {
            //System.out.println("GIVE HINT");
            try{
                if(safeCells.size() != 0){
                    Location location = safeCells.remove(new Random().nextInt(safeCells.size()));

                    while(gameBoard.getCellAtLocation(location).getType() == CellType.SAFE_CHECKED){
                        location = safeCells.get(new Random().nextInt(safeCells.size()));
                    }

                    grid.add(makeGameSquare(location.getRow(), location.getCol(), HINT_IMG), location.getRow(), location.getCol());
                }
            }
            catch(MinesweeperException e){
                e.printStackTrace();
            }
        });

        //Creating a button for Hint
        Button resetButton = new Button(" Reset ");
        resetButton.setStyle("-fx-font-size: 2em; ");
        resetButton.setPrefWidth(120);

        //Implementing event handler when "Reset" button is pressed
        //Resets the game by calling the start() method
        resetButton.setOnAction(value -> {
            //System.out.println("RESET GAME");
            try {
                start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //Creating a button for Solve
        Button solveButton = new Button(" Solve ");
        solveButton.setStyle("-fx-font-size: 2em; ");

        //Implementing event handler when "Solve" button is pressed
        solveButton.setOnAction(value ->{
            //TODO
        });

        //Creating a label for the game status message display.
        statusLabel = makeLabel(" ", Color.BLACK, Color.GREY, new Font("Courier New", 15));;
        statusLabel.setPrefWidth(240);

        //Used to contain the label for game status display.
        HBox statusBar = new HBox();
        statusBar.getChildren().add(statusLabel);

        gameButtons.getChildren().addAll(hintButton, resetButton);

        gameBox.getChildren().addAll(gameInfo, grid, gameButtons, solveButton, statusBar);
        gameBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(gameBox);
        stage.setScene(scene);
        stage.setTitle("Minesweeper");
        stage.show();
    }

    /**
     * Handles updating grid buttons on click, makes selection on board.
     * @author Jack Noble, Michael McI.
     * @param button
     * @param location
     * @throws MinesweeperException
     */
    public void updateButton(Button button, Location location) throws MinesweeperException{
        //System.out.println("Updating: " + location.getRow() + ", " + location.getCol());
        
        if(gameBoard.getCellAtLocation(location).getType() == CellType.SAFE_UNCHECKED){
            game.makeSelection(location);
            //System.out.println(gameBoard.getAdjacentCells(gameBoard.getCellAtLocation(location)));
            updateMovesLabel(movesLabel);
            switch(gameBoard.getMinesAroundCell(gameBoard.getCellAtLocation(location))){
                case 1:
                    button.setGraphic(new ImageView(ONE_IMG));
                    break;
                case 2:
                    button.setGraphic(new ImageView(TWO_IMG));
                    break;
                case 3:
                    button.setGraphic(new ImageView(THREE_IMG));
                    break;
                case 4:
                    button.setGraphic(new ImageView(FOUR_IMG));
                    break;
                case 5:
                    button.setGraphic(new ImageView(FIVE_IMG));
                    break;
                case 6:
                    button.setGraphic(new ImageView(SIX_IMG));
                    break;
                case 7:
                    button.setGraphic(new ImageView(SEVEN_IMG));
                    break;
                case 8:
                    button.setGraphic(new ImageView(EIGHT_IMG));
                    break;
                default:
                    button.setGraphic(new ImageView(BLANK_IMG));
                    //System.out.println("No bombs around here :)");
            }
            //button.setGraphic(new ImageView(BLANK_IMG));
            gameBoard.getCellAtLocation(location).setType(CellType.SAFE_CHECKED);

        } else if(gameBoard.getCellAtLocation(location).getType() == CellType.BOMB_UNCHECKED){
            updateMovesLabel2(movesLabel); 

            button.setGraphic(new ImageView(MINE_IMG));
            
            gameBoard.getCellAtLocation(location).setType(CellType.BOMB_CHECKED);

            hintButton.setOnAction(value -> {
            });
            
            statusLabel.setText("Boom! Better luck next time!");
            statusLabel.setFont(new Font("Arial", 15));
            statusLabel.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY)));
            game.setGameState(GameState.LOST);
            for(int i = 0; i<ROW_COUNT; i++){
                for(int j = 0; j<COL_COUNT; j++){
                    grid.add(uncoverBoard(i, j), i, j);
                }
            }
        }

        if(isWin(gameBoard)) {
            //System.out.println("WE WON WE DID IT!");
            statusLabel.setText("You win!!!");
            statusLabel.setFont(new Font("Arial", 20));
            statusLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(10), Insets.EMPTY)));
            game.setGameState(GameState.WON);
            for(int i = 0; i<ROW_COUNT; i++){
                for(int j = 0; j<COL_COUNT; j++){
                    grid.add(uncoverBoard(i, j), i, j);
                }
            }
        }
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
     * Method to print the entire board uncovered.
     * Used for win or lose condition.
     * @author Michael McI.
     * @param row the row of the button.
     * @param col the column of the button.
     * @return string containing the entire board uncovered.
     */
    public Button uncoverBoard(int row, int col){
        Button button = new Button();
        button.setPadding(Insets.EMPTY);
        button.setPrefSize(24, 24);

        try{
            Cell currentCell = gameBoard.getCellAtLocation(new Location(row, col));

            if(currentCell.getType() == CellType.BOMB_CHECKED){
                button.setGraphic(new ImageView(MINE_IMG));

            } else if(currentCell.getType() == CellType.BOMB_UNCHECKED){
                button.setGraphic(new ImageView(MINE_IMG));

            } else if(currentCell.getType() == CellType.SAFE_UNCHECKED){
                switch(gameBoard.getMinesAroundCell(currentCell)){
                    case 1:
                        button.setGraphic(new ImageView(ONE_IMG));
                        break;
                    case 2:
                        button.setGraphic(new ImageView(TWO_IMG));
                        break;
                    case 3:
                        button.setGraphic(new ImageView(THREE_IMG));
                        break;
                    case 4:
                        button.setGraphic(new ImageView(FOUR_IMG));
                        break;
                    case 5:
                        button.setGraphic(new ImageView(FIVE_IMG));
                        break;
                    case 6:
                        button.setGraphic(new ImageView(SIX_IMG));
                        break;
                    case 7:
                        button.setGraphic(new ImageView(SEVEN_IMG));
                        break;
                    case 8:
                        button.setGraphic(new ImageView(EIGHT_IMG));
                        break;
                    default:
                        button.setGraphic(new ImageView(BLANK_IMG));
                        //System.out.println("No bombs around here :)");
                }

            } else if(currentCell.getType() == CellType.SAFE_CHECKED){
                switch(gameBoard.getMinesAroundCell(currentCell)){
                    case 1:
                        button.setGraphic(new ImageView(ONE_IMG));
                        break;
                    case 2:
                        button.setGraphic(new ImageView(TWO_IMG));
                        break;
                    case 3:
                        button.setGraphic(new ImageView(THREE_IMG));
                        break;
                    case 4:
                        button.setGraphic(new ImageView(FOUR_IMG));
                        break;
                    case 5:
                        button.setGraphic(new ImageView(FIVE_IMG));
                        break;
                    case 6:
                        button.setGraphic(new ImageView(SIX_IMG));
                        break;
                    case 7:
                        button.setGraphic(new ImageView(SEVEN_IMG));
                        break;
                    case 8:
                        button.setGraphic(new ImageView(EIGHT_IMG));
                        break;
                    default:
                        button.setGraphic(new ImageView(BLANK_IMG));
                        //System.out.println("No bombs around here :)");
                }
            }

        } catch(MinesweeperException e){
            System.out.println(e);
        }

        button.setOnAction(value -> {
            try {
                if(gameBoard.getCellAtLocation(new Location(row, col)).getType() == CellType.BOMB_UNCHECKED){
                    ;
                }

                if(gameBoard.getCellAtLocation(new Location(row, col)).getType() == CellType.BOMB_CHECKED){
                    ;
                }

                if(gameBoard.getCellAtLocation(new Location(row, col)).getType() == CellType.SAFE_UNCHECKED){
                    ;
                }
                        
            } catch (MinesweeperException e) {
                e.printStackTrace();
            }
        });
        
        return button;
    }

    /**
     * Method which updates the move count every time a cell is selected by user
     * @author Bibhash
     * @param movesLabel
     */
    private void updateMovesLabel(Label movesLabel){
        movesLabel.setText("Moves: " + game.getMoveCount());
    }

    /**
     * Method which updates the move count when a mine is selected
     * @author Bibhash
     * @param movesLabel
     */
    private void updateMovesLabel2(Label movesLabel){
        int count = game.getMoveCount() + 1;
        movesLabel.setText("Moves: " + count);
    }

    /**
     * Method which represents a cell as a button given its location and an image
     * @param row
     * @param col
     * @param image
     * @return
     */
    private Button makeGameSquare(int row, int col, Image image){
        Button button = new Button();
        button.setGraphic(new ImageView(image));
        button.setPadding(Insets.EMPTY);
        button.setPrefSize(24, 24);

        //OMG IT'S A LAMBDA - Jack
        button.setOnAction(value -> {
            try {
                updateButton(button, new Location(row, col));
                
            } catch (MinesweeperException e) {
                e.printStackTrace();
            }
        });

        return button;
    }

    /**
     * Used to make a move on the board through the Minesweeper(game) object
     * THIS COULD BE NOT USED IN THE FINAL GAME, use it as a helper function!!!!
     * makeMove(new Location(1,2))
     * @param location location that the player clicks
     */
    public void makeMove(minesweeper.model.Location location){
        try {
            game.makeSelection(location);
            
        } catch (MinesweeperException e){
            System.out.println("BAD MOVE TRY AGAIN");
        }
    }

    /**
     * Method which creates a Label.
     * @param text
     * @param textColor
     * @param bgColor
     * @param font
     * @param pos
     * @return
     */
    public static Label makeLabel(String text, Color textColor, Color bgColor, Font font){
        Label label = new Label(text);
        label.setFont(font);
        label.setTextFill(textColor);
        label.setPadding(new Insets(10));
        label.setBorder(new Border(new BorderStroke(textColor, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderStroke.THIN)));
        label.setBackground(new Background(new BackgroundFill(bgColor, new CornerRadii(10), Insets.EMPTY)));
        label.setAlignment(Pos.CENTER);
        label.setMaxHeight(Double.POSITIVE_INFINITY);
        label.setMaxWidth(Double.POSITIVE_INFINITY);

        return label;
    }

    public static void main(String[] args) {
        launch(args);
    }
}