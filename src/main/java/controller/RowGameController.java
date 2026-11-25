package controller;

import model.RowGameModel;
import view.RowGameGUI;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

/**
 * Controller mediating user actions between the game model and the active rules strategy.
 */
public class RowGameController{
    private RowGameRulesStrategy gameRule;

    /**
     * Creates a new controller for the given model and rule strategy, resetting the game.
     *
     * @param gameModel The game model to manage.
     * @param gameRule The rules strategy to apply.
     */
    public RowGameController(RowGameModel gameModel, RowGameRulesStrategy gameRule){
        this.gameRule = gameRule;
        reset(gameModel);
    }

    /**
     * Resets the game state according to the active rules strategy.
     *
     * @param gameModel The game model to reset.
     */
    public void reset(RowGameModel gameModel) {
        gameRule.reset(gameModel);
    }

    /**
     * Executes a move at the specified board coordinates and updates win/draw/turn state.
     *
     * @param gameModel The game model to update.
     * @param row The zero-based row index of the move.
     * @param col The zero-based column index of the move.
     */
    public void move(RowGameModel gameModel, int row, int col) {
        gameRule.move(gameModel, row, col);
        if(gameRule.isWin(gameModel)) {
            gameModel.playerWins(); endGame(gameModel);
        }
        else if(gameRule.isTie(gameModel)) {
            gameModel.gameDraw(); endGame(gameModel);
        }
        else gameModel.nextTurn();
    }

    /**
     * Ends the game by disabling legal moves on all board blocks.
     *
     * @param gameModel The game model whose board should be locked.
     */
    public void endGame(RowGameModel gameModel) {
        for(int row = 0;row < 3;row++) {
            for(int col = 0;col < 3;col++) {
                gameModel.blocksData[row][col].setIsLegalMove(false);
            }
        }
    }

    /**
     * Returns the display name of the active rules strategy.
     *
     * @return The name of the current game mode.
     */
    @Override
    public String toString() {
        return gameRule.toString();
    }
}
