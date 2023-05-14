package GameComponents.GameObjects;

import GameComponents.GameObjects.Landscape.IceTile;

import java.awt.*;
import java.util.ArrayList;

public class TileMap {
    final private static int DEFAULT_MAP_SIZE = 2;
    private ArrayList<ArrayList<TiledObject>> tiles; //Read from 0, 0 being top center tile


    public TileMap(){
        this(DEFAULT_MAP_SIZE);
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
        getTile(x, y).setPos(100 + 50 * x, 100 + 50 * y);
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
                if(tile != null)
                    tile.drawSprite(g);
            }
        }
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
        sBuilder += "End";
        return sBuilder;
    }
}
