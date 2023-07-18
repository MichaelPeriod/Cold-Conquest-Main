package GameComponents.GameObjects.Tiles;

import GameComponents.GameObjects.Tiles.Landscape.IceTile;
import GameComponents.GameObjects.Tiles.TiledObject;
import GameComponents.InputHandler.InputManager;
import GameComponents.InputHandler.MouseMovementObserver;
import GameComponents.SpriteRenderer;
import GameComponents.UI.TileSelector.TileSelector;
import GameManagers.GameWindow;
import Utilities.Cords;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TileMap implements MouseMovementObserver {
    /*Variables*/
    private final Point startPoint;
    private final int[] tileBaseSize;
    private final int tileBaseThinkness;
    private final int[] tileSize;
    private final int[] tilemapSize;
    private final GameWindow gw;
    private final int pixelSize;
    private final static int DEFAULT_MAP_SIZE = 5;

    /*Tile finder*/
    private final HashMap<Integer, TiledObject> tiles;

    /*Sub-objects*/
    public final Cords cords;
    private final InputManager inputManager;
    private final TileSelector tileSelector;

    /*Dynamic Variables*/
    private Point tileSelected = new Point(0, 0);
    private Point mousePos = new Point(0, 0);

    /*Constructors*/
    public TileMap(GameWindow gw){
        this(DEFAULT_MAP_SIZE, gw);
    }

    public TileMap(int mapSize, GameWindow _gw){
        //Variable setter
        this.gw = _gw;
        this.pixelSize = SpriteRenderer.getPixelSize();
        this.tileBaseSize = new int[]{pixelSize * 32, pixelSize * 20};
        this.tileBaseThinkness = 4 * pixelSize;
        this.tileSize = new int[]{32, 32};
        tilemapSize = new int[] {mapSize, mapSize};
        startPoint = calculateStartPoint(mapSize);

        //Declare subclasses
        cords = new Cords(this);
        inputManager = InputManager.current();
        tileSelector = new TileSelector(this);

        //Set variables
        tiles = new HashMap<>();
        InputManager.current().addMouseMoveListener(this);
    }

    /*Tile manipulation*/
    public void SetTile(TiledObject obj, int x, int y){
        SetTile(obj, new Point(x, y));
    }

    public void SetTile(TiledObject obj, Point tilePos){
        tiles.put(getTileKey(tilePos.x, tilePos.y), obj);
        getTile(tilePos.x, tilePos.y).setPos(cords.mapToWorld(tilePos));
    }

    public TiledObject getTile(int x, int y){
        return tiles.get(getTileKey(x, y));
    }

    public int getTileKey(int x, int y){
        return x + y * tilemapSize[0];
    }

    public void FillIceSheet(){
        for(int i = 0; i < tilemapSize[1]; i++){
            for(int j = 0; j < tilemapSize[0]; j++){
                SetTile(new IceTile(), i, j);
            }
        }
    }

    /*Render sprites*/
    public void DrawTiles(Graphics2D g){
        int rows = tilemapSize[1];
        int columns = tilemapSize[0];

        int maxSum = rows + columns - 2; // Maximum number of iterations needed

        for (int sum = 0; sum <= maxSum; sum++) {
            for (int i = 0; i <= sum; i++) {
                int j = sum - i;
                if (i < rows && j < columns) {
                    if(!tiles.containsKey(getTileKey(j, i))) continue;
                    TiledObject obj = getTile(j, i);
                    obj.drawSprite(g);
                }
            }
        }

        tileSelector.drawSprite(g);
    }

    /*Searialize Tiles*/
    @Override
    public String toString(){
        String sBuilder = "";
        for(TiledObject obj : tiles.values()){
            sBuilder += obj.toString() + ", ";
        }
        return sBuilder;
    }

    /*Getters and Setters*/
    private Point calculateStartPoint(int mapSize){
        return new Point(gw.screenWidth / 2, (gw.screenHeight - tileSize[1] - tileBaseThinkness) / 2);
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public int[] getTileBaseSize() {
        return tileBaseSize;
    }

    public int getTileBaseThinkness() {
        return tileBaseThinkness;
    }

    /*Input Management*/
    @Override
    public void onMouseMove(Point pos) {
        selectTile(pos);
    }

    @Override
    public void onMouseDelta(Point lastPos, Point currPos) {

    }

    private void selectTile(Point pos){
        tileSelected = cords.worldToMapCenter(pos);
        mousePos = pos;
    }
}
