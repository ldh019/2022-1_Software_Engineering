package boardGame.view;

import javax.swing.*;
import java.awt.*;

public class startView {
    public JPanel panel;
    public JButton buttonStart;
    public JButton buttonLoad;
    public JButton buttonExit;

    public JPanel getPanel() {
        return panel;
    }

    public startView(mainView mainV) {
        super();

        panel = new JPanel();

        JPanel pn1 = new JPanel(); // 패널1
        JPanel pn2 = new JPanel(); // 패널2
        JPanel titlePanel = new JPanel();
        JPanel namePanel = new JPanel();

        buttonStart = new JButton("게임 시작"); //컴포넌트1
        buttonLoad = new JButton("맵 불러오기"); //컴포넌트2
        buttonExit = new JButton("게임 종료");

        String title = "<html><body style='text-align:center;'>2022-1 Software Engineering<br/>Term project - BoardGame<br/></body></html>";
        JLabel name = new JLabel("20202475 이동훈");
        JLabel lb = new JLabel(title);

        lb.setHorizontalAlignment(JLabel.CENTER);
        lb.setFont(lb.getFont().deriveFont(30.0f));
        name.setFont(name.getFont().deriveFont(20.0f));

        pn1.setLayout(new BoxLayout(pn1, BoxLayout.Y_AXIS));

        titlePanel.add(lb);
        pn1.add(titlePanel);
        pn1.add(new JPanel());

        namePanel.add(name);
        pn1.add(namePanel);
        pn1.add(pn2);

        //레이아웃 설정
        BorderLayout fl = new BorderLayout(0, 120); //레이아웃 설정

        //패널 1 설정
        pn2.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        pn2.add(buttonStart); // 컨테이너(패널) 안 컴포넌트(버튼1) 넣기
        pn2.add(buttonLoad); // 컨테이너(패널) 안 컴포넌트(버튼2) 넣기
        pn2.add(buttonExit);


        buttonStart.addActionListener(e -> mainV.change());
        buttonExit.addActionListener(e -> mainV.gameV.controller.exit());

        //프레임 설정
        panel.setLayout(fl); // 프레임 레이아웃 설정
        panel.add(new JPanel(), BorderLayout.NORTH);
        panel.add(pn1, BorderLayout.CENTER); // 큰 컨테이너(프레임) 안 작은 컨테이너(패널1) 넣기
        panel.add(new JPanel(), BorderLayout.SOUTH); // 큰 컨테이너(프레임) 안 작은 컨테이너(패널2) 넣기
        panel.setSize(1200, 900); // 프레임 크기 설정
        panel.setVisible(true);// 프레임이 보이도록 설정
    }
}