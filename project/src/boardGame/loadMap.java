package boardGame;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class loadMap {
    private boolean success = false;
    private Cells map;
    private int startX, startY;
    private int maxX, maxY;
    private int curX, curY;

    public loadMap() {
        String filePath;
        File mapFile;
        Path mapPath;
        List<String> mapData;

        try {
            filePath = "../resources/recent.map";
            mapFile = new File(filePath);

            if(!mapFile.exists()) {
                String tmpfilePath = "../resources/default.map";;
                File save = new File(tmpfilePath);

                Files.copy(save.toPath(), mapFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            mapPath = mapFile.toPath();

            curX = curY = 0;

            startX = startY = maxX = maxY = 0;
            success = true;

            mapData = Files.readAllLines(mapPath);
            map = new Cells();

            String line = mapData.get(0);
            ArrayList<String> element = new ArrayList<>(Arrays.asList(line.split(" ")));
            int Bnum = 0;

            if (validCell(element) == 2) {
                map.add(new Cell(element, CellType.START, 0) );
                setSize(element.get(1));

                for (int i = 1; i < mapData.size(); i++) {
                    line = mapData.get(i);
                    element = new ArrayList<String>(Arrays.asList(line.split(" ")));

                    if (validCell(element) == 1) {
                        Cell tmp = new Cell(element, i);

                        if (tmp.isSbridge()) {
                            tmp.setBridgeNumber(++Bnum);
                            ArrayList<String> tmpstr = new ArrayList<>();
                            tmpstr.add("BB");
                            tmpstr.add("L");
                            tmpstr.add("R");
                            Cell bridge = new Cell(tmpstr, -Bnum);
                            map.add(bridge);
                            bridge.setBridgeLeft(tmp.getIndex());
                        }
                        else if(tmp.isEbridge()) {
                            Cell bridge = map.getCell(-Bnum);
                            tmp.setBridgeNumber(Bnum--);
                            bridge.setBridgeRight(i);
                        }
                        map.add(tmp);
                        setSize(element.get(2));
                    }
                    else if (validCell(element) == 3) {
                        String tmp = mapData.get(i - 1).split(" ")[2];
                        switch(tmp) {
                            case "U" -> element.add("D");
                            case "D" -> element.add("U");
                            case "L" -> element.add("R");
                            case "R" -> element.add("L");
                        }

                        map.add(new Cell(element, CellType.END, i));
                    }
                    else {
                        success = false;
                        break;
                    }
                }
            } else
                success = false;

            if (startX < 0) {
                maxX = maxX + (-startX);
                startX = -startX;
            }
            if (startY < 0) {
                maxY = maxY + (-startY);
                startY = -startY;
            }
            maxX++; maxY++;
        } catch (IOException e) {}
    }

    public Cells getMap() {
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
        else if(validCellType(cell.get(0)) && cell.size() == 3 &&
                validDirection(cell.get(1), cell.get(2)))
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

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setSize(String dir) {
        switch(dir) {
            case "U" -> curY--;
            case "D" -> curY++;
            case "L" -> curX--;
            case "R" -> curX++;
        }

        if (curY < startY) startY = curY;
        if (curY > maxY) maxY = curY;
        if (curX < startX) startX = curX;
        if (curX > maxX) maxX = curX;
    }
}
