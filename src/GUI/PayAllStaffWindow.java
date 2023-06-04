package GUI;

import javax.swing.*;
import java.sql.SQLException;

public class PayAllStaffWindow extends JFrame {
    public PayAllStaffWindow() {
        int month = MonthPopUp();
        if (month < 1) {
            return;
        }


        int year = YearPopUp();
        String deposits_str;
       // DataBaseController db = new DataBaseController();
        try {

            deposits_str = Menu.db.payAllStaff(month, year);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JLabel deposits = new JLabel(deposits_str);
        JScrollPane scroller = new JScrollPane(deposits, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scroller);
        setVisible(true);
        setBounds(300, 400, 500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private int MonthPopUp() {
        int month = 0;
        while (month < 1 || month > 12) {
            Object result = JOptionPane.showInputDialog(null, "Month:");
            if (result == null) {
                month = -11;
                break;
            }
            try {
                month = Integer.parseInt(result.toString());
            } catch (NumberFormatException nfe) {
                month = -1;
            }
        }
        return month;
    }

    private int YearPopUp() {
        int year = 0;
        while (year < 1) {
            Object result = JOptionPane.showInputDialog(null, "Year:");
            if (result == null)
                continue;
            try {
                year = Integer.parseInt(result.toString());
            } catch (NumberFormatException nfe) {
                year = -1;
            }
        }
        return year;
    }


    public static void main(String[] s) {
        PayAllStaffWindow p = new PayAllStaffWindow();
    }
}
