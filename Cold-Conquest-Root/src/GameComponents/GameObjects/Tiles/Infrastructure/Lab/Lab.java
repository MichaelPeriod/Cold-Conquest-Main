package GameComponents.GameObjects.Tiles.Infrastructure.Lab;

import GameComponents.GameObjects.Tiles.TiledObject;

public class Lab extends TiledObject {
    private static String fileLocation = "lab.png";
    private static int[] spriteArray = null;
    final private static int[] spriteDim = {64, 64};


    public Lab(){
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
