package GameManagers;

import GameComponents.GameObjects.PixelObject;
import GameComponents.GameObjects.Tiles.Indicators.ConcreteIndicator;
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
import GameComponents.GameObjects.Tiles.Indicators.Pole;
import GameComponents.GameObjects.Tiles.TileMap;
import GameComponents.InputHandler.InputManager;
import GameComponents.SpriteRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameWindow extends JPanel implements Runnable {
    //Declare window constants
    public final int screenWidth = 854;
    public final int screenHeight = 480;
    public final int FPS = 60;

    //Store object collections
    ArrayList<TileMap> tileMaps = new ArrayList<>();
    ArrayList<PixelObject> unorganizedObjects = new ArrayList<>();

    public GameWindow(){
        //Set up window on initialization
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(54, 197, 244));
        this.setDoubleBuffered(true);
        this.addKeyListener(InputManager.current());
        this.addMouseListener(InputManager.current());
        this.addMouseMotionListener(InputManager.current());
        this.setFocusable(true);
    }

    public void setup(){
        //Setup the game

        //Declare constants
        final int tileMapSize = 6;

        //Initialize the sprite renderer
        SpriteRenderer.renderer();

        //Create tilemap of ice sheet
        tileMaps.add(new TileMap(tileMapSize,this)); // Ice sheet
        tileMaps.get(0).FillIceSheet();

        //Create objects tilemap
        tileMaps.add(new TileMap(tileMapSize,this)); // Main objects layer
        tileMaps.get(1).enableTileSelector();

        //Temp: Add sample tiles to screen
        addTiles();

        //Start the game on asynchronous thread
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    private void addTiles(){
        //Set all tiles
        //Once out of testing, need to delete all of these objects and references
        tileMaps.get(1).SetTile(new BaseMiner(), 0, 0);
        tileMaps.get(1).SetTile(new SolarPanel(), 1, 0);
        tileMaps.get(1).SetTile(new StorageContainerLiquid(), 2, 0);
        tileMaps.get(1).SetTile(new StorageContainerSolid(), 3, 0);
        tileMaps.get(1).SetTile(new Pole(), 5, 1);

        tileMaps.get(1).SetTile(new Lab(), 1, 3);
        tileMaps.get(1).SetTile(new Base(), 3, 3);
        tileMaps.get(1).SetTile(new ShipYard(), 5, 3);

        tileMaps.get(1).SetTile(new ConcreteIndicator(), 0, 5);
        tileMaps.get(1).SetTile(new WaterIndicator(), 1, 5);
        tileMaps.get(1).SetTile(new OilIndicator(), 2, 5);
        tileMaps.get(1).SetTile(new MetalIndicator(), 3, 5);
    }

    public void paint(Graphics g) {
        //Draw window and create 2d drawing plane
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        //Draw all tiles
        for(TileMap m : tileMaps){
            m.DrawTiles(g2d);
        }

        //Draw any unorganized object
        for(PixelObject o : unorganizedObjects){
            o.drawSprite(g2d);
        }
    }

    @Override
    public void run(){
        //Declare variables
        final double drawInterval = 1000000000/(float)FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer;

        while(true){
            //Get time
            currentTime = System.nanoTime();

            //Figure out if time is past new frame time
            timer = currentTime - lastTime;
            delta += timer / drawInterval;
            lastTime = currentTime;
            if(delta <= 1) continue;

            //Draw frame and restart time till next frame
            repaint();
            delta--;
        }
    }
}
