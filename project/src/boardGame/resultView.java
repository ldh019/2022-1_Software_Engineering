package boardGame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

public class resultView extends JFrame{
    private JPanel panel;
    private ArrayList<Player> list;
    public JButton button;

    public resultView(BoardGame game) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        list = game.getPlayers();
        list.sort(new MyComparator());

        for (Player i : list) {
            JLabel label = new JLabel();
            label.setText("Player " + i.getIndex() + " Score : " + i.getStatus().getScore());
            label.setHorizontalAlignment(JLabel.CENTER);
            panel.add(label);
        }

        button = new JButton("처음으로");

        panel.add(button);

        this.add(panel);

        this.setSize(400, 300);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void onExit() {
        this.dispose();
    }
}

class MyComparator implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        if (o1.getStatus().getScore() > o2.getStatus().getScore())
            return -1;
        else if (o1.getStatus().getScore() > o2.getStatus().getScore())
            return 1;
        else return 0;
    }
}