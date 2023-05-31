package GameComponents.GameObjects.Tiles.Infrastructure.Miner;

import GameComponents.GameObjects.Tiles.TiledObject;

public class BaseMiner extends TiledObject {
    private static String fileLocation = "miner-base.png";
    private static int[] spriteArray = null;

    public BaseMiner(){
        if(spriteArray == null)
            loadSprite(fileLocation);
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
