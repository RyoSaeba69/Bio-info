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
	private JSplitPane split2;
	private JPanel container;
	private JPanel menu;
	private JPanel progressBar;
	private JPanel TreeArborescence;
	private JPanel console;

	public MainFenetre() {
		this.setTitle(bioProject.appName + " - " + bioProject.appDescription + " v" + bioProject.appVersion);
	    this.setSize(1200, 700);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    
	    try {
	    	  //On force à utiliser le « look and feel » du système
	    	  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    	  //Ici on force tous les composants de notre fenêtre (this) à se redessiner avec le « look and feel » du système
	    	  SwingUtilities.updateComponentTreeUI(this);
	    	}
	    	catch (InstantiationException e) {System.out.println(e);}
	    	catch (ClassNotFoundException e) {System.out.println(e);}
	    	catch (UnsupportedLookAndFeelException e) {System.out.println(e);}
	    	catch (IllegalAccessException e) {System.out.println(e);}
	    
	    container = new JPanel();
	    container.setLayout(new BorderLayout());

	    menu = new MenuPanel();
	    
	    progressBar = new ProgressBarPanel();
	    progressBar.setBackground(Color.white);
	    console = new TextAreaOutputStreamPanel();
	    console.setBackground(Color.white);
	    
	    container.add(progressBar, BorderLayout.PAGE_END);
	    container.add(console, BorderLayout.CENTER);
	    
	    
	    TreeArborescence = new TreePanel();
	    TreeArborescence.setBackground(Color.white);
	    
	    split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menu, TreeArborescence);
	    split2.setDividerLocation(300);
	    
	    //On passe les deux précédents JSplitPane à celui-ci
	    split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split2, container);
	    //On place le troisième séparateur
	    split.setDividerLocation(200);
	
	    //On le passe ensuite au content pane de notre objet Fenetre
	    //placé au centre pour qu'il utilise tout l'espace disponible
	    this.getContentPane().add(split, BorderLayout.CENTER);
		
	    this.setVisible(true);
	}
	
	public void refreshTree() {
	}
	
	public JPanel getTreeArborescence() {
		return this.TreeArborescence;
	}

	public void setTreeArborescence(JPanel treeArborescence) {
		this.TreeArborescence = treeArborescence;
	}
	
	public JSplitPane getSplit() {
		return split;
	}

	public void setSplit(JSplitPane split) {
		this.split = split;
	}

	public JPanel getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JPanel progressBar) {
		this.progressBar = progressBar;
	}

	public JPanel getConsole() {
		return console;
	}

	public void setConsole(JPanel console) {
		this.console = console;
	}
	
	public JSplitPane getSplit2() {
		return split2;
	}

	public void setSplit2(JSplitPane split2) {
		this.split2 = split2;
	}
}
