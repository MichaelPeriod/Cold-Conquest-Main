package GameComponents.GameObjects;

import GameComponents.IDrawable;
import GameComponents.SpriteRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PixelObject extends GameObject implements IDrawable {
    //Includes all game objects which are pixelated

    //Sprite box is maintained as -
    //0: Left X
    //1: Top: Y
    //2: Width: Right X - Left X
    //3: Height: Bottom Y - Top Y
    private int[] spriteBox = new int[4];

    /*Load sprites as a serialized array, do not read yet*/
    public void loadSprite(String spriteLocation){
        loadSprite(spriteLocation, new int[] {32, 32});
    }

    public void loadSprite(String spriteLocation, int[] dim){
        setSprite(SpriteRenderer.loadSpriteToArray(spriteLocation));
        setDimensions(dim);
    }

    /*Getters and Setters*/
    //Sprite Visuals
    public abstract int[] getSprite();
    public abstract void setSprite(int[] sprite);

    @Override
    public int[] getSpriteBox() {
        return spriteBox;
    }

    public void setSpriteBox(int[] spriteBox) {
        this.spriteBox = spriteBox;
    }

    //Sprite positions
    public void setPos(int[] pos){
        setPos(pos[0], pos[1]);
    }
    public void setPos(int x, int y){
        setPosX(x);
        setPosY(y);
    }
    public void setPos(Point pos){
        setPos(pos.x, pos.y);
    }
    public Point getPos(){
        return new Point(getPosX(), getPosY());
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

    //Sprite dimensions
    public void setDimensions(int[] dim){
        setDimensions(dim[0], dim[1]);
    }
    public void setDimensions(int x, int y){
        setWidth(x);
        setHeight(y);
    }
    public int[] getDimensions(){
        int[] toReturn = {getWidth(), getHeight()};
        return toReturn;
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
}
