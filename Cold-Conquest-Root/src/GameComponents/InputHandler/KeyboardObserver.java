package GameComponents.InputHandler;

public interface KeyboardObserver {
    //Called from InputManager acting as observer pattern
    void onKeyPress(char keyCode);
    void onKeyRelease(char keyCode);
}
