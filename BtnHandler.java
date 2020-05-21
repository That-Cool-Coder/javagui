import java.awt.event.*; 
import javax.swing.*;

class BtnHandler extends JFrame implements ActionListener {
    public BtnHandler() {}

    public void actionPerformed(ActionEvent aE) {
        String btnName = aE.getActionCommand();
        GuiMode guiMode = Main.guiMode;
        
        if (guiMode == GuiMode.LOGIN_SCREEN && btnName == "Login") {
            Main.loginBtnClicked();
        }

        if (guiMode == GuiMode.LOGIN_SCREEN && btnName == "Create new account") {
            Main.goToNewAcctScreen();
        }

        if (btnName == "Logout") {
            Main.logout();
        }

        if (guiMode == GuiMode.HOME_SCREEN && btnName == "Start new note") {
            Main.startNewNote();
        }

        if (guiMode == GuiMode.NEW_ACCT_SCREEN && btnName == "Create Account") {
            Main.tryMakeNewAcct();
        }

        if (guiMode == GuiMode.NEW_ACCT_SCREEN && btnName == "Cancel") {
            Main.logout(); // ie go to login screen
        }

        if (guiMode == GuiMode.NOTE_EDIT_SCREEN && btnName == "Save") {
            Main.saveNote();
        }

        // if no known button pressed, safely ignore command
        // !DEBUG!
    }
}