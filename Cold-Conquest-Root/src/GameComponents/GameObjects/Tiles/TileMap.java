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
    final int[] startPoint;
    final int[] tileBaseSize;
    final int tileBaseThinkness;
    final int[] tileSize;
    final int[] tilemapSize;
    private GameWindow gw;
    final int pixelSize;
    final private static int DEFAULT_MAP_SIZE = 5;
    private HashMap<Integer, TiledObject> tiles;

    private int[] tileSelected = new int[2];

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
        tiles.put(getTileKey(x, y), obj);
        getTile(x, y).setPos(Cords.mapToWorld(this, x, y));
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
        int[] nearestTilePos = tileSelected;
        g.fillOval(nearestTilePos[0] - 10, nearestTilePos[1] - 10, 20, 20);
    }

    @Override
    public String toString(){
        String sBuilder = "";
        for(TiledObject obj : tiles.values()){
            sBuilder += obj.toString() + ", ";
        }
        return sBuilder;
    }

    private int[] calculateStartPoint(int mapSize){
        return new int[] {gw.screenWidth / 2, gw.screenHeight / 2};
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public int[] getStartPoint() {
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
        tileSelected = new int[]{pos.x, pos.y};
    }
}
