package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The RowBlockModel class represents a given block in the game.
 */
public class RowBlockModel {
    /**
     * The game that contains this block.
     */
    private RowGameModel game;

    /**
     * The current value of the contents of this block.
     */
    private String contents;

    /**
     * Whether or not it is currently legal to move into this block.
     */
    private boolean isLegalMove;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Creates a new block that will be contained in the given game.
     *
     * @param game The game that will contain the new block
     * @throws IllegalArgumentException When the given game is null
     */
    public RowBlockModel(RowGameModel game) {
        super();

        if (game == null) {
            throw new IllegalArgumentException("The game must be non-null.");
        }

        this.game = game;
        this.reset();
    }

    /**
     * Returns the game model that owns this block.
     *
     * @return The containing {@link RowGameModel}.
     */
    public RowGameModel getGame() {
        return this.game;
    }

    /**
     * Registers a listener for property change events on this block.
     *
     * @param listener The listener to register.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    /**
     * Unregisters a previously added property change listener from this block.
     *
     * @param listener The listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    /**
     * Sets the contents of this block to the given value.
     *
     * @param newValue The new value for the contents of this block
     * @throws IllegalArgumentException When the given value is null
     */
    public void setContents(String newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("The value must be non-null.");
        }
        this.contents = newValue;
        this.pcs.firePropertyChange("content", null, newValue);
    }

    /**
     * Returns the non-null String value of the contents of this block.
     *
     * @return The non-null String value
     */
    public String getContents() {
        return this.contents;
    }

    /**
     * Sets whether moving into this block is currently legal and notifies listeners.
     *
     * @param isLegalMove {@code true} if a move is legal; {@code false} otherwise.
     */
    public void setIsLegalMove(boolean isLegalMove) {
        this.isLegalMove = isLegalMove;
        this.pcs.firePropertyChange("legal", null, isLegalMove);
    }

    /**
     * Returns whether moving into this block is currently legal.
     *
     * @return {@code true} if a move is legal; {@code false} otherwise.
     */
    public boolean getIsLegalMove() {
        return this.isLegalMove;
    }

    /**
     * Resets this block before starting a new game.
     */
    public void reset() {
        this.setContents("");
        this.setIsLegalMove(false);
    }
}
