import GameComponents.GameObjects.Landscape.IceTile;
import GameComponents.GameObjects.PixelObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameWindow extends JFrame {
    ArrayList<PixelObject> objects = new ArrayList<>();

    public void setup(){
        objects.add(new IceTile());

        setTitle("Cold Conquest");
        getContentPane().setBackground(Color.BLUE);
        setSize(848, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(20, 100, 100, 100);

        for(PixelObject o : objects){
            o.drawSprite(g2d);
        }
    }

    public void run(){

    }
}
