package GameComponents.InputHandler;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener {
    private static InputManager inputManager;

    public InputManager(){
        keyboardSetup();
        mouseButtonSetup();
        mouseMovementSetup();
    }

    public static InputManager current(){
        if(inputManager == null)
            inputManager = new InputManager();

        return inputManager;
    }

    /*Keyboard Management*/
    private ArrayList<KeyboardObserver> kbListeners;

    char[] buttonsDown = new char[10];
    int buttonsDownCount = 0;

    private void keyboardSetup(){
        Arrays.fill(buttonsDown, Character.MIN_VALUE);

        kbListeners = new ArrayList<>();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(buttonsDownCount == buttonsDown.length) return;

        for(char curr : buttonsDown){
            if(e.getKeyChar() == curr)
                return;
        }

        buttonsDown[buttonsDownCount] = e.getKeyChar();
        buttonsDownCount++;

        notifyKeyboardEvent(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int i;
        for(i = 0; i < buttonsDown.length; i++){
            if(buttonsDown[i] == e.getKeyChar()) {
                buttonsDown[i] = Character.MIN_VALUE;
                buttonsDownCount--;
                break;
            }
        }

        for(int j = i; j < buttonsDown.length - 1; j++){
            buttonsDown[j] = buttonsDown[j + 1];
        }

        buttonsDown[buttonsDown.length - 1] = Character.MIN_VALUE;
        notifyKeyboardEvent(e, false);
    }

    public String buttonsDown(){
        StringBuilder s = new StringBuilder();
        for(char letter : buttonsDown){
            s.append(letter);
        }

        if(s.isEmpty())
            return "No buttons currently down";
        return s.toString();
    }

    public void addKeyboardObserver(KeyboardObserver listener){
        kbListeners.add(listener);
    }

    public void removeKeyboardObserver(KeyboardObserver listener){
        kbListeners.remove(listener);
    }

    private void notifyKeyboardEvent(KeyEvent e, boolean isDown){
        for(KeyboardObserver listener : kbListeners){
            if(isDown){
                listener.onKeyPress(e.getKeyChar());
            } else {
                listener.onKeyRelease(e.getKeyChar());
            }
        }
    }

    /*Mouse Button Management*/
    private ArrayList<MouseButtonObserver> mouseListeners;

    private boolean[] mouseDown = new boolean[3];

    public void mouseButtonSetup(){
        Arrays.fill(mouseDown, false);

        mouseListeners = new ArrayList<>();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDown[e.getButton() - 1] = true;

        notifyMouseClickEvent(e, true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown[e.getButton() - 1] = false;

        notifyMouseClickEvent(e, false);
    }

    private void notifyMouseClickEvent(MouseEvent e, boolean isDown){
        for(MouseButtonObserver listener : mouseListeners){
            if(isDown){
                listener.onMouseDown(e.getButton() - 1, e.getPoint());
            } else {
                listener.onMouseUp(e.getButton() - 1, e.getPoint());
            }
        }
    }

    /*Mouse Movement Manager*/
    private ArrayList<MouseMovementObserver> mouseMovementObservers;

    private Point currPos;
    private Point lastPos;

    private void mouseMovementSetup(){
        currPos = new Point(0, 0);
        lastPos = new Point(0, 0);

        mouseMovementObservers = new ArrayList<>();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        lastPos = currPos;
        currPos = e.getPoint();

        notifyMouseMoveEvent(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        lastPos = currPos;
        currPos = e.getPoint();

        notifyMouseMoveEvent(e);
    }

    private void notifyMouseMoveEvent(MouseEvent e){
        for(MouseMovementObserver listener : mouseMovementObservers){
            listener.onMouseMove(currPos);
            listener.onMouseDelta(lastPos, currPos);
        }
    }
}
