package boardGame.model;

public class Player {
    private Status status;
    private int position;
    private boolean goalIn;

    public Player() {
        status = new Status();
        position = 0;
        goalIn = false;
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
}
