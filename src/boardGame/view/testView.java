package boardGame.view;

import boardGame.controller.boardGameController;
import boardGame.model.Board;
import boardGame.model.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class testView extends JFrame {
    public testView() {
        this.setSize(1200, 800); // 프레임 크기 설정
        this.setVisible(true);// 프레임이 보이도록 설정
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); //창을 종료하면 데몬스레드도 같이 종료
        this.setLayout(new FlowLayout());
        Board b = new Board();
        boardView bv = new boardView();
        JPanel panel = new JPanel();

        //bv.setboardView(b);

        //panel.add(bv.getPanel());
       // panel.setLayout(new GridBagLayout());
        //GridBagConstraints gbc = new GridBagConstraints();

        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("C"); tmp.add("U"); tmp.add("D");
        Cell c = new Cell(tmp, 0);
      /*  gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1;
        gbc.gridwidth = gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
*/
        JPanel m = bv.getCell(c);
        JPanel n = bv.cell;
        //panel.add(m, gbc);

        this.add(n);
        this.add(m);
    }
}
