package GameComponents;

import java.awt.*;

public interface IDrawable {
    default void drawSprite(Graphics2D g2d){
        SpriteRenderer.renderer().drawSprite(g2d, getSprite(), getSpriteBox());
    }

    int[] getSprite();

    int[] getSpriteBox();
}
