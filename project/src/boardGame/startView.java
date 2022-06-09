package boardGame;

import javax.swing.*;
import java.awt.*;

public class startView extends JFrame{
    public JButton buttonStart;
    public JButton buttonLoad;
    public JButton buttonExit;

    public startView() {
        super();

        JPanel pn1 = new JPanel();
        JPanel pn2 = new JPanel();
        JPanel titlePanel = new JPanel();
        JPanel namePanel = new JPanel();

        buttonStart = new JButton("게임 시작");
        buttonLoad = new JButton("맵 불러오기");
        buttonExit = new JButton("게임 종료");

        String title = "<html><body style='text-align:center;'>" +
                "2022-1 Software Engineering<br/>" +
                "Term project - BoardGame<br/>" +
                "</body></html>";

        JLabel name = new JLabel("20202475 이동훈");
        JLabel lb = new JLabel(title);

        lb.setHorizontalAlignment(JLabel.CENTER);
        lb.setFont(lb.getFont().deriveFont(30.0f));
        name.setFont(name.getFont().deriveFont(20.0f));

        pn1.setLayout(new BoxLayout(pn1, BoxLayout.Y_AXIS));

        titlePanel.add(lb);
        pn1.add(titlePanel);

        namePanel.add(name);
        pn1.add(namePanel);
        pn1.add(pn2);

        BorderLayout fl = new BorderLayout(0, 120);

        pn2.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        pn2.add(buttonStart);
        pn2.add(buttonLoad);
        pn2.add(buttonExit);

        this.setLayout(fl);
        this.add(new JPanel(), BorderLayout.NORTH);
        this.add(pn1, BorderLayout.CENTER);
        this.add(new JPanel(), BorderLayout.SOUTH);
        this.setSize(1200, 900);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void onExit() {
        this.dispose();
    }
}