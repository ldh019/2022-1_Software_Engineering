package boardGame.view;

import boardGame.model.BoardGame;
import boardGame.model.Player;
import boardGame.model.Status;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class resultView extends JFrame{
    private JPanel panel;
    private ArrayList<Player> list;
    public JButton button;

    public resultView(BoardGame game) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        list = game.getPlayers();

        Collections.sort(list, new MyComparator());
        for (Player i : list) {
            JLabel label = new JLabel();
            label.setText("Player " + i.getIndex() + " Score : " + i.getStatus().getScore());
            label.setHorizontalAlignment(JLabel.CENTER);
            panel.add(label);
        }

        button = new JButton("처음으로");

        panel.add(button);

        this.add(panel);

        this.setSize(400, 300); // 프레임 크기 설정
        this.setVisible(true);// 프레임이 보이도록 설정
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setResult(BoardGame game) {
        ArrayList<Status> result = game.getResult();
    }

    public void onExit() {
        this.dispose();
    }
}

class MyComparator implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        if (o1.getStatus().getScore() > o2.getStatus().getScore())
            return 1;
        else if (o1.getStatus().getScore() > o2.getStatus().getScore())
            return -1;
        else return 0;
    }
}