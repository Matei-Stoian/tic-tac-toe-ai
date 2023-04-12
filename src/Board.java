import java.util.HashSet;

public class Board {
    static final int BOARD_WIDTH = 3;

    public enum Mark {X, O, Blank}


    private final Mark[][] board;
    private Mark playerTurn;
    private Mark winner;

    private final HashSet<Integer> moveAvailable;

    private int moveCnt;
    private boolean gameOver;

    Board() {
        board = new Mark[BOARD_WIDTH][BOARD_WIDTH];
        moveAvailable = new HashSet<>();
        reset();
    }
    Board(Board b)
    {
        this.board = new Mark[BOARD_WIDTH][BOARD_WIDTH];
        for (int i = 0; i < b.board.length; i++) {
            this.board[i] = b.board[i].clone();
        }

        this.playerTurn = b.playerTurn;
        this.winner = b.winner;
        this.moveAvailable = new HashSet<>();
        this.moveAvailable.addAll(b.moveAvailable);
        this.moveCnt = b.moveCnt;
        this.gameOver = b.gameOver;
    }


    private void init() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = Mark.Blank;
            }
        }
        moveAvailable.clear();
        for (int i = 0; i < BOARD_WIDTH * BOARD_WIDTH; i++) {
            moveAvailable.add(i);
        }
    }

    void reset() {
        moveCnt = 0;
        gameOver = false;
        playerTurn = Mark.X;
        winner = Mark.Blank;
        init();
    }

    public HashSet<Integer> getAvailableMoves() {
        return moveAvailable;
    }

    public boolean move(int index) {
        return move(index / BOARD_WIDTH, index % BOARD_WIDTH);
    }

    private boolean move(int x, int y) {
        if (gameOver) {
            throw new IllegalStateException("TicTacToe game is over");
        }
        if (board[x][y] == Mark.Blank) {
            board[x][y] = playerTurn;
        } else {
            return true;
        }
        moveCnt++;
        moveAvailable.remove(x * BOARD_WIDTH + y);
        if (moveCnt == BOARD_WIDTH * BOARD_WIDTH) {
            winner = Mark.Blank;
            gameOver = true;
        }
        checkRow(x);
        checkColumn(y);
        checkDiagonalFromRight(x, y);
        checkDiagonalFromLeft(x, y);
        playerTurn = (playerTurn == Mark.X) ? Mark.O : Mark.X;
        return true;
    }

    private void checkRow(int x) {
        for (int i = 1; i < BOARD_WIDTH; i++) {
            if (board[x][i] != board[x][i - 1])
                return;
        }
        winner = playerTurn;
        gameOver = true;
    }

    private void checkColumn(int y) {
        for (int i = 1; i < BOARD_WIDTH; i++) {
            if (board[i][y] != board[i - 1][y])
                return;

        }
        winner = playerTurn;
        gameOver = true;
    }

    private void checkDiagonalFromLeft(int x, int y) {
        if(x==y) {
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] == board[2][2]) {
                gameOver = true;
                winner = playerTurn;
            }
        }
    }

    private void checkDiagonalFromRight(int x, int y) {
        if(BOARD_WIDTH-x-1==y) {
            if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] == board[2][0]) {
                gameOver = true;
                winner = playerTurn;
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Mark getTurn() {
        return playerTurn;
    }

    public Mark getWinner() {
        if (!gameOver) {
            throw new IllegalStateException("The game is not over");
        }
        return winner;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] == Mark.Blank) {
                    sb.append('-');
                } else {
                    sb.append(board[i][j].name());
                }
                sb.append(" ");
            }
            if (i != BOARD_WIDTH - 1)
                sb.append('\n');
        }
        return new String(sb);
    }
}
