import java.util.*;
import java.util.Scanner;
import java.awt.event.*; 
import java.awt.*;
import java.awt.color.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;;

class Main {
    // Global consts
    // -------------
    public static final Dimension prefSize = new Dimension(600, 400);
    public static final Color bgColor = new Color(0.95f, 0.95f, 0.95f);
    public static final String userFilePath = "data/users.dat";
    public static final String infoFilePath = "data/info.dat";

    // GUI component inits
    // -------------------
    // frame should not really be accessed
    // to change screen, remove mainPanel and recreate it
    static JFrame frame;
    static JPanel mainPanel;
    static JButton startButton;

    // Main class inits
    // ----------------
    static BtnHandler btnHandler;
    static Database database;

    public static void main(String[] args) {
	System.out.println("Starting...");
        btnHandler = new BtnHandler();
        database = new Database(userFilePath, infoFilePath);

        frame = new JFrame("<Program Name Here>");

        setupStartScreen();

        frame.add(mainPanel);
        frame.setPreferredSize(prefSize);
        frame.setSize(prefSize);
        //frame.pack();
        frame.setVisible(true);
    }

    public static void start() {
        clearScreen();
    }

    private static void setupStartScreen() {
        // set up start button etc
        mainPanel = new JPanel();
        mainPanel.setBackground(bgColor);

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
        mainPanel.setBackground(bgColor);
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
