package ihm;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class ProgressBarPanel extends JPanel {

	private static JProgressBar progressBar;
	
	public ProgressBarPanel() {
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setStringPainted(true);
		
		setLayout(new BorderLayout());
		add(progressBar);
	}
	
	public static void setProgressBarValue(int val) {
		progressBar.setValue(val);
	}
	
	public static void setMaximumProgressBar(int max) {
		progressBar.setMaximum(max);
	}
}
