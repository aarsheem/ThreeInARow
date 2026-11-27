import controller.RowGameController;
import controller.RowGameRulesStrategy;
import controller.RowGameRulesThreeInARow;
import controller.RowGameRulesTicTacToe;
import model.RowBlockModel;
import model.RowGameModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import view.RowGameGUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas unitarias de las variantes Tres en raya y Tic-Tac-Toe,
 * incluyendo el modelo, los controladores y la interfaz grafica.
 */
@Tag("unit")
public class TestExample {
    private RowGameModel mThreeInARow;
    private RowGameModel mTicTacToe;
    private RowGameRulesStrategy threeInARow;
    private RowGameRulesStrategy ticTacToe;
    private RowGameController cTicTacToe;
    private RowGameController cThreeInARow;
    private RowGameGUI gThreeInARow;
    private RowGameGUI gTicTacToe;

    /**
     * Crea modelos, estrategias, controladores y vistas antes de cada prueba.
     */
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

    /**
     * Libera las referencias creadas durante una prueba.
     */
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

    /**
     * Comprueba el estado inicial de una partida nueva.
     */
    @Test
    @Tag("model")
    @DisplayName("Una partida nueva empieza con el jugador 1 y nueve movimientos")
    public void testNewGame() {
        assertEquals(RowGameModel.Player.Player1, mThreeInARow.getPlayer());
        assertEquals(9, mThreeInARow.movesLeft);
    }

    /**
     * Comprueba que no se puede crear un bloque sin un modelo asociado.
     */
    @Test
    @Tag("model")
    @DisplayName("Crear un bloque sin juego lanza una excepcion")
    public void testNewBlockViolatesPrecondition() {
        assertThrows(IllegalArgumentException.class, () -> new RowBlockModel(null));
    }

    /**
     * Comprueba que un movimiento realizado por el controlador
     * coloca la ficha y bloquea la casilla correspondiente.
     */
    @Test
    @Tag("controller")
    @Tag("tic-tac-toe")
    @DisplayName("Mover en una casilla la marca como usada")
    public void testControllerMove() {
        cTicTacToe.move(mThreeInARow, 0, 0);
        assertFalse(mThreeInARow.blocksData[0][0].getIsLegalMove());
        assertEquals("X", mThreeInARow.blocksData[0][0].getContents());
    }

    /**
     * Comprueba que el modelo alterna los jugadores al cambiar de turno.
     */
    @Test
    @Tag("model")
    @DisplayName("Cambiar de turno alterna el jugador")
    public void testModelNextTurn() {
        RowGameModel.Player player = mThreeInARow.getPlayer();
        mThreeInARow.nextTurn();
        assertNotEquals(player, mThreeInARow.getPlayer());
        mThreeInARow.nextTurn();
        assertEquals(player, mThreeInARow.getPlayer());
    }

    /**
     * Comprueba que la vista inicial muestra el turno del jugador 1
     * y deja todas las casillas vacias.
     */
    @Test
    @Tag("view")
    @Tag("tic-tac-toe")
    @DisplayName("La vista inicial muestra el turno y un tablero vacio")
    public void testView() {
        assertEquals("Player 1 to play 'X'", gTicTacToe.gameStatusView.playerturn.getText());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals("", gTicTacToe.gameBoardView.blocks[i][j].getText());
            }
        }
    }

    /**
     * Comprueba que pulsar dos veces una casilla ocupada en Tic-Tac-Toe
     * no modifica su ficha.
     */
    @Test
    @Tag("view")
    @Tag("tic-tac-toe")
    @DisplayName("Tic-Tac-Toe impide pulsar dos veces la misma casilla")
    public void testTicTacIllegal() {
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        assertEquals("X", mTicTacToe.blocksData[0][0].getContents());
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        assertEquals("X", mTicTacToe.blocksData[0][0].getContents());
    }

    /**
     * Comprueba que Tic-Tac-Toe coloca X y O alternativamente
     * en movimientos legales consecutivos.
     */
    @Test
    @Tag("view")
    @Tag("tic-tac-toe")
    @DisplayName("Tic-Tac-Toe alterna X y O en movimientos legales")
    public void testTicTacLegal() {
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        assertEquals("X", mTicTacToe.blocksData[0][0].getContents());
        gTicTacToe.gameBoardView.blocks[0][1].doClick();
        assertEquals("O", mTicTacToe.blocksData[0][1].getContents());
    }

    /**
     * Comprueba que el primer jugador gana Tic-Tac-Toe
     * completando la primera fila.
     */
    @Test
    @Tag("view")
    @Tag("tic-tac-toe")
    @Tag("win")
    @DisplayName("El jugador 1 gana una partida de Tic-Tac-Toe")
    public void testTicTacPlayer1Wins() {
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        gTicTacToe.gameBoardView.blocks[1][0].doClick();
        gTicTacToe.gameBoardView.blocks[0][1].doClick();
        gTicTacToe.gameBoardView.blocks[2][0].doClick();
        gTicTacToe.gameBoardView.blocks[0][2].doClick();
        assertEquals("Player 1 wins!", mTicTacToe.getFinalResult());
    }

    /**
     * Comprueba que una partida completa de Tic-Tac-Toe sin un ganador
     * termina en empate.
     */
    @Test
    @Tag("view")
    @Tag("tic-tac-toe")
    @Tag("draw")
    @DisplayName("Tic-Tac-Toe puede terminar en empate")
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

    /**
     * Comprueba que reiniciar Tic-Tac-Toe restaura los movimientos
     * disponibles y vacia una casilla ocupada.
     */
    @Test
    @Tag("view")
    @Tag("tic-tac-toe")
    @Tag("reset")
    @DisplayName("Resetear Tic-Tac-Toe restaura el tablero")
    public void testTicTacReset() {
        gTicTacToe.gameBoardView.blocks[0][0].doClick();
        cTicTacToe.reset(mTicTacToe);
        assertEquals(9, mTicTacToe.movesLeft);
        assertEquals("", mTicTacToe.blocksData[0][0].getContents());
    }

    /**
     * Comprueba que una casilla bloqueada no acepta movimientos
     * en la variante Tres en raya.
     */
    @Test
    @Tag("view")
    @Tag("three-in-a-row")
    @DisplayName("Tres en raya impide movimientos ilegales")
    public void testThreeInARowIllegal() {
        gThreeInARow.gameBoardView.blocks[1][1].doClick();
        assertFalse(mThreeInARow.blocksData[1][1].getIsLegalMove());
        assertEquals("", mThreeInARow.blocksData[1][1].getContents());
    }

    /**
     * Comprueba que se puede colocar una ficha en una casilla
     * legal de la fila inferior.
     */
    @Test
    @Tag("view")
    @Tag("three-in-a-row")
    @DisplayName("Tres en raya acepta movimientos en la fila inferior")
    public void testThreeInARowLegal() {
        gThreeInARow.gameBoardView.blocks[2][2].doClick();
        assertEquals("X", mThreeInARow.blocksData[2][2].getContents());
    }

    /**
     * Comprueba que el primer jugador gana Tres en raya
     * formando una columna con sus fichas.
     */
    @Test
    @Tag("view")
    @Tag("three-in-a-row")
    @Tag("win")
    @DisplayName("El jugador 1 gana una partida de Tres en raya")
    public void testThreeInARowPlayer1Wins() {
        gThreeInARow.gameBoardView.blocks[2][0].doClick();
        gThreeInARow.gameBoardView.blocks[2][1].doClick();
        gThreeInARow.gameBoardView.blocks[1][0].doClick();
        gThreeInARow.gameBoardView.blocks[1][1].doClick();
        gThreeInARow.gameBoardView.blocks[0][0].doClick();
        assertEquals("Player 1 wins!", mThreeInARow.getFinalResult());
    }

    /**
     * Comprueba que una partida completa de Tres en raya sin un ganador
     * termina en empate.
     */
    @Test
    @Tag("view")
    @Tag("three-in-a-row")
    @Tag("draw")
    @DisplayName("Tres en raya puede terminar en empate")
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

    /**
     * Comprueba que reiniciar Tres en raya restaura los movimientos
     * disponibles y vacia una casilla ocupada.
     */
    @Test
    @Tag("view")
    @Tag("three-in-a-row")
    @Tag("reset")
    @DisplayName("Resetear Tres en raya restaura el tablero")
    public void testThreeInARowReset() {
        gThreeInARow.gameBoardView.blocks[0][0].doClick();
        cThreeInARow.reset(mThreeInARow);
        assertEquals(9, mThreeInARow.movesLeft);
        assertEquals("", mThreeInARow.blocksData[0][0].getContents());
    }
}
