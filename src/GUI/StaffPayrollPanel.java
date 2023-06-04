package GUI;

import DataBase.DBActions;
import DataBase.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class StaffPayrollPanel extends JPanel {

    private JTextPane textPane;

    private JScrollPane outScroll;

    private JTextField staff;

    JLabel lblMonth;

    JLabel lblYear;

    JTextField month;

    JTextField year;


    JButton allYears;

    JButton monthInYear;

    DBActions act;
    public StaffPayrollPanel() {
        initialize();
    }

    private void initialize() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(550,300));

        act = new DBActions();
        act.useConnectionAndController(DBConnection.getInstance().getConnection() , Menu.db);

        lblMonth = new JLabel("Month");
        lblMonth.setBounds(10,5,39,20);
        add(lblMonth);

        month = new JTextField();
        month.setText("1");
        month.setBounds(10,25,39,20);
        add(month);

        lblYear = new JLabel("Year");
        lblYear.setBounds(55,5,70,20);
        add(lblYear);

        year = new JTextField();
        year.setText("2023");
        year.setBounds(55,25,70,20);
        add(year);

        staff = new JTextField();
        staff.setBounds(10,50,120,20);
        add(staff);

        monthInYear = new JButton("Month In Year");
        monthInYear.setBounds(380,20,130,20);
        add(monthInYear);

        allYears = new JButton("All Years");
        allYears.setBounds(380,50,130,20);
        add(allYears);


        textPane = new JTextPane();
        textPane.setContentType("text/html");
        outScroll = new JScrollPane(textPane);
        outScroll.setBounds(10 , 90, 510,200);
        add(outScroll);


        monthInYear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int imonth , iyear;
                try {
                    imonth = Integer.parseInt(month.getText());
                    if (imonth < 1 || imonth > 12 ) {
                        return;
                    }
                    iyear = Integer.parseInt(year.getText());

                } catch (Exception ex) {
                    return;
                }
                try {
                    String str ;
                    str = act.getAllSalaryDataForEmployStr(staff.getText() , imonth,iyear);
                    textPane.setText(str);
                } catch(SQLException ex) {
                    String html = "<b>" + ex.getMessage()+ "</b>";
                    textPane.setText(html);
                }

            }
        });


        allYears.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    textPane.setText(act.getAllSalaryDataForEmployStr(staff.getText()));
                } catch(SQLException ex) {
                    String html = "<b>" + ex.getMessage()+ "</b>";
                    textPane.setText(html);
                }

            }
        });



    }
}


