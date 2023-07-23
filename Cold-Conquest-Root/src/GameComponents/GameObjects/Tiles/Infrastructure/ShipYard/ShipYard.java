package GameComponents.GameObjects.Tiles.Infrastructure.ShipYard;

import GameComponents.GameObjects.Tiles.TiledObject;

public class ShipYard extends TiledObject {
    /*Sprite Info*/
    private static String fileLocation = "ship-yard.png";
    private static int[] spriteArray = null;
    final private static int[] spriteDim = {64, 64};


    /*Initialize and load sprite when new one added*/
    public ShipYard() {
        if (spriteArray == null)
            loadSprite(fileLocation);
        setDimensions(spriteDim);
    }

    @Override
    public int[] getSprite() {
        return spriteArray;
    }

    @Override
    public void setSprite(int[] sprite) {
        spriteArray = sprite;
    }

    /*Run game loop*/
    @Override
    public void OnStart() {

    }

    @Override
    public void OnUpdate() {

    }

    @Override
    public void LateUpdate() {

    }
}