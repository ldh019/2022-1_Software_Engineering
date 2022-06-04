package boardGame.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import boardGame.model.Map;

public class loadMap {
    private boolean success = false;
    private Map map;
    public loadMap() throws IOException {
        onStart();
    }

    private void onStart() throws IOException {
        File mapPath = new File("C://Program Files/SEBoardGame/recent.map");
        success = false;

        map.initialize(Files.readAllLines(mapPath.toPath()));

        if (readMap(map.sendData())) {
            success = true;
        }
    }

    Map map() {
        return map;
    }

    private boolean readMap(List<String> data) {
        boolean start = false;
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
