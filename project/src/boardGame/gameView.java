package boardGame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class gameView extends JFrame{
    private JLayeredPane mainPanel;
    private JPanel controlPanel;
    private JPanel boardPanel;
    private JPanel[] tokenPanel;
    private JPanel joinPanel, statusPanel, buttonJoinPanel, buttonPlayPanel;
    private JPanel turnPanel, diePanel, inputPanel;
    private JLabel turnLabel, dieLabel;
    private JButton inputButton;
    private JTextField inputField;
    private String turnString, dieString;
    private JTable statusTable;
    private JScrollPane scrollPane;
    private DefaultTableModel dtm;
    public JButton startButton, exitButton, rollButton, restButton;
    public JButton plusButton, minusButton;
    private JLabel playerCount;

    private boardGameController controller;
    private BoardGame game;

    private String[] header;

    public gameView() {
        this.setLayout(new BorderLayout());

        controller = new boardGameController();
        game = controller.reset();

        //Main Panel setting start
        mainPanel = new JLayeredPane();

        //Board Panel setting start
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1;
        gbc.gridwidth = gbc.gridheight = 1;
        gbc.ipadx = gbc.ipady = 0;

        Cells cells = game.getBoard().getCells();
        int[] start = game.getBoard().getStart();

        for (Cell i : cells) {
            if (i.isStart()) {
                gbc.gridx = start[0];
                gbc.gridy = start[1];

                JLabel imgLabel = new JLabel();
                String cellImage = getCell(i);
                ImageIcon icon = new ImageIcon(cellImage);

                imgLabel.setIcon(icon);
                boardPanel.add(imgLabel, gbc);
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

                JLabel imgLabel = new JLabel();
                String cellImage = getCell(i);
                ImageIcon icon = new ImageIcon(cellImage);

                imgLabel.setIcon(icon);
                boardPanel.add(imgLabel, gbc);

                if (i.isSbridge()) {
                    gbc.gridx++;
                    imgLabel = new JLabel();
                    cellImage = "boardGame/resources/image/bridge.png";
                    icon = new ImageIcon(cellImage);

                    imgLabel.setIcon(icon);
                    boardPanel.add(imgLabel, gbc);
                    gbc.gridx--;
                }
            }
        }
        boardPanel.setBounds(20, 20,
                game.getSize()[0] * 60,
                game.getSize()[1] * 60);

        boardPanel.setOpaque(true);
        //Board Panel setting end

        //Token Panel setting start

        tokenPanel = new JPanel[4];
        for(int i = 0; i < 4; i++)
            tokenPanel[i] = new JPanel();

        for (JPanel i : tokenPanel)
            i.setBackground(new Color(255, 0, 0, 0));

        for(int i = 0; i < 4; i++) {
            JLabel imgLabel = new JLabel();
            String cellImage = "boardGame/resources/image/token" + i + ".png";
            ImageIcon icon = new ImageIcon(cellImage);

            imgLabel.setIcon(icon);
            tokenPanel[i].add(imgLabel);
            tokenPanel[i].setBounds(14 + start[0] * 60,
                    10 + start[1] * 60,
                    60, 60);
            tokenPanel[i].setOpaque(true);
            tokenPanel[i].setVisible(false);
            mainPanel.add(tokenPanel[i]);
        }
        //Token Panel setting end
        mainPanel.setBounds(10, 10, 800, 800);
        mainPanel.add(boardPanel);
        //Main Panel setting end

        //Control Panel setting start
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        turnPanel = new JPanel(new FlowLayout());
        turnLabel = new JLabel();
        turnString = "Turn Player : ";
        turnLabel.setFont(new Font(turnLabel.getFont().getFontName(), Font.PLAIN, 30));
        turnPanel.add(turnLabel);

        diePanel = new JPanel();
        dieLabel = new JLabel();
        dieString = "Left To Move : ";
        dieLabel.setFont(new Font(turnLabel.getFont().getFontName(), Font.PLAIN, 30));
        diePanel.add(dieLabel);

        inputPanel = new JPanel(new FlowLayout());
        inputField = new JTextField(10);
        inputButton = new JButton("??????");

        inputField.setBounds(0, 0, 200, 40);

        inputPanel.add(inputField);
        inputPanel.add(inputButton);

        inputButton.addActionListener(e -> {
            String input = inputField.getText();

            if (input.length() == game.getMoveCount()) {
                boolean flag = true;
                boardGameController tmpc = null;
                BoardGame tmp = null;
                try {
                    tmpc = controller.clone();
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }

                String[] moveDir = input.split("");
                int[] prev = game.getCurrentCell();

                for (String i : moveDir) {
                    tmp = tmpc.move(i);
                    if (tmp.getCurrentCell() != prev) {
                        dieLabel.setText(dieString + (tmp.getMoveCount()));
                        tokenPanel[tmp.getPlayerIndex()].setBounds(
                                14 + tmp.getCurrentCell()[0] * 60,
                                10 + tmp.getCurrentCell()[1] * 60,
                                60, 60);
                        prev = tmp.getCurrentCell();
                    } else if (tmp.getMoveCount() == 0)
                        break;
                    else if (!tmp.getCurrentPlayer().getGoalIn()){
                        flag = false;
                        dieLabel.setText(dieString + (game.getMoveCount()));
                        tokenPanel[game.getPlayerIndex()].setBounds(
                                14 + game.getCurrentCell()[0] * 60,
                                10 + game.getCurrentCell()[1] * 60,
                                60, 60);
                        break;
                    }

                }

                if (flag) {
                    controller = tmpc;
                    game = tmp;
                    inputField.setText("");
                    controlPanel.revalidate();
                    controlPanel.repaint();
                    moveDone();
                    rollButton.setEnabled(true);
                    restButton.setEnabled(true);
                }
            }
        });

        //Join Panel setting start
        joinPanel = new JPanel();
        joinPanel.setLayout(new FlowLayout());

        plusButton = new JButton("+");
        minusButton = new JButton("-");
        playerCount = new JLabel("2");

        plusButton.addActionListener(e -> {
            game = controller.join();
            playerCount.setText(Integer.toString(game.getPlayerNum()));

            if (game.getPlayerNum() > dtm.getRowCount()) {
                dtm.addRow(get_status_contents(game.getPlayerNum()));
            }
        });

        minusButton.addActionListener(e -> {
            game = controller.leave();
            playerCount.setText(Integer.toString(game.getPlayerNum()));

            if (game.getPlayerNum() < dtm.getRowCount()) {
                dtm.removeRow(game.getPlayerNum());
            }
        });

        joinPanel.add(minusButton);
        joinPanel.add(playerCount);
        joinPanel.add(plusButton);
        //Join Panel setting end

        //Status Panel setting start
        statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout());

        header = new String[]{"Player", "Bridge", "P-Driver", "Hammer", "Saw", "Score"};
        dtm = new DefaultTableModel(header, 0);
        statusTable = new JTable(dtm);
        dtm.addRow(get_status_contents(1));
        dtm.addRow(get_status_contents(2));

        DefaultTableCellRenderer tscr = new DefaultTableCellRenderer();
        tscr.setHorizontalAlignment(SwingConstants.CENTER);
        TableColumnModel tcm = statusTable.getColumnModel();
        for (int i = 0; i < tcm.getColumnCount(); i++)
            tcm.getColumn(i).setCellRenderer(tscr);

        statusTable.setRowHeight(30);

        scrollPane = new JScrollPane(statusTable);
        statusPanel.add(scrollPane);

        //Status Panel setting end

        //Button Join Panel setting start
        buttonJoinPanel = new JPanel();
        buttonJoinPanel.setLayout(new FlowLayout());

        startButton = new JButton("START");
        exitButton = new JButton("EXIT");

        startButton.addActionListener(e -> {
            change();
        });

        buttonJoinPanel.add(startButton);
        buttonJoinPanel.add(exitButton);
        //Button Join Panel setting end

        //Button Play Panel setting start
        buttonPlayPanel = new JPanel();
        buttonPlayPanel.setLayout(new FlowLayout());

        rollButton = new JButton("ROLL");
        restButton = new JButton("REST");

        rollButton.addActionListener(e -> {
            rollButton.setEnabled(false);
            restButton.setEnabled(false);
            game = controller.roll();
            moving();
            inputPanel.setVisible(true);
        });

        restButton.addActionListener(e -> {
            game = controller.rest();
            game = controller.endTurn();
            inputPanel.setVisible(false);
            nextTurn();
        });

        buttonPlayPanel.add(rollButton);
        buttonPlayPanel.add(restButton);
        //Button Join Panel setting end

        controlPanel.add(joinPanel);
        controlPanel.add(buttonJoinPanel);
        //Control Panel setting done

        this.add(new JPanel(), BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.EAST);

        this.setSize(1200, 900);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void change() {
        controlPanel.removeAll();

        controlPanel.add(turnPanel);
        controlPanel.add(diePanel);
        controlPanel.add(inputPanel);
        controlPanel.add(statusPanel);
        controlPanel.add(buttonPlayPanel);

        scrollPane.setPreferredSize(new Dimension(350, 30));

        game = controller.start();

        turnLabel.setText(turnString + (game.getPlayerIndex() + 1));
        dieLabel.setText(dieString);

        for (int i = 0; i < game.getPlayerNum(); i++)
            tokenPanel[i].setVisible(true);

        inputPanel.setVisible(false);

        controlPanel.revalidate();
        controlPanel.repaint();
    }

    public void setStatusTable(ArrayList<Status> list) {
         for (int i = 0; i < list.size(); i++) {
             dtm.setValueAt(list.get(i).getBridge(), i, 1);
             dtm.setValueAt(list.get(i).getPdriver(), i, 2);
             dtm.setValueAt(list.get(i).getHammer(), i, 3);
             dtm.setValueAt(list.get(i).getSaw(), i, 4);
             dtm.setValueAt(list.get(i).getScore(), i, 5);
         }
     }

    public void moving() {
        dieLabel.setText(dieString + (game.getMoveCount()));
        controlPanel.revalidate();
        controlPanel.repaint();
    }

    public void moveDone() {
        game = controller.moveAfter();
        game = controller.endTurn();
        inputPanel.setVisible(false);

        if (game.isFinish()) {
            controller.gameFinish(game);
            this.onExit();
        }
        else nextTurn();
    }

    public void nextTurn() {
        setStatusTable(game.getStatus());

        game = controller.startTurn();

        turnLabel.setText(turnString + (game.getPlayerIndex() + 1));
        dieLabel.setText(dieString);
        controlPanel.revalidate();
        controlPanel.repaint();
    }

    private String[] get_status_contents(int idx) {
        String[] ret = new String[6];

        Arrays.fill(ret, "0");
        ret[0] = Integer.toString(idx);

        return ret;
    }

    public String getCell(Cell c) {
        String cellImage = "";

        if (c.isStart())
            cellImage = "boardGame/resources/image/start.png";
        else if (c.isEnd())
            cellImage = "boardGame/resources/image/end.png";
        else if (c.isEbridge() || c.isCell())
            cellImage = "boardGame/resources/image/cell.png";
        else if (c.isHammer())
            cellImage = "boardGame/resources/image/hammer.png";
        else if (c.isPdriver())
            cellImage = "boardGame/resources/image/pdriver.png";
        else if (c.isSaw())
            cellImage = "boardGame/resources/image/saw.png";
        else if (c.isSbridge())
            cellImage = "boardGame/resources/image/sbridge.png";
        else if (c.isBridge())
            cellImage = "boardGame/resources/image/bridge.png";

        return cellImage;
    }

    public void onExit() {
        this.dispose();
    }
}


