package boardGame;

import java.util.ArrayList;

public class BoardGame implements Cloneable{
    private Board board;
    private ArrayList<Player> players;
    private int playerIndex;
    private Player currentPlayer;
    private int moveCount;
    private int rank;
    private boolean goalInFlag;

    public BoardGame() {
        board = new Board();
        players = new ArrayList<Player>();

        this.join(); this.join();
    }

    @Override
    public BoardGame clone() throws CloneNotSupportedException {
        BoardGame clone = (BoardGame) super.clone();
        ArrayList<Player> tmp = new ArrayList<>();
        for (Player player : players) {
            tmp.add(player.clone());
        }
        clone.players = tmp;
        clone.currentPlayer = clone.players.get(playerIndex);
        clone.board = board.clone();
        return (BoardGame) clone;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int[] getCurrentCell() {
        return currentPlayer.getCellxy();
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getPlayerNum() {
        return players.size();
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public int getRank() {
        return rank;
    }

    public int[] getSize() {
        return board.getSize();
    }

    public ArrayList<Status> getStatus() {
        ArrayList<Status> list = new ArrayList<>();

        for (Player i : players)
            list.add(i.getStatus());

        return list;
    }

    private Player getNextPlayer() {
        do {
            playerIndex++;
            if (playerIndex == getPlayerNum()) playerIndex = 0;
        } while (players.get(playerIndex).getGoalIn());

        return players.get(playerIndex);
    }

    public void setMoveCount(int count) {
        moveCount = count;
    }

    public void setGoalInFlag() {
        goalInFlag = true;
    }

    public void addRank() {
        rank++;
    }

    public void addBridge() {
        currentPlayer.getStatus().addBridge();
    }

    public void resetBridge() {
        currentPlayer.resetBridgeFlag();
    }

    public void setCrossBridgeLeft() {
        currentPlayer.setBridgeFlagLeft();
    }

    public void setCrossBridgeRight() {
        currentPlayer.setBridgeFlagRight();
    }



    public boolean isGoalIn() {
        return goalInFlag;
    }

    public boolean isFinish() {
        return rank > getPlayerNum();
    }



    public void move(int idx, DirectionType input) {
        switch (input) {
            case UP -> currentPlayer.setCellxy(new int[]{getCurrentCell()[0], getCurrentCell()[1] - 1});
            case DOWN -> currentPlayer.setCellxy(new int[]{getCurrentCell()[0], getCurrentCell()[1] + 1});
            case LEFT -> currentPlayer.setCellxy(new int[]{getCurrentCell()[0] - 1, getCurrentCell()[1]});
            case RIGHT -> currentPlayer.setCellxy(new int[]{getCurrentCell()[0] + 1, getCurrentCell()[1]});
        }

        currentPlayer.move(idx);
    }

    public void roll() {
        moveCount = board.getDie().roll() - getCurrentPlayer().getStatus().getBridge();
        if (moveCount < 0) moveCount = 0;
    }

    public void rest() {
        currentPlayer.getStatus().removeBridge();
    }

    public void startTurn() {
        currentPlayer = players.get(playerIndex);
    }

    public void endTurn() {
        if (!isFinish())
            currentPlayer = getNextPlayer();
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

        for (Player i : players)
            i.setCellxy(board.getStart());

        startTurn();
    }
}
