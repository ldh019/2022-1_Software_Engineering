package boardGame.model;

import java.util.ArrayList;

public class BoardGame {
    private Board board;
    private ArrayList<Player> players;
    private int playerIndex;
    private Player currentPlayer;
    private GameState currentState;
    private int moveCount;

    public BoardGame() {
        board = new Board();
        players = new ArrayList<Player>();
        currentState = GameState.WAITING;
    }

    public Player getNextPlayer() {
        while (true) {
            playerIndex++;
            if (!players.get(playerIndex).getGoalIn())
                break;
        }

        return players.get(playerIndex);
    }

    public void move() {
        currentState = GameState.MOVING;
        Cell currentCell;
        while (moveCount-- > 0) {
            currentCell = board.getCells().get(currentPlayer.getPosition());


        }
    }

    public void roll() {
        currentState = GameState.ROLLING;
        currentPlayer = getNextPlayer();

        moveCount = board.getDie().roll();
    }

    public void join() {

    }

    public void start() {
        playerIndex = 0;
        moveCount = 0;
    }
}
