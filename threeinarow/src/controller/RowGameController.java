package controller;

import model.RowGameModel;
import view.RowGameGUI;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class RowGameController{
    private RowGameRulesStrategy gameRule;
    public RowGameController(RowGameModel gameModel, RowGameRulesStrategy gameRule){
        this.gameRule = gameRule;
        reset(gameModel);
    }

    public void reset(RowGameModel gameModel) {
        gameRule.reset(gameModel);
    }

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


    public void endGame(RowGameModel gameModel) {
        for(int row = 0;row < 3;row++) {
            for(int col = 0;col < 3;col++) {
                gameModel.blocksData[row][col].setIsLegalMove(false);
            }
        }
    }
}
