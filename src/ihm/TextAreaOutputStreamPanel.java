package ihm;

import java.awt.BorderLayout;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TextAreaOutputStreamPanel extends JPanel {

   private JTextArea textArea = new JTextArea(15, 30);
   private TextAreaOutputStream taOutputStream = new TextAreaOutputStream(textArea, "bioProject");

   public TextAreaOutputStreamPanel() {
      setLayout(new BorderLayout());
      add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
      try {
    	  System.setOut(new PrintStream(taOutputStream));
      } catch (Exception e) {
    	  System.out.println(e);
    	  e.printStackTrace();
      }
   }
}