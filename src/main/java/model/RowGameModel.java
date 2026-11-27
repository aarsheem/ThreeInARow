package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Model representing the 3x3 board state, active player, remaining moves, and game outcome.
 */
public class RowGameModel {
    /**
     * 3x3 matrix of board block models.
     */
    public RowBlockModel[][] blocksData = new RowBlockModel[3][3];
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    /**
     * Number of remaining moves before the board is full.
     */
    public int movesLeft;
    private Player player;
    private Result result;

    /**
     * Represents the two players in the game.
     */
    public enum Player {
        /**
         * First player, using mark {@code "X"}.
         */
        Player1,
        /**
         * Second player, using mark {@code "O"}.
         */
        Player2;

        /**
         * Returns the other player for the next turn.
         *
         * @return The next {@link Player}.
         */
        public Player nextTurn() {
            if (this == Player1) {
                return Player2;
            }
            return Player1;
        }

        /**
         * Returns the human-readable name of this player.
         *
         * @return {@code "Player 1"} or {@code "Player 2"}.
         */
        @Override
        public String toString() {
            if (this == Player1) {
                return "Player 1";
            }
            return "Player 2";
        }

        /**
         * Returns the board mark symbol associated with this player.
         *
         * @return {@code "X"} for Player 1 or {@code "O"} for Player 2.
         */
        public String mark() {
            if (this == Player1) {
                return "X";
            }
            return "O";
        }
    }

    private enum Result {
        Ongoing, Draw, Win;

        public String toString(Player player) {
            if (this == Ongoing) {
                return null;
            }
            if (this == Draw) {
                return "Game ends in a draw";
            }
            return player.toString() + " wins!";
        }
    }

    /**
     * Initializes a new 3x3 board of {@link RowBlockModel} instances and resets the game state.
     */
    public RowGameModel() {
        super();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                blocksData[row][col] = new RowBlockModel(this);
            }
        }
        reset();
    }

    /**
     * Registers a listener for game-level property change events.
     *
     * @param listener The listener to register.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    /**
     * Unregisters a previously added game-level property change listener.
     *
     * @param listener The listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    /**
     * Returns the final result message if the game has ended, or {@code null} if ongoing.
     *
     * @return The result string, or {@code null} while the game is in progress.
     */
    public String getFinalResult() {
        return result.toString(player);
    }

    /**
     * Marks the game as a draw and notifies registered listeners.
     */
    public void gameDraw() {
        result = Result.Draw;
        setFinalResult(result.toString(player));
    }

    /**
     * Marks the current player as the winner and notifies registered listeners.
     */
    public void playerWins() {
        result = Result.Win;
        setFinalResult(result.toString(player));
    }

    private void setFinalResult(String finalResult) {
        pcs.firePropertyChange("result", null, finalResult);
    }

    /**
     * Returns the player whose turn it currently is.
     *
     * @return The active {@link Player}.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Advances to the next player's turn and notifies registered listeners.
     */
    public void nextTurn() {
        player = player.nextTurn();
        pcs.firePropertyChange("player", null, player);
    }

    /**
     * Resets the move counter, result, and active player back to Player 1.
     */
    public void reset() {
        movesLeft = 9;
        result = Result.Ongoing;
        player = Player.Player2;
        nextTurn();
    }
}
