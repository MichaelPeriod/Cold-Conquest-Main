package GameComponents.GameObjects.Tiles;

import GameComponents.GameObjects.PixelObject;

public abstract class TiledObject extends PixelObject {
    //Currently doesn't do anything, might refactor later when implementing UI or non-tiled objects that use the tilemap
    private int tileX, tileY;
    private int tileW, tileH;

    protected int[] calculateDims(){
        return new int[]{
                tileW * 16 + tileH * 16,
                tileW * 16 + tileH * 16,
        }; //Doesn't calculate for non-square tiles
    }
}
