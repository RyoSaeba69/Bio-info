package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import bioProject.bioProject;
import controllers.FileController;

public class MainFenetre extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JSplitPane split;
	
	private JPanel progressBar;
	private static JPanel TreeArborescence;

	private JPanel console;

	public MainFenetre() {
		this.setTitle(bioProject.appName + " - " + bioProject.appDescription + " v" + bioProject.appVersion);
	    this.setSize(1200, 500);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    
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
	    
	    JFileChooser dialogue = new JFileChooser(new File("."));
	    dialogue.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		if (dialogue.showSaveDialog(null)== 
		    JFileChooser.APPROVE_OPTION) {
		    FileController.setFichier(dialogue.getSelectedFile());
		}
	    
	    JPanel container = new JPanel();
	    container.setLayout(new BorderLayout());

	    progressBar = new ProgressBarPanel();
	    progressBar.setBackground(Color.white);
	    console = new TextAreaOutputStreamPanel();
	    console.setBackground(Color.white);
	    
	    container.add(progressBar, BorderLayout.PAGE_END);
	    container.add(console, BorderLayout.CENTER);
	    
	    
	    //On cr�e deux conteneurs de couleurs diff�rentes
	    TreeArborescence = new TreePanel();
	    TreeArborescence.setBackground(Color.white);
	    //On passe les deux pr�c�dents JSplitPane � celui-ci
	    split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TreeArborescence, container);
	    //On place le troisi�me s�parateur
	    split.setDividerLocation(300);
	
	    //On le passe ensuite au content pane de notre objet Fenetre
	    //plac� au centre pour qu'il utilise tout l'espace disponible
	    this.getContentPane().add(split, BorderLayout.CENTER);
		
	    this.setVisible(true);
	}
	
	public void refreshTree() {
		TreeArborescence = new TreePanel();
		TreeArborescence.updateUI();
		split.updateUI();
	}
	
	public static JPanel getTreeArborescence() {
		return TreeArborescence;
	}

	public static void setTreeArborescence(JPanel treeArborescence) {
		TreeArborescence = treeArborescence;
	}
}
