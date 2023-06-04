package GUI;
import javax.swing.*;
import javax.swing.text.*;

public class NumberRangeText extends JTextField {
    public NumberRangeText() {
        this(1,12);
    }

    public NumberRangeText(int start , int end) {
        super();
        ((AbstractDocument) getDocument()).setDocumentFilter(new IntFilter(start, end));
    }
}

