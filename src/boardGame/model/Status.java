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

    public void addBridge() { bridge++; }

    public void removeBridge() { if (bridge > 0) bridge--; }

    public int getPdriver() {
        return pDriver;
    }

    public int getHammer() {
        return hammer;
    }

    public int getSaw() {
        return saw;
    }

    public int getBridge() { return bridge; }

    public int getScore() {
        return pDriver + hammer * 2 + saw * 3 + endBonus;
    }

    public void setEndBonus(int rank) {
        switch(rank) {
            case 1 -> endBonus = 7;
            case 2 -> endBonus = 3;
            case 3 -> endBonus = 1;
            default -> endBonus = 0;
        }
    }
}
