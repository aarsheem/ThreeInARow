import controller.RowGameController;
import controller.RowGameRulesThreeInARow;
import controller.RowGameRulesTicTacToe;
import model.RowBlockModel;
import model.RowGameModel;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Additional unit tests covering core board, model, and rule logic.
 */
public class RowGameLogicTest {

    @Test
    public void testBlockModelGettersSettersAndListeners() {
        RowGameModel model = new RowGameModel();
        RowBlockModel block = model.blocksData[0][0];

        assertSame(model, block.getGame());
        assertThrows(IllegalArgumentException.class, () -> block.setContents(null));

        AtomicBoolean notified = new AtomicBoolean(false);
        PropertyChangeListener listener = evt -> notified.set(true);
        block.addPropertyChangeListener(listener);
        block.setContents("X");
        assertTrue(notified.get());

        notified.set(false);
        block.removePropertyChangeListener(listener);
        block.setContents("O");
        assertFalse(notified.get());
    }

    @Test
    public void testModelListenersAndOngoingResult() {
        RowGameModel model = new RowGameModel();
        assertNull(model.getFinalResult());

        AtomicBoolean notified = new AtomicBoolean(false);
        PropertyChangeListener listener = evt -> notified.set(true);
        model.addPropertyChangeListener(listener);
        model.nextTurn();
        assertTrue(notified.get());

        notified.set(false);
        model.removePropertyChangeListener(listener);
        model.nextTurn();
        assertFalse(notified.get());
    }

    @Test
    public void testTicTacToePlayer2WinsMainDiagonal() {
        RowGameModel model = new RowGameModel();
        RowGameController controller = new RowGameController(model, new RowGameRulesTicTacToe());

        controller.move(model, 0, 1); // X
        controller.move(model, 0, 0); // O
        controller.move(model, 0, 2); // X
        controller.move(model, 1, 1); // O
        controller.move(model, 1, 2); // X
        controller.move(model, 2, 2); // O wins diagonal

        assertEquals("Player 2 wins!", model.getFinalResult());
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertFalse(model.blocksData[r][c].getIsLegalMove());
            }
        }
    }

    @Test
    public void testTicTacToePlayer1WinsAntiDiagonal() {
        RowGameModel model = new RowGameModel();
        RowGameController controller = new RowGameController(model, new RowGameRulesTicTacToe());

        controller.move(model, 0, 2); // X
        controller.move(model, 0, 0); // O
        controller.move(model, 1, 1); // X
        controller.move(model, 1, 0); // O
        controller.move(model, 2, 0); // X wins anti-diagonal

        assertEquals("Player 1 wins!", model.getFinalResult());
    }

    @Test
    public void testThreeInARowInitialLegalMovesAndUnlockingAbove() {
        RowGameModel model = new RowGameModel();
        RowGameController controller = new RowGameController(model, new RowGameRulesThreeInARow());

        for (int c = 0; c < 3; c++) {
            assertFalse(model.blocksData[0][c].getIsLegalMove());
            assertFalse(model.blocksData[1][c].getIsLegalMove());
            assertTrue(model.blocksData[2][c].getIsLegalMove());
        }

        controller.move(model, 2, 1);
        assertFalse(model.blocksData[2][1].getIsLegalMove());
        assertTrue(model.blocksData[1][1].getIsLegalMove());
        assertFalse(model.blocksData[0][1].getIsLegalMove());
    }

    @Test
    public void testControllerToString() {
        RowGameModel model = new RowGameModel();
        RowGameController ticTacToeController = new RowGameController(model, new RowGameRulesTicTacToe());
        RowGameController threeInARowController = new RowGameController(model, new RowGameRulesThreeInARow());

        assertEquals("Tic Tac Toe", ticTacToeController.toString());
        assertEquals("Three In A Row", threeInARowController.toString());
    }
}
