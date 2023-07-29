package GameComponents.UI.TileSelector;

import GameComponents.GameObjects.Tiles.TileMap;
import GameComponents.IDrawable;
import GameComponents.SpriteRenderer;

import java.awt.*;

public class TileSelector implements IDrawable {
    private Point selectedTile;
    private final TileMap tm;
    private final int[] defaultColor;
    private int selectionSize = 1;

    public TileSelector(TileMap _tm){
        selectedTile = new Point(0, 0);
        tm = _tm;
        defaultColor = new int[]{51, 136, 222, 255};
    }



    public void setSelectedTile(Point selectedTile) {
        this.selectedTile = selectedTile;
    }

    public Point getSelectedTile() {
        return selectedTile;
    }

    @Override
    public void drawSprite(Graphics2D g2d) {
        SpriteRenderer.renderer().renderRhombusOutline(g2d, getSpriteBox(), getColor());
    }

    @Override
    public int[] getSpriteBox() {
        int[] box = new int[4];
        Point st = new Point(selectedTile);
        st.translate(-selectionSize + 1, 0);
        Point camPos = new Point(0, 0);
        camPos.setLocation(tm.cords.mapToCameraCorner(st).x, 0);
        st.translate(0, -selectionSize + 1);
        camPos.translate(0, tm.cords.mapToCameraCorner(st).y);

        box[2] = tm.getTileBaseSize()[0] * selectionSize;
        box[3] = (tm.getTileBaseSize()[1] - tm.getTileBaseThinkness()) * selectionSize;
        box[0] = camPos.x;
        box[1] = camPos.y;
        return box;
    }

    public int[] getColor(){
        return this.defaultColor;
    }

    public void setSelectionSize(int selectionSize) {
        this.selectionSize = Math.max(selectionSize, 1);
    }

    public int getSelectionSize(){
        return this.selectionSize;
    }
}
