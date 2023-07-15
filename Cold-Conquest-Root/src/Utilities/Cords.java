package Utilities;

import GameComponents.GameObjects.Tiles.TileMap;

import java.awt.*;

public class Cords {
    private final TileMap tm;

    public Cords(TileMap tm){
        this.tm = tm;

        for(int i = 0; i < 16; i++){
            System.out.println(worldToMap(mapToWorld(new Point(Math.floorDiv(i, 4), i % 4))).toString());
        }
        System.out.println();
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

    public Point mapToWorld(Point pos){
        //When placing tiles the top seems to be sub-pixel off so leaving for now but to keep an eye on
        final Point sp = tm.getStartPoint();
        final int[] size = tm.getTileBaseSize().clone();
        size[1] -= tm.getTileBaseThinkness();
        return new Point(
                sp.x + (pos.x * size[0] / 2) - (pos.y * size[0] / 2),
                sp.y + ((pos.x + pos.y) * size[1] / 2)
        );
    }

    public Point mapToWorldCorner(Point pos){
        Point toReturn = mapToWorld(pos);
        return worldCornerOffset(toReturn);
    }

    public Point mapToWorldCenter(Point pos){
        Point toReturn = mapToWorld(pos);
        toReturn.y -= (tm.getTileBaseSize()[1] + tm.getTileBaseThinkness()) / 2;

        return toReturn;
    }

    public Point worldToMap(Point pos) {
        Point worldPos = new Point(pos.x, pos.y);

        Point startPos = tm.getStartPoint();
        worldPos.translate(-startPos.x, -startPos.y);

        int[] tileSize = tm.getTileBaseSize().clone();
        tileSize[1] -= tm.getTileBaseThinkness();
        tileSize[0] /= 2;
        tileSize[1] /= 2;

        //worldX / tileSize[0] = isoX - isoY
        //worldY / tileSize[1] = isoX + isoY

        //given this
        //isoX = isoY + (worldX / tileSize[0])
        //(worldY / tileSize[1] - worldX / tileSize[0]) / 2 = isoY
        //worldY / tileSize[1] - ((worldY / tileSize[1] - worldX / tileSize[0]) / 2) = isoX

//        worldPos = new Point(0, 59);
        float isoY = (worldPos.y / (float)tileSize[1] - worldPos.x / (float)tileSize[0]) / 2f;
        float isoX = worldPos.y / (float)tileSize[1] - isoY;
        return new Point(
                Math.round(isoX),
                Math.round(isoY)
        );
    }

    public Point worldToMapCorner(Point pos){
        return worldToMap(worldCornerOffset(pos));
    }

    public Point worldToMapCenter(Point pos){
        return worldToMap(worldToCenterOffset(pos));
    }

    private Point worldCornerOffset(Point pos){
        Point toReturn = pos;
        toReturn.x -= tm.getTileBaseSize()[0] / 2;
        toReturn.y -= tm.getTileBaseSize()[1];

        return toReturn;
    }

    private Point worldToCenterOffset(Point pos){
        return new Point(
                pos.x,
                pos.y + ((tm.getTileBaseThinkness() + tm.getTileBaseSize()[1]) / 2)
        );
    }
}