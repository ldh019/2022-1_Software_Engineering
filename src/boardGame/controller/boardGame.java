package boardGame.controller;

import boardGame.model.Map;
import boardGame.view.loadFailView;
import boardGame.view.startView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class boardGame {
    startView main = new startView();

    private Map map;
    private ArrayList<Player> players;
    private ArrayList<Status> status;

    public boardGame() {
        map = new Map();
        players = new ArrayList<Player>();
        status = new ArrayList<Status>();
    }

    public static void onStart() throws IOException {
        loadMap load = new loadMap();
        if (load.isSuccess()) {
            new startGame(load.map());
        } else {
            new loadFailView();
        }
    }

    public static void onLoadMap() throws IOException {
        int result = 0;
        try {
            result = saveMap.load();
        } catch (Exception ignored) {
            new loadFailView();
        }
        if (result != 0)
            new loadFailView();
    }

    public static void onExit() {
        System.exit(0);
    }
}
