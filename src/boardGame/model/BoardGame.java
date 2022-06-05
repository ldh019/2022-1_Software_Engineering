package boardGame.model;

import java.util.ArrayList;

public class BoardGame {
    private Board board;
    private ArrayList<Player> players;
    private int playerIndex;
    private Player currentPlayer;
    private GameState currentState;
    private int moveCount;
    private int rank;
    private boolean goalInFlag;

    public BoardGame() {
        board = new Board();
        players = new ArrayList<Player>();
        currentState = GameState.WAITING;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getPlayerNum() {
        return players.size();
    }

    private Player getNextPlayer() {
        while (true) {
            playerIndex++;
            if (!players.get(playerIndex).getGoalIn())
                break;
        }

        return players.get(playerIndex);
    }

    public void setGoalInFlag() {
        goalInFlag = true;
    }

    public boolean isGoalIn() {
        return goalInFlag;
    }

    public void setCurrentState(GameState gs) {
        currentState = gs;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void addRank() {
        rank++;
    }

    public int getRank() {
        return rank;
    }

    public void move() {

    }

    public void roll() {
        currentState = GameState.ROLLING;
        currentPlayer = getNextPlayer();

        moveCount = board.getDie().roll();
    }

    public void rest() {
        currentPlayer.getStatus().removeBridge();
    }

    public void join() {
        players.add(new Player());
    }

    public void start() {
        if (getPlayerNum() < 2)
            return ;
        else if (getPlayerNum() > 4)
            return ;

        playerIndex = 0;
        moveCount = 0;
        rank = 1;
        goalInFlag = false;
    }
}
