package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.RowGameModel;

/**
 * Swing view displaying the current player's turn or the final game result.
 */
public class RowGameStatusView {
    /**
     * Text area displaying turn and outcome messages.
     */
    public JTextArea playerturn = new JTextArea(); //can be private
    /**
     * Panel containing the status text area.
     */
    public JPanel messages = new JPanel(new FlowLayout());

    /**
     * Creates the status view and subscribes to player and result updates from the model.
     *
     * @param gameModel The game model to observe.
     */
    public RowGameStatusView(RowGameModel gameModel) {
        super();

        messages.setBackground(Color.white);
        messages.add(playerturn);
        gameModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String property = evt.getPropertyName();
                if (property.equals("result")) {
                    updateResult((String) evt.getNewValue());
                }
                if (property.equals("player")) {
                    updatePlayer((RowGameModel.Player) evt.getNewValue());
                }
            }
        });
    }

    /**
     * Updates the status display to show which player's turn it is.
     *
     * @param player The player whose turn is active.
     */
    public void updatePlayer(RowGameModel.Player player) {
        playerturn.setText(player.toString() + " to play '" + player.mark() + "'");
    }

    /**
     * Updates the status display with the final game result message.
     *
     * @param result The final result string to display.
     */
    public void updateResult(String result) {
        playerturn.setText(result);
    }
}
