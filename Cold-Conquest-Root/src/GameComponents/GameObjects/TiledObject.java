package GameComponents.GameObjects;

public abstract class TiledObject extends PixelObject {
    //Utilizes positioning to draw pixel object in place

    protected int tileX, tileY;

    public TiledObject(String spriteLocation){
        super(spriteLocation);
    }

    //Should make a tilemap object to calculate
}
