package boardGame.model;

import java.io.IOException;
import java.util.ArrayList;
import boardGame.controller.loadMap;

public class Board {
    private ArrayList<Cell> cells;
    private Die die;

    public Board() throws IOException {
        cells = new loadMap().loadMap();


    }
}
