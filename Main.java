import java.util.*;
import java.util.Scanner;
import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 

class Main {
    // Global consts
    // -------------
    public static final int startWidth = 600;
    public static final int startHeight = 400;

    // GUI component inits
    // -------------------
    // frame should not really be accessed
    // to change screen, remove mainPanel and recreate it
    static JFrame frame;
    static JPanel mainPanel;
    static JButton startButton;

    static BtnHandler btnHandler;

    public static void main(String[] args) {
        btnHandler = new BtnHandler();

        frame = new JFrame("<Program Name Here>");
        mainPanel = new JPanel();

        setupStartScreen();

        frame.add(mainPanel);
        frame.setSize(startWidth, startHeight);
        frame.setVisible(true);
    }

    public static void start() {
        clearScreen();
    }

    private static void setupStartScreen() {
        // set up start button etc
        JLabel startText = new JLabel("Welcome to <Program Name Here>");
        startText.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton = new JButton("Start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(btnHandler);
 
        mainPanel.add(startText);
        mainPanel.setLayout(new BoxLayout (mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(startButton);
    }

    private static void clearScreen() {
        frame.remove(mainPanel);
        mainPanel = new JPanel();
        frame.add(mainPanel);
        refreshFrame(frame);
    }

    
    // util functions - public but not important
    // -----------------------------------------

    public static void refreshFrame(JFrame frame) {
        frame.setVisible(false);
        frame.setVisible(true);
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