package boardGame.view;

import boardGame.model.BoardGame;
import boardGame.model.Status;

import javax.swing.*;
import java.util.ArrayList;

public class finishView {
    private JPanel panel;
    private ArrayList<Status> result;

    public finishView() {
        panel = new JPanel();
    }

    public void setResult(BoardGame game) {
        ArrayList<Status> result = game.getResult();
    }

    public JPanel getPanel() {
        return panel;
    }
}
