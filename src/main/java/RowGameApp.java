import controller.*;
import model.RowGameModel;
import view.RowGameGUI;


/**
 * Entry point for the ThreeInARow and Tic-Tac-Toe desktop application.
 */
public class RowGameApp 
{
    /**
     * Starts a new game in the GUI using the selected rule set.
     *
     * @param args Command-line arguments; pass {@code "ThreeInARow"} (default) or another value for Tic-Tac-Toe.
     */
    public static void main(String[] args) {
        RowGameRulesStrategy rule;
        RowGameModel model = new RowGameModel();
        if(args.length == 0 || args[0].equals("ThreeInARow")) rule = new RowGameRulesThreeInARow();
        else rule = new RowGameRulesTicTacToe();
        RowGameController controller = new RowGameController(model, rule);
        RowGameGUI gui = new RowGameGUI(controller, model);
        gui.gui.setVisible(true);
    }
}