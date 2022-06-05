package boardGame.model;

public class Status {

    private int pDriver;
    private int hammer;
    private int saw;
    private int bridge;
    private int endBonus;

    public Status() {
        pDriver = 0;
        hammer = 0;
        saw = 0;
        endBonus = 0;
    }

    public void addPdriver() {
        pDriver++;
    }

    public void addHammer() {
        hammer++;
    }

    public void addSaw() {
        saw++;
    }

    public int getPdriver() {
        return pDriver;
    }

    public int getHammer() {
        return hammer;
    }

    public int getSaw() {
        return saw;
    }

    public int getScore() {
        return pDriver + hammer * 2 + saw * 3 + endBonus;
    }

    public void setEndBonus(int point) {
        endBonus = point;
    }
}
