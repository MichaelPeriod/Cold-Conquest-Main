package GameManagers;

import GameComponents.GameObjects.PixelObject;
import GameComponents.GameObjects.TileMap;
import GameComponents.GameObjects.Tiles.TestTile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameWindow extends JPanel implements Runnable {
    public final int screenWidth = 854;
    public final int screenHeight = 480;
    public final int FPS = 60;
    public final int PIXEL_SIZE = 4;

    ArrayList<TileMap> tileMaps = new ArrayList<>();
    ArrayList<PixelObject> unorganizedObjects = new ArrayList<>();

    public GameWindow(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(54, 197, 244));
        this.setDoubleBuffered(true);
        //this.addKeyListener(keyH);
        //this.addMouseListener(keyH);
        //this.addMouseMotionListener(keyH);
        this.setFocusable(true);
    }

    public void setup(){
        tileMaps.add(new TileMap(this));
        tileMaps.get(0).FillIceSheet();

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        for(TileMap m : tileMaps){
            m.DrawTiles(g2d);
        }

        for(PixelObject o : unorganizedObjects){
            o.drawSprite(g2d);
        }
    }

    @Override
    public void run(){
        double drawInterval = 1000000000/(float)FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer;

        while(true){
            currentTime = System.nanoTime();

            timer = currentTime - lastTime;
            delta += timer / drawInterval;
            lastTime = currentTime;

            if(delta <= 1) continue;

            repaint();
            delta--;
        }
    }
}
