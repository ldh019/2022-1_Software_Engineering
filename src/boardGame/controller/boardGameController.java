package boardGame.controller;

import boardGame.model.*;
import boardGame.view.finishView;
import boardGame.view.gameView;
import boardGame.view.mainView;
import boardGame.view.startView;

import java.awt.event.KeyEvent;

public class boardGameController {
    private static BoardGame game;

    public boardGameController() {
        reset();
    }

    public void exit() {
        System.exit(0);
    }

    public void reset() {
        game = new BoardGame();
    }

    public boolean canPlay() {
        return game.getPlayerNum() >= 2 && game.getPlayerNum() <= 4;
    }

    public int[] getSize() {
        return game.getBoard().getSize();
    }

    public BoardGame start() {
        game.start();

        return game;
    }

    public BoardGame join(int num) {
        for (int i = 0; i < num; i++)
            game.join();

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

    public static void main(String[] args) {
        mainView main = new mainView();
    }
}
