package GUI;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;


public class IntFilter extends DocumentFilter {
    private int min;
    private int max;

    public IntFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        int currentValue = 0;
        try {
            currentValue = Integer.parseInt(fb.getDocument().getText(0, fb.getDocument().getLength()));
        } catch (NumberFormatException e) {
            // ignore
        }
        int newValue = currentValue * 10 + Integer.parseInt(string);
        if (newValue >= min && newValue <= max) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text.matches("^[0-9]{1,2}$")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}