package Birds;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Resultat extends JFrame implements ActionListener {
	// Fenêtre résultat venant s'afficher lorsque la fin du jeu (non mouvement de tous les objets) est détectée
	public Resultat(boolean win) {
		super("Résultat");
		ImageIcon icon =new ImageIcon((getClass().getResource("/resources/angabii.png")));
		setIconImage(icon.getImage());
		setSize(226,250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(450,250);
		setVisible(true);
		setResizable(false);
		toFront();
		setAlwaysOnTop(true);
		JLabel background;
		if(win) {
			background=new JLabel(new ImageIcon(getClass().getResource("/resources/win.png")));
		}else {
			background=new JLabel(new ImageIcon(getClass().getResource("/resources/levelfailed1.png")));
		}
		add(background);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
