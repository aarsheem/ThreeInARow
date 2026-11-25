package controller;

import model.RowGameModel;

/**
 * Rules strategy for classic Tic-Tac-Toe, where any empty cell is a legal move.
 */
public class RowGameRulesTicTacToe extends RowGameRulesStrategy{
    /**
     * Resets all blocks and marks every cell on the board as a legal move.
     *
     * @param gameModel The game model to reset.
     */
    @Override
    public void reset(RowGameModel gameModel) {
        for(int row = 0;row<3;row++) {
            for(int col = 0;col<3;col++) {
                gameModel.blocksData[row][col].reset();
                gameModel.blocksData[row][col].setIsLegalMove(true);
            }
        }
        gameModel.reset();
    }

    /**
     * Places the current player's mark at the specified position and disables further moves there.
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
    }

    /**
     * Returns the display name of this game mode.
     *
     * @return {@code "Tic Tac Toe"}.
     */
    @Override
    public String toString() {
        return "Tic Tac Toe";
    }
}
