import java.util.*;
import java.util.Scanner;
import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 
//hey
class Main {
    static JFrame frame;
    static JButton startButton;
    static JButton button;
    static JLabel label;

    public static void main(String[] args) {
        BtnHandler btnHandler = new BtnHandler();
        frame = new JFrame("<Program Name Here>");

        JPanel topbar = new JPanel();
        label = new JLabel("Welcome to <Program Name Here>");
        startButton = new JButton("Start");
        startButton.addActionListener(btnHandler);
        
        topbar.add(label);
        topbar.add(startButton);

        frame.add(topbar);
        frame.setSize(800, 400);
        frame.show();
    }

    public static String readInput() {
        String input = System.console().readLine(); 
        return input;
    }

    public static int randint(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public static int constrain(int val, int min, int max) {
        // include min and max
        if (val < min) {
            val = min;
        }
        else if (val > max) {
            val = max;
        }
        return val;
    }

    public static String duplicateStr(String strToDuplicate, int amount) {
        String result = "";
        for (int i = 0; i < amount; i ++) {
            result += strToDuplicate;
        }
        return result;
    }
}

class BtnHandler extends JFrame implements ActionListener{
    public BtnHandler() {

    }

    public void actionPerformed(ActionEvent aE) {
        String action = aE.getActionCommand();
        System.out.println(action);
    }
}