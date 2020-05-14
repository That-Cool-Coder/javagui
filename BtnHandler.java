import java.util.*;
import java.util.Scanner;
import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*;

class BtnHandler extends JFrame implements ActionListener {
    public BtnHandler() {}

    public void actionPerformed(ActionEvent aE) {
        String action = aE.getActionCommand();
        switch(action) {
            case "Start":
                Main.start();
                break;
            default:
                // do nothing
        }
    }
}