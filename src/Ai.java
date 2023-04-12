public class Ai {
    private Ai(){}
    private static double maxPly;
    static void run(Board board,Board.Mark player,double maxPly)
    {
        if(maxPly < 1)
        {
            throw new IllegalStateException("Maximum depth must be positiv");
        }
        Ai.maxPly = maxPly;
        alphaBetaPruning(board,player,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,0);
    }
    private static int alphaBetaPruning(Board board,Board.Mark player,double alpha,double beta,int currentD)
    {
        if(currentD == maxPly || board.isGameOver())
        {
            return score(board,player);
        }
        if(board.getTurn() == player)
        {
            return getmax(board,player,alpha,beta,currentD);
        }
        else
        {
            return getmin(board,player,alpha,beta,currentD);
        }
    }
    private static int getmax(Board board, Board.Mark player, double alpha, double beta, int currentD)
    {
        int indexBextMove = -1;
        for(Integer move : board.getAvailableMoves())
        {
            Board modifiedBoard = new Board(board);
            modifiedBoard.move(move);
            int score = alphaBetaPruning(modifiedBoard,player,alpha,beta,currentD);
            if(score > alpha)
            {
                alpha = (double)score;
                indexBextMove = move;
            }
            if(alpha >= beta)
            {
                break;
            }
        }
        if(indexBextMove != -1)
        {
            board.move(indexBextMove);
        }
        return (int)alpha;
    }
    private static int getmin(Board board,Board.Mark player,double alpha,double beta,int currentD)
    {
        int indexBestMove = -1;
        for(Integer move : board.getAvailableMoves())
        {
            Board modifiedBoard = new Board(board);
            modifiedBoard.move(move);
            int score = alphaBetaPruning(modifiedBoard,player,alpha,beta,currentD);
            if(score<beta)
            {
                beta = (double) score;
                indexBestMove = move;
            }
            if(alpha >= beta)
            {
                break;
            }
        }
        if(indexBestMove != -1)
        {
            board.move(indexBestMove);
        }
        return (int)beta;
    }
    private static int score(Board board, Board.Mark player)
    {
        if(player == Board.Mark.Blank)
        {
            try {
                throw new IllegalAccessException("Wrong player symbol");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        Board.Mark opponent = (player == Board.Mark.X) ? Board.Mark.O : Board.Mark.X;

        if(board.isGameOver() && board.getWinner() == opponent)
        {
            return -100;
        }
        else if(board.isGameOver() && board.getWinner() == player)
        {
            return 100;
        }
        else
        {
            return 0;
        }
    }
}
