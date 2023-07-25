package GameManagers;

import javax.swing.*;

public class MainRunner {
    public static void main(String[] args) {
        //Create window and set properties
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Cold Colonization");

        //Add a window to console
        GameWindow gw = new GameWindow();
        window.add(gw);

        //Display window
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //Run game
        gw.setup();
    }
}
