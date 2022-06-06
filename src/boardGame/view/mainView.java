package boardGame.view;

import boardGame.controller.boardGameController;
import boardGame.model.BoardGame;

import javax.swing.*;
import java.awt.*;

public class mainView extends JFrame {
    public startView startV;
    public gameView gameV;
    public finishView finishV;
    public boardGameController controller;
    public JPanel panel;

    public mainView() {
        this.setSize(1200, 800); // 프레임 크기 설정
        this.setVisible(true);// 프레임이 보이도록 설정
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); //창을 종료하면 데몬스레드도 같이 종료

        this.setLayout(new FlowLayout());
        panel = new JPanel();
        controller = new boardGameController();

        this.add(panel);
        startV = new startView(this);
        gameV = new gameView(this);
        finishV = new finishView();

        panel.add(startV.getPanel());
    }

    public void change() {
        panel.removeAll();
        panel.add(gameV.getPanel());
        panel.revalidate();
        panel.repaint();
        System.out.println("change");
    }

    public void finish(BoardGame game) {
        finishV.setResult(game);
        this.removeAll();
        this.add(finishV.getPanel());
        this.revalidate();
        this.repaint();
    }
}
