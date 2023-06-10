package GameComponents.GameObjects.Tiles.Infrastructure.Storage;

public class StorageContainerLiquid extends StorageContainerBase {
    private static String fileLocation = "storage-container-liquid.png";
    private static int[] spriteArray = null;
    final private static int[] spriteDim = {32, 32};


    public StorageContainerLiquid(){
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
        setDimensions(spriteDim);
    }
}
