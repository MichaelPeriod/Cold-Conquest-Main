package Utilities;

import GameComponents.GameObjects.TileMap;

public class Cords {
    public static int[] mapToWorld(TileMap tm, int[] cords){
        if(cords.length != 2)
            System.out.println("Invalid mapping");
        else
            return mapToWorld(tm, cords[0], cords[1]);

        return null;
    }

    public static int[] mapToWorld(TileMap tm, int x, int y){
        final int[] sp = tm.getStartPoint();
        final int[] size = tm.getTileBaseSize();
        int[] toReturn = {sp[0] + (x * size[0] / 2) - (y * size[0] / 2),
                          sp[1] + ((x + y) * size[1] / 2)};
        return toReturn;
    }

    public static int[] worldToMap(TileMap tm, int x, int y) {
        final int[] sp = tm.getStartPoint();
        final int[] size = tm.getTileBaseSize();
        int[] toReturn = {sp[0] + (x / size[0]) - (y / size[1]),
                          sp[1] + (x / size[0]) + (y / size[1])};
        return toReturn;
    }

    public static int[] worldToMapCorner(TileMap tm, int x, int y){
        final int MAX_SIZE = 32;
        int[] toReturn = worldToMap(tm, x, y); // get top left of possible sprite
        int[] tilePixelSize = {tm.getTileBaseSize()[0] / tm.getPixelSize(),
                tm.getTileBaseSize()[1] / tm.getPixelSize()};
        toReturn[0] += (MAX_SIZE - tilePixelSize[0]) * tm.getPixelSize();
        toReturn[1] += (MAX_SIZE - tilePixelSize[1]) * tm.getPixelSize();
        return toReturn;
    }

    public static int[] worldToMapCenter(TileMap tm, int x, int y){
        final int MAX_SIZE = 32;
        int[] toReturn = worldToMap(tm, x, y); // get top left of possible sprite
        int[] tilePixelSize = {tm.getTileBaseSize()[0] / tm.getPixelSize(),
                               tm.getTileBaseSize()[1] / tm.getPixelSize()};
        toReturn[0] += (MAX_SIZE - tilePixelSize[0] / 2) * tm.getPixelSize();
        toReturn[1] += (MAX_SIZE - (tilePixelSize[1] + tm.getTileBaseThinkness()) / 2) * tm.getPixelSize();
        return toReturn;
    }
}
