package boardGame.model;

import java.util.ArrayList;

public class Cell {
    private String name;
    private CellType type = CellType.NONE;
    private ArrayList<Player> players = new ArrayList<>();
    private DirectionType previous;
    private DirectionType next;
    private DirectionType bridge;

    private int index;

    public Cell(ArrayList<String> input) {
        type = transferToType(input.get(0));
        switch (type) {
            case SBRIDGE -> {
                previous = transferToDir(input.get(1));
                next = transferToDir(input.get(2));
                bridge = DirectionType.RIGHT;
            }
            case EBRIDGE -> {
                previous = transferToDir(input.get(1));
                next = transferToDir(input.get(2));
                bridge = DirectionType.LEFT;
            }
            default -> {
                previous = transferToDir(input.get(1));
                next = transferToDir(input.get(2));
                bridge = DirectionType.NONE;
            }
        }
    }

    public Cell(ArrayList<String> input, CellType type) {
        this.type = transferToType(input.get(0));
        switch (type) {
            case START -> {
                previous = DirectionType.NONE;
                next = transferToDir(input.get(1));
                bridge = DirectionType.NONE;
            }
            case END -> {
                previous = DirectionType.NONE;
                next = DirectionType.NONE;
                bridge = DirectionType.NONE;
            }
        }
    }

    public void setIndex(int i) {
        index = i;
    }

    public int getIndex() {
        return index;
    }

    public DirectionType getPrevDir() {
        return previous;
    }

    public DirectionType getNextDir() {
        return next;
    }

    public DirectionType getBridgeDir() { return bridge; }

    public void setOwner(Player p) {
        players.add(p);
    }

    public void removeOwner(Player p) {
        players.remove(p);
    }

    public boolean isStart() {
        return type.equals(CellType.START);
    }

    public boolean isEnd() {
        return type.equals(CellType.END);
    }

    public boolean isCell() {
        return type.equals(CellType.CELL);
    }

    public boolean isSbridge() {
        return type.equals(CellType.SBRIDGE);
    }

    public boolean isEbridge() {
        return type.equals(CellType.EBRIDGE);
    }

    public boolean isBridge() {
        return type.equals(CellType.BRIDGE);
    }

    public boolean isHammer() {
        return type.equals(CellType.HAMMER);
    }

    public boolean isSaw() {
        return type.equals(CellType.SAW);
    }

    public boolean isPdriver() {
        return type.equals(CellType.PDRIVER);
    }

    public void removeTool() {
        type = CellType.CELL;
    }

    public CellType transferToType(String input) {
        switch(input) {
            case "E":
                return CellType.END;
            case "B":
                return CellType.SBRIDGE;
            case "b":
                return CellType.EBRIDGE;
            case "H":
                return CellType.HAMMER;
            case "S":
                return CellType.SAW;
            case "P":
                return CellType.PDRIVER;
            case "C":
                return CellType.CELL;
            default:
                return CellType.NONE;
        }
    }

    public DirectionType transferToDir(String input) {
        switch(input) {
            case "L":
                return DirectionType.LEFT;
            case "R":
                return DirectionType.RIGHT;
            case "U":
                return DirectionType.UP;
            case "D":
                return DirectionType.DOWN;
            default:
                return DirectionType.NONE;
        }
    }
}
