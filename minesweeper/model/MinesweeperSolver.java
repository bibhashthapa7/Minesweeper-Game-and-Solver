package minesweeper.model;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;


import backtracker.Configuration;

public class MinesweeperSolver implements Configuration{
    private Minesweeper minesweeper;
    private final ArrayList<Location> previousMoves;
    

    public MinesweeperSolver(Minesweeper minesweeper){
        //Uses defult 10 by 10 size;
        this.minesweeper = minesweeper;
        this.previousMoves = new ArrayList<>();

    };

    public MinesweeperSolver(Minesweeper minesweeper, ArrayList<Location> previousMoves){
        this.minesweeper = minesweeper;
        this.previousMoves = previousMoves;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        List<Configuration> successors = new ArrayList<>();

        //Look through ever location on the board
        for(Location location : minesweeper.getAllPossibleSelections()){

            //Create new instances of these things for use in the algorithm
            Minesweeper game = new Minesweeper(minesweeper);
            MinesweeperSolver thisConfiguration = new MinesweeperSolver(game, previousMoves);

            //Is used to make sure that the 
            try {
                game.makeSelection(location);
                //error would occur here, making the code below unreached...
                //Check to see if the move wasnt a mine, if it was, dont bother adding it to the config
                if(game.getGameState() != GameState.LOST){
                    thisConfiguration.previousMoves.add(location);
                    successors.add(thisConfiguration);
                }
            } catch(MinesweeperException e) {
                if(game.getGameState() == GameState.WON){
                    thisConfiguration.previousMoves.add(location);
                    successors.add(thisConfiguration);
                }
            }
        }
        return successors;
    }

    @Override
    public boolean isValid() {
        // this should return true if the move made was accepted (returned true
        // and the game is not lost)
        if(minesweeper.getGameState() != GameState.LOST){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isGoal() {
        return minesweeper.getGameState() == GameState.WON;
    }

    @Override
    public String toString(){
        return minesweeper.toString();
    }


    public List<Location> getPreviousMoves(){
        return this.previousMoves;
    }

    // public static void main(String[] args) {
    //     //TO DO
    //     Board board = new Board(10,10);
    //     Minesweeper game = new Minesweeper(10, 10, board);
    //     MinesweeperSolver solver = new MinesweeperSolver(game);
    //     Backtracker bt = new Backtracker(true);
    //     MinesweeperSolver solution = (MinesweeperSolver) bt.solve(solver);

    //     if(solution == null){
    //         System.out.println("No solution");
    //     }
    //     else{
    //         System.out.println(solution);
    //     }
    // }
    
    
}
