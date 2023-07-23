package GameComponents.InputHandler;

import java.awt.*;

public interface MouseMovementObserver {
    //Called from InputManager as observer pattern
    void onMouseMove(Point pos);
    void onMouseDelta(Point lastPos, Point currPos);
}
