import java.awt.event.*; 
import javax.swing.*;

class BtnHandler extends JFrame implements ActionListener {
    public BtnHandler() {}

    public void actionPerformed(ActionEvent aE) {
        String btnName = aE.getActionCommand();
        GuiMode guiMode = Main.guiMode;
        
        if (guiMode == GuiMode.START_SCREEN) {
            Main.start();
        }

        else if (guiMode == GuiMode.NEXT_SCREEN) {
            Main.test();
        }
    }
}