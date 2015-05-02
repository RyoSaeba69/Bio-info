package ihm;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class SysTrayIcon {

	public SysTrayIcon() {
		createAndShowGUI();
	}
	
	private static void createAndShowGUI() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(createImage("./images/bulb.gif"));
        final SystemTray tray = SystemTray.getSystemTray();
         
        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        MenuItem exitItem = new MenuItem("Exit");
         
        //Add components to popup menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(exitItem);
         
        trayIcon.setPopupMenu(popup);
         
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
         
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This dialog box is run from System Tray");
            }
        });
         
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Nom de l'application : "+bioProject.bioProject.appName + "\nDescription : " + 
                bioProject.bioProject.appDescription + "\nVersion : " + 
                        		bioProject.bioProject.appVersion + "\nCree par : " + bioProject.bioProject.appDevs, 
                        		"A propos", JOptionPane.INFORMATION_MESSAGE);
            }
        });
         
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }
     
    //Obtain the image URL
    protected static Image createImage(String path) {
    	ImageIcon res = new ImageIcon(path);
        
        return res.getImage();
    }
}
