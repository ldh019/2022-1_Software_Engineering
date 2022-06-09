package boardGame;

public class Board implements Cloneable{
    private Cells cells;
    private Die die;
    private int sizeX;
    private int sizeY;
    private int startX, startY;
    private loadMap loadMap;

    public Board() {
        loadMap = new loadMap();
        cells = loadMap.getMap();
        die = new Die();
        sizeX = loadMap.getMaxX();
        sizeY = loadMap.getMaxY();
        startX = loadMap.getStartX();
        startY = loadMap.getStartY();
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        return (Board) super.clone();
    }

    public Cells getCells() {
        return cells;
    }

    public Die getDie() {
        return die;
    }

    public int[] getSize() {
        int[] size = {sizeX, sizeY};

        return size;
    }

    public int[] getStart() {
        int[] size = {startX, startY};

        return size;
    }
}
