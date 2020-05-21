import java.awt.*;
import java.io.IOException;
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
    LOGIN_SCREEN,
    HOME_SCREEN,
    NEW_ACCT_SCREEN,
    NOTE_EDIT_SCREEN
}

class Main {
    // Global consts
    // -------------
    public static final Dimension prefSize = new Dimension(600, 400);
    public static final Color bgColor = new Color(0.95f, 0.95f, 0.95f);
    public static final String dataDirPath = "data/";
    public static final String userFilePath = "data/users.dat";
    public static final String infoDirPath = "data/info/";

    // GUI component inits
    // -------------------
    // frame should not really be accessed
    // to change screen, remove mainPanel and recreate it
    static JFrame frame;
    static JPanel mainPanel;
    // (arrayLists of temporary components that you need for later)
    static ArrayList<JLabel> tempLabels = new ArrayList<JLabel>();
    static ArrayList<JTextField> tempTextFields = new ArrayList<JTextField>();

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
        database = new Database(dataDirPath, userFilePath, infoDirPath);
        util = new Util();

        // do initial setup for JFrame
        frame = new JFrame("<Program Name Here>");
        frame.setPreferredSize(prefSize);
        frame.setSize(prefSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setBackground(bgColor);

        setupLoginScreen();
    }

    public static void safelyCrash() {
        System.out.println("\n\n\n\n\n\nFATAL ERROR");
        System.out.println("Press enter to close\n\n\n");
        util.readInput(); // pause until enter press
        System.exit(0);
    }

    // Button onclicks
    // ---------------

    public static void logout() {
        setupLoginScreen();
    }

    public static void loginBtnClicked() {
        // fully self-contained login attempt
        String username = tempTextFields.get(0).getText();
        String password = tempTextFields.get(1).getText();

        BoolAndStatus passwordData = database.passwordCorrect(username, password);
        if (passwordData.status == Status.OK) {
            if (passwordData.bool) {
                // passwordData.bool is bool of whether password is correct
                util.printStrLn("Correct");
                goToHomeScreen();
            }
            else {
                util.printStrLn("Incorrect pw");
            }
        }
        else if (passwordData.status == Status.WARNING) {
            util.printStrLn("User doesn't exist");
        }
    }

    public static void goToHomeScreen() {
        setupHomeScreen();
    }

    public static void goToNewAcctScreen() {
        // go to the new acct page (might contain other things later on)
        setupNewAcctScreen();
    }

    public static void tryMakeNewAcct() {
        // check with the database if the account already exists

        String username = tempTextFields.get(0).getText();
        String password = tempTextFields.get(1).getText();

        Status status = database.createNewAcct(username, password);
        if (status == Status.OK) {
            // (if create account successful)
            goToHomeScreen(); // login (doesn't need pw checking)
        }
    }

    public static void startNewNote() {
        setupNoteEditScreen();
    }

    public static void saveNote() {
        String title    = tempTextFields.get(0).getText();
        String fileName = tempTextFields.get(1).getText();
        String subject  = tempTextFields.get(2).getText();
        String body     = tempTextFields.get(3).getText();

        Note note = new Note(title, fileName, subject, body);
        database.saveNote(note);
    }

    // Main screen editors
    // -------------------

    private static void setupLoginScreen() {
        clearScreen();
        clearTempItems();

        JLabel startText = new JLabel("Welcome to <Program Name Here>");
        startText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField usernameField = new JTextField(50);
        tempTextFields.add(usernameField);

        JTextField passwordField = new JTextField(50);
        tempTextFields.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(btnHandler);

        JButton newAcctBtn = new JButton("Create new account");
        newAcctBtn.addActionListener(btnHandler);
        
        mainPanelCenterAlign();

        mainPanel.add(startText);
        mainPanel.add(usernameField);
        mainPanel.add(passwordField);
        mainPanel.add(loginBtn);
        mainPanel.add(newAcctBtn);

        guiMode = GuiMode.LOGIN_SCREEN;
        updateMainFrame();
    }

    private static void setupHomeScreen() {
        clearScreen();
        clearTempItems();

        JLabel label = new JLabel("Good job logging in!");

        JButton newNoteBtn = new JButton("Start new note");
        newNoteBtn.addActionListener(btnHandler);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(btnHandler);

        mainPanelCenterAlign();
        mainPanel.add(label);
        mainPanel.add(newNoteBtn);
        mainPanel.add(logoutBtn);

        guiMode = GuiMode.HOME_SCREEN;
        updateMainFrame();
    }

    private static void setupNewAcctScreen() {
        clearScreen();
        clearTempItems();

        JLabel prompt = new JLabel("Create an account");
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField usernameField = new JTextField(50);
        tempTextFields.add(usernameField);

        JTextField passwordField = new JTextField(50);
        tempTextFields.add(passwordField);

        JButton createBtn = new JButton("Create Account");
        createBtn.addActionListener(btnHandler);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(btnHandler);

        mainPanelCenterAlign();
        mainPanel.add(prompt);
        mainPanel.add(usernameField);
        mainPanel.add(passwordField);
        mainPanel.add(createBtn);
        mainPanel.add(cancelBtn);

        guiMode = GuiMode.NEW_ACCT_SCREEN;
        updateMainFrame();
    }

    private static void setupNoteEditScreen() {
        clearScreen();
        clearTempItems();

        JTextField titleField = new JTextField(30);
        tempTextFields.add(titleField);

        JTextField fileNameField = new JTextField(30);
        tempTextFields.add(fileNameField);

        JTextField subjectField = new JTextField(60);
        tempTextFields.add(subjectField);

        JTextField bodyField = new JTextField(70);
        tempTextFields.add(bodyField);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(btnHandler);

        mainPanel.add(titleField);
        mainPanel.add(fileNameField);
        mainPanel.add(subjectField);
        mainPanel.add(bodyField);
        mainPanel.add(saveBtn);

        guiMode = GuiMode.NOTE_EDIT_SCREEN;
        updateMainFrame();
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

    private static void clearTempItems() {
        tempLabels.clear();
        tempTextFields.clear();
    }

    private static Component makeVerticalSpace(JPanel panel, int pixels) {
        return Box.createVerticalStrut(pixels);
    }

    private static void mainPanelCenterAlign() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    }

    private static void refreshFrame(JFrame frame) {
        frame.setVisible(true);
    }
}
