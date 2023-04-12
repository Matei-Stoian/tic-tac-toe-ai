import java.util.Scanner;
public class Main {

    private final Board  board;
    private final Scanner sc = new Scanner(System.in);

    private Main() {
        board = new Board();
    }

    private void play() {
        System.out.println("Starting the game");
        while (true) {
            printGameStatus();
            playMove();
            if (board.isGameOver()) {
                printWinner();
                if (!tryAgain()) {
                    break;
                }
            }
        }
    }

    private void printGameStatus() {
        System.out.println("\n" + board + "\n");
        System.out.println(board.getTurn().name() + "'s turn.");
    }

    private void playMove() {
        if (board.getTurn() == Board.Mark.X) {
            getPlayerMove();
        } else {
            Ai.run(board, board.getTurn(), Double.POSITIVE_INFINITY);
        }
    }

    private void getPlayerMove() {
        System.out.println("Index of move: ");
        int move = sc.nextInt();
        if (move < 0 || move >= 9) {
            System.out.println("\nInvalid move");
            System.out.println("\nChoose an index between 0 and 8 inclusive");
        } else if (!board.move(move)) {
            System.out.println("\nInvalid move");
            System.out.println("\nIndex selected is filled");
        }
    }

    private boolean tryAgain() {
        if (promptTryAgain()) {
            board.reset();
            System.out.println("Started new game");
            System.out.println("X's turn");
            return true;
        }
        return false;
    }

    private boolean promptTryAgain() {
        while (true) {
            System.out.println("Would like to start a new game (Y/N): ");
            String response = sc.next();
            if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("Y")) {
                return true;
            } else if (response.equalsIgnoreCase("n") || response.equalsIgnoreCase("N"))
                return false;
            System.out.println("\nInvalid input");
        }
    }

    private void printWinner() {
        Board.Mark winner = board.getWinner();

        System.out.println("\n" + board + "\n");
        if (winner == Board.Mark.Blank) {
            System.out.println("The game ended in a draw");
        } else {
            System.out.println("The winner is: " + winner.toString());
        }
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.play();
    }
}