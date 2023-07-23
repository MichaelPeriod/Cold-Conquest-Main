package GameComponents.GameObjects.Tiles.Indicators;

import GameComponents.GameObjects.Tiles.TiledObject;

public class MetalIndicator extends TiledObject {
    /*Sprite Info*/
    private static String fileLocation = "indicator-metal.png";
    private static int[] spriteArray = null;
    final private static int[] spriteDim = {32, 32};

    /*Initialize and load sprite when new one added*/
    public MetalIndicator(){
        if(spriteArray == null)
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
