package GUI;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class hireListener implements ActionListener {

    public HireWindow hireWindow;
    public void actionPerformed(ActionEvent e) {
       // Menu.frame.dispose();
        if (e.getSource() == Menu.hire) {
            hireWindow = new HireWindow();

        }
    }

}
