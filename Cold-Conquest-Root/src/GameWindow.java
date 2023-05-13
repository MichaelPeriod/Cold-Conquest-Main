import GameComponents.GameObjects.Landscape.IceTile;
import GameComponents.GameObjects.PixelObject;
import GameComponents.GameObjects.TileMap;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameWindow extends JFrame {
    ArrayList<TileMap> tileMaps = new ArrayList<>();

    public void setup(){
        tileMaps.add(new TileMap());
        tileMaps.get(0).FillMap(new IceTile());

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

        for(TileMap m : tileMaps){
            m.DrawTiles(g2d);
        }

        System.out.println("Frame drew");
        System.out.println(tileMaps.get(0).getTile(2, 5).getPos()[0]);
    }

    public void run(){

    }
}
