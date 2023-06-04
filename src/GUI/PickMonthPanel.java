package GUI;

import javax.swing.*;
import java.awt.*;

public class PickMonthPanel extends JPanel {

    JLabel lblMonth;

    JLabel lblYear;

    NumberRangeText month;

    NumberRangeText year;

    public PickMonthPanel(){
        initialise();
    }

    private void initialise() {

        this.setLayout(null);
        this.setSize(new Dimension(120,30));

        lblMonth = new JLabel("Month");
        lblMonth.setBounds(5,5,49,20);

        month = new NumberRangeText(1,12);
        month.setText("1");
        month.setBounds(5,25,49,20);

        lblYear = new JLabel("Month");
        lblYear.setBounds(50,5,49,20);

        year = new NumberRangeText(2000,2030);
        year.setText("2023");
        year.setBounds(50,25,49,20);

    }

    public String getMonth() {
        return month.getText();
    }

    public String getYear() {
        return year.getText();
    }
}
