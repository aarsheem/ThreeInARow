package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.RowGameModel;


public class RowGameStatusView
{
    public JTextArea playerturn = new JTextArea(); //can be private
    public JPanel messages = new JPanel(new FlowLayout());

    
    public RowGameStatusView(RowGameModel gameModel) {
        super();

        messages.setBackground(Color.white);
        messages.add(playerturn);
        gameModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String property = evt.getPropertyName();
                if(property.equals("result")) updateResult((String)evt.getNewValue());
                if(property.equals("player")) updatePlayer((RowGameModel.Player) evt.getNewValue());
            }
        });
    }

    public void updatePlayer(RowGameModel.Player player) {
    	playerturn.setText(player.toString() + " to play '" + player.mark() + "'");
    }

    public void updateResult(String result){
    	playerturn.setText(result);
	}
}
