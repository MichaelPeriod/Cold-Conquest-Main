package Utilities;

import GameComponents.GameObjects.Tiles.TileMap;

public class Cords {
    //Need to attach to the tilemap itself
    public static int[] mapToWorld(TileMap tm, int x, int y){
        //When placing tiles the top seems to be sub-pixel off so leaving for now but to keep an eye on
        final int[] sp = tm.getStartPoint();
        final int[] size = tm.getTileBaseSize().clone();
        size[1] -= tm.getTileBaseThinkness();
        int[] toReturn = {sp[0] + (x * size[0] / 2) - (y * size[0] / 2),
                          sp[1] + ((x + y) * size[1] / 2)};
        return toReturn;
    }

    public static int[] mapToWorldCorner(TileMap tm, int x, int y){
        int[] toReturn = mapToWorld(tm, x, y);
        return worldCornerOffset(tm, toReturn[0], toReturn[1]);
    }

    public static int[] mapToWorldCenter(TileMap tm, int x, int y){
        int[] toReturn = mapToWorldCorner(tm, x, y);
        return worldToCenterOffset(tm, toReturn[0], toReturn[1]);
    }

    public static int[] worldToMap(TileMap tm, int worldX, int worldY) {
        //TODO Need to fix, calculations don't work
        System.out.println("Maintenance needed on Cords.worldToMap()");
        final int[] sp = tm.getStartPoint();
        final int[] size = tm.getTileBaseSize().clone();
        size[1] -= tm.getTileBaseThinkness();

        int deltaX = worldX - sp[0];
        int deltaY = worldY - sp[1];

        int[] toReturn = new int[2];
        toReturn[0] = (2 * deltaX + deltaY * size[0]) / (2 * size[0]);
        toReturn[1] = (2 * deltaY - deltaX * size[1]) / (2 * size[1]);

        return toReturn;
    }

    public static int[] worldToMapCorner(TileMap tm, int x, int y){
        int[] toReturn = worldToMap(tm, x, y);
        return worldCornerOffset(tm, toReturn[0], toReturn[1]);
    }

    public static int[] worldToMapCenter(TileMap tm, int x, int y){
        int[] toReturn = worldToMapCorner(tm, x, y);
        return worldToCenterOffset(tm, toReturn[0], toReturn[1]);
    }

    private static int[] worldCornerOffset(TileMap tm, int x, int y){
        final int MAX_SIZE = 32;
        int[] toReturn = {x, y};
        int[] tilePixelSize = {tm.getTileBaseSize()[0] / tm.getPixelSize(),
                tm.getTileBaseSize()[1] / tm.getPixelSize()};
        toReturn[0] += (MAX_SIZE - tilePixelSize[0]) * tm.getPixelSize();
        toReturn[1] += (MAX_SIZE - tilePixelSize[1]) * tm.getPixelSize();
        return toReturn;
    }

    private static int[] worldToCenterOffset(TileMap tm, int x, int y){
        int[] corner = {x, y};
        int[] dimensions = tm.getTileBaseSize().clone();
        dimensions[1] -= tm.getTileBaseThinkness();

        int[] toReturn = new int[2];
        for(int i = 0; i < corner.length; i++)
            toReturn[i] = corner[i] + dimensions[i] / 2;
        return toReturn;
    }
}
