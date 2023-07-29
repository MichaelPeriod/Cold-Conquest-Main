package GameComponents;

import GameComponents.Camera.GameCamera;

import java.awt.*;

public interface IDrawable {
    //Used for all objects that can be drawn
    default void drawSprite(Graphics2D g2d){
        Point camPos = GameCamera.camera().getCameraPosition();
        int[] spriteBox = getSpriteBox().clone();
        spriteBox[0] -= camPos.x;
        spriteBox[1] -= camPos.y;
        SpriteRenderer.renderer().drawSprite(g2d, getSprite(), spriteBox);
    }

    default int[] getSprite() {return new int[0];}

    default int[] getSpriteBox() {
        return new int[]{0, 0, 32, 32};
    }
}
