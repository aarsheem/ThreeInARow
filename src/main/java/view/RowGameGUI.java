package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import controller.RowGameController;
import model.RowGameModel;


public class RowGameGUI
{
    public JFrame gui;
    public RowGameBoardView gameBoardView; //can be private
    private JButton reset = new JButton("Reset");
    public RowGameStatusView gameStatusView; //can be private


    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameGUI(RowGameController gameController, RowGameModel gameModel) {
        gui = new JFrame(gameController.toString());
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(new Dimension(500, 350));
        gui.setResizable(true);

	    gameBoardView = new RowGameBoardView(gameController, gameModel);
        JPanel gamePanel = gameBoardView.gamePanel;

        JPanel options = new JPanel(new FlowLayout());
        options.add(reset);

	    gameStatusView = new RowGameStatusView(gameModel);
        JPanel messages = gameStatusView.messages;

        gui.add(gamePanel, BorderLayout.NORTH);
        gui.add(options, BorderLayout.CENTER);
        gui.add(messages, BorderLayout.SOUTH);

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameController.reset(gameModel);
            }
        });
        gameController.reset(gameModel);
    }
}
