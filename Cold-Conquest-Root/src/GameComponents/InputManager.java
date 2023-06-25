package GameComponents;

import java.awt.event.*;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener {
    private static InputManager inputManager;

    char[] buttonsDown = new char[10];
    int buttonsDownCount = 0;

    public InputManager(){
        for(int i = 0; i < buttonsDown.length; i++){
            buttonsDown[i] = Character.MIN_VALUE;
        }
    }

    public static InputManager current(){
        if(inputManager == null)
            inputManager = new InputManager();

        return inputManager;
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
