package boardGame.view;

import javax.swing.*;

public class mainView extends JFrame {
    public startView startV;
    public gameView gameV;
    public finishView finishV;

    public mainView() {
        this.setSize(1200, 800); // 프레임 크기 설정
        this.setVisible(true);// 프레임이 보이도록 설정
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); //창을 종료하면 데몬스레드도 같이 종료

        this.add(startV.getPanel());
    }

    public void change() {
        this.removeAll();
        this.add(gameV.getPanel());
    }

    public void finish() {
        this.removeAll();
        this.add(finishV.getPanel());
    }
}
