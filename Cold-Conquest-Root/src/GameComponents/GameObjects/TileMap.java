package GameComponents.GameObjects;

import GameComponents.GameObjects.Tiles.Landscape.IceTile;
import GameComponents.GameObjects.Tiles.TiledObject;
import GameComponents.SpriteRenderer;
import GameManagers.GameWindow;
import Utilities.Cords;

import java.awt.*;
import java.util.ArrayList;

public class TileMap {
    final int[] startPoint;
    final int[] tileBaseSize;
    final int tileBaseThinkness;
    final int[] tileSize;
    private GameWindow gw;
    final int pixelSize;
    final private static int DEFAULT_MAP_SIZE = 5;
    private ArrayList<ArrayList<TiledObject>> tiles; //Read from 0, 0 being top center tile

    public TileMap(GameWindow gw){
        this(DEFAULT_MAP_SIZE, gw);
    }

    public TileMap(int mapSize, GameWindow gw){
        this.pixelSize = SpriteRenderer.getPixelSize();
        this.tileBaseSize = new int[]{pixelSize * 32, pixelSize * 20};
        this.tileBaseThinkness = 5 * pixelSize;
        this.tileSize = new int[]{32, 32};
        this.gw = gw;

        tiles = new ArrayList<>(mapSize);
        for(int i = 0; i < mapSize; i++){
            tiles.add(new ArrayList<>(mapSize));
            for(int j = 0; j < mapSize; j++){
                tiles.get(i).add(null);
            }
        }

        startPoint = calculateStartPoint(mapSize);
    }

    public void FillIceSheet(){
        for(int i = 0; i < tiles.size(); i++){
            for(int j = 0; j < tiles.get(0).size(); j++){
                SetTile(new IceTile(), i, j);
            }
        }
    }

    public void SetTile(TiledObject obj, int x, int y){
        if(!isInBounds(tiles, x, y)){
            System.out.println("Tile is out of bounds...");
            return;
        }

        tiles.get(x).set(y, obj);
        getTile(x, y).setPos(Cords.mapToWorld(this, x, y));
        getTile(x, y).setDimensions(tileSize);
    }

    private static boolean isInBounds(ArrayList<ArrayList<TiledObject>> tileMap, int x, int y){
        if(tileMap.size() == 0 || tileMap.size() <= x)
            return false;

        return tileMap.get(0).size() != 0 || tileMap.get(0).size() > y;
    }

    public TiledObject getTile(int x, int y){
        if(!isInBounds(tiles, x, y)){
            System.out.println("Tile is out of bounds...");
            return null;
        }

        return tiles.get(x).get(y);
    }

    public void DrawTiles(Graphics2D g){
        for(ArrayList<TiledObject> row : tiles){
            for(TiledObject tile : row){
                if(tile != null)
                    tile.drawSprite(g);
            }
        }

        //int[] start = Cords.worldToMap(this, 0, 400);
        //int[] start = {400, 400};
        //g.setColor(Color.BLACK);
        //g.fillOval(start[0] - 10, start[1] - 10, 20, 20);
    }

    @Override
    public String toString(){
        String sBuilder = "";
        for(ArrayList<TiledObject> row : tiles){
            for(TiledObject tile : row){
                sBuilder += tile.toString() + " ";
            }
            sBuilder += "\n";
        }
        return sBuilder;
    }

    private int[] calculateStartPoint(int mapSize){
        final int[] windowCenter = {gw.screenWidth / 2, gw.screenHeight / 2};
        final int tileHeight = tileBaseSize[1] - tileBaseThinkness;
        final int tileWidth = tileBaseSize[0];
        int[] toReturn = {windowCenter[0] - tileWidth / 2, -(mapSize * tileHeight / 2) + windowCenter[1]};
        return toReturn;
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
}
