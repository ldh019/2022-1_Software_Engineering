package boardGame.controller;

import boardGame.model.*;
import boardGame.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class boardGameController {
    private static BoardGame game;
    private static gameView gameV;
    private static resultView resultV;

    public boardGameController() {
        reset();
    }

    public BoardGame reset() {
        game = new BoardGame();

        return game;
    }

    public BoardGame start() {
        game.start();

        return game;
    }

    public BoardGame join() {
        game.join();

        return game;
    }

    public BoardGame leave() {
        game.leave();

        return game;
    }

    public BoardGame roll() {
        game.roll();

        return game;
    }

    public BoardGame rest() {
        game.rest();

        return game;
    }

    public BoardGame move(KeyEvent e) {
        game.setCurrentState(GameState.MOVING);
        Player currentPlayer = game.getCurrentPlayer();
        Cell currentCell = game.getBoard().getCells().get(currentPlayer.getPosition());

        game.setMoveCount(game.getMoveCount() - 1);

        DirectionType input;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> input = DirectionType.UP;
            case KeyEvent.VK_DOWN -> input = DirectionType.DOWN;
            case KeyEvent.VK_LEFT -> input = DirectionType.LEFT;
            case KeyEvent.VK_RIGHT -> input = DirectionType.RIGHT;
            default -> input = DirectionType.NONE;
        }

        if (currentCell.isBridge()) {
            if (!game.isGoalIn() && input == currentCell.getPrevDir()) {
                game.move(currentCell.getBridgeLeft());
                if (currentPlayer.getBridgeFlag() == -1) {
                    game.addBridge();
                    game.resetBridge();
                } else
                    currentPlayer.resetBridgeFlag();

            } else if (input == currentCell.getNextDir()) {
                game.move(currentCell.getBridgeRight());
                if (currentPlayer.getBridgeFlag() == 1) {
                    game.addBridge();
                    game.resetBridge();
                } else
                    game.resetBridge();
            } else
                game.setMoveCount(game.getMoveCount() + 1);
        } else {
            if (input == currentCell.getNextDir())
                game.move(currentPlayer.getPosition() + 1);
            else if (!game.isGoalIn() && input == currentCell.getPrevDir())
                game.move(currentPlayer.getPosition() - 1);
            else if (!game.isGoalIn() && input == DirectionType.LEFT && input == currentCell.getBridgeDir()) {
                game.move(currentCell.getBridgeNumber());
                game.setCrossBridgeLeft();
            } else if (input == DirectionType.RIGHT && input == currentCell.getBridgeDir()) {
                game.move(currentCell.getBridgeNumber());
                game.setCrossBridgeRight();
            } else
                game.setMoveCount(game.getMoveCount() + 1);
        }

        if (currentCell.isEnd())
            game.setMoveCount(0);
        return game;
    }

    public BoardGame moveAfter() {
        Cell currentCell = game.getBoard().getCells().get(game.getCurrentPlayer().getPosition());

        if (currentCell.isEnd()) {
            game.getCurrentPlayer().setGoalIn();
            game.getCurrentPlayer().getStatus().setEndBonus(game.getRank());
            game.addRank();
            game.setGoalInFlag();
        } else if (currentCell.isPdriver()) {
            game.getCurrentPlayer().getStatus().addPdriver();
        } else if (currentCell.isHammer()) {
            game.getCurrentPlayer().getStatus().addHammer();
        } else if (currentCell.isSaw()) {
            game.getCurrentPlayer().getStatus().addSaw();
        }

        return game;
    }

    public void startTurn() {
        game.startTurn();
    }

    public BoardGame endTurn() {
        game.endTurn();

        return game;
    }

    private static void backToStart(gameView gameV) {
        gameV.onExit();
        programExecute();
    }

    private static void gameEnter(startView startV) {
        startV.onExit();
        gameV = new gameView();

        gameV.exitButton.addActionListener(e -> backToStart(gameV));
    }

    private static void newMapLoad() throws IOException {
        saveMap.load();
    }

    private static void gameExit() {
        System.exit(0);
    }

    private static void programExecute() {
        startView startV = new startView();

        startV.buttonStart.addActionListener(e -> gameEnter(startV));
        startV.buttonLoad.addActionListener(e -> {
            try {
                newMapLoad();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        startV.buttonExit.addActionListener(e -> gameExit());
    }

    public static void main(String[] args) {
        programExecute();
    }
}
