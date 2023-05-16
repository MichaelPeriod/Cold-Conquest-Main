package GameManagers;

import javax.swing.*;

public class MainRunner {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Cold Colonization");

        GameWindow gw = new GameWindow();
        window.add(gw);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gw.setup();
    }
}
