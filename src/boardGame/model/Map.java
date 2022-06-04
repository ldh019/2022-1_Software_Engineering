package boardGame.model;

import java.util.List;

public class Map {
    List<String> data;

    public void initialize(List<String> input) {
        data = input;
    }

    public List<String> sendData() {
        return data;
    }
}
