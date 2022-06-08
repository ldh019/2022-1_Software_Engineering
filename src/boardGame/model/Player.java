package boardGame.model;

public class Player implements Cloneable{
    private Status status;
    private int index;
    private int[] cellxy;
    private int position;
    private boolean goalIn;
    private int bridgeFlag;

    public Player(int idx) {
        status = new Status();
        position = 0;
        goalIn = false;
        bridgeFlag = 0;
        index = idx;
    }

    @Override
    public Player clone() throws CloneNotSupportedException {
        return (Player) super.clone();
    }

    public void setCellxy(int[] coor) {
        cellxy = coor;
    }

    public int[] getCellxy() {
        return cellxy;
    }

    public int getPosition() {
        return position;
    }

    public Status getStatus() {
        return status;
    }

    public void move(int idx) {
        position = idx;
    }

    public int getIndex() {
        return index;
    }

    public void setGoalIn() {
        goalIn = true;
    }

    public boolean getGoalIn() {
        return goalIn;
    }

    public void setBridgeFlagLeft() {
        bridgeFlag = -1;
    }

    public void setBridgeFlagRight() {
        bridgeFlag = 1;
    }

    public int getBridgeFlag() {
        return bridgeFlag;
    }

    public void resetBridgeFlag() {
        bridgeFlag = 0;
    }
}
