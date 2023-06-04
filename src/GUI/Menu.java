package GUI;


import DataBase.DBActions;
import DataBase.DBConnection;
import DataBase.DataBaseController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Menu extends JFrame {
    static public DataBaseController db ;
    static {
        try {
            DBConnection.getInstance().initializeFromFile ("DataBase/config.properties");
            db = new DataBaseController(DBConnection.getInstance().getConnection());
        }  catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    static JFrame frame;

    static JButton hire;
    static JButton edit;
    static JButton fire;
    static JButton PayAllStaffButton;
    static JButton constants;


    private JButton doQuery;

    private JButton doDrop;

    private JButton doCreate;

    private JButton doGenerateRandom;

    private JButton doPayrollView;

    private JButton doPayrollMinMaxAvg;

    private JButton doPayrollChanges;

    private JButton doStaffView;

    private JButton doPayrollTotal;

    private JButton doAbout;

    public Menu(){



        //frame = new JFrame();

        hire = new JButton("Hire Employee");
        edit = new JButton("Edit Employee");
        fire = new JButton("Fire Employee");
        PayAllStaffButton = new JButton("Pay All Staff");
        constants = new JButton("Change Salary");



        hire.setBounds(20,20,150,30);
        edit.setBounds(20,70,150,30);
        fire.setBounds(20,120,150,30);
        PayAllStaffButton.setBounds(20,170,150,30);
        constants.setBounds(20,220,150,30);


        hire.addActionListener(new hireListener());
        edit.addActionListener(new editListener());
        fire.addActionListener(new fireListener());
        PayAllStaffButton.addActionListener(new PayAllStaffListener());
        constants.addActionListener(new constantsListener());




        this.add(hire);
        this.add(edit);
        this.add(fire);
        this.add(PayAllStaffButton);
        this.add(constants);

        ////////////////////////////////////////////////


        //////////////////////////////////////////////

        doDrop = new JButton("Drop All Tables");
        doDrop.setBounds(220,20,150,30);
        initializeDoDrop();
        this.add(doDrop);

        doCreate =  new JButton("Create DB Structure");
        doCreate.setBounds(220,70,150,30);
        initializeDoCreate();
        this.add(doCreate);

        doGenerateRandom = new JButton("Create Staff Entries");
        doGenerateRandom.setBounds(220,120,150,30);
        initializeDoGenerateRandom();
        this.add(doGenerateRandom);

        doPayrollView = new JButton("Payroll");
        doPayrollView.setBounds(220,170,150,30);
        initializeDoPayroll();
        this.add(doPayrollView);

        doPayrollMinMaxAvg = new JButton("Payroll Statistics");
        doPayrollMinMaxAvg.setBounds(220,220,150,30);
        initializeDoPayrollMinMaxAvg();
        this.add(doPayrollMinMaxAvg);

        doQuery = new JButton("Execute Queries");
        doQuery.setBounds(420,20,150,30);
        initializeDoQuery();
        this.add(doQuery);

        doPayrollChanges  = new JButton("Payroll Changes");
        doPayrollChanges.setBounds(420,70,150,30);
        initializeDoPayrollChanges();
        this.add(doPayrollChanges);

        doStaffView  = new JButton("Staff Payroll");
        doStaffView.setBounds(420,120,150,30);
        initializeDoStaffView();
        this.add(doStaffView);

        doPayrollTotal   = new JButton("Payroll Totals");
        doPayrollTotal.setBounds(420,170,150,30);
        initializeDoPayrollTotal();
        this.add(doPayrollTotal);



        doAbout   = new JButton("About");
        doAbout.setBounds(420,220,150,30);
        initializeDoAbout();
        this.add(doAbout);

        ///////////////////////////////////////////////


        this.setSize(610,320);
        this.setLayout(null);


        //mainPanel = new JPanel();
        //mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        //this.setLayout(new BorderLayout());
        //this.getContentPane().add(mainPanel , BorderLayout.CENTER);

        this.setTitle("Database Project hy360");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initializeDoAbout() {
        doAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutPanel p = new AboutPanel();
                JOptionPane pane = new JOptionPane(p);
                JDialog dialog = pane.createDialog("About");
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    private void initializeDoPayrollTotal() {
        doPayrollTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PayrollTotalsPanel p = new PayrollTotalsPanel();
                JOptionPane pane = new JOptionPane(p);
                JDialog dialog = pane.createDialog("Payroll Totals");
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    private void initializeDoStaffView(){
        doStaffView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StaffPayrollPanel p = new StaffPayrollPanel();
                JOptionPane pane = new JOptionPane(p);
                JDialog dialog = pane.createDialog("Staff Payroll");
                dialog.pack();
                dialog.setVisible(true);
            }
        });

    }

    private void initializeDoPayrollChanges() {
        doPayrollChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PayrollAverageChangePanel p = new PayrollAverageChangePanel();
                JOptionPane pane = new JOptionPane(p);
                JDialog dialog = pane.createDialog("Payroll Average Changes");
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    private void initializeDoPayrollMinMaxAvg() {
        doPayrollMinMaxAvg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PayrollStatPanel p = new PayrollStatPanel();
                JOptionPane pane = new JOptionPane(p);
                JDialog dialog = pane.createDialog("Payroll Statistics");
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    private void initializeDoPayroll() {
        doPayrollView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PayrollPanel p = new PayrollPanel();
                JOptionPane pane = new JOptionPane(p);
                JDialog dialog = pane.createDialog("Payroll");
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    private void initializeDoGenerateRandom() {
        doGenerateRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateStaffPanel p = new CreateStaffPanel();
                JOptionPane pane = new JOptionPane(p);
                JDialog dialog = pane.createDialog("Create Random Staff Entries");
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    private void initializeDoCreate() {
        doCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    DBActions act = new DBActions();
                    act.useConnectionAndController(DBConnection.getInstance().getConnection() , db);
                    act.createTablesAndViews();
                    JOptionPane.showMessageDialog(null,"The database structure has been created" ,
                            "DB Action Result", JOptionPane.INFORMATION_MESSAGE);
                } catch(SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage() ,
                            "DB Action Result", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    private void initializeDoDrop() {
        doDrop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    DBActions act = new DBActions();
                    act.useConnectionAndController(DBConnection.getInstance().getConnection()  ,db);
                    act.clearDatabaseAndViews();
                    JOptionPane.showMessageDialog(null,"All tables and views they have been dropped" ,
                            "DB Action Result", JOptionPane.INFORMATION_MESSAGE);
                } catch(SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage() ,
                            "DB Action Result", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    private void initializeDoQuery() {
        doQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QueryPanel p = new QueryPanel();
                JOptionPane pane = new JOptionPane(p);
                JDialog dialog = pane.createDialog("Query Execution");
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

}
