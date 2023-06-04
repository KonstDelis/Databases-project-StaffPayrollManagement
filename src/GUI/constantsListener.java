package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class constantsListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        ConstantsWindow constantsWindow;
        if (e.getSource() == Menu.constants) {
            constantsWindow = new ConstantsWindow();

        }
    }
}
