import java.util.ArrayList;
import java.util.List;

/**
 * A class that implements a state and the playing logic of the TicTacToe game.
 */
public class TicTacToeState implements State {
    private Square[] field; /**< The field, consisting of nine squares. First three values correspond to the first row, and so on. */
    public Square player; /**< The player, either X or O. */
    public Square playerToMove; /**< The player that is about to move. */
    private float utility; /**< The utility value of this state. Can be 0, 1 (won) or -1 (lost). */

    /**
     * Updates the utility value.
     */
    private void updateUtility() {
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (field[i * 3] == field[i * 3 + 1] && field[i * 3] == field[i * 3 + 2]) {
                if (field[i * 3] == player) {
                    utility = 1;
                } else if (field[i * 3] != Square.EMPTY) {
                    utility = -1;
                }
            }
            // Check columns
            if (field[i] == field[i + 3] && field[i] == field[i + 6]) {
                if (field[i] == player) {
                    utility = 1;
                } else if (field[i] != Square.EMPTY) {
                    utility = -1;
                }
            }
        }
        // Check diagonals
        if ((field[0] == field[4] && field[0] == field[8]) || (field[2] == field[4] && field[2] == field[6])) {
            if (field[4] == player) {
                utility = 1;
            } else if (field[4] != Square.EMPTY) {
                utility = -1;
            }
        }

        // Check for a draw
        boolean isFull = true;
        for (int i = 0; i < 9; i++) {
            if (field[i] == Square.EMPTY) {
                isFull = false;
                break;
            }
        }
        if (isFull && utility == 0) {
            utility = 0;
        }
    }

    /**
     * Default constructor.
     */
    public TicTacToeState() {
        field = new Square[9];
        for (int i = 0; i < 9; ++i) {
            field[i] = Square.EMPTY;
        }
        player = Square.X;
        playerToMove = Square.X;
        utility = 0;
    }

    @Override
    public List<Action> getActions() {
        List<Action> actions = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (field[i] == Square.EMPTY) {
                actions.add(new TicTacToeAction(playerToMove, i));
            }
        }
        return actions;
    }

    @Override
    public float getUtility() {
        return utility;
    }

    @Override
    public State getResult(Action action) {
        TicTacToeAction ticTacToeAction = (TicTacToeAction) action;
        TicTacToeState newState = new TicTacToeState();
        newState.player = player;
        newState.playerToMove = (playerToMove == Square.X) ? Square.O : Square.X;
        newState.utility = utility;
        newState.field = new Square[9]; // Initialize the field array in the new state

        // Copy the field array from the current state to the new state
        for (int i = 0; i < field.length; i++) {
            newState.field[i] = field[i];
        }

        // Check if the selected square is empty before setting it to 'O'
        int position = ticTacToeAction.position;
        if (newState.field[position] == Square.EMPTY) {
            newState.field[position] = ticTacToeAction.player;
        }

        newState.updateUtility();
        return newState;
    }

    @Override
    public boolean isTerminal() {
        return utility != 0 || getActions().isEmpty();
    }

    @Override
    public void print() {
        String s = "";
        for (int i = 0; i < 9; i++) {
            s += field[i];
            if (i % 3 == 2) {
                s += "\n";
                if (i < 6) {
                    s += "-+-+-\n";
                }
            } else {
                s += "|";
            }
        }
        System.out.println(s);
    }
}