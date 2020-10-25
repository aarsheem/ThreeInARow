package controller;

import model.RowGameModel;

public class RowGameRulesThreeInARow extends RowGameRulesStrategy {
    @Override
    public void reset(RowGameModel gameModel) {
        for(int row = 0;row<3;row++) {
            for(int col = 0;col<3;col++) {
                gameModel.blocksData[row][col].reset();
                gameModel.blocksData[row][col].setIsLegalMove(row == 2);
            }
        }
        gameModel.reset();
    }

    @Override
    public void move(RowGameModel gameModel, int row, int col) {
        gameModel.movesLeft = gameModel.movesLeft - 1;
        RowGameModel.Player player = gameModel.getPlayer();

        gameModel.blocksData[row][col].setContents(player.mark());
        gameModel.blocksData[row][col].setIsLegalMove(false);
        if(row != 0) gameModel.blocksData[row-1][col].setIsLegalMove(true);//nextturn, draw, win
    }
}
