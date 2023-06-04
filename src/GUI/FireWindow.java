package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
public class FireWindow {

    JFrame frame = new JFrame();
    JButton fire = new JButton("Fire / Retire");
    JLabel sidText = new JLabel("SID");
    JLabel firingDateText = new JLabel("firing or retire date (Month / Year):");
    JLabel success = new JLabel("Success");
    JLabel error = new JLabel("Error");
    JTextArea sid = new JTextArea();
    JTextArea month = new JTextArea();
    JTextArea year = new JTextArea();

    String last;

    FireWindow(){

        sidText.setBounds(20,60,50,20);
        sid.setBounds(70,60,310,20);
        firingDateText.setBounds(100,90,200,20);
        month.setBounds(125,120,70,20);
        year.setBounds(205,120,70,20);
        fire.setBounds(125,150,150,30);
        success.setBounds(165,190,70,20);
        error.setBounds(165,190,70,20);


        success.setVisible(false);
        error.setVisible(false);
        success.setForeground(Color.GREEN);
        error.setForeground(Color.RED);

        sidText.setFont(null);
        firingDateText.setFont(null);

        fire.addActionListener(new fireEmployeeListener());

        frame.add(fire);
        frame.add(sid);
        frame.add(sidText);
        frame.add(firingDateText);
        frame.add(month);
        frame.add(year);
        frame.add(success);
        frame.add(error);

        frame.setSize(400,300);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Fire/Retire employee");
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }


    public class fireEmployeeListener implements ActionListener{


        public void actionPerformed(ActionEvent e) {
            success.setVisible(false);
            error.setVisible(false);
            if (e.getSource() == fire) {

                boolean isSIDExists = false;

                try {
                    if(Menu.db.SIDexists(sid.getText())){
                        isSIDExists = true;
                    }else{
                        isSIDExists = false;
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


                if(!sid.getText().equals("")&&!month.getText().equals("")&&!year.getText().equals("")&&isSIDExists) {
                    error.setVisible(false);
                    success.setVisible(true);
                    try {
                      last =  Menu.db.fire_retire_staff(sid.getText(), Integer.parseInt(month.getText()), Integer.parseInt(year.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    lastPopUp();

                    frame.dispose();
                }else{
                    success.setVisible(false);
                    error.setVisible(true);
                }
            }
        }
    }

    private void lastPopUp() {

            JOptionPane.showMessageDialog(null, last);





    }
}
