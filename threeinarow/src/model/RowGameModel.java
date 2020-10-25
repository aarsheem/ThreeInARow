package model;


import view.RowGameGUI;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RowGameModel
{
    public RowBlockModel[][] blocksData = new RowBlockModel[3][3];

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    public int movesLeft;

    /**
     * The current player taking their turn
     */
    public enum Player{
        Player1, Player2;
        public Player nextTurn(){
            if(this == Player1) return Player2;
            return Player1;
        }
        public String toString() {
            if (this == Player1) return "Player 1";
            return "Player 2";
        }
        public String mark(){
            if(this == Player1) return "X";
            return "O";
        }
    }

    private enum Result{
        Ongoing, Draw, Win;
        public String toString(Player player){
            if(this == Ongoing) return null;
            if(this == Draw) return "Game ends in a draw";
            return player.toString() + " wins!";
        }
    }

    private Player player;
    private Result result;


    public RowGameModel() {
	    super();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                blocksData[row][col] = new RowBlockModel(this);
            }
        }
        reset();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public String getFinalResult() {
        return result.toString(player);
    }

    public void gameDraw(){
        result = Result.Draw;
        setFinalResult(result.toString(player));
    }

    public void playerWins(){
        result = Result.Win;
        setFinalResult(result.toString(player));
    }

    private void setFinalResult(String finalResult) {
	    pcs.firePropertyChange("result", null, finalResult);
    }

    public Player getPlayer() {
        return player;
    }

    public void nextTurn(){
        player = player.nextTurn();
        pcs.firePropertyChange("player", null, player);
    }

    public void reset(){
        movesLeft = 9;
        result = Result.Ongoing;
        player = Player.Player2;
        nextTurn();
    }
}
