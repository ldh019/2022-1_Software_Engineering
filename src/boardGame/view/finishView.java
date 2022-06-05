package boardGame.view;

import boardGame.model.BoardGame;
import boardGame.model.Status;

import javax.swing.*;
import java.util.ArrayList;

public class finishView {
    private JPanel panel;

    public finishView(BoardGame game) {
        panel = new JPanel();

        ArrayList<Status> result = game.getResult();
    }

    public JPanel getPanel() {
        return panel;
    }
}
