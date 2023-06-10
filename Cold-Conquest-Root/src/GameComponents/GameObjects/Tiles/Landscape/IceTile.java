package GameComponents.GameObjects.Tiles.Landscape;

import GameComponents.GameObjects.Tiles.TiledObject;
import GameComponents.SpriteRenderer;

public class IceTile extends TiledObject {
    private static String fileLocation = "ice-sheet.png";
    private static int[] spriteArray = null;
    final private static int[] spriteDim = {32, 32};

    public IceTile(){
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
