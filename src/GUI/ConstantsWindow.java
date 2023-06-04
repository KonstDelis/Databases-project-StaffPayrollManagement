package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ConstantsWindow {

    JFrame frame = new JFrame();
    JButton update = new JButton("Update");
    JLabel familySupportText = new JLabel("Family Support Percentage");
    JLabel yearsWorkedText = new JLabel("Years Worked Percentage");
    JLabel basicSalaryAdText = new JLabel("Basic Salary Admin");
    JLabel basicSalaryTeaText = new JLabel("Basic Salary Teaching");
    JLabel researchGrantText = new JLabel("Research Grant");
    JLabel libraryGrantText = new JLabel("Library Grant");
    JLabel success = new JLabel("Success");
    JLabel error = new JLabel("Error");
    JTextArea familySupport = new JTextArea();
    JTextArea yearsWorked = new JTextArea();
    JTextArea basicSalaryAdmin = new JTextArea();
    JTextArea basicSalaryTeaching = new JTextArea();
    JTextArea researchGrant = new JTextArea();
    JTextArea libraryGrant = new JTextArea();
    ConstantsWindow(){

        familySupport.setText(Integer.toString(Menu.db.family_support_percentage));
        yearsWorked.setText(Integer.toString(Menu.db.years_worked_percentage));
        basicSalaryAdmin.setText(Integer.toString(Menu.db.basic_salary_admin));
        basicSalaryTeaching.setText(Integer.toString(Menu.db.basic_salary_teaching));
        researchGrant.setText(Integer.toString(Menu.db.research_grant));
        libraryGrant.setText(Integer.toString(Menu.db.library_grant));


        familySupportText.setBounds(20,60,180,20);
        familySupport.setBounds(200,60,180,20);
        yearsWorkedText.setBounds(20,90,180,20);
        yearsWorked.setBounds(200,90,180,20);
        basicSalaryAdText.setBounds(20,120,180,20);
        basicSalaryAdmin.setBounds(200,120,180,20);
        basicSalaryTeaText.setBounds(20,150,180,20);
        basicSalaryTeaching.setBounds(200,150,180,20);
        researchGrantText.setBounds(20,180,180,20);
        researchGrant.setBounds(200,180,180,20);
        libraryGrantText.setBounds(20,210,180,20);
        libraryGrant.setBounds(200,210,180,20);
        update.setBounds(150,240,100,30);
        success.setBounds(260,240,80,20);
        error.setBounds(260,240,80,20);



        update.addActionListener(new updateListener());

        success.setForeground(Color.GREEN);
        error.setForeground(Color.RED);

        success.setVisible(false);
        error.setVisible(false);

        familySupportText.setFont(null);
        yearsWorkedText.setFont(null);
        basicSalaryAdText.setFont(null);
        basicSalaryTeaText.setFont(null);
        researchGrantText.setFont(null);
        libraryGrantText.setFont(null);

        frame.add(familySupport);
        frame.add(familySupportText);
        frame.add(basicSalaryAdmin);
        frame.add(basicSalaryAdText);
        frame.add(basicSalaryTeaching);
        frame.add(basicSalaryTeaText);
        frame.add(researchGrant);
        frame.add(researchGrantText);
        frame.add(libraryGrant);
        frame.add(libraryGrantText);
        frame.add(yearsWorked);
        frame.add(yearsWorkedText);
        frame.add(update);
        frame.add(success);
        frame.add(error);

        frame.setSize(400,350);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Change Salary Constants");
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public class updateListener implements ActionListener {


        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == update) {

                boolean checkValues;

                if(Integer.parseInt(familySupport.getText())>=Menu.db.family_support_percentage&&Integer.parseInt(yearsWorked.getText())>=Menu.db.years_worked_percentage&&
                        Integer.parseInt(basicSalaryAdmin.getText())>=Menu.db.basic_salary_admin&&Integer.parseInt(basicSalaryTeaching.getText())>=Menu.db.basic_salary_teaching&&
                        Integer.parseInt(researchGrant.getText())>=Menu.db.research_grant&&Integer.parseInt(libraryGrant.getText())>=Menu.db.library_grant){

                    checkValues = true;
                }else{
                    checkValues = false;
                }

                if(!familySupport.getText().equals("")&&!yearsWorked.getText().equals("")&&!basicSalaryAdmin.getText().equals("")&&!basicSalaryTeaching.getText().equals("")&&!researchGrant.getText().equals("")&&!libraryGrant.getText().equals("")&&checkValues) {


                    try {
                        Menu.db.changeSalaryConstants(Integer.parseInt(familySupport.getText()), Integer.parseInt(yearsWorked.getText()), Integer.parseInt(basicSalaryAdmin.getText()), Integer.parseInt(basicSalaryTeaching.getText()), Integer.parseInt(researchGrant.getText()), Integer.parseInt(libraryGrant.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    error.setVisible(false);
                    success.setVisible(true);

                }else{
                    success.setVisible(false);
                    error.setVisible(true);

                    familySupport.setText(Integer.toString(Menu.db.family_support_percentage));
                    yearsWorked.setText(Integer.toString(Menu.db.years_worked_percentage));
                    basicSalaryAdmin.setText(Integer.toString(Menu.db.basic_salary_admin));
                    basicSalaryTeaching.setText(Integer.toString(Menu.db.basic_salary_teaching));
                    researchGrant.setText(Integer.toString(Menu.db.research_grant));
                    libraryGrant.setText(Integer.toString(Menu.db.library_grant));
                }
            }
        }
    }
}
