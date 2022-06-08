package boardGame.view;

import boardGame.controller.boardGameController;
import boardGame.model.BoardGame;
import boardGame.model.Cell;
import boardGame.model.Cells;
import boardGame.model.DirectionType;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

public class gameView extends JFrame{
    private JLayeredPane mainPanel;
    private JPanel controlPanel;
    private JPanel boardPanel;
    private JPanel[] tokenPanel;
    private JPanel joinPanel, statusPanel, buttonJoinPanel, buttonPlayPanel;
    private JPanel turnPanel, diePanel;
    private JLabel turnLabel, dieLabel;
    private String turnString, dieString;
    private JTable statusTable;
    private JScrollPane scrollPane;
    private DefaultTableModel dtm;
    public JButton startButton, exitButton, rollButton, restButton;
    public JButton plusButton, minusButton;
    private JLabel playerCount;

    private boolean startFlag;

    private boardGameController controller;
    private BoardGame game;

    private String[] header;

    public gameView() {
        this.setLayout(new BorderLayout());

        startFlag = false;

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
                    cellImage = "src/boardGame/resources/image/bridge.png";
                    icon = new ImageIcon(cellImage);

                    imgLabel.setIcon(icon);
                    boardPanel.add(imgLabel, gbc);
                    gbc.gridx--;
                }
            }
        }
        boardPanel.setBounds(10, 10, 800, 800);
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
            String cellImage = "src/boardGame/resources/image/token" + i + ".png";
            ImageIcon icon = new ImageIcon(cellImage);

            imgLabel.setIcon(icon);
            tokenPanel[i].add(imgLabel);
            tokenPanel[i].setBounds(14 + start[0] * 60, 10 + start[1] * 60, 60, 60);
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
        diePanel = new JPanel();
        dieLabel = new JLabel();
        dieString = "Left To Move : ";
        dieLabel.setFont(new Font(turnLabel.getFont().getFontName(), Font.PLAIN, 30));
        turnPanel.add(turnLabel);
        diePanel.add(dieLabel);

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
            game = controller.roll();
            moving();
            rollButton.setFocusable(false);
            this.setFocusable(true);
            requestFocusInWindow();
        });

        restButton.addActionListener(e -> {
            game = controller.rest();
            restButton.setFocusable(false);
            this.setFocusable(true);
            requestFocusInWindow();
        });

        this.addKeyListener(new movingListener());

        if (startFlag && game.getMoveCount() == 0) {
            moveDone();
        }

        buttonPlayPanel.add(rollButton);
        buttonPlayPanel.add(restButton);
        //Button Join Panel setting end

        controlPanel.add(joinPanel);
        controlPanel.add(buttonJoinPanel);
        //Control Panel setting done

        this.add(new JPanel(), BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.EAST);

        this.setSize(1200, 900); // 프레임 크기 설정
        this.setVisible(true);// 프레임이 보이도록 설정
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setFocusable(true);
        System.out.println(requestFocusInWindow());
    }

    class movingListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (startFlag) {
                int[] prev = game.getCurrentCell();
                game = controller.move(e);

                if (game.getCurrentCell() != prev) {
                    dieLabel.setText(dieString + (game.getMoveCount()));
                    tokenPanel[game.getPlayerIndex()].setBounds(14 + game.getCurrentCell()[0] * 60, 10 + game.getCurrentCell()[1] * 60, 60, 60);
                    controlPanel.revalidate();
                    controlPanel.repaint();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public void change() {
        controlPanel.removeAll();

        controlPanel.add(turnPanel);
        controlPanel.add(diePanel);
        controlPanel.add(statusPanel);
        controlPanel.add(buttonPlayPanel);

        scrollPane.setPreferredSize(new Dimension(350, 30));

        game = controller.start();

        turnLabel.setText(turnString + (game.getPlayerIndex() + 1));
        dieLabel.setText(dieString);

        for (int i = 0; i < game.getPlayerNum(); i++)
            tokenPanel[i].setVisible(true);

        controlPanel.revalidate();
        controlPanel.repaint();
    }

    public void moving() {
        startFlag = true;
        dieLabel.setText(dieString + (game.getMoveCount()));
        diePanel.revalidate();
        diePanel.repaint();
        controlPanel.revalidate();
        controlPanel.repaint();
    }

    public void moveDone() {
        if (game.getMoveCount() == 0) {
            game = controller.moveAfter();
            game = controller.endTurn();

            if (game.isFinish())
                ;
        }
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
            cellImage = "src/boardGame/resources/image/start.png";
        else if (c.isEnd())
            cellImage = "src/boardGame/resources/image/end.png";
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

        return cellImage;
    }

    public void onExit() {
        this.dispose();
    }
}


