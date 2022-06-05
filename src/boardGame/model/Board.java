package boardGame.model;

import java.util.ArrayList;
import boardGame.controller.loadMap;

public class Board {
    private ArrayList<Cell> cells;
    private Die die;

    public Board() {
        cells = new loadMap().getMap();
        die = new Die();
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public Die getDie() {
        return die;
    }
}
