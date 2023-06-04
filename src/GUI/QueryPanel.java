package GUI;

import DataBase.DBActions;
import DataBase.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class QueryPanel extends JPanel {

    private JTextPane textPane;

    private JScrollPane outScroll;

    private JScrollPane queryScroll;

    JTextArea queryArea;

    JButton submit;

    DBActions act;

    public QueryPanel(){
        initialise();
    }

    private void initialise() {
        act = new DBActions();
        act.useConnectionAndController(DBConnection.getInstance().getConnection() , Menu.db);

        this.setLayout(null);
        this.setPreferredSize(new Dimension(550,400));


        textPane = new JTextPane();
        textPane.setContentType("text/html");
        outScroll = new JScrollPane(textPane);
        outScroll.setBounds(20 , 90, 510,300);

        queryArea = new JTextArea(2,0);
        queryScroll = new JScrollPane(queryArea);
        queryScroll.setBounds(20,20,400,70);

        submit = new JButton("Submit");
        submit.setBounds(430,30,100,20);

        add(submit);
        add(queryScroll);
        add(outScroll);



        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String queryTxt = queryArea.getText();
                textPane.setText("");
                try {
                    textPane.setText(act.executeQuery(queryTxt));
                } catch(SQLException ex) {
                    String html = "<b>" + ex.getMessage()+ "</b>";
                    textPane.setText(html);
                }
            }
        });

    }
}
