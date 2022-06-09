package boardGame.model;

import java.util.ArrayList;

public class Cell {
    private String name;
    private CellType type;
    private DirectionType previous;
    private DirectionType next;
    private DirectionType bridge;
    private int bridgeNumber;
    private int bridgeLeft;
    private int bridgeRight;

    private int index;

    public Cell(ArrayList<String> input, int i) {
        index = i;
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

    public Cell(ArrayList<String> input, CellType type, int i) {
        index = i;
        switch (type) {
            case START -> {
                this.type = CellType.START;
                previous = DirectionType.NONE;
                next = transferToDir(input.get(1));
                bridge = DirectionType.NONE;
            }
            case END -> {
                this.type = CellType.END;
                previous = transferToDir(input.get(1));
                next = DirectionType.NONE;
                bridge = DirectionType.NONE;
            }
        }
    }

    public void setBridgeNumber(int bridgeNumber) {
        this.bridgeNumber = bridgeNumber;
    }

    public int getBridgeNumber() {
        return bridgeNumber;
    }

    public void setBridgeLeft(int idx) {
        bridgeLeft = idx;
    }

    public void setBridgeRight(int idx) {
        bridgeRight = idx;
    }

    public int getBridgeLeft() {
        return bridgeLeft;
    }

    public int getBridgeRight() {
        return bridgeRight;
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
        switch (input) {
            case "E":
                return CellType.END;
            case "B":
                return CellType.SBRIDGE;
            case "b":
                return CellType.EBRIDGE;
            case "BB":
                return CellType.BRIDGE;
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
