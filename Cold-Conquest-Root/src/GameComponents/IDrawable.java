package GameComponents;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface IDrawable {
    String spriteRoot = "res/";

    //Used for animation ect.
    //UNTESTED
    static ArrayList<BufferedImage> loadSpriteSheet(@NotNull String spriteSheetLocation, int[] spriteSize){
        try {
            BufferedImage unsplicedSheet = ImageIO.read(new File(spriteRoot + spriteSheetLocation));

            final int spritesWidth = unsplicedSheet.getWidth() / spriteSize[0];
            final int spritesHeight = unsplicedSheet.getHeight() / spriteSize[1];
            ArrayList<BufferedImage> splicedSheet = new ArrayList<>();

            for(int i = 0; i < spritesHeight; i++){
                for(int j = 0; j < spritesWidth; j++){
                    BufferedImage testImage = unsplicedSheet.getSubimage(j * spriteSize[0], i * spriteSize[1], spriteSize[0], spriteSize[1]);
                    if(pngHasPixels(testImage))
                        splicedSheet.add(testImage);
                }
            }

            return splicedSheet;
        } catch (IOException e){
            System.out.println("File location is invalid for " + spriteSheetLocation);
            return null;
        }
    }

    private static boolean pngHasPixels(BufferedImage png){
        int[] pngData = new int[png.getWidth() * png.getHeight() * 4];
        png.getData().getPixels(0, 0, png.getWidth(), png.getHeight(), pngData);
        for(int data : pngData){
            if(data != 0)
                return true;
        }
        return false;
    }
    static BufferedImage loadSprite(@NotNull String spriteLocation){
        try {
            return ImageIO.read(new File(spriteRoot + spriteLocation));
        } catch(IOException e){
            System.out.println("File location is invalid for " + spriteLocation);
            return null;
        }
    }

    void drawSprite(Graphics2D g2d);
}
