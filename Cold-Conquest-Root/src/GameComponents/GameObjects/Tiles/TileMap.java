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
    private final int pixelSize; //Gotten from sprite renderer
    private final static int DEFAULT_MAP_SIZE = 5;

    /*Tile finder*/
    private final HashMap<Integer, TiledObject> tiles;

    /*Sub-objects*/
    public final Cords cords;
    private final InputManager inputManager;
    private TileSelector tileSelector;

    /*Dynamic Variables*/
    private Point previousTileSelected = new Point(0, 0);
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

        //Set variables
        tiles = new HashMap<>();
        InputManager.current().addMouseMoveListener(this);
    }

    /*Tile manipulation*/
    public int getTileKey(Point pos){
        return getTileKey(pos.x, pos.y);
    }

    public int getTileKey(int x, int y){
        //Gets an interger to represent 2d tilemap
        //Used because using point or int array would compare objects not values if used as key
        return x + y * tilemapSize[0];
    }

    public TiledObject getTile(Point pos){
        return tiles.get(getTileKey(pos));
    }
    public TiledObject getTile(int x, int y){
        return tiles.get(getTileKey(x, y));
    }

    public void SetTile(TiledObject obj, int x, int y){
        SetTile(obj, new Point(x, y));
    }

    public void SetTile(TiledObject obj, Point tilePos){
        //Add the tile to tile hashmap
        tiles.put(getTileKey(tilePos.x, tilePos.y), obj);
        //Set tile position in world
        getTile(tilePos.x, tilePos.y).setPos(cords.mapToWorld(tilePos));
    }

    /*Render sprites*/
    public void DrawTiles(Graphics2D g){
        int rows = tilemapSize[1];
        int columns = tilemapSize[0];

        int maxSum = rows + columns - 2; // Maximum number of iterations needed

        if(tileSelector != null)
            tileSelector.drawSprite(g);

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
    }

    /*Searialize Tilemap*/
    @Override
    public String toString(){
        String sBuilder = "";
        for(TiledObject obj : tiles.values()){
            sBuilder += obj.toString() + ", ";
        }
        return sBuilder;
    }

    /*Getters and Setters*/

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
        //Update tile selection
        previousTileSelected = tileSelected;
        tileSelected = cords.worldToMapCenter(pos);

        //Hold mouse position
        mousePos = pos;

        //If tile selector has changed tiles
        if(tileSelector != null && !previousTileSelected.equals(tileSelected)) {
            //Update selected tile
            tileSelector.setSelectedTile(tileSelected);
            TiledObject selectedTile = tiles.get(getTileKey(tileSelected.x, tileSelected.y));

            //Update selector size
            if(selectedTile == null)
                tileSelector.setSelectionSize(1);
            else {
                int tileWidth = selectedTile.getWidth() / tileSize[0];
                tileSelector.setSelectionSize(tileWidth);
            }
        }
    }

    /*General Functions*/
    public void FillIceSheet(){
        for(int i = 0; i < tilemapSize[1]; i++){
            for(int j = 0; j < tilemapSize[0]; j++){
                SetTile(new IceTile(), i, j);
            }
        }
    }

    private Point calculateStartPoint(int mapSize){
        //Currently set for near middle of screen but will need modification with moving camera
        return new Point(gw.screenWidth / 2, (gw.screenHeight - tileSize[1] - tileBaseThinkness) / 2);
    }

    public void enableTileSelector(){
        tileSelector = new TileSelector(this);
    }
}
