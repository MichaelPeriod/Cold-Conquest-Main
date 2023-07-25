package GameComponents;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SpriteRenderer {
    //Declare constants
    private static final String spriteRoot = "res/";
    private static final int pixelSize = 4;
    private static final int DATA_PER_PIXEL = 4;


    //Make into singleton
    private static SpriteRenderer renderer = null;
    public static SpriteRenderer renderer(){
        if(renderer == null){
            renderer = new SpriteRenderer();
        }
        return renderer;
    }

    /*Image Loading*/
    //Returns to original object which will pass in the static buffered image
    public static BufferedImage loadSprite(@NotNull String spriteLocation){
        //Read directly and return value
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
        //Get original sheet
        BufferedImage unsplicedSheet = loadSprite(spriteSheetLocation);

        //Get the total dimensions of sprite sheet
        assert unsplicedSheet != null;
        final int spritesWidth = unsplicedSheet.getWidth() / spriteSize[0];
        final int spritesHeight = unsplicedSheet.getHeight() / spriteSize[1];
        //For smaller sheets
        ArrayList<BufferedImage> splicedSheet = new ArrayList<>();

        //Go over each sprite location and check if not empty, if so add to the spliced sheet
        for(int i = 0; i < spritesHeight; i++){
            for(int j = 0; j < spritesWidth; j++){
                BufferedImage testImage = unsplicedSheet.getSubimage(j * spriteSize[0], i * spriteSize[1], spriteSize[0], spriteSize[1]);
                if(spriteHasPixels(testImage))
                    splicedSheet.add(testImage);
            }
        }

        return splicedSheet;
    }

    private static boolean spriteHasPixels(BufferedImage sprite){
        //Go through each data bit till one has non-0 data in it
        int[] pngData = spriteToIntArray(sprite);

        for(int data : pngData){
            if(data != 0)
                return true;
        }
        return false;
    }

    public static int[] spriteToIntArray(BufferedImage sprite){
        if(sprite == null) return new int[0];

        //Load all pixels into array
        final int TOTAL_PIXELS = sprite.getHeight() * sprite.getWidth();
        int[] pixels = new int[TOTAL_PIXELS * getDataPerPixel()];
        sprite.getData().getPixels(0, 0, sprite.getWidth(), sprite.getHeight(), pixels);

        return pixels;
    }

    /*Draw Sprite*/
    public void drawSprite(Graphics2D g2d, BufferedImage sprite, int[] spriteBox){
        drawSprite(g2d, spriteToIntArray(sprite), spriteBox);
    }

    public void drawSprite(Graphics2D g2d, int[] pixels, int[] spriteBox){
        //Calculate x and y starting pos
        final int PIXEL_SIZE = getPixelSize();
        final int initX = spriteBox[0] - spriteBox[2] / 2 * PIXEL_SIZE;
        final int initY = spriteBox[1] - spriteBox[3] * PIXEL_SIZE;

        for(int i = 0; i < spriteBox[3]; i++) { //height
            for(int j = 0; j < spriteBox[2]; j++) { //width
                //Draw each pixel in order from top left to bottom right
                int pixelIndex = (j + i * spriteBox[2]) * getDataPerPixel();
                g2d.setColor(new Color(pixels[pixelIndex], pixels[pixelIndex + 1], pixels[pixelIndex + 2], pixels[pixelIndex + 3]));
                g2d.fillRect(initX + j * PIXEL_SIZE, initY + i * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }

    /*Pixelated Shapes*/
    //Maybe change to sub-object?
    public void renderRhombusOutline(Graphics2D g2d, int[] outline, int[] color){
        //Declare all lines required
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

        //Draw lines from declared positions
        renderLine(g2d, topLeft, color);
        renderLine(g2d, topRight, color);
        renderLine(g2d, bottomLeft, color);
        renderLine(g2d, bottomRight, color);
    }

    public void renderLine(Graphics2D g2d, int[] points, int[] color){
        //Set brush color
        g2d.setColor(new Color(color[0], color[1], color[2], color[3]));

        //Choose the order of points based on point one being left of point two
        float[] point1, point2;
        if(points[0] <= points[2]){
            point1 = new float[]{points[0], points[1]};
            point2 = new float[]{points[2], points[3]};
        } else {
            point2 = new float[]{points[0], points[1]};
            point1 = new float[]{points[2], points[3]};
        }

        //Make a traveling point that moves where the pixel is to be drawn
        int[] travelingPoint = {(int)point1[0], (int)point1[1]};

        //Find angle of points and round to the nearest allowed angle
        float slope = (point1[1] - point2[1]) / (point1[0] - point2[0]);
        final float totalPossibleAngles = 4; //25 degree angle

        //Add the sprint to the slope after every loop and subtract form split when point is moved vertically
        slope = Math.round(slope * totalPossibleAngles) / totalPossibleAngles;
        float currentSprint = 0;

        while(travelingPoint[0] <= point2[0]){
            //Draw at moving point
            g2d.fillRect(travelingPoint[0], travelingPoint[1], getPixelSize(), getPixelSize());

            //Move the point and solve if it is needed to move vertically or not
            travelingPoint[0] += getPixelSize();
            currentSprint += slope;
            if (currentSprint >= 1){
                travelingPoint[1] += Math.floor(currentSprint) * getPixelSize();
                currentSprint -= Math.floor(currentSprint);
            } else if (currentSprint <= -1){
                travelingPoint[1] += Math.ceil(currentSprint) * getPixelSize();
                currentSprint -= Math.ceil(currentSprint);
            }
        }
    }

    /*Getters and Setters*/
    public static int getPixelSize() {
        return pixelSize;
    }
    public static int getDataPerPixel(){
        return DATA_PER_PIXEL;
    }
}
