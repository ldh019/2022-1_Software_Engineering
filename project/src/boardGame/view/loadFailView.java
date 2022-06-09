package boardGame.view;

import javax.swing.*;
import java.awt.*;

public class loadFailView {
    public loadFailView() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        String text = "맵을 불러오는 데 실패했습니다.";

        frame.setLayout(new FlowLayout());

        label.setFont(label.getFont().deriveFont(15.0f));
        label.setText(text);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        panel.add(label);
        frame.add(panel);

        frame.setSize(300, 150);
        frame.setVisible(true);
    }
}
