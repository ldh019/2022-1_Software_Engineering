package boardGame.view;

import boardGame.model.Board;
import boardGame.model.Cell;
import boardGame.model.Cells;
import boardGame.model.DirectionType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class boardView {
    private JPanel panel;
    private int[] size, start;
    private Cells cells;
    public JPanel cell;

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
                gbc.gridx = start[0];
                gbc.gridy = start[1];
                panel.add(getCell(i), gbc);
            }
            else if (!i.isBridge()){
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

    public JPanel getCell(Cell c) {
        JPanel cell = new JPanel();
        JLabel imgLabel = new JLabel();
        this.cell = new JPanel();

        String cellImage = "";

        if (c.isStart())
            cellImage = "src/boardGame/resources/image/start.png";
        else if (c.isEnd())
            cellImage = "src/boardGame/resources/image/start.png";
        else if (c.isEbridge() || c.isCell())
            cellImage = "src/boardGame/resources/image/cell.png";
        else if (c.isHammer())
            cellImage = "src/boardGame/resources/image/hammer.png";
        else if (c.isPdriver())
            cellImage = "src/boardGame/resources/image/pdriver.png";
        else if (c.isSaw())
            cellImage = "src/boardGame/resources/image/saw.png";
        else if (c.isSbridge())
            cellImage = "src/boardGame/resources/image/sbridge.png";
        else if (c.isBridge())
            cellImage = "src/boardGame/resources/image/bridge.png";

        ImageIcon icon = new ImageIcon(cellImage);

        imgLabel.setIcon(icon);
        cell.add(imgLabel);
        this.cell.add(imgLabel);

        return cell;
    }

    public JPanel getPanel() {
        return panel;
    }
}
