package GameComponents.GameObjects.Tiles.Infrastructure.SolarPanel;

import GameComponents.GameObjects.Tiles.TiledObject;

public class SolarPanel extends TiledObject {
    private static String fileLocation = "solar-panels.png";
    private static int[] spriteArray = null;
    final private static int[] spriteDim = {32, 32};

    public SolarPanel(){
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
