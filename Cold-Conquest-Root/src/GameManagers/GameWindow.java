package GameManagers;

import GameComponents.GameObjects.PixelObject;
import GameComponents.GameObjects.Tiles.Indicators.ConcreateIndicator;
import GameComponents.GameObjects.Tiles.Indicators.MetalIndicator;
import GameComponents.GameObjects.Tiles.Indicators.OilIndicator;
import GameComponents.GameObjects.Tiles.Indicators.WaterIndicator;
import GameComponents.GameObjects.Tiles.Infrastructure.Base.Base;
import GameComponents.GameObjects.Tiles.Infrastructure.Lab.Lab;
import GameComponents.GameObjects.Tiles.Infrastructure.Miner.BaseMiner;
import GameComponents.GameObjects.Tiles.Infrastructure.ShipYard.ShipYard;
import GameComponents.GameObjects.Tiles.Infrastructure.SolarPanel.SolarPanel;
import GameComponents.GameObjects.Tiles.Infrastructure.Storage.StorageContainerLiquid;
import GameComponents.GameObjects.Tiles.Infrastructure.Storage.StorageContainerSolid;
import GameComponents.GameObjects.Tiles.Landscape.IceTile;
import GameComponents.GameObjects.Tiles.TileMap;
import GameComponents.SpriteRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameWindow extends JPanel implements Runnable {
    public final int screenWidth = 854;
    public final int screenHeight = 480;
    public final int FPS = 60;

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
        final int tileMapSize = 6;
        SpriteRenderer.renderer();

        tileMaps.add(new TileMap(tileMapSize,this)); // Ice sheet
        tileMaps.get(0).FillIceSheet();
        tileMaps.get(0).SetTile(new IceTile(), 0, 0);

        tileMaps.add(new TileMap(tileMapSize,this)); // Main objects layer

        tileMaps.get(1).SetTile(new BaseMiner(), 0, 0);
        tileMaps.get(1).SetTile(new SolarPanel(), 1, 0);
        tileMaps.get(1).SetTile(new StorageContainerLiquid(), 2, 0);
        tileMaps.get(1).SetTile(new StorageContainerSolid(), 3, 0);

        tileMaps.get(1).SetTile(new Lab(), 1, 3);
        tileMaps.get(1).SetTile(new Base(), 3, 3);
        tileMaps.get(1).SetTile(new ShipYard(), 5, 3);

        tileMaps.get(1).SetTile(new ConcreateIndicator(), 0, 5);
        tileMaps.get(1).SetTile(new WaterIndicator(), 1, 5);
        tileMaps.get(1).SetTile(new OilIndicator(), 2, 5);
        tileMaps.get(1).SetTile(new MetalIndicator(), 3, 5);

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
