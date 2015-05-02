package ihm;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import controllers.FileController;

@SuppressWarnings("serial")
public class FileButton extends JButton implements MouseListener {

	public FileButton() {
		this.setText("Choisir un dossier");
		
		this.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JFileChooser dialogue = new JFileChooser(new File("."));
	    dialogue.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		if (dialogue.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
		    FileController.setFichier(dialogue.getSelectedFile());
		}
		
		bioProject.bioProject.ihm.setVisible(false);
		bioProject.bioProject.ihm = new MainFenetre();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
