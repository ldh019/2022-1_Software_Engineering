package boardGame.view;

import boardGame.model.BoardGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class startView {
    public startView() {
        JFrame fr = new JFrame(); // 프레임

        JPanel pn1 = new JPanel(); // 패널1
        JPanel pn2 = new JPanel(); // 패널2
        JPanel titlePanel = new JPanel();
        JPanel namePanel = new JPanel();

        JButton buttonStart = new JButton("게임 시작"); //컴포넌트1
        JButton buttonLoad = new JButton("맵 불러오기"); //컴포넌트2
        JButton buttonExit = new JButton("게임 종료");

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

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BoardGame.onStart();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BoardGame.onLoadMap();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardGame.onExit();
            }
        });

        //프레임 설정
        fr.setLayout(fl); // 프레임 레이아웃 설정
        fr.add(new JPanel(), BorderLayout.NORTH);
        fr.add(pn1, BorderLayout.CENTER); // 큰 컨테이너(프레임) 안 작은 컨테이너(패널1) 넣기
        fr.add(new JPanel(), BorderLayout.SOUTH); // 큰 컨테이너(프레임) 안 작은 컨테이너(패널2) 넣기
        fr.setDefaultCloseOperation(EXIT_ON_CLOSE); //창을 종료하면 데몬스레드도 같이 종료
        fr.setSize(1000,600); // 프레임 크기 설정
        fr.setVisible(true);// 프레임이 보이도록 설정
    }
}
