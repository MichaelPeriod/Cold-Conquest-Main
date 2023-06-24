package GameComponents.GameObjects.Tiles.Indicators;

import GameComponents.GameObjects.Tiles.TiledObject;

public class OilIndicator extends TiledObject {
    private static String fileLocation = "indicator-oil.png";
    private static int[] spriteArray = null;
    final private static int[] spriteDim = {32, 32};

    public OilIndicator(){
        if(spriteArray == null)
            loadSprite(fileLocation);
        setDimensions(spriteDim);
    }

    @Override
    public void OnStart() {

    }

    @Override
    public void OnUpdate() {

    }

    @Override
    public void LateUpdate() {

    }

    @Override
    public int[] getSprite() {
        return spriteArray;
    }

    @Override
    public void setSprite(int[] sprite) {
        spriteArray = sprite;
    }
}
