package GUI;

import DataBase.DBActions;
import DataBase.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;



public class PayrollAverageChangePanel extends JPanel {

    private JTextPane textPane;

    private JScrollPane outScroll;

    JLabel lblMonth;

    JLabel lblYear;

    JLabel lblFrom;

    JLabel lblTo;


    JTextField month;

    JTextField year;


    JTextField month2;

    JTextField year2;


    JButton submit;

    DBActions act;
    public PayrollAverageChangePanel() {
        initialize();
    }

    private void initialize() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(550,300));

        act = new DBActions();
        act.useConnectionAndController(DBConnection.getInstance().getConnection() , Menu.db);

        lblMonth = new JLabel("Month");
        lblMonth.setBounds(40,5,39,20);
        add(lblMonth);

        month = new JTextField();
        month.setText("1");
        month.setBounds(40,25,39,20);
        add(month);

        lblFrom = new JLabel("1st");
        lblFrom.setBounds(10,25,39,20);
        add(lblFrom);

        month2 = new JTextField();
        month2.setText("1");
        month2.setBounds(40,45,39,20);
        add(month2);

        lblTo = new JLabel("2nd");
        lblTo.setBounds(10,45,39,20);
        add(lblTo);


        lblYear = new JLabel("Year");
        lblYear.setBounds(85,5,70,20);
        add(lblYear);

        year = new JTextField();
        year.setText("2023");
        year.setBounds(85,25,70,20);
        add(year);

        year2 = new JTextField();
        year2.setText("2023");
        year2.setBounds(85,45,70,20);
        add(year2);

        submit = new JButton("Submit");
        submit.setBounds(380,45,130,20);
        add(submit);

        textPane = new JTextPane();
        textPane.setContentType("text/html");
        outScroll = new JScrollPane(textPane);
        outScroll.setBounds(10 , 90, 510,200);
        add(outScroll);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int m1 , y1 , m2 , y2;

                try {
                    m1 = Integer.parseInt(month.getText());
                    if (m1 < 1 || m1 > 12 ) {
                        return;
                    }
                    m2 = Integer.parseInt(month2.getText());
                    if (m2 < 1 || m2 > 12 ) {
                        return;
                    }
                    y1 = Integer.parseInt(year.getText());
                    y2 = Integer.parseInt(year2.getText());
                } catch (Exception ex) {
                    return;
                }
                try {
                    textPane.setText(act.getSalaryAverageChangesStr(m1, y1,m2,y2));
                } catch(SQLException ex) {
                    String html = "<b>" + ex.getMessage()+ "</b>";
                    textPane.setText(html);
                }
            }
        });

    }
}

