package GUI;

import javax.swing.*;
import java.awt.*;


public class AboutPanel extends JPanel {


    private JTextPane textPane;

    private JScrollPane outScroll;


    public AboutPanel() {
        initialize();
    }

    private void initialize() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(450,200));


        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("<div style=\"text-align:center;\">\n" +
                "\n" +
                "<h3>Database Project - hy360</h3>\n" +
                "\n" +
                "<hr style=\"border-left: 2px solid blue\">\n" +
                "<b>Κωνσταντίνος Παπαδόπουλος csd4713</b>  <br>\n" +
                "<hr style=\"border-left: 2px solid blue\">\n" +
                "<b>Κωνσταντίνος Δελής csd4623</b>  <br>\n" +
                "<hr style=\"border-left: 2px solid blue\">\n" +
                "<b>Δημήτρης Κλειναυτάκης csd4807</b>  <br>\n" +
                "<hr style=\"border-left: 2px solid blue\">\n" +
                "\n" +
                "</div>\n");
        outScroll = new JScrollPane(textPane);
        outScroll.setBounds(10 , 10, 440,150);
        add(outScroll);



    }
}

