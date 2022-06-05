package boardGame.view;

import boardGame.controller.boardGameController;
import boardGame.model.BoardGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameView {
    JPanel panel, controlPanel;
    boardView boardV;
    statusView statusV;
    boardGameController controller;
    BoardGame game;

    public gameView() {
        JTextField tf = new JTextField();
        JButton startButton = new JButton();
        final int[] number = {0};
        panel = new JPanel();
        controlPanel.add(controlView.waitingPanel(tf, startButton));
        panel.setLayout(new BorderLayout());

        boardV = new boardView();
        statusV = new statusView();

        panel.add(boardV.getPanel(), BorderLayout.CENTER);
        panel.add(statusV.getPanel(), BorderLayout.EAST);
        controller = new boardGameController();

        tf.addActionListener(e -> number[0] = Integer.parseInt(tf.getText()));
        startButton.addActionListener(e -> {
            controller.join(number[0]);

            if (controller.canPlay() != null) {
                controlPanel.removeAll();
                controlPanel.add(controlView.playingPanel());

                controller.reset();
                game = controller.start();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}

class controlView {

    public static JPanel waitingPanel(JTextField tf, JButton start) {
        JPanel panel = new JPanel();
        JLabel text = new JLabel();
        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);

        start.setText("시작");
        panel.setLayout(bl);
        text.setText("게임에 참가할 인원을 입력해주세요.");

        panel.add(text);
        panel.add(tf);
        panel.add(start);

        return panel;
    }

    public static JPanel playingPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton roll, rest;
        roll = new JButton("주사위 굴리기");
        rest = new JButton("한 번 쉬기");

        return panel;
    }
}
