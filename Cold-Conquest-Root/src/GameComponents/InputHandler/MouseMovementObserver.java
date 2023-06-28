package GameComponents.InputHandler;

import java.awt.*;

public interface MouseMovementObserver {
    void onMouseMove(Point pos);
    void onMouseDelta(Point lastPos, Point currPos);
}
