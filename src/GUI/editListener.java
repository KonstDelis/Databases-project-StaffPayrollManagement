package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class editListener implements ActionListener{

    public EditWindow editWindow;
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Menu.edit) {
            try {
                editWindow = new EditWindow();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
