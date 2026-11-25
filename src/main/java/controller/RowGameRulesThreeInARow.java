package controller;

import model.RowGameModel;

/**
 * Rules strategy for the Three In A Row game, where pieces stack from the bottom row upwards.
 */
public class RowGameRulesThreeInARow extends RowGameRulesStrategy {
    /**
     * Resets all blocks and marks only the bottom row as legal initial moves.
     *
     * @param gameModel The game model to reset.
     */
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

    /**
     * Places the current player's mark at the specified position and unlocks the cell directly above.
     *
     * @param gameModel The game model to update.
     * @param row The zero-based row index of the move.
     * @param col The zero-based column index of the move.
     */
    @Override
    public void move(RowGameModel gameModel, int row, int col) {
        gameModel.movesLeft = gameModel.movesLeft - 1;
        RowGameModel.Player player = gameModel.getPlayer();

        gameModel.blocksData[row][col].setContents(player.mark());
        gameModel.blocksData[row][col].setIsLegalMove(false);
        if(row != 0) gameModel.blocksData[row-1][col].setIsLegalMove(true);//nextturn, draw, win
    }

    /**
     * Returns the display name of this game mode.
     *
     * @return {@code "Three In A Row"}.
     */
    @Override
    public String toString() {
        return "Three In A Row";
    }
}
