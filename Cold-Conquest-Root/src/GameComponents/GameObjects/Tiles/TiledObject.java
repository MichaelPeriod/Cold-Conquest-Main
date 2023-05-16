package GameComponents.GameObjects.Tiles;

import GameComponents.GameObjects.PixelObject;

public abstract class TiledObject extends PixelObject {
    //Utilizes positioning to draw pixel object in place

    private int tileX, tileY;

    public TiledObject(String spriteLocation, int pixelSize){
        super(spriteLocation,pixelSize);
    }

    //Should make a tilemap object to calculate
}
