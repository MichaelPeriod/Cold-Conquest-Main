package GameComponents.UI.TileSelector;

import GameComponents.GameObjects.Tiles.TileMap;
import GameComponents.IDrawable;

import java.awt.*;

public class TileSelector implements IDrawable {
    private Point selectedTile;
    private final TileMap tm;

    public TileSelector(TileMap _tm){
        selectedTile = new Point(0, 0);
        tm = _tm;
    }



    public void setSelectedTile(Point selectedTile) {
        this.selectedTile = selectedTile;
    }

    public Point getSelectedTile() {
        return selectedTile;
    }

    //Draw sprite with the spriterenderer drawtileoutline and call it from tilemap
    @Override
    public void drawSprite(Graphics2D g2d) {
        IDrawable.super.drawSprite(g2d);
    }

    @Override
    public int[] getSpriteBox() {
        int[] box = new int[4];
        Point worldPos = tm.cords.mapToWorldCorner(selectedTile);
        box[0] = worldPos.x;
        box[1] = worldPos.y;
        box[2] = tm.getTileBaseSize()[0];
        box[3] = tm.getTileBaseSize()[1] - tm.getTileBaseThinkness();
        return box;
    }
}
