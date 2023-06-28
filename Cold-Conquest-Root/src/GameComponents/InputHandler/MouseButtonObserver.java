package GameComponents.InputHandler;

import java.awt.*;

public interface MouseButtonObserver {
    void onMouseDown(int mouseButton, Point pos);
    void onMouseUp(int mouseButton, Point pos);
}
