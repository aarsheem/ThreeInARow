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


/**
 * Swing view rendering the 3x3 grid of interactive board buttons.
 */
public class RowGameBoardView
{
    /**
     * 3x3 matrix of Swing buttons representing each board cell.
     */
    public JButton[][] blocks = new JButton[3][3];
    /**
     * Container panel holding the 3x3 board grid.
     */
    public JPanel gamePanel = new JPanel(new FlowLayout());

    /**
     * Builds the 3x3 button grid and binds click and model property-change listeners.
     *
     * @param gameController The controller handling board move actions.
     * @param gameModel The game model whose block states are observed.
     */
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
                        JButton button = (JButton)e.getSource();
                        int row = (int)button.getClientProperty("row");
                        int col = (int)button.getClientProperty("col");
                        gameController.move(gameModel, row, col);
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
}
