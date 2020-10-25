package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.RowGameController;
import model.RowGameModel;


public class RowGameBoardView
{
    public JButton[][] blocks = new JButton[3][3];
    public JPanel gamePanel = new JPanel(new FlowLayout());

    public RowGameBoardView(RowGameController gameController, RowGameModel gameModel) {
        JPanel game = new JPanel(new GridLayout(3,3));
        gamePanel.add(game, BorderLayout.CENTER);
	
       // Initialize a JButton for each cell of the 3x3 game board.
        for(int row = 0; row<3; row++) {
            for(int column = 0; column<3 ;column++) {
                blocks[row][column] = new JButton();
                blocks[row][column].putClientProperty("row", row);
                blocks[row][column].putClientProperty("col", column);
                blocks[row][column].setPreferredSize(new Dimension(75,75));
                game.add(blocks[row][column]);
                blocks[row][column].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
			            move((JButton)e.getSource(), gameController, gameModel);
                    }
                });
                JButton button = blocks[row][column];
                gameModel.blocksData[row][column].addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        String property = e.getPropertyName();
                        if(property.equals("content")) button.setText((String)e.getNewValue());
                        if(property.equals("legal")) button.setEnabled((boolean)e.getNewValue());
                    }
                });
            }
        }	
    }

    private void move(JButton button, RowGameController gameController, RowGameModel gameModel){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(button == blocks[i][j]){
                    gameController.move(gameModel, i, j);
                }
            }
        }
    }
}
