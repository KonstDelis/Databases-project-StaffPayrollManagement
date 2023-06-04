package GUI;

import DataBase.DBActions;
import DataBase.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class CreateStaffPanel extends JPanel {

    private JLabel staffNumberLbl;

    private JTextField staffNumber;

    private JLabel staffIdLbl;

    private JTextField idNumber;


    JButton submit;

    DBActions act;

    public CreateStaffPanel(){
        initialise();
    }

    private void initialise() {

        this.setLayout(null);
        this.setPreferredSize(new Dimension(250,70));

        staffIdLbl = new JLabel("Start ID");
        staffIdLbl.setBounds(10,10,70,20);

        idNumber = new JTextField();
        idNumber.setText("1");
        idNumber.setBounds(60,10,50,20);


        staffNumberLbl = new JLabel("Count");
        staffNumberLbl.setBounds(10,40,70,20);

        staffNumber = new JTextField();
        staffNumber.setText("30");
        staffNumber.setBounds(60,40,50,20);


        submit = new JButton("Submit");
        submit.setBounds(140,40,100,20);

        add(staffIdLbl);
        add(idNumber);
        add(submit);
        add(staffNumberLbl);
        add(staffNumber);



        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    act = new DBActions();
                    act.useConnectionAndController(DBConnection.getInstance().getConnection(),Menu.db);
                    int value;
                    value = Integer.parseInt(staffNumber.getText());
                    int start;
                    start = Integer.parseInt(idNumber.getText());
                    act.createRandomStaff(value , start);
                    JOptionPane.showMessageDialog(null,value + " random staff entries have been created" ,
                            "DB Action Result", JOptionPane.INFORMATION_MESSAGE);
                } catch(SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage() ,
                            "DB Action Result", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



    }
}

