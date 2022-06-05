package boardGame.view;

import boardGame.controller.boardGameController;
import boardGame.model.BoardGame;
import boardGame.model.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class gameView {
    JPanel panel, controlPanel;
    boardView boardV;
    statusView statusV;
    boardGameController controller;
    static BoardGame game;
    mainView parent;

    public gameView(mainView parent) {
        controller = new boardGameController();
        panel = new JPanel();
        controlPanel.add(controlView.waitingPanel(this, controller));
        panel.setLayout(new BorderLayout());

        boardV = new boardView();
        statusV = new statusView();
        this.parent = parent;

        panel.add(boardV.getPanel(), BorderLayout.CENTER);
        panel.add(statusV.getPanel(), BorderLayout.EAST);
    }

    public void change() {
        controlPanel.removeAll();
        controlPanel.add(controlView.playingPanel(this, controller));

        controller.reset();
        game = controller.start();
    }

    public void moving() {
        while (game.getMoveCount() > 0) {
            panel.addKeyListener(new KeyAdapter() {
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
            parent.finish();
    }

    public JPanel getPanel() {
        return panel;
    }

    static class controlView {
        public static JPanel waitingPanel(gameView parent, boardGameController controller) {
            JPanel panel = new JPanel();
            JLabel text = new JLabel();
            JTextField tf = new JTextField();
            JButton start = new JButton();
            BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
            final int[] number = new int[0];

            start.setText("시작");
            panel.setLayout(bl);
            text.setText("게임에 참가할 인원을 입력해주세요.");

            panel.add(text);
            panel.add(tf);
            panel.add(start);

            tf.addActionListener(e -> number[0] = Integer.parseInt(tf.getText()));
            start.addActionListener(e -> {
                controller.join(number[0]);

                if (controller.canPlay())
                    parent.change();
            });

            return panel;
        }

        public static JPanel playingPanel(gameView parent, boardGameController controller) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JButton roll, rest;
            roll = new JButton("주사위 굴리기");
            rest = new JButton("한 번 쉬기");

            roll.addActionListener(actionEvent -> {
                controller.startTurn();
                game = controller.roll();
                parent.moving();
            });

            rest.addActionListener(actionEvent -> game = controller.rest());

            return panel;
        }
    }
}


