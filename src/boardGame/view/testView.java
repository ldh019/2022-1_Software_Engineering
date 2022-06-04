package boardGame.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class testView extends JFrame {
    BufferedImage img = null;

    public testView(){
        setTitle("Load Image test");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        try {
            Path cur = Paths.get("");
            String path = cur.toAbsolutePath().toString() + "\\src\\boardGame\\resources\\image\\start.png";
            System.out.println(path);
            img = ImageIO.read(new File(path));
        } catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }

        add(new MyPanel());
        //add(new MyPanel());
        setVisible(true);
    }

    class MyPanel extends JPanel{
        public void paint(Graphics g){
            g.drawImage(img, 0, 0, null);
        }
    }
}
