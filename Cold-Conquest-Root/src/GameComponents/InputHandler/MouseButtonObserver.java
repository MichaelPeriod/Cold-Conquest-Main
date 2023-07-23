package GameComponents.InputHandler;

import java.awt.*;

public interface MouseButtonObserver {
    //Called from InputManager acting as observer pattern
    void onMouseDown(int mouseButton, Point pos);
    void onMouseUp(int mouseButton, Point pos);
}
