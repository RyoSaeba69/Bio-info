package ihm;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {

	private JButton fichier = new FileButton();
	private JButton lancer = new LancerButton();
	
	public MenuPanel() {
		this.setLayout(new GridLayout(2,1, 10, 10));
		add(fichier);
		add(lancer);
		
		setBackground(Color.white);
	}
}
