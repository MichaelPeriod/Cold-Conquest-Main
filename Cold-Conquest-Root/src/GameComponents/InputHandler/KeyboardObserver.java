package GameComponents.InputHandler;

public interface KeyboardObserver {
    void onKeyPress(char keyCode);
    void onKeyRelease(char keyCode);
}
