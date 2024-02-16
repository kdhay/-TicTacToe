import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("The squares are numbered as follows:");
        System.out.println("1|2|3\n-+-+-\n4|5|6\n-+-+-\n7|8|9\n");

        System.out.print("Who should start? 1=you 2=computer ");
        int temp = scanner.nextInt();

        TicTacToeState s = new TicTacToeState();
        s.player = Square.O; // You play as O
        if (temp == 2) {
            Action bestMove = MiniMax.MinimaxDecision(s, true);
            s = (TicTacToeState) s.getResult(bestMove);
            s.playerToMove = Square.O; // Its your turn next
		}else{
            s.playerToMove = Square.O;
        }

        boolean gameIsOver = false;
        do {
            s.print();
            if (s.playerToMove == Square.X) { // Computer is to move
                Action bestMove = MiniMax.MinimaxDecision(s, true);
                s = (TicTacToeState) s.getResult(bestMove);
            } else { // You are to move
                System.out.print("Which square do you want to set? (1--9) ");
                int userMove;
                do {
                    userMove = scanner.nextInt();
                } while (userMove < 1 || userMove > 9);
                TicTacToeAction a = new TicTacToeAction(Square.O, userMove - 1);
                s = (TicTacToeState) s.getResult(a);
            }
            if (s.getUtility() != 0) {
                gameIsOver = true;
            }
        } while (!s.isTerminal() && !gameIsOver);

        s.print();

        if (s.getUtility() == 0) {
            System.out.println("Draw");
        } else if (s.getUtility() == 1) {
            System.out.println("You win");
        } else {
            System.out.println("You lost");
        }

        scanner.close();
    }
}
