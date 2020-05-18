import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import java.util.ArrayList;

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
    public static final String dataDirPath = "data/";
    public static final String userFilePath = "data/users.dat";
    public static final String infoFilePath = "data/info.dat";

    // GUI component inits
    // -------------------
    // frame should not really be accessed
    // to change screen, remove mainPanel and recreate it
    static JFrame frame;
    static JPanel mainPanel;
    // (arrayList of temporary components that you need for later)
    static ArrayList<JLabel> tempLabels = new ArrayList<JLabel>();

    // Main class inits
    // ----------------
    static BtnHandler btnHandler;
    static Database database;
    static Util util;

    // Misc inits
    // ----------
    static GuiMode guiMode = GuiMode.LOADING_GUI;

    public static void main(String[] args) {
        System.out.println("Starting...");
        btnHandler = new BtnHandler();
        database = new Database(dataDirPath, userFilePath, infoFilePath);
        util = new Util();

        frame = new JFrame("<Program Name Here>");

        frame.setPreferredSize(prefSize);
        frame.setSize(prefSize);

        setupStartScreen();
        updateMainFrame();

        guiMode = GuiMode.START_SCREEN;
    }

    public static void safelyCrash() {
        System.out.println("\n\n\n\n\n\nFATAL ERROR");
        System.out.println("Press enter to close\n\n\n");
        util.readInput(); // pause until enter press
        System.exit(0);
    }

    // Button onclicks
    // ---------------

    public static void start() {
        guiMode = GuiMode.NEXT_SCREEN;
        setupNextScreen();
        updateMainFrame();
    }

    public static void test() {
        // tempLabels[0] should be set in setupNextScreen
        ResultAndStatus results = database.readInfoFile();

        JLabel resultLabel = new JLabel("Welcome to <Program Name Here>");
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (results.status == Status.OK) {
            String result = results.result;
            if (result.length() == 0 ) {
                result = "(empty file)";
            }
            resultLabel.setText("File contents: " + result);
        }
        else {
            resultLabel.setText("problem reading file!");
        }
        
        addVerticalSpace(mainPanel, 50);
        mainPanel.add(resultLabel);
        mainPanelCenterAlign();
        refreshFrame(frame);
    }

    // Main screen editors
    // -------------------

    private static void setupStartScreen() {
        // set up start button etc
        mainPanel = new JPanel();
        mainPanel.setBackground(bgColor);

        JLabel startText = new JLabel("Welcome to <Program Name Here>");
        startText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("Start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(btnHandler);
        
        addVerticalSpace(mainPanel, 20);
        mainPanel.add(startText);
        addVerticalSpace(mainPanel, 50);
        mainPanel.add(startButton);
        mainPanelCenterAlign();
    }

    private static void setupNextScreen() {
        // this is not a generic function, it's just a random screen that I called nextScreen for testing
        clearScreen();

        JLabel label = new JLabel("This is the hub");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton readButton = new JButton("Read file!");
        readButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        readButton.addActionListener(btnHandler);
   
        addVerticalSpace(mainPanel, 20);
        mainPanel.add(label);
        addVerticalSpace(mainPanel, 50);
        mainPanel.add(readButton);
        mainPanelCenterAlign();
    }

    // Small private functions related to the screen
    // ---------------------------------------------

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
        tempLabels.clear();
    }

    private static void addVerticalSpace(JPanel panel, int pixels) {
        panel.add(Box.createVerticalStrut(pixels));
    }

    private static void mainPanelCenterAlign() {
        mainPanel.setLayout(new BoxLayout (mainPanel, BoxLayout.Y_AXIS));
    }

    private static void refreshFrame(JFrame frame) {
        frame.setVisible(true);
    }
}
