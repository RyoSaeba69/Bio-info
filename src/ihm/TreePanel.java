package ihm;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class TreePanel extends JPanel {

	private JTree arbre;
	private DefaultMutableTreeNode racine;
	
	public TreePanel() {
		setLayout(new BorderLayout());
		listRoot();
	}
	
	private void listRoot(){      
	    this.racine = new DefaultMutableTreeNode();       
	    for(File file : File.listRoots()){
	      DefaultMutableTreeNode lecteur = 
	      new DefaultMutableTreeNode(file.getAbsolutePath());
	      try {
	        for(File nom : file.listFiles()){
	          DefaultMutableTreeNode node = new DefaultMutableTreeNode(nom.getName()+"\\");               
	          lecteur.add(this.listFile(nom, node));               
	        }
	      } catch (NullPointerException e) {}

	      this.racine.add(lecteur);                 
	    }
	    //Nous créons, avec notre hiérarchie, un arbre
	    arbre = new JTree(this.racine);
	    arbre.setRootVisible(false);
	    //Que nous plaçons sur le ContentPane de notre JPanel à l'aide d'un scroll 
	    add(new JScrollPane(arbre));
	  }

	  private DefaultMutableTreeNode listFile(File file, DefaultMutableTreeNode node){
	    int count = 0;
	      
	    if(file.isFile())
	      return new DefaultMutableTreeNode(file.getName());
	    else{
	      File[] list = file.listFiles();
	      if(list == null)
	        return new DefaultMutableTreeNode(file.getName());

	      for(File nom : list){
	        count++;
	        //Pas plus de 5 enfants par noeud
	        if(count < 5){
	          DefaultMutableTreeNode subNode;
	          if(nom.isDirectory()){
	            subNode = new DefaultMutableTreeNode(nom.getName()+"\\");
	            node.add(this.listFile(nom, subNode));
	          }else{
	            subNode = new DefaultMutableTreeNode(nom.getName());
	          }
	          node.add(subNode);
	        }
	      }
	      return node;
	    }
	  }

	public JTree getArbre() {
		return arbre;
	}

	public void setArbre(JTree arbre) {
		this.arbre = arbre;
	}
}
