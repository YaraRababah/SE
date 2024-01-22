
package javaapplication15;
import javax.swing.*;



public class TestBook {
   public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BookStoreFrame().setVisible(true);
            }
        });
    } 
}
