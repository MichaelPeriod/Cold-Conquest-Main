package GameComponents;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SpriteRenderer {
    private static SpriteRenderer renderer = null;
    private static final String spriteRoot = "res/";
    private static int pixelSize = 4;
    private static final int DATA_PER_PIXEL = 4;


    public static SpriteRenderer renderer(){
        if(renderer == null){
            renderer = new SpriteRenderer();
        }
        return renderer;
    }

    /*Image Loading*/
    //Returns to original object which will pass in the static buffered image
    public static BufferedImage loadSprite(@NotNull String spriteLocation){
        try {
            return ImageIO.read(new File(spriteRoot + spriteLocation));
        } catch(IOException e){
            System.out.println("File location is invalid for " + spriteLocation);
            return null;
        }
    }

    public static int[] loadSpriteToArray(@NotNull String spriteLocation){
        return spriteToIntArray(loadSprite(spriteLocation));
    }

    //Untested
    public static ArrayList<BufferedImage> loadSpriteSheet(@NotNull String spriteSheetLocation, int[] spriteSize){
        try {
            BufferedImage unsplicedSheet = ImageIO.read(new File(spriteRoot + spriteSheetLocation));

            final int spritesWidth = unsplicedSheet.getWidth() / spriteSize[0];
            final int spritesHeight = unsplicedSheet.getHeight() / spriteSize[1];
            ArrayList<BufferedImage> splicedSheet = new ArrayList<>();

            for(int i = 0; i < spritesHeight; i++){
                for(int j = 0; j < spritesWidth; j++){
                    BufferedImage testImage = unsplicedSheet.getSubimage(j * spriteSize[0], i * spriteSize[1], spriteSize[0], spriteSize[1]);
                    if(spriteHasPixels(testImage))
                        splicedSheet.add(testImage);
                }
            }

            return splicedSheet;
        } catch (IOException e){
            System.out.println("File location is invalid for " + spriteSheetLocation);
            return null;
        }
    }

    private static boolean spriteHasPixels(BufferedImage sprite){
        int[] pngData = spriteToIntArray(sprite);

        for(int data : pngData){
            if(data != 0)
                return true;
        }
        return false;
    }

    public static int[] spriteToIntArray(BufferedImage sprite){
        if(sprite == null) return null;

        final int TOTAL_PIXELS = sprite.getHeight() * sprite.getWidth();
        int[] pixels = new int[TOTAL_PIXELS * DATA_PER_PIXEL];
        sprite.getData().getPixels(0, 0, sprite.getWidth(), sprite.getHeight(), pixels);

        return pixels;
    }

    public void drawSprite(Graphics2D g2d, BufferedImage sprite, int[] spriteBox){
        drawSprite(g2d, spriteToIntArray(sprite), spriteBox);
    }

    public void drawSprite(Graphics2D g2d, int[] pixels, int[] spriteBox){
        //Calculate x and y pos
        final int PIXEL_SIZE = getPixelSize();
        final int initX = spriteBox[0] - spriteBox[2] / 2 * PIXEL_SIZE;
        final int initY = spriteBox[1] - spriteBox[3] * PIXEL_SIZE;

        for(int i = 0; i < spriteBox[3]; i++) { //height
            for(int j = 0; j < spriteBox[2]; j++) { //width
                int pixelIndex = (j + i * spriteBox[2]) * DATA_PER_PIXEL;
                g2d.setColor(new Color(pixels[pixelIndex], pixels[pixelIndex + 1], pixels[pixelIndex + 2], pixels[pixelIndex + 3]));
                g2d.fillRect(initX + j * PIXEL_SIZE, initY + i * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }

    /*Sprite Shapes*/
    private void rendererRhombusOutlne(Graphics2D g2d, int[] outline, int[] color){
        g2d.setColor(Color.BLUE);
        g2d.fillRect(outline[0], outline[1] + outline[3] / 2 - getPixelSize() / 2, getPixelSize(), getPixelSize());
        g2d.fillRect(outline[0] + outline[2] / 2 - getPixelSize(), outline[1], getPixelSize(), getPixelSize());
        g2d.fillRect(outline[0] + outline[2] / 2 - getPixelSize(),
                outline[1] - outline[3] - getPixelSize(), getPixelSize(), getPixelSize());
        g2d.fillRect(outline[0] + outline[2] - getPixelSize(),
                outline[1] - outline[3] / 2 - getPixelSize(), getPixelSize(), getPixelSize());
    }

    /*Getters and Setters*/
    public static int getPixelSize() {
        return pixelSize;
    }

    public static void setPixelSize(int pixelSize) {
        SpriteRenderer.pixelSize = pixelSize;
    }
}
