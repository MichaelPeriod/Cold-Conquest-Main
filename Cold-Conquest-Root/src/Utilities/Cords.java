package Utilities;

import GameComponents.GameObjects.Tiles.TileMap;

import java.awt.*;

public class Cords {
    private final TileMap tm; //The object cords belongs to

    //Initialized the cords object
    public Cords(TileMap tm){
        this.tm = tm;

        /*
        for the xOut cord:
        xIn = 1 means xOut + 64
        xIn = -1 means xOut - 64
        yIn = 1 means xOut -64
        yIn = -1 means xOut + 64

        for the yOut cord:
        xIn = 1 means xOut + 30
        xIn = -1 means xOut -30
        yIn = 1 means xOut + 30
        yIn = -1 means xOut -30
        */
    }

    /*Map to world*/
    //Converts from map coordinates to world coordinates
    public Point mapToWorld(Point pos){
        final Point sp = tm.getStartPoint();
        final int[] size = tm.getTileBaseSize().clone();
        size[1] -= tm.getTileBaseThinkness();
        return new Point(
                sp.x + (pos.x * size[0] / 2) - (pos.y * size[0] / 2),
                sp.y + ((pos.x + pos.y) * size[1] / 2)
        );
    }

    //Takes the world cords and offsets it to the top left corner
    public Point mapToWorldCorner(Point pos){
        Point toReturn = mapToWorld(pos);
        return worldCornerOffset(toReturn);
    }

    //Moves the tile selection to the center of the tile
    public Point mapToWorldCenter(Point pos){
        Point toReturn = mapToWorld(pos);
        toReturn.y -= (tm.getTileBaseSize()[1] + tm.getTileBaseThinkness()) / 2;

        return toReturn;
    }

    /*World to map*/
    //Takes physical cords and finds the map point closest to it
    public Point worldToMap(Point pos) {
        //Save point as clone
        Point worldPos = new Point(pos.x, pos.y);

        //Offset by world start draw point
        Point startPos = tm.getStartPoint();
        worldPos.translate(-startPos.x, -startPos.y);

        //Offset by tile base
        int[] tileSize = tm.getTileBaseSize().clone();
        tileSize[1] -= tm.getTileBaseThinkness();
        tileSize[0] /= 2;
        tileSize[1] /= 2;

        //CALCULATIONS
        //worldX / tileSize[0] = isoX - isoY
        //worldY / tileSize[1] = isoX + isoY
        //given this
        //isoX = isoY + (worldX / tileSize[0])
        //(worldY / tileSize[1] - worldX / tileSize[0]) / 2 = isoY
        //worldY / tileSize[1] - ((worldY / tileSize[1] - worldX / tileSize[0]) / 2) = isoX

        //Get position
        float isoY = (worldPos.y / (float)tileSize[1] - worldPos.x / (float)tileSize[0]) / 2f;
        float isoX = worldPos.y / (float)tileSize[1] - isoY;
        return new Point(
                Math.round(isoX),
                Math.round(isoY)
        );
    }

    //Gets the nearest tile corner
    public Point worldToMapCorner(Point pos){
        return worldToMap(worldCornerOffset(pos));
    }

    //Gets the nearest tile center
    public Point worldToMapCenter(Point pos){
        return worldToMap(worldToCenterOffset(pos));
    }

    /*Util*/
    //Offsets cords to corner
    private Point worldCornerOffset(Point pos){
        Point toReturn = pos;
        toReturn.x -= tm.getTileBaseSize()[0] / 2;
        toReturn.y -= tm.getTileBaseSize()[1];

        return toReturn;
    }

    //Offsets cords to center
    private Point worldToCenterOffset(Point pos){
        return new Point(
                pos.x,
                pos.y + ((tm.getTileBaseThinkness() + tm.getTileBaseSize()[1]) / 2)
        );
    }
}