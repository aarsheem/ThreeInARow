import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

/**
 * Java implementation of the 3 in a row game, using the Swing framework.
 *
 * This quick-and-dirty implementation violates a number of software engineering
 * principles and needs a thorough overhaul to improve readability,
 * extensibility, and testability.
 */
public class ThreeInARowGame {
    public static final String GAME_END_NOWINNER = "Game ends in a draw";

    public JFrame gui = new JFrame("Three in a Row");
    public ThreeInARowBlock[][] blocksData = new ThreeInARowBlock[3][3];
    public JButton[][] blocks = new JButton[3][3];
    public JButton reset = new JButton("Reset");
    public JTextArea playerturn = new JTextArea();
    /**
     * The current player taking their turn
     */
    private enum Player{
    	Player1, Player2;
    	public Player nextTurn(){
    		if(this == Player1) return Player2;
    		return Player1;
		}
		public String toString() {
			if (this == Player1) return "Player 1";
			return "Player 2";
		}
		public String write(){
			if(this == Player1) return "X";
			return "O";
		}
	}
    public int movesLeft;
    private Player player;

    /**
     * Starts a new game in the GUI.
     */
    public static void main(String[] args) {
        ThreeInARowGame game = new ThreeInARowGame(1, 1);
        game.gui.setVisible(true);
    }

    /**
     * Creates a new game initializing the GUI.
     */
    public ThreeInARowGame(int rows, int columns) {
    	if(rows <= 0 || columns <= 0){
    		throw new IllegalArgumentException("rows and cols should be greater than three");
		}

    	player = Player.Player1;
    	movesLeft = rows * columns;
		blocksData = new ThreeInARowBlock[rows][columns];
		blocks = new JButton[rows][columns];

        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(new Dimension( 80 * columns + 50, 80 * rows + 100));
        gui.setResizable(true);

        JPanel gamePanel = new JPanel(new FlowLayout());
        JPanel game = new JPanel(new GridLayout(rows,columns));
        gamePanel.add(game, BorderLayout.CENTER);

        JPanel options = new JPanel(new FlowLayout());
        options.add(reset);
        JPanel messages = new JPanel(new FlowLayout());
        messages.setBackground(Color.white);

        gui.add(gamePanel, BorderLayout.NORTH);
        gui.add(options, BorderLayout.CENTER);
        gui.add(messages, BorderLayout.SOUTH);

        messages.add(playerturn);
		playerturn.setText(player.toString() + " to play " + player.write());

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        // Initialize a JButton for each cell of the 3x3 game board.
        for(int row = 0; row<rows; row++) {
            for(int column = 0; column<columns ;column++) {
				blocksData[row][column] = new ThreeInARowBlock(this);
				// The last row contains the legal moves
				blocksData[row][column].setContents("");
				blocksData[row][column].setIsLegalMove(row == rows-1);
				blocks[row][column] = new JButton();
				blocks[row][column].setPreferredSize(new Dimension(75,75));
				updateBlock(row,column);
				game.add(blocks[row][column]);
				blocks[row][column].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						move((JButton) e.getSource());
					}
				});
            }
        }
    }

    private boolean checkThreeInDir(int x, int y, int[] dir, String content, int remaining){
    	if(remaining == 0) return true;
    	if(x < 0 || x >= blocksData.length || y < 0 || y >= blocksData[0].length){
    		return false;
		}
    	if(content.equals("start") || blocksData[x][y].getContents().equals(content))
    		return checkThreeInDir(x + dir[0], y + dir[1], dir, blocksData[x][y].getContents(), remaining-1);
    	return false;
	}

    private boolean checkThreeInARow(int x, int y){
    	int[][] dir = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};
    	for(int i = 0; i < dir.length; i++) {
    		if(checkThreeInDir(x, y, dir[i], "start", 3)) return true;
		}
    	if(checkThreeInDir(x-1, y-1, dir[1], "start", 3)) return true;
    	if(checkThreeInDir(x-1, y, dir[2], "start", 3)) return true;
    	if(checkThreeInDir(x, y-1, dir[0], "start", 3)) return true;
    	return false;
	}

    /**
     * Moves the current player into the given block.
     *
     * @param block The block to be moved to by the current player
     */
    private void move(JButton block) {
		--movesLeft;
		playerturn.setText(player.write() + " : " + player.toString());

		int x = 0, y = 0;
		for(int i = 0; i < blocks.length; i++){
			for(int j = 0; j < blocks[i].length; j++){
				if(block == blocks[i][j]){
					x = i;
					y = j;
					break;
				}
			}
		}

		blocksData[x][y].setContents(player.write());
		blocksData[x][y].setIsLegalMove(false);
		updateBlock(x, y);
		if(x != 0){
			blocksData[x-1][y].setIsLegalMove(true);
			updateBlock(x-1, y);
		}
		if(checkThreeInARow(x, y)){
			playerturn.setText(player.toString() + " wins!");
			endGame();
		}
		else if(movesLeft == 0) {
			playerturn.setText(GAME_END_NOWINNER);
		}
		player = player.nextTurn();
    }

    /**
     * Updates the block at the given row and column 
     * after one of the player's moves.
     *
     * @param row The row that contains the block
     * @param column The column that contains the block
     */
    protected void updateBlock(int row, int column) {
	blocks[row][column].setText(blocksData[row][column].getContents());
	blocks[row][column].setEnabled(blocksData[row][column].getIsLegalMove());
    }

    /**
     * Ends the game disallowing further player turns.
     */
    public void endGame() {
	for(int row = 0;row<blocksData.length;row++) {
	    for(int column = 0;column<blocksData[0].length;column++) {
		blocks[row][column].setEnabled(false);
	    }
	}
    }

    /**
     * Resets the game to be able to start playing again.
     */
    public void resetGame() {
        for(int row = 0;row<blocksData.length;row++) {
            for(int column = 0;column<blocksData[0].length;column++) {
                blocksData[row][column].reset();
		// Enable the bottom row
		blocksData[row][column].setIsLegalMove(row == blocksData.length-1);
		updateBlock(row,column);
            }
        }
        player = Player.Player1;
        movesLeft = blocksData.length * blocksData[0].length;
        playerturn.setText(player.toString() + " to play " + player.write());
    }
}
