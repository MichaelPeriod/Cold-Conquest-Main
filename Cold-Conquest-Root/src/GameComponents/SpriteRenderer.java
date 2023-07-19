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
    private static int pixelSize = 8;
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
    public void renderRhombusOutline(Graphics2D g2d, int[] outline, int[] color){
        //TODO: Make connections and draw pixelated lines
        //T-L
        int[] topLeft = {
                outline[0],
                outline[1] + outline[3] / 2 - getPixelSize(),
                outline[0] + outline[2] / 2 - getPixelSize(),
                outline[1]
        };
        //T-R
        int[] topRight = {
                outline[0] + outline[2] - getPixelSize(),
                outline[1] + outline[3] / 2 - getPixelSize(),
                outline[0] + outline[2] / 2,
                outline[1]
        };
        //L-B
        int[] bottomLeft = {
                outline[0],
                outline[1] + outline[3] / 2,
                outline[0] + outline[2] / 2 - getPixelSize(),
                outline[1] + outline[3] - getPixelSize()
        };
        //R-B
        int[] bottomRight = {
                outline[0] + outline[2] - getPixelSize(),
                outline[1] + outline[3] / 2,
                outline[0] + outline[2] / 2,
                outline[1] + outline[3] - getPixelSize()
        };

        renderLine(g2d, topLeft, color);
        renderLine(g2d, topRight, color);
        renderLine(g2d, bottomLeft, color);
        renderLine(g2d, bottomRight, color);

        g2d.setColor(new Color(color[0], color[1], color[2], color[3]));
        //g2d.fillRect(outline[0], outline[1], outline[2], outline[3]);

        //Left
        g2d.fillRect(outline[0],
                outline[1] + outline[3] / 2 - getPixelSize(), getPixelSize(), getPixelSize());
        g2d.fillRect(outline[0],
                outline[1] + outline[3] / 2, getPixelSize(), getPixelSize());
        //Right
        g2d.fillRect(outline[0] + outline[2] - getPixelSize(),
                outline[1] + outline[3] / 2 - getPixelSize(), getPixelSize(), getPixelSize());
        g2d.fillRect(outline[0] + outline[2] - getPixelSize(),
                outline[1] + outline[3] / 2, getPixelSize(), getPixelSize());

        //Top
        g2d.fillRect(outline[0] + outline[2] / 2 - getPixelSize(),
                outline[1], getPixelSize(), getPixelSize());
        g2d.fillRect(outline[0] + outline[2] / 2,
                outline[1], getPixelSize(), getPixelSize());
        //Bottom
        g2d.fillRect(outline[0] + outline[2] / 2 - getPixelSize(),
                outline[1] + outline[3] - getPixelSize(), getPixelSize(), getPixelSize());
        g2d.fillRect(outline[0] + outline[2] / 2,
                outline[1] + outline[3] - getPixelSize(), getPixelSize(), getPixelSize());
    }

    public void renderLine(Graphics2D g2d, int[] points, int[] color){
        g2d.setColor(Color.BLUE);
        int[] point1 = new int[2];
        int[] point2 = new int[2];

        if(points[0] >= points[2]){
            point1 = new int[]{points[0], points[1]};
            point2 = new int[]{points[2], points[3]};
        } else {
            point2 = new int[]{points[0], points[1]};
            point1 = new int[]{points[2], points[3]};
        }

        int[] travelingPoint = point1.clone();
        float slope = (float)(point1[1] / point2[1]) / (point1[0] - point2[0]);
        int currentSprint = 0;
        while(travelingPoint[0] <= point2[0]){
            g2d.fillRect(travelingPoint[0], travelingPoint[1], getPixelSize(), getPixelSize());
            travelingPoint[0] += getPixelSize();
            currentSprint += slope;
            if (currentSprint >= 1){
                travelingPoint[1] += Math.floor(currentSprint) * getPixelSize();
                currentSprint -= Math.floor(currentSprint);
            } else if (currentSprint <= -1){
                travelingPoint[1] -= Math.ceil(currentSprint) * getPixelSize();
                currentSprint -= Math.ceil(currentSprint);
            }
        }
    }

    /*Getters and Setters*/
    public static int getPixelSize() {
        return pixelSize;
    }

    public static void setPixelSize(int pixelSize) {
        SpriteRenderer.pixelSize = pixelSize;
    }
}
