package GameComponents.GameObjects;

import GameComponents.IDrawable;

import java.awt.image.BufferedImage;

public abstract class PixelObject extends GameObject implements IDrawable {
    //Positional variables
    //Make pixel size dynamic
    private int[] spriteBox;
    private String spriteLocation; //Make into sprite sheet later

    @Override
    public int[] getSpriteBox() {
        return spriteBox;
    }

    public void setSpriteBox(int[] spriteBox) {
        this.spriteBox = spriteBox;
    }

    public void setPosX(int x){
        this.spriteBox[0] = x;
    }

    public int getPosX(){
        return spriteBox[0];
    }

    public void setPosY(int y){
        this.spriteBox[1] = y;
    }

    public int getPosY(){
        return spriteBox[1];
    }

    public void setWidth(int w){
        this.spriteBox[2] = w;
    }

    public int getWidth(){
        return spriteBox[2];
    }

    public void setHeight(int h){
        this.spriteBox[3] = h;
    }

    public int getHeight(){
        return spriteBox[3];
    }

    public int[] getPos(){
        return new int[2]{getPosX(), getPosY()};
    }

    public void setPos(int[] pos){
        setPos(pos[0], pos[1]);
    }
    public void setPos(int x, int y){
        setPosX(x);
        setPosY(y);
    }

    public int[] getDimensions(){
        return new int[2]{getWidth(), getHeight()};
    }

    public void setDimensions(int[] dim){
        setDimensions(dim[0], dim[1]);
    }
    public void setDimensions(int x, int y){
        setWidth(x);
        setHeight(y);
    }

    //TODO Finish renderer work

    public BufferedImage getSprite() {
        return this.sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public int getPixelSize() {
        return this.pixelSize;
    }

    @Override
    public String toString(){
        return this.spriteLocation;
    }
}
