package boardGame;

import java.util.ArrayList;

public class Cells extends ArrayList<Cell>{

    public Cell getCell(int idx) {
        for (Cell i : this) {
            if (i.getIndex() == idx)
                return i;
        }
        return null;
    }
}
