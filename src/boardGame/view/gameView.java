package boardGame.view;

import boardGame.controller.boardGameController;
import boardGame.model.BoardGame;
import boardGame.model.Cell;
import boardGame.model.Cells;
import boardGame.model.DirectionType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class gameView extends JFrame{
    private JPanel mainPanel, controlPanel;
    private JPanel boardPanel;
    private JPanel joinPanel, statusPanel, buttonJoinPanel, buttonPlayPanel;
    public JButton startButton, exitButton, rollButton, restButton;
    public JButton plusButton, minusButton;
    private JLabel playerCount;
    private boardGameController controller;
    private BoardGame game;

    public gameView() {
        this.setLayout(new BorderLayout());

        controller = new boardGameController();

        //Main Panel setting start
        mainPanel = new JPanel();

        //Board Panel setting start
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1;
        gbc.gridwidth = gbc.gridheight = 1;

        int[] size = game.getBoard().getSize();
        Cells cells = game.getBoard().getCells();
        int[] start = game.getBoard().getStart();

        for (Cell i : cells) {
            if (i.isStart()) {
                gbc.gridx = start[0];
                gbc.gridy = start[1];

                JPanel cell = new JPanel();
                JLabel imgLabel = new JLabel();
                String cellImage = getCell(i);
                ImageIcon icon = new ImageIcon(cellImage);

                imgLabel.setIcon(icon);
                cell.add(imgLabel);
                boardPanel.add(cell, gbc);
            }
            else if (!i.isBridge()){
                if (i.getPrevDir() == DirectionType.LEFT)
                    gbc.gridx++;
                else if (i.getPrevDir() == DirectionType.RIGHT)
                    gbc.gridx--;
                else if (i.getPrevDir() == DirectionType.UP)
                    gbc.gridy++;
                else if (i.getPrevDir() == DirectionType.DOWN)
                    gbc.gridy--;

                JPanel cell = new JPanel();
                JLabel imgLabel = new JLabel();
                String cellImage = getCell(i);
                ImageIcon icon = new ImageIcon(cellImage);

                imgLabel.setIcon(icon);
                cell.add(imgLabel);
                boardPanel.add(cell, gbc);

                if (i.isSbridge()) {
                    gbc.gridx++;
                    cell = new JPanel();
                    imgLabel = new JLabel();
                    cellImage = "src/boardGame/resources/image/bridge.png";
                    icon = new ImageIcon(cellImage);

                    imgLabel.setIcon(icon);
                    cell.add(imgLabel);
                    boardPanel.add(cell, gbc);

                    boardPanel.add(cell, gbc);
                    gbc.gridx--;
                }
            }
        }
        //Board Panel setting end

        mainPanel.add(boardPanel);
        //Main Panel setting end

        //Control Panel setting start
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        //Join Panel setting start
        joinPanel = new JPanel();
        joinPanel.setLayout(new FlowLayout());

        plusButton = new JButton("+");
        minusButton = new JButton("-");
        playerCount = new JLabel("2");

        plusButton.addActionListener(e -> {
            game = controller.join();
            playerCount.setText(Integer.toString(game.getPlayerNum()));
        });

        minusButton.addActionListener(e -> {
            controller.leave();
            playerCount.setText(Integer.toString(game.getPlayerNum()));
        });

        joinPanel.add(minusButton);
        joinPanel.add(playerCount);
        joinPanel.add(plusButton);
        //Join Panel setting end

        //Status Panel setting start
        statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout());
        //Status Panel setting end

        //Button Join Panel setting start
        buttonJoinPanel = new JPanel();
        buttonJoinPanel.setLayout(new FlowLayout());

        startButton = new JButton("START");
        exitButton = new JButton("EXIT");

        startButton.addActionListener(e -> {
            game = controller.start();
        });

        buttonJoinPanel.add(startButton);
        buttonJoinPanel.add(exitButton);
        //Button Join Panel setting end

        //Button Play Panel setting start
        buttonPlayPanel = new JPanel();
        buttonPlayPanel.setLayout(new FlowLayout());

        rollButton = new JButton("ROLL");
        restButton = new JButton("REST");

        rollButton.addActionListener(e -> {
            game = controller.roll();
        });

        restButton.addActionListener(e -> {
            game = controller.rest();
        });

        buttonPlayPanel.add(rollButton);
        buttonPlayPanel.add(restButton);
        //Button Join Panel setting end

        controlPanel.add(joinPanel);
        controlPanel.add(buttonJoinPanel);
        //Control Panel setting done

        this.add(mainPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.EAST);
    }

    public void change() {
        controlPanel.removeAll();
        controlPanel.add(statusPanel);

        controller.reset();
        game = controller.start();
    }

    public void moving() {
        while (game.getMoveCount() > 0) {
            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    controller.move(e);
                }
            });
        }

        game = controller.moveAfter();
        game = controller.endTurn();

        if (game.isFinish())
            ;
    }
    

    public String getCell(Cell c) {
        String cellImage = "";

        if (c.isStart())
            cellImage = "src/boardGame/resources/image/start.png";
        else if (c.isEnd())
            cellImage = "src/boardGame/resources/image/start.png";
        else if (c.isEbridge() || c.isCell())
            cellImage = "src/boardGame/resources/image/cell.png";
        else if (c.isHammer())
            cellImage = "src/boardGame/resources/image/hammer.png";
        else if (c.isPdriver())
            cellImage = "src/boardGame/resources/image/pdriver.png";
        else if (c.isSaw())
            cellImage = "src/boardGame/resources/image/saw.png";
        else if (c.isSbridge())
            cellImage = "src/boardGame/resources/image/sbridge.png";
        else if (c.isBridge())
            cellImage = "src/boardGame/resources/image/bridge.png";

        return cellImage;
    }

    public void onExit() {
        this.dispose();
    }
}


