package boardGame.view;

import boardGame.model.Board;
import boardGame.model.Cell;
import boardGame.model.DirectionType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class boardView {
    private JPanel panel;
    private int[] size, start;
    private ArrayList<Cell> cells;

    public boardView() {
        panel = new JPanel();
    }

    public void setboardView(Board board) {
        size = board.getSize();
        cells = board.getCells();
        start = board.getStart();

        panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1;
        gbc.gridwidth = gbc.gridheight = 1;

        for (Cell i : cells) {
            if (i.isStart()) {
                gbc.gridx = start[1];
                gbc.gridy = start[0];
                panel.add(getCell(i), gbc);
            }
            else {
                if (i.getPrevDir() == DirectionType.LEFT)
                    gbc.gridx++;
                else if (i.getPrevDir() == DirectionType.RIGHT)
                    gbc.gridx--;
                else if (i.getPrevDir() == DirectionType.UP)
                    gbc.gridy++;
                else if (i.getPrevDir() == DirectionType.DOWN)
                    gbc.gridy--;

                panel.add(getCell(i), gbc);

                if (i.isSbridge()) {
                    gbc.gridx++;
                    panel.add(getCell(cells.get(i.getBridgeNumber())), gbc);
                    gbc.gridx--;
                }
            }
        }
    }

    private JPanel getCell(Cell c) {
        JPanel cell = new JPanel();
        JLabel imgLabel = new JLabel();

        String cellImage = "";

        if (c.isStart())
            cellImage = "/boardGame/resources/image/start.png";
        else if (c.isEnd())
            cellImage = "/boardGame/resources/image/start.png";
        else if (c.isEbridge() || c.isCell())
            cellImage = "/boardGame/resources/image/cell.png";
        else if (c.isHammer())
            cellImage = "/boardGame/resources/image/hammer.png";
        else if (c.isPdriver())
            cellImage = "/boardGame/resources/image/pdriver.png";
        else if (c.isSaw())
            cellImage = "/boardGame/resources/image/saw.png";
        else if (c.isSbridge())
            cellImage = "/boardGame/resources/image/sbridge.png";
        else if (c.isBridge())
            cellImage = "/boardGame/resources/image/bridge.png";

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(boardView.class.getResource(cellImage)));

        imgLabel.setIcon(icon);
        cell.add(imgLabel);

        return cell;
    }

    public JPanel getPanel() {
        return panel;
    }
}
