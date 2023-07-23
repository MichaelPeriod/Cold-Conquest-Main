package GameComponents.GameObjects.Tiles.Infrastructure.Storage;

public class StorageContainerSolid extends StorageContainerBase {
    /*Sprite Info*/
    private static String fileLocation = "storage-container-solid.png";
    private static int[] spriteArray = null;
    final private static int[] spriteDim = {32, 32};

    /*Initialize and set sprite when new one added*/
    public StorageContainerSolid(){
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
}
