package GameObjects;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class PixelObject extends GameObject {
    //Read from a file and draw in a pixel art format

    private static String spriteRoot = "res/";
    private String spriteLocation; //Make into sprite sheet later
    private BufferedImage sprite; //Convert to 2d array list for animation

    public PixelObject(String spriteLocation){
        this.spriteLocation = spriteLocation;
        this.sprite = loadSprite(this.spriteLocation);
    }

    protected static BufferedImage loadSprite(@NotNull String spriteLocation){
        try {
            BufferedImage toReturn = ImageIO.read(new File(spriteRoot + spriteLocation));
            return toReturn;
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
