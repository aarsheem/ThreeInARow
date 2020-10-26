import controller.RowGameController;
import controller.RowGameRulesStrategy;
import controller.RowGameRulesThreeInARow;
import controller.RowGameRulesTicTacToe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.RowBlockModel;
import model.RowGameModel;
import view.RowGameGUI;

import javax.swing.*;


/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
    private RowGameModel mThreeInARow, mTicTacToe;
    private RowGameRulesStrategy threeInARow;
    private RowGameRulesStrategy ticTacToe;
    private RowGameController cTicTacToe;
    private RowGameController cThreeInARow;
    private RowGameGUI gThreeInARow, gTicTacToe;

    @Before
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

    @After
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
        assertEquals (RowGameModel.Player.Player1, mThreeInARow.getPlayer());
        assertEquals (9, mThreeInARow.movesLeft);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockViolatesPrecondition() {
	    RowBlockModel block = new RowBlockModel(null);
    }

    @Test
    public void testControllerMove(){
        cTicTacToe.move(mThreeInARow, 0, 0);
        assertEquals(mThreeInARow.blocksData[0][0].getIsLegalMove(),false);
        assertEquals(mThreeInARow.blocksData[0][0].getContents(), "X");
    }

    @Test
    public void testModelNextTurn(){
        RowGameModel.Player player = mThreeInARow.getPlayer();
        mThreeInARow.nextTurn();
        assertNotEquals(player, mThreeInARow.getPlayer());
        mThreeInARow.nextTurn();
        assertEquals(player, mThreeInARow.getPlayer());
    }

    @Test
    public void testView(){
        assertEquals(gTicTacToe.gameStatusView.playerturn.getText(), "Player 1 to play 'X'");
        for(int i = 0; i < 3; i++) for(int j = 0; j < 3; j++)
            assertEquals(gTicTacToe.gameBoardView.blocks[i][j].getText(), "");
    }

    @Test
    public void testTicTacIllegal(){
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        assertEquals(mTicTacToe.blocksData[0][0].getContents(), "X");
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        assertEquals(mTicTacToe.blocksData[0][0].getContents(), "X");
    }

    @Test
    public void testTicTacLegal(){
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        assertEquals(mTicTacToe.blocksData[0][0].getContents(), "X");
        gTicTacToe.gameBoardView.blocks[0][1].doClick();
        assertEquals(mTicTacToe.blocksData[0][1].getContents(), "O");
    }

    @Test
    public void testTicTacPlayer1Wins(){
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        gTicTacToe.gameBoardView.blocks[1][0].doClick();
        gTicTacToe.gameBoardView.blocks[0][1].doClick();
        gTicTacToe.gameBoardView.blocks[2][0].doClick();
        gTicTacToe.gameBoardView.blocks[0][2].doClick();
        assertEquals(mTicTacToe.getFinalResult(), "Player 1 wins!");
    }

    @Test
    public void testTicTacDraw(){
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        gTicTacToe.gameBoardView.blocks[0][1].doClick();
        gTicTacToe.gameBoardView.blocks[0][2].doClick();
        gTicTacToe.gameBoardView.blocks[2][0].doClick();
        gTicTacToe.gameBoardView.blocks[2][1].doClick();
        gTicTacToe.gameBoardView.blocks[2][2].doClick();
        gTicTacToe.gameBoardView.blocks[1][0].doClick();
        gTicTacToe.gameBoardView.blocks[1][1].doClick();
        gTicTacToe.gameBoardView.blocks[1][2].doClick();
        assertEquals(mTicTacToe.getFinalResult(), "Game ends in a draw");
    }
    
    @Test
    public void testTicTacReset(){
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        cTicTacToe.reset(mTicTacToe);
        assertEquals(mTicTacToe.movesLeft, 9);
        assertEquals(mTicTacToe.blocksData[0][0].getContents(), "");
    }

    @Test
    public void testThreeInARowIllegal(){
        gThreeInARow.gameBoardView.blocks[1][1].doClick();
        assertEquals(mThreeInARow.blocksData[1][1].getIsLegalMove(), false);
        assertEquals(mThreeInARow.blocksData[1][1].getContents(), "");

    }

    @Test
    public void testThreeInARowLegal(){
        gThreeInARow.gameBoardView.blocks[2][2].doClick();
        assertEquals(mThreeInARow.blocksData[2][2].getContents(), "X");
    }

    @Test
    public void testThreeInARowPlayer1Wins(){
        gThreeInARow.gameBoardView.blocks[2][0].doClick();
        gThreeInARow.gameBoardView.blocks[2][1].doClick();
        gThreeInARow.gameBoardView.blocks[1][0].doClick();
        gThreeInARow.gameBoardView.blocks[1][1].doClick();
        gThreeInARow.gameBoardView.blocks[0][0].doClick();
        assertEquals(mThreeInARow.getFinalResult(), "Player 1 wins!");
    }

    @Test
    public void testThreeInARowDraw(){
        gThreeInARow.gameBoardView.blocks[2][0].doClick();
        gThreeInARow.gameBoardView.blocks[2][1].doClick();
        gThreeInARow.gameBoardView.blocks[1][0].doClick();
        gThreeInARow.gameBoardView.blocks[1][1].doClick();
        gThreeInARow.gameBoardView.blocks[0][1].doClick();
        gThreeInARow.gameBoardView.blocks[0][0].doClick();
        gThreeInARow.gameBoardView.blocks[2][2].doClick();
        gThreeInARow.gameBoardView.blocks[1][2].doClick();
        gThreeInARow.gameBoardView.blocks[0][2].doClick();
        assertEquals(mThreeInARow.getFinalResult(), "Game ends in a draw");
    }

    @Test
    public void testThreeInARowReset(){
        gThreeInARow.gameBoardView.blocks[0][0].doClick();
        cThreeInARow.reset(mThreeInARow);
        assertEquals(mThreeInARow.movesLeft, 9);
        assertEquals(mThreeInARow.blocksData[0][0].getContents(), "");
    }
}
