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

        this.join(); this.join();
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

    public void setMoveCount(int count) {
        moveCount = count;
    }

    public int getPlayerNum() {
        return players.size();
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public ArrayList<Status> getResult() {
        ArrayList<Status> result = new ArrayList<>();

        for (Player p : players) {
            result.add(p.getStatus());
        }

        return result;
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

    public void addBridge() {
        currentPlayer.getStatus().addBridge();
    }

    public void resetBridge() {
        currentPlayer.resetBridgeFlag();
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCrossBridgeLeft() {
        currentPlayer.setBridgeFlagLeft();
    }

    public void setCrossBridgeRight() {
        currentPlayer.setBridgeFlagRight();
    }

    public void addRank() {
        rank++;
    }

    public int getRank() {
        return rank;
    }

    public void move(int idx) {
        currentPlayer.move(idx);
    }

    public void roll() {
        currentState = GameState.ROLLING;

        moveCount = board.getDie().roll();
    }

    public void startTurn() {
        currentState = GameState.PLAYING;
    }

    public void endTurn() {
        currentState = GameState.DONE;

        currentPlayer = getNextPlayer();
    }

    public void rest() {
        currentPlayer.getStatus().removeBridge();

        currentPlayer = getNextPlayer();
    }

    public boolean isFinish() {
        return rank > getPlayerNum();
    }

    public void join() {
        if (players.size() < 4)
            players.add(new Player(players.size() + 1));
    }

    public void leave() {
        if (players.size() > 2)
            players.remove(players.size() - 1);
    }

    public void start() {
        playerIndex = 0;
        moveCount = 0;
        rank = 1;
        goalInFlag = false;

        startTurn();
    }
}
