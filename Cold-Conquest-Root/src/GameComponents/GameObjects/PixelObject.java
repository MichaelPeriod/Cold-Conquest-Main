package GameComponents.GameObjects;

import GameComponents.IDrawable;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PixelObject extends GameObject implements IDrawable {
    //Positional variables
    //Make pixel size dynamic
    private int posX, posY;
    private static int pixelSize = 4;

    private String spriteLocation; //Make into sprite sheet later
    private BufferedImage sprite; //Convert to 2d array list for animation

    //Set sprite and load it
    public PixelObject(String spriteLocation){
        this.spriteLocation = spriteLocation;
        this.sprite = IDrawable.loadSprite(this.spriteLocation);
    }

    //Draw the sprite every call
    @Override
    public void drawSprite(Graphics2D g2d){
        //Calculate x and y pos
        final int initX = getPosX();
        final int initY = getPosY();
        final int PIXEL_SIZE = getPixelSize();

        //Calculate constants
        final int DATA_PER_PIXEL = 4;
        final int TOTAL_PIXELS = sprite.getHeight() * sprite.getWidth();
        final int DATA_PER_WIDTH = DATA_PER_PIXEL * sprite.getWidth();

        //Load all pixel data into array
        int[] pixels = new int[TOTAL_PIXELS * DATA_PER_PIXEL];
        sprite.getData().getPixels(0, 0, sprite.getWidth(), sprite.getHeight(), pixels);

        //Draw pixel
        for(int i = 0; i < TOTAL_PIXELS * DATA_PER_PIXEL; i += DATA_PER_PIXEL){
            int xOffset = (i % DATA_PER_WIDTH) / DATA_PER_PIXEL * PIXEL_SIZE;
            int yOffset = Math.floorDiv(i, DATA_PER_WIDTH) * PIXEL_SIZE;

            g2d.setColor(new Color(pixels[i], pixels[i + 1], pixels[i + 2], pixels[i + 3]));
            g2d.fillRect(initX + xOffset, initY + yOffset, PIXEL_SIZE, PIXEL_SIZE);
        }
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int[] getPos(){
        int[] pos = new int[2];
        pos[0] = getPosX();
        pos[1] = getPosY();
        return pos;
    }

    public void setPos(int x, int y){
        setPosX(x);
        setPosY(y);
    }

    public BufferedImage getSprite() {
        return this.sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public int getPixelSize() {
        return this.pixelSize;
    }

    public void setPixelSize(int pixelSize) {
        this.pixelSize = pixelSize;
    }

    @Override
    public String toString(){
        return this.spriteLocation;
    }
}
