package GameComponents;

import java.awt.*;

public interface IDrawable {
    static int[] spriteArray;

    default void drawSprite(Graphics2D g2d){
        SpriteRenderer.renderer().drawSprite(g2d, spriteArray, getSpriteBox());
    }

    void loadSprite();

    int[] getSpriteBox();
}
