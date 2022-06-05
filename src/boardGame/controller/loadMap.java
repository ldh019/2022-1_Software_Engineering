package boardGame.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import boardGame.model.Board;
import boardGame.model.Cell;
import boardGame.model.CellType;

public class loadMap {
    private boolean success = false;
    private ArrayList<Cell> map;

    public loadMap() {
        Path currentPath;
        String filePath;
        File mapFile;
        Path mapPath;
        List<String> mapData;

        try {
            currentPath = Paths.get("");
            filePath = currentPath.toAbsolutePath().toString() + "\\resources\\default.map";
            mapFile = new File(filePath);
            mapPath = mapFile.toPath();

            success = true;

            mapData = Files.readAllLines(mapPath);
            map = new ArrayList<Cell>();

            String line = mapData.get(0);
            ArrayList<String> element = new ArrayList<>(Arrays.asList(line.split(" ")));
            int Bnum = 0, bnum = 0;

            if (validCell(element) == 2) {
                map.add(new Cell(element, CellType.START));

                for (int i = 1; i < mapData.size(); i++) {
                    line = mapData.get(i);
                    element = new ArrayList<String>(Arrays.asList(line.split(" ")));

                    if (validCell(element) == 1) {
                        map.add(new Cell(element));
                    }
                    else if (validCell(element) == 3) {
                        Cell tmp = new Cell(element, CellType.END);
                        if (tmp.isSbridge())
                            tmp.setBridgeNumber(++Bnum);
                        else if(tmp.isEbridge())
                            tmp.setBridgeNumber(++bnum);
                        map.add(tmp);
                    }
                    else {
                        success = false;
                        break;
                    }
                }
            } else
                success = false;
        } catch (IOException e) {}
    }

    public ArrayList<Cell> getMap() {
        if (isSuccess())
            return map;
        else
            return null;
    }

    private int validCell(ArrayList<String> cell) {
        if (cell.get(0).equals("S")) {
            if (cell.size() == 2 && validDirection(cell.get(1)))
                return 2;
            else if (cell.size() == 3 && validDirection(cell.get(1), cell.get(2)))
                return 1;
            else
                return 0;
        }
        else if (cell.get(0).equals("E") && cell.size() == 1)
            return 3;
        else if(validCellType(cell.get(0)) && cell.size() == 3 && validDirection(cell.get(1), cell.get(2)))
            return 1;
        else
            return 0;
    }

    private boolean validDirection(String c) {
        List<String> list = new ArrayList<>();
        list.add("L");
        list.add("R");
        list.add("U");
        list.add("D");

        return list.contains(c);
    }

    private boolean validCellType(String c) {
        List<String> list = new ArrayList<>();
        list.add("C"); list.add("H"); list.add("S"); list.add("P");
        list.add("B"); list.add("b");

        return list.contains(c);
    }

    private boolean validDirection(String c1, String c2) {
        List<String> list = new ArrayList<>();
        list.add("L");
        list.add("R");
        list.add("U");
        list.add("D");

        return list.contains(c1) && list.contains(c2);
    }

    public boolean isSuccess() {
        return success;
    }
}
