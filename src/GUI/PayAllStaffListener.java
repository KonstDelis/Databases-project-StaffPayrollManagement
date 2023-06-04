package GUI;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayAllStaffListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() ==    Menu.PayAllStaffButton ) {

           PayAllStaffWindow p = new PayAllStaffWindow();
        }
    }
}
