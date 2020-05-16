import java.awt.*;
import java.util.Arrays;

import javax.swing.*;

// Enums
// -----
enum Status {
    OK,
    WARNING,
    FATAL_ERROR
}

enum GuiMode {
    LOADING_GUI,
    START_SCREEN,
    NEXT_SCREEN
}

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
    // (array of temporary components that you need for later)
    static JLabel[] tempLabels = new JLabel[10];

    // Main class inits
    // ----------------
    static BtnHandler btnHandler;
    static Database database;

    // Misc inits
    // ----------
    static GuiMode guiMode = GuiMode.LOADING_GUI;

    public static void main(String[] args) {
        System.out.println("Starting...");
        btnHandler = new BtnHandler();
        database = new Database(userFilePath, infoFilePath);

        frame = new JFrame("<Program Name Here>");

        frame.setPreferredSize(prefSize);
        frame.setSize(prefSize);

        setupStartScreen();
        updateMainFrame();

        guiMode = GuiMode.START_SCREEN;
    }

    public static void start() {
        guiMode = GuiMode.NEXT_SCREEN;
        setupNextScreen();
        updateMainFrame();
    }

    public static void test() {
        // tempLabels[0] should be set in setupNextScreen
        ResultAndStatus results = database.readInfoFile();
        if (results.status == Status.OK) {
            tempLabels[0].setText(results.result);
        }
        else {
            tempLabels[0].setText("problem reading file!");
        }
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
        mainPanel.add(startButton);
        mainPanelCenterAlign();
    }

    private static void setupNextScreen() {
        clearScreen();
        clearTempLabels();

        JLabel label = new JLabel("This is the hub");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        tempLabels[0] = label;

        JButton readButton = new JButton("Read file!");
        readButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        readButton.addActionListener(btnHandler);

        mainPanel.add(label);
        mainPanel.add(readButton);
        mainPanelCenterAlign();
    }

    private static void clearScreen() {
        frame.remove(mainPanel);
        mainPanel = new JPanel();
        mainPanel.setBackground(bgColor);
        updateMainFrame();
    }

    private static void updateMainFrame() {
        frame.add(mainPanel);
        refreshFrame(frame);
    }

    private static void clearTempLabels() {
        Arrays.fill(tempLabels, null);
    }

    private static void mainPanelCenterAlign() {
        mainPanel.setLayout(new BoxLayout (mainPanel, BoxLayout.Y_AXIS));
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
