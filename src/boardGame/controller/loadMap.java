package boardGame.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import boardGame.model.Board;
import boardGame.model.Cell;

public class loadMap {
    private boolean success = false;
    private Board board;

    public ArrayList<Cell> loadMap() throws IOException {
        Path cur = Paths.get("");
        String path = cur.toAbsolutePath().toString() + "\\resources\\recent.map";
        File mapPath = new File(path);
        boolean start = false;

        success = false;

        List<String> data = Files.readAllLines(mapPath.toPath());

        for (String line : data) {
            String[] element = line.split(" ");
            if (!start && element[0].equals('S')) {
                if (element.length == 2 && validDirection(element[1]))
                    start = true;
                else return false;
            } else if (start && element[0].equals('E')) {
                return element.length == 1;
            } else if(start) {
                List<String> cell = new ArrayList<>();
                cell.add("C"); cell.add("H"); cell.add("S"); cell.add("P");
                cell.add("B"); cell.add("b");

                if (cell.contains(element[0])) {
                    if (element.length != 3)
                        return false;
                    if (validDirection(element[1]))
                        return false;
                    if (validDirection(element[2]))
                        return false;
                }
            } else return false;
        }
        return false;
    }

    private boolean readMap(File map) throws IOException {

    }

    private boolean validDirection(String c) {
        List<String> list = new ArrayList<>();
        list.add("L");
        list.add("R");
        list.add("U");
        list.add("D");

        return list.contains(c);
    }

    public boolean isSuccess() {
        return success;
    }
}
