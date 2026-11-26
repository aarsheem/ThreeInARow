import controller.RowGameController;
import controller.RowGameRulesStrategy;
import controller.RowGameRulesThreeInARow;
import controller.RowGameRulesTicTacToe;
import model.RowBlockModel;
import model.RowGameModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.RowGameGUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for ThreeInARow and TicTacToe game modes using JUnit 5.
 */
public class TestExample {
    private RowGameModel mThreeInARow;
    private RowGameModel mTicTacToe;
    private RowGameRulesStrategy threeInARow;
    private RowGameRulesStrategy ticTacToe;
    private RowGameController cTicTacToe;
    private RowGameController cThreeInARow;
    private RowGameGUI gThreeInARow;
    private RowGameGUI gTicTacToe;

    @BeforeEach
    public void setUp() {
        mThreeInARow = new RowGameModel();
        mTicTacToe = new RowGameModel();
        threeInARow = new RowGameRulesThreeInARow();
        ticTacToe = new RowGameRulesTicTacToe();
        cThreeInARow = new RowGameController(mThreeInARow, threeInARow);
        cTicTacToe = new RowGameController(mTicTacToe, ticTacToe);
        gThreeInARow = new RowGameGUI(cThreeInARow, mThreeInARow);
        gTicTacToe = new RowGameGUI(cTicTacToe, mTicTacToe);
    }

    @AfterEach
    public void tearDown() {
        mTicTacToe = null;
        mThreeInARow = null;
        threeInARow = null;
        ticTacToe = null;
        cTicTacToe = null;
        cThreeInARow = null;
        gThreeInARow = null;
        gTicTacToe = null;
    }

    @Test
    public void testNewGame() {
        assertEquals(RowGameModel.Player.Player1, mThreeInARow.getPlayer());
        assertEquals(9, mThreeInARow.movesLeft);
    }

    @Test
    public void testNewBlockViolatesPrecondition() {
        assertThrows(IllegalArgumentException.class, () -> new RowBlockModel(null));
    }

    @Test
    public void testControllerMove() {
        cTicTacToe.move(mThreeInARow, 0, 0);
        assertFalse(mThreeInARow.blocksData[0][0].getIsLegalMove());
        assertEquals("X", mThreeInARow.blocksData[0][0].getContents());
    }

    @Test
    public void testModelNextTurn() {
        RowGameModel.Player player = mThreeInARow.getPlayer();
        mThreeInARow.nextTurn();
        assertNotEquals(player, mThreeInARow.getPlayer());
        mThreeInARow.nextTurn();
        assertEquals(player, mThreeInARow.getPlayer());
    }

    @Test
    public void testView() {
        assertEquals("Player 1 to play 'X'", gTicTacToe.gameStatusView.playerturn.getText());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals("", gTicTacToe.gameBoardView.blocks[i][j].getText());
            }
        }
    }

    @Test
    public void testTicTacIllegal() {
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        assertEquals("X", mTicTacToe.blocksData[0][0].getContents());
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        assertEquals("X", mTicTacToe.blocksData[0][0].getContents());
    }

    @Test
    public void testTicTacLegal() {
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        assertEquals("X", mTicTacToe.blocksData[0][0].getContents());
        gTicTacToe.gameBoardView.blocks[0][1].doClick();
        assertEquals("O", mTicTacToe.blocksData[0][1].getContents());
    }

    @Test
    public void testTicTacPlayer1Wins() {
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        gTicTacToe.gameBoardView.blocks[1][0].doClick();
        gTicTacToe.gameBoardView.blocks[0][1].doClick();
        gTicTacToe.gameBoardView.blocks[2][0].doClick();
        gTicTacToe.gameBoardView.blocks[0][2].doClick();
        assertEquals("Player 1 wins!", mTicTacToe.getFinalResult());
    }

    @Test
    public void testTicTacDraw() {
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        gTicTacToe.gameBoardView.blocks[0][1].doClick();
        gTicTacToe.gameBoardView.blocks[0][2].doClick();
        gTicTacToe.gameBoardView.blocks[2][0].doClick();
        gTicTacToe.gameBoardView.blocks[2][1].doClick();
        gTicTacToe.gameBoardView.blocks[2][2].doClick();
        gTicTacToe.gameBoardView.blocks[1][0].doClick();
        gTicTacToe.gameBoardView.blocks[1][1].doClick();
        gTicTacToe.gameBoardView.blocks[1][2].doClick();
        assertEquals("Game ends in a draw", mTicTacToe.getFinalResult());
    }

    @Test
    public void testTicTacReset() {
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        cTicTacToe.reset(mTicTacToe);
        assertEquals(9, mTicTacToe.movesLeft);
        assertEquals("", mTicTacToe.blocksData[0][0].getContents());
    }

    @Test
    public void testThreeInARowIllegal() {
        gThreeInARow.gameBoardView.blocks[1][1].doClick();
        assertFalse(mThreeInARow.blocksData[1][1].getIsLegalMove());
        assertEquals("", mThreeInARow.blocksData[1][1].getContents());
    }

    @Test
    public void testThreeInARowLegal() {
        gThreeInARow.gameBoardView.blocks[2][2].doClick();
        assertEquals("X", mThreeInARow.blocksData[2][2].getContents());
    }

    @Test
    public void testThreeInARowPlayer1Wins() {
        gThreeInARow.gameBoardView.blocks[2][0].doClick();
        gThreeInARow.gameBoardView.blocks[2][1].doClick();
        gThreeInARow.gameBoardView.blocks[1][0].doClick();
        gThreeInARow.gameBoardView.blocks[1][1].doClick();
        gThreeInARow.gameBoardView.blocks[0][0].doClick();
        assertEquals("Player 1 wins!", mThreeInARow.getFinalResult());
    }

    @Test
    public void testThreeInARowDraw() {
        gThreeInARow.gameBoardView.blocks[2][0].doClick();
        gThreeInARow.gameBoardView.blocks[2][1].doClick();
        gThreeInARow.gameBoardView.blocks[1][0].doClick();
        gThreeInARow.gameBoardView.blocks[1][1].doClick();
        gThreeInARow.gameBoardView.blocks[0][1].doClick();
        gThreeInARow.gameBoardView.blocks[0][0].doClick();
        gThreeInARow.gameBoardView.blocks[2][2].doClick();
        gThreeInARow.gameBoardView.blocks[1][2].doClick();
        gThreeInARow.gameBoardView.blocks[0][2].doClick();
        assertEquals("Game ends in a draw", mThreeInARow.getFinalResult());
    }

    @Test
    public void testThreeInARowReset() {
        gThreeInARow.gameBoardView.blocks[0][0].doClick();
        cThreeInARow.reset(mThreeInARow);
        assertEquals(9, mThreeInARow.movesLeft);
        assertEquals("", mThreeInARow.blocksData[0][0].getContents());
    }
}
