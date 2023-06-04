package GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class HireWindow {

     static String[] departmentlist = {"Administrator", "Teaching"};
     String[] typelist = {"Permanent", "Contractor"};
    static public  JFrame frame = new JFrame();

      JButton hireButt = new JButton("Hire");
    JLabel sidText = new JLabel("sid");
    JLabel fnameText = new JLabel("first name");
    JLabel lnameText = new JLabel("last name");
    JLabel departmentText = new JLabel("department");
    JLabel typeText = new JLabel("staff type");
    JLabel dateText = new JLabel("start date (Month / Year)");
    JLabel addressText = new JLabel("address");
    JLabel phoneText = new JLabel("phone");
    JLabel ibanText = new JLabel("IBAN");
    JLabel bnameText = new JLabel("bank name");
    JLabel marriedText = new JLabel("married");
    JLabel numofchildText = new JLabel("number of children");
    JLabel childageText = new JLabel("children's ages (comma seperated)");
    JLabel success = new JLabel("Success");
    JLabel error = new JLabel("Error");
    JTextArea sid = new JTextArea();
    JTextArea fname = new JTextArea();
    JTextArea lname = new JTextArea();
    JTextArea month = new JTextArea();
    JTextArea year = new JTextArea();
    JTextArea address = new JTextArea();
    JTextArea phone = new JTextArea();
    JTextArea iban = new JTextArea();
    JTextArea bname = new JTextArea();
    JTextArea childage = new JTextArea();
    JTextArea numofchildren = new JTextArea();
    JList<String> type = new JList<String>(typelist);
    JList<String> department = new JList<String>(departmentlist);
    JCheckBox ismarried = new JCheckBox();


    public HireWindow() {




        sidText.setBounds(20, 60, 40, 20);
        sid.setBounds(60, 60, 520, 20);
        fnameText.setBounds(20, 90, 70, 20);
        fname.setBounds(90, 90, 205, 20);
        lnameText.setBounds(305, 90, 70, 20);
        lname.setBounds(375, 90, 205, 20);
        departmentText.setBounds(20, 120, 70, 20);
        department.setBounds(90, 120, 490, 40);
        typeText.setBounds(20, 160, 70, 20);
        type.setBounds(90, 160, 490, 40);
        dateText.setBounds(20, 210, 140, 20);
        month.setBounds(160, 210, 205, 20);
        year.setBounds(375, 210, 205, 20);
        addressText.setBounds(20, 240, 70, 20);
        address.setBounds(90, 240, 490, 20);
        phoneText.setBounds(20, 270, 70, 20);
        phone.setBounds(90, 270, 490, 20);
        ibanText.setBounds(20, 300, 40, 20);
        iban.setBounds(60, 300, 520, 20);
        bnameText.setBounds(20, 330, 70, 20);
        bname.setBounds(90, 330, 490, 20);
        ismarried.setBounds(20, 360, 20, 20);
        marriedText.setBounds(40, 360, 70, 20);
        numofchildText.setBounds(120, 360, 100, 20);
        numofchildren.setBounds(220, 360, 360, 20);
        childageText.setBounds(20, 390, 200, 20);
        childage.setBounds(220, 390, 360, 20);
        hireButt.setBounds(270, 420, 70, 30);
        success.setBounds(350, 420, 70, 20);
        error.setBounds(350, 420, 70, 20);


        success.setForeground(Color.GREEN);
        error.setForeground(Color.RED);
        success.setVisible(false);
        error.setVisible(false);


        hireButt.addActionListener(new HireWindow.hireEmployeeListener());


        sidText.setFont(null);
        fnameText.setFont(null);
        lnameText.setFont(null);
        departmentText.setFont(null);
        department.setFont(null);
        dateText.setFont(null);
        type.setFont(null);
        typeText.setFont(null);
        addressText.setFont(null);
        phoneText.setFont(null);
        ibanText.setFont(null);
        bnameText.setFont(null);
        marriedText.setFont(null);
        numofchildText.setFont(null);
        childageText.setFont(null);

        frame.add(sid);
        frame.add(sidText);
        frame.add(fnameText);
        frame.add(lnameText);
        frame.add(fname);
        frame.add(lname);
        frame.add(departmentText);
        frame.add(department);
        frame.add(typeText);
        frame.add(type);
        frame.add(dateText);
        frame.add(month);
        frame.add(year);
        frame.add(address);
        frame.add(addressText);
        frame.add(phone);
        frame.add(phoneText);
        frame.add(ibanText);
        frame.add(iban);
        frame.add(bname);
        frame.add(bnameText);
        frame.add(marriedText);
        frame.add(ismarried);
        frame.add(numofchildren);
        frame.add(numofchildText);
        frame.add(childage);
        frame.add(childageText);
        frame.add(hireButt);
        frame.add(success);
        frame.add(error);


        frame.setSize(600, 600);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Hire Employee");
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);


    }
    public class hireEmployeeListener  implements ActionListener {


        public int[] childages;
        public String childId;

        boolean check,checkChild;

        String[] ages;

        public void actionPerformed(ActionEvent e) {

            success.setVisible(false);
            error.setVisible(false);
            if(e.getSource() == hireButt){

                boolean isSidExists = true;

                try {
                    if(Menu.db.SIDexists(sid.getText())){
                        isSidExists = true;
                    }else if(!Menu.db.SIDexists(sid.getText())){
                        isSidExists = false;
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


                if(month.getText()!=null &&year.getText()!=null && !month.getText().equals("")&& !year.getText().equals("")) {


                    check = true;
                }else{
                    check = false;
                }


                if(Integer.parseInt(numofchildren.getText())==0 && childage.getText().equals("") &&!isSidExists){

                    checkChild = true;
                }
                else if(!childage.getText().equals("") && !numofchildren.getText().equals("")&&!isSidExists){
                    childages = new int[Integer.parseInt(numofchildren.getText())];
                    ages = childage.getText().split(",");
                    if(ages.length == Integer.parseInt(numofchildren.getText())) {
                        checkChild = true;

                    }else{
                        checkChild = false;
                    }
                }else{
                    checkChild = false;
                }



                if(sid.getText()!=null&&fname.getText()!=null&&lname.getText()!=null&&iban.getText()!=null&&bname.getText()!=null&&phone.getText()!=null&&department.getSelectedValue()!=null&&type.getSelectedValue()!=null&&address.getText()!=null
                        && !sid.getText().equals("")&&!fname.getText().equals("")&&!lname.getText().equals("")&&!iban.getText().equals("")&&!bname.getText().equals("")&&!phone.getText().equals("")&&!address.getText().equals("")&&check&&checkChild && !isSidExists ){


                    if(Integer.parseInt(numofchildren.getText())!=0) {
                        for (int i = 0; i < ages.length; i++) {

                            childages[i] = Integer.parseInt(ages[i]);

                        }
                    }



                    if(type.getSelectedValue().equals("Permanent") )
                    {
                        try {
                            Menu.db.newPermanentStaff(sid.getText(),fname.getText(),lname.getText(),ismarried.isSelected(),iban.getText(),bname.getText(),Integer.parseInt(year.getText()),Integer.parseInt(month.getText()),phone.getText(),department.getSelectedValue(),address.getText());
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }


                    }else if(type.getSelectedValue().equals("Contractor")){


                        int ContractSalary = ContractSalarypopup(),contract_end_month = endMonthPopUp(),contract_end_year=endYearPopUp() ;
                        String ContractId = sid.getText()+"Contr"+ contract_end_month +"_"+contract_end_year;

                        try {
                            Menu.db.newContractorStaff(sid.getText(),fname.getText(),lname.getText(),ismarried.isSelected(),iban.getText(),bname.getText(),Integer.parseInt(year.getText()),Integer.parseInt(month.getText()),phone.getText(),department.getSelectedValue(),address.getText(),ContractId,ContractSalary,Integer.parseInt(month.getText()),Integer.parseInt(year.getText()),contract_end_month,contract_end_year);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }


                    }
                    error.setVisible(false);
                    success.setVisible(true);
                    try {
                        success.setVisible(true);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    success.setVisible(false);
                    HireWindow.frame.dispose();

                }else{

                    error.setVisible(true);
                }

                if(!isSidExists) {
                    for (int i = 0; i < ages.length; i++) {
                        childId = sid.getText() + "_C" + Integer.toString(i + 1);

                        try {
                            Menu.db.newChild(sid.getText(), childId, childages[i]);

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }


        private int ContractSalarypopup() {
            int salary = 0;

            while(salary<1) {
                Object result = JOptionPane.showInputDialog(null, "Contract Salary:");
                if (result == null) {
                    continue;
                }
                salary = Integer.parseInt(result.toString());

            }
            return salary;
        }



        private int endMonthPopUp() {
            int month = 0;
            while (month < 1 || month > 12) {
                Object result = JOptionPane.showInputDialog(null, "Contract End Month:");
                if (result == null)
                    continue;
                try {
                    month = Integer.parseInt(result.toString());
                } catch (NumberFormatException nfe) {
                    month = -1;
                }
            }
            return month;
        }


        private int endYearPopUp() {
            int year = 0;
            while (year < 1) {
                Object result = JOptionPane.showInputDialog(null, "Contract End Year:");
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
    }

}
