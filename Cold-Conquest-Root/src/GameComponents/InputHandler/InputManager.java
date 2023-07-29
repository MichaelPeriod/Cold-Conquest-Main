package GameComponents.InputHandler;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener {
    /*Make singleton static*/
    private static InputManager inputManager;

    public static InputManager current(){
        if(inputManager == null)
            inputManager = new InputManager();

        return inputManager;
    }

    public InputManager(){
        //Run setup for each
        keyboardSetup();
        mouseButtonSetup();
        mouseMovementSetup();
    }

    /*Keyboard Management*/
    private ArrayList<KeyboardObserver> kbListeners;

    char[] buttonsDown = new char[10];
    int buttonsDownCount = 0;

    private void keyboardSetup(){
        //Maintain array of currently held buttons by char value
        Arrays.fill(buttonsDown, Character.MIN_VALUE);

        kbListeners = new ArrayList<>();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(buttonsDownCount == buttonsDown.length) return; //Ensure no rollover

        //Do not add if key is already registered as down
        for(char curr : buttonsDown){
            if(e.getKeyChar() == curr)
                return;
        }

        //Add to array
        buttonsDown[buttonsDownCount] = e.getKeyChar();
        buttonsDownCount++;

        //Notify all listeners
        notifyKeyboardEvent(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Find char documented as pressed and remove it
        int i;
        for(i = 0; i < buttonsDown.length; i++){
            if(buttonsDown[i] == e.getKeyChar()) {
                buttonsDown[i] = Character.MIN_VALUE;
                buttonsDownCount--;
                break;
            }
        }

        //Move all other indexes back
        for(int j = i; j < buttonsDown.length - 1; j++){
            buttonsDown[j] = buttonsDown[j + 1];
        }
        buttonsDown[buttonsDown.length - 1] = Character.MIN_VALUE;

        //Update all listeners
        notifyKeyboardEvent(e, false);
    }

    public String buttonsDown(){
        //Get all buttons down
        StringBuilder s = new StringBuilder();
        for(char letter : buttonsDown){
            s.append(letter);
        }

        if(s.isEmpty())
            return "No buttons currently down";
        return s.toString();
    }

    //Add and remove listeners from keyboard events
    public void addKeyboardObserver(KeyboardObserver listener){
        kbListeners.add(listener);
    }

    public void removeKeyboardObserver(KeyboardObserver listener){
        kbListeners.remove(listener);
    }

    private void notifyKeyboardEvent(KeyEvent e, boolean isDown){
        //Notify all listeners when event takes place
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
        //Record all mouse buttons held
        Arrays.fill(mouseDown, false);

        mouseListeners = new ArrayList<>();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Set true on relevant mouse button
        if(e.getButton() - 1 < mouseDown.length)
            mouseDown[e.getButton() - 1] = true;

        //Notify all click listeners
        notifyMouseClickEvent(e, true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Set false on relevant mouse button
        if(e.getButton() - 1 < mouseDown.length)
            mouseDown[e.getButton() - 1] = false;

        //Notify all click listeners
        notifyMouseClickEvent(e, false);
    }

    //Add and remove listeners
    public void addMouseButtonListener(MouseButtonObserver listener){
        mouseListeners.add(listener);
    }

    public void removeMouseButtonListener(MouseButtonObserver listener){
        mouseListeners.remove(listener);
    }

    private void notifyMouseClickEvent(MouseEvent e, boolean isDown){
        //Notify listeners when action is triggered
        for(MouseButtonObserver listener : mouseListeners){
            if(isDown){
                listener.onMouseDown(e.getButton() - 1, e.getPoint());
            } else {
                listener.onMouseUp(e.getButton() - 1, e.getPoint());
            }
        }
    }

    //Getters
    public boolean getMouseDown(int button){
        return mouseDown[button];
    }

    public boolean[] getMouseDown() {
        return mouseDown;
    }

    /*Mouse Movement Manager*/
    private ArrayList<MouseMovementObserver> mouseMovementObservers;

    private Point currPos;
    private Point lastPos;

    private void mouseMovementSetup(){
        //Track both the current and previous position
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
    public void mouseMoved(MouseEvent e) {
        lastPos = currPos;
        currPos = e.getPoint();

        notifyMouseMoveEvent(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //Treat same as mouse moved
        mouseMoved(e);
    }

    //Add and remove listeners
    public void addMouseMoveListener(MouseMovementObserver listener){
        mouseMovementObservers.add(listener);
    }

    public void removeMouseMoveListener(MouseMovementObserver listener){
        mouseMovementObservers.remove(listener);
    }

    private void notifyMouseMoveEvent(MouseEvent e){
        //Update all listeners when mouse is moved
        for(MouseMovementObserver listener : mouseMovementObservers){
            listener.onMouseMove(currPos);
            listener.onMouseDelta(lastPos, currPos);
        }
    }
}
