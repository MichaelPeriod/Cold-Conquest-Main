package GameComponents.GameObjects;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TileMap {
    final private static int DEFAULT_MAP_SIZE = 10;
    private ArrayList<ArrayList<TiledObject>> tiles; //Read from 0, 0 being top center tile


    public TileMap(){
        this(50);
    }

    public TileMap(int mapSize){
        tiles = new ArrayList<>(mapSize);
        for(int i = 0; i < mapSize; i++){
            tiles.add(new ArrayList<>(mapSize));
            for(int j = 0; j < mapSize; j++){
                tiles.get(i).add(null);
            }
        }
    }

    public void FillMap(TiledObject obj){
        for(int i = 0; i < tiles.size(); i++){
            for(int j = 0; j < tiles.get(0).size(); j++){
                SetTile(obj, i, j);
            }
        }
    }

    public void SetTile(TiledObject obj, int x, int y){
        if(!isInBounds(tiles, x, y)){
            System.out.println("Tile is out of bounds...");
            return;
        }

        tiles.get(x).set(y, obj);
        getTile(x, y).setPos(100, 100);
    }

    private static boolean isInBounds(ArrayList<ArrayList<TiledObject>> tileMap, int x, int y){
        if(tileMap.size() == 0 || tileMap.size() <= x)
            return false;

        if (tileMap.get(0).size() == 0 && tileMap.get(0).size() <= y)
            return false;

        return true;
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
                tile.drawSprite(g);
            }
        }
    }
}
