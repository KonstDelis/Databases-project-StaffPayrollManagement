package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fireListener implements ActionListener{

    public void actionPerformed(ActionEvent e) {
        FireWindow fireWindow;
        if (e.getSource() == Menu.fire) {
            fireWindow = new FireWindow();

        }
    }
}
