package ch.epfl.xblast.menu;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * An ip filter that allows only numbers and '.' in the field
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class IpFilter extends DocumentFilter{
    @Override
    public void insertString(DocumentFilter.FilterBypass bypass, int offset, String str, AttributeSet set)
            throws BadLocationException {
        int l = str.length();
        boolean valid = true;
               
        for (int i = 0; i < l; i++){
            if (!(str.charAt(i)==46 || (str.charAt(i)>=48 && str.charAt(i)<=57))) {
                //allows only the numbers and the '.'
                valid = false;
                break;
                
            }
        }
        if (valid)
            super.insertString(bypass, offset, str, set);
        else
            Toolkit.getDefaultToolkit().beep();
    }

    @Override
    public void replace(DocumentFilter.FilterBypass bypass, int offset, int length, String str, AttributeSet set)
            throws BadLocationException {
        int l = str.length();
        boolean valid = true;
               
        for (int i = 0; i < l; i++){
            if (!(str.charAt(i)==46 || (str.charAt(i)>=48 && str.charAt(i)<=57))) {
                //allows only the numbers and the '.'
                valid = false;
                break;
                
            }
        }
        if (valid)
            super.replace(bypass, offset, length, str, set);
        else
            Toolkit.getDefaultToolkit().beep();
    }
}
