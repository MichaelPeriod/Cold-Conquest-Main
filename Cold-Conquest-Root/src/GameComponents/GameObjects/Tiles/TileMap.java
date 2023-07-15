package GameComponents.GameObjects.Tiles;

import GameComponents.GameObjects.Tiles.Landscape.IceTile;
import GameComponents.GameObjects.Tiles.TiledObject;
import GameComponents.InputHandler.InputManager;
import GameComponents.InputHandler.MouseMovementObserver;
import GameComponents.SpriteRenderer;
import GameManagers.GameWindow;
import Utilities.Cords;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TileMap implements MouseMovementObserver {
    final Point startPoint;
    final int[] tileBaseSize;
    final int tileBaseThinkness;
    final int[] tileSize;
    final int[] tilemapSize;
    private GameWindow gw;
    final int pixelSize;
    final private static int DEFAULT_MAP_SIZE = 5;
    private HashMap<Integer, TiledObject> tiles;

    private Point tileSelected = new Point(0, 0);
    private Point mousePos = new Point(0, 0);

    private final Cords cords;

    public TileMap(GameWindow gw){
        this(DEFAULT_MAP_SIZE, gw);
    }

    public TileMap(int mapSize, GameWindow gw){
        this.pixelSize = SpriteRenderer.getPixelSize();
        this.tileBaseSize = new int[]{pixelSize * 32, pixelSize * 20};
        this.tileBaseThinkness = 5 * pixelSize;
        this.tileSize = new int[]{32, 32};
        this.gw = gw;

        tiles = new HashMap<>();
        startPoint = calculateStartPoint(mapSize);
        tilemapSize = new int[] {mapSize, mapSize};

        cords = new Cords(this);

        InputManager.current().addMouseMoveListener(this);
    }

    public void FillIceSheet(){
        for(int i = 0; i < tilemapSize[1]; i++){
            for(int j = 0; j < tilemapSize[0]; j++){
                SetTile(new IceTile(), i, j);
            }
        }
    }

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

        g.setColor(Color.BLACK);
        Point nearbyTile = cords.mapToWorldCenter(tileSelected);
        g.fillOval(nearbyTile.x - 10, nearbyTile.y - 10, 20, 20);
    }

    @Override
    public String toString(){
        String sBuilder = "";
        for(TiledObject obj : tiles.values()){
            sBuilder += obj.toString() + ", ";
        }
        return sBuilder;
    }

    private Point calculateStartPoint(int mapSize){
        return new Point(gw.screenWidth / 2, gw.screenHeight / 2 - 100);
//        return new Point(0, 0);

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
