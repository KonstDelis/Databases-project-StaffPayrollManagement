package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditWindow {


            static JFrame frame = new JFrame();
             JButton update = new JButton("Update");
             JButton fetch = new JButton("Fetch Data");

            JLabel success = new JLabel("Success");
            JLabel error = new JLabel("Error");

            JList<String> department = new JList<String>(HireWindow.departmentlist);

            String[] data = new String[11];
            JLabel sidText = new JLabel("sid");
            JLabel fnameText = new JLabel("first name");
            JLabel lnameText = new JLabel("last name");
            JLabel departmentText = new JLabel("department");
            JLabel dateText = new JLabel("start date (Month / Year)");
            JLabel addressText = new JLabel("address");
            JLabel phoneText = new JLabel("phone");
            JLabel ibanText = new JLabel("IBAN");
            JLabel bnameText = new JLabel("bank name");
            JLabel marriedText = new JLabel("married");
            JLabel wrongSid = new JLabel("Wrong SID");


                ResultSet rs;

                public String[] returnData(ResultSet rs) throws SQLException {

                    String[] data = new String[11];
                    rs.next();
                        data[0] = rs.getString("sid");
                        data[1] = rs.getString("first_name");
                    data[2] = rs.getString("last_name");
                    data[3] = rs.getString("hire_month");
                    data[4] = rs.getString("hire_year");
                    data[5] = rs.getString("address");
                    data[6] = rs.getString("phone");
                    data[7] = rs.getString("iban");
                    data[8] = rs.getString("bank_name");
                    data[9] = rs.getString("marriage_status");
                    data[10] = rs.getString("staff_type");
                    return data;
                }



                JTextArea sid = new JTextArea();
                JTextArea fname = new JTextArea();
                JTextArea lname = new JTextArea();
                JTextArea  month = new JTextArea();
                JTextArea year = new JTextArea();
                JTextArea  address = new JTextArea();
                JTextArea   phone = new JTextArea();
                JTextArea   iban = new JTextArea();
                JTextArea   bname = new JTextArea();
                JCheckBox   ismarried = new JCheckBox();
            EditWindow() throws SQLException {

                wrongSid.setBounds(400,30,100,20);
                fetch.setBounds(200,20,180,30);
                sidText.setBounds(20,30,50,20);
                sid.setBounds(70,30,120,20);
                fnameText.setBounds(20,60,70,20);
                fname.setBounds(90,60,205,20);
                lnameText.setBounds(305,60,70,20);
                lname.setBounds(375,60,205,20);
                dateText.setBounds(20,90,140,20);
                month.setBounds(160,90,205,20);
                year.setBounds(375,90,205,20);
                addressText.setBounds(20,120,70,20);
                address.setBounds(90,120,490,20);
                phoneText.setBounds(20,150,70,20);
                phone.setBounds(90,150,490,20);
                ibanText.setBounds(20,180,70,20);
                iban.setBounds(90,180,490,20);
                bnameText.setBounds(20,210,70,20);
                bname.setBounds(90,210,490,20);
                ismarried.setBounds(20,240,20,20);
                marriedText.setBounds(40,240,70,20);
                departmentText.setBounds(20, 270, 70, 20);
                department.setBounds(90, 270, 490, 40);
                update.setBounds(250,320,100,30);
                success.setBounds(360,320,70,20);
                error.setBounds(360,320,70,20);


                update.addActionListener(new updateListener());
                fetch.addActionListener(new fetchListener());

                success.setVisible(false);
                error.setVisible(false);
                wrongSid.setVisible(false);

                success.setForeground(Color.GREEN);
                error.setForeground(Color.RED);
                wrongSid.setForeground(Color.RED);

                sidText.setFont(null);
                fnameText.setFont(null);
                lnameText.setFont(null);
                dateText.setFont(null);
                addressText.setFont(null);
                phoneText.setFont(null);
                ibanText.setFont(null);
                bnameText.setFont(null);
                marriedText.setFont(null);
                departmentText.setFont((null));


                frame.add(wrongSid);
                frame.add(fetch);
                frame.add(update);
                frame.add(sid);
                frame.add(sidText);
                frame.add(fnameText);
                frame.add(fname);
                frame.add(lnameText);
                frame.add(lname);
                frame.add(dateText);
                frame.add(month);
                frame.add(year);
                frame.add(addressText);
                frame.add(address);
                frame.add(phone);
                frame.add(phoneText);
                frame.add(iban);
                frame.add(ibanText);
                frame.add(bnameText);
                frame.add(bname);
                frame.add(ismarried);
                frame.add(marriedText);
                frame.add(department);
                frame.add(departmentText);
                frame.add(success);
                frame.add(error);

                frame.setSize(600,600);
                frame.setLayout(null);
                frame.setLocationRelativeTo(null);
                frame.setTitle("Edit employee");
                frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }


    public class fetchListener implements ActionListener {


        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == fetch) {

                boolean checkSid=false;


                try {
                    if(Menu.db.SIDexists(sid.getText())){
                        checkSid = true;
                        wrongSid.setVisible(false);
                    }else{
                        checkSid = false;
                        wrongSid.setVisible(true);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if(checkSid) {
                try {
                    rs = Menu.db.getStaffInfo(sid.getText());

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


                try {
                    data = returnData(rs);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }




                        fname.setText(data[1]);
                        lname.setText(data[2]);
                        month.setText(data[3]);
                        year.setText(data[4]);
                        address.setText(data[5]);
                        phone.setText(data[6]);
                        iban.setText(data[7]);
                        bname.setText(data[8]);
                        if(data[9].equals("1")){
                            ismarried.setSelected(true);
                        }else if(data[9].equals("0")){
                            ismarried.setSelected(false);
                        }



                    }else{

                    fname.setText(null);
                    lname.setText(null);
                    month.setText(null);
                    year.setText(null);
                    address.setText(null);
                    phone.setText(null);
                    iban.setText(null);
                    bname.setText(null);
                    }


            }
        }
    }

    public class updateListener implements ActionListener{


        public void actionPerformed(ActionEvent e) {
                success.setVisible(false);
                error.setVisible(false);
            if (e.getSource() == update) {


                try {
                    if(!sid.getText().equals("")&&!fname.getText().equals("")&&!lname.getText().equals("")&&!iban.getText().equals("")&&!bname.getText().equals("")&&!year.getText().equals("")&&!month.getText().equals("")&&!phone.getText().equals("")&&department.getSelectedValue() !=null&&!address.getText().equals("")) {
                        error.setVisible(false);
                        success.setVisible(true);
                        Menu.db.changeStaffInfo(sid.getText(), fname.getText(), lname.getText(), ismarried.isSelected(), iban.getText(), bname.getText(), Integer.parseInt(year.getText()), Integer.parseInt(month.getText()), phone.getText(), department.getSelectedValue(), data[10], address.getText());
                    }else{
                        success.setVisible(false);
                        error.setVisible(true);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

}
