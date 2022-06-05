package boardGame.controller;

import boardGame.model.*;
import boardGame.view.gameView;
import boardGame.view.mainView;
import boardGame.view.startView;

public class boardGameController {
    private static BoardGame game;

    public void exit() {
        System.exit(0);
    }

    public void reset() {
        game = new BoardGame();
    }

    public BoardGame canPlay() {
        return game;
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
/*
    public void move() {
        game.setCurrentState(GameState.MOVING);
        Player currentPlayer = game.getCurrentPlayer();
        Cell currentCell = game.getBoard().getCells().get(currentPlayer.getPosition());

        int moveCount = game.getMoveCount() - currentPlayer.getStatus().getBridge();
        while (moveCount-- > 0) {
            currentCell = game.getBoard().getCells().get(currentPlayer.getPosition());

            if (currentCell.isEnd())
                break;

            DirectionType input = getMoveDir();

            if (currentCell.isBridge()) {
                if (!game.isGoalIn() && input == currentCell.getPrevDir()) {
                    currentPlayer.move(currentCell.getBridgeLeft());
                    if (currentPlayer.getBridgeFlag() == -1) {
                        currentPlayer.getStatus().addBridge();
                        currentPlayer.resetBridgeFlag();
                    }
                }
                else if (input == currentCell.getNextDir()) {
                    currentPlayer.move(currentCell.getBridgeRight());
                    if (currentPlayer.getBridgeFlag() == 1) {
                        currentPlayer.getStatus().addBridge();
                        currentPlayer.resetBridgeFlag();
                    }
                }
                else moveCount++;
            }
            else {
                if (input == currentCell.getNextDir())
                    currentPlayer.move(currentPlayer.getPosition() + 1);
                else if (!game.isGoalIn() && input == currentCell.getPrevDir())
                    currentPlayer.move(currentPlayer.getPosition() - 1);
                else if (!game.isGoalIn() && input == DirectionType.LEFT && input == currentCell.getBridgeDir()) {
                    currentPlayer.move(currentCell.getBridgeNumber());
                    currentPlayer.setBridgeFlagLeft();
                }
                else if (input == DirectionType.RIGHT && input == currentCell.getBridgeDir()) {
                    currentPlayer.move(currentCell.getBridgeNumber());
                    currentPlayer.setBridgeFlagRight();
                }
                else moveCount++;
            }
        }

        if (currentCell.isEnd()) {
            currentPlayer.setGoalIn();
            currentPlayer.getStatus().setEndBonus(game.getRank());
            game.addRank();
            game.setGoalInFlag();
        }
        else if (currentCell.isPdriver()) {
            currentPlayer.getStatus().addPdriver();
            currentCell.removeTool();
        }
        else if (currentCell.isHammer()) {
            currentPlayer.getStatus().addHammer();
            currentCell.removeTool();
        }
        else if (currentCell.isSaw()) {
            currentPlayer.getStatus().addSaw();
            currentCell.removeTool();
        }
    }
*/
    public static void main(String[] args) {
        mainView main = new mainView(game);

        main.startV = new startView(main);
        main.gameV = new gameView();
    }
}
