package boardGame.model;

public class Player {
    private Status status;
    private int position;
    private boolean goalIn;
    private int bridgeFlag;

    public Player(int idx) {
        status = new Status();
        position = 0;
        goalIn = false;
        bridgeFlag = 0;
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
