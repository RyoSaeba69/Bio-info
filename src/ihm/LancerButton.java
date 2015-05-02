package ihm;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import controllers.FileController;
import tasks.ThreadManager;

@SuppressWarnings("serial")
public class LancerButton extends JButton implements MouseListener {

	public LancerButton() {
		this.setText("Lancement");
		
		this.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(FileController.getFichier()!=null) {
			this.setEnabled(false);
			ThreadManager.startAllTasks();
		} else {
			System.out.println("Veuillez selectionner un dossier d'enregistrement des donnees...");
		}
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
