package ihm;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import bioProject.bioProject;

public class MainFenetre extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JSplitPane split;

	public MainFenetre() {
		this.setTitle(bioProject.appName + " - " + bioProject.appDescription + " v" + bioProject.appVersion);
	    this.setSize(1200, 500);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    
	    JPanel container = new JPanel();
	    container.setLayout(new BorderLayout());

	    JPanel panel1 = new ProgressBarPanel();
	    panel1.setBackground(Color.white);
	    JPanel panel2 = new TextAreaOutputStreamPanel();
	    panel2.setBackground(Color.white);
	    
	    container.add(panel1, BorderLayout.PAGE_END);
	    container.add(panel2, BorderLayout.CENTER);
	    
	    
	    //On cr�e deux conteneurs de couleurs diff�rentes
	    JPanel pan = new TreePanel();
	    pan.setBackground(Color.white);
	    //On passe les deux pr�c�dents JSplitPane � celui-ci
	    split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pan, container);
	    //On place le troisi�me s�parateur
	    split.setDividerLocation(300);
	
	    //On le passe ensuite au content pane de notre objet Fenetre
	    //plac� au centre pour qu'il utilise tout l'espace disponible
	    this.getContentPane().add(split, BorderLayout.CENTER);
	    
	    try {
	    	  //On force � utiliser le � look and feel � du syst�me
	    	  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    	  //Ici on force tous les composants de notre fen�tre (this) � se redessiner avec le � look and feel � du syst�me
	    	  SwingUtilities.updateComponentTreeUI(this);
	    	}
	    	catch (InstantiationException e) {System.out.println(e);}
	    	catch (ClassNotFoundException e) {System.out.println(e);}
	    	catch (UnsupportedLookAndFeelException e) {System.out.println(e);}
	    	catch (IllegalAccessException e) {System.out.println(e);}
	    
	    this.setVisible(true);
	}
}
