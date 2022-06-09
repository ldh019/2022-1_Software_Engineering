package boardGame;

import java.io.IOException;

public class boardGameController implements Cloneable {
    private BoardGame game;
    private static startView startV;
    private static gameView gameV;
    private static resultView resultV;

    public boardGameController() {
        reset();
    }

    @Override
    public boardGameController clone() throws CloneNotSupportedException {
        boardGameController clone = (boardGameController) super.clone();
        clone.game = game.clone();
        return clone;
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

    public BoardGame move(String d) {
        Player currentPlayer = game.getCurrentPlayer();
        Cell currentCell = game.getBoard().getCells().getCell(currentPlayer.getPosition());

        game.setMoveCount(game.getMoveCount() - 1);

        DirectionType input;
        switch (d) {
            case "U", "u" -> input = DirectionType.UP;
            case "D", "d" -> input = DirectionType.DOWN;
            case "L", "l" -> input = DirectionType.LEFT;
            case "R", "r" -> input = DirectionType.RIGHT;
            default -> input = DirectionType.NONE;
        }

        if (currentCell.isBridge()) {
            if (!game.isGoalIn() && input == currentCell.getPrevDir()) {
                game.move(currentCell.getBridgeLeft(), input);
                if (currentPlayer.getBridgeFlag() == -1) {
                    game.addBridge();
                    game.resetBridge();
                } else
                    currentPlayer.resetBridgeFlag();

            } else if (input == currentCell.getNextDir()) {
                game.move(currentCell.getBridgeRight(), input);
                if (currentPlayer.getBridgeFlag() == 1) {
                    game.addBridge();
                    game.resetBridge();
                } else
                    game.resetBridge();
            } else
                game.setMoveCount(game.getMoveCount() + 1);
        } else if (currentCell.isEnd())
            game.setMoveCount(0);
        else {
            if (input == currentCell.getNextDir())
                game.move(currentPlayer.getPosition() + 1, input);
            else if (!game.isGoalIn() && input == currentCell.getPrevDir())
                game.move(currentPlayer.getPosition() - 1, input);
            else if (!game.isGoalIn() && input == DirectionType.LEFT
                    && input == currentCell.getBridgeDir()) {
                game.move(-currentCell.getBridgeNumber(), input);
                game.setCrossBridgeLeft();
            } else if (input == DirectionType.RIGHT && input == currentCell.getBridgeDir()) {
                game.move(-currentCell.getBridgeNumber(), input);
                game.setCrossBridgeRight();
            } else
                game.setMoveCount(game.getMoveCount() + 1);
        }

        return game;
    }

    public BoardGame moveAfter() {
        Cell currentCell = game.getBoard().getCells().getCell(
                game.getCurrentPlayer().getPosition());

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

    public BoardGame startTurn() {
        game.startTurn();
        return game;
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

    public void gameFinish(BoardGame game) {
        resultV = new resultView(game);

        resultV.button.addActionListener(e -> {
            resultV.onExit();
            programExecute();
        });
    }

    private static void gameExit() {
        System.exit(0);
    }

    static void programExecute() {
        startV = new startView();

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

}

class main {
    public static void main(String[] args) {
        boardGameController.programExecute();
    }
}
