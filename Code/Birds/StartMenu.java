package Birds;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class StartMenu extends JFrame implements ActionListener {

	// Attributs

	private static final long serialVersionUID = 1L;


	private JButton B1;
	private JButton B2;
	private JButton B3;
	private JButton B4;
	private JButton B1Options = newButton("Retour");
	private JLabel T1Options  = newLabel("Gravité (en G) :");
	private JTextField T2Options;
	public JButton B1Niveau = newButton("Niveau 1");
	public JButton B2Niveau = newButton("Niveau 2");
	public JButton B3Niveau = newButton("Niveau 3");
	public JButton B4Niveau = newButton("Niveau 4");
	public JButton B5Niveau = newButton("Niveau 5");
	public JButton B6Niveau = newButton("Niveau 6");
	public JButton BRNiveau = newButton("Retour");
	private JButton B1Regles = newButton("Retour");
	private JButton VolumeButton;
	protected static float gvt=1;
	protected Sound smenu = new Sound(getClass().getResource("/resources/Accueil.wav"));
	private JLabel background;
	private ImageIcon volume;
	private ImageIcon volumew;
	private ImageIcon volumef;
	private ImageIcon volumefw;
	private ImageIcon B1Image1;
	private ImageIcon B1Image1bis;
	private ImageIcon B2Image1;
	private ImageIcon B2Image1bis;
	private ImageIcon B3Image1;
	private ImageIcon B3Image1bis;
	private ImageIcon B4Image1;
	private ImageIcon B4Image1bis;
	private JLabel titreOptions = newLabel("Options");
	private JLabel tailleLabel = newLabel("Taille de l'oiseau :");
	private JLabel tailleLabel0 = newLabel("Petit");
	private JLabel tailleLabel1 = newLabel("Moyen");
	private JLabel tailleLabel2 = newLabel("Grand");
	private JLabel poidsLabel = newLabel("Poids de l'oiseau :");
	private JLabel poidsLabel0 = newLabel("Léger");
	private JLabel poidsLabel1 = newLabel("Moyen");
	private JLabel poidsLabel2 = newLabel("Lourd");
	private JLabel typeLabel = newLabel("Type de structure :");
	private JLabel typeLabel0 = newLabel("Paille");
	private JLabel typeLabel1 = newLabel("Bois");
	private JLabel typeLabel2 = newLabel("Béton");
	JRadioButton optionTaille0 = new JRadioButton();
	JRadioButton optionTaille1 = new JRadioButton();
	JRadioButton optionTaille2 = new JRadioButton();
	JRadioButton optionPoids0 = new JRadioButton();
	JRadioButton optionPoids1 = new JRadioButton();
	JRadioButton optionPoids2 = new JRadioButton();
	JRadioButton optionType0 = new JRadioButton();
	JRadioButton optionType1 = new JRadioButton();
	JRadioButton optionType2 = new JRadioButton();
	ButtonGroup groupeTaille = new ButtonGroup();
	ButtonGroup groupeType = new ButtonGroup();
	ButtonGroup groupePoids = new ButtonGroup();
	private JLabel titleRegles = newLabel("Règles :");
	private JLabel L1Regles = newLabel("Le but d'Angry Birds est d'utiliser un lance-pierre ");
	private JLabel L2Regles = newLabel("pour envoyer un oiseau sur des structures contenant");
	private JLabel L3Regles = newLabel("des ennemis, et en éliminer un maximum !");
	private JLabel L4Regles = newLabel("Pour jouer, il suffit de lancer un niveau, de cliquer");
	private JLabel L5Regles = newLabel("sur l'oiseau, de le tirer en arrière, puis");
	private JLabel L6Regles = newLabel("puis celui-ci sera lancé en relachant la souris.");
	private JLabel L7Regles = newLabel("Pour passer un niveau, il suffit d'éliminer l'ensemble");
	private JLabel L8Regles = newLabel("des ennemis.");
	private JLabel L9Regles = newLabel("Bonne chance !");
	private JLabel titleInfos = newLabel("Informations");
	private JLabel L1Info = newLabel("En accédant au menu Options, il est possible");
	private JLabel L2Info = newLabel("de customizer ce jeu comme bon vous semble:");
	private JLabel L3Info = newLabel("Gravité : change la gravité pour l'ensemble des objets");
	private JLabel L4Info = newLabel("Taille: Change la taille et le poids de l'oiseau");
	private JLabel L5Info = newLabel("Type: Change la densité des structures");
	private JLabel L6Info = newLabel("Poids: Change la densité de l'oiseau");
	private JLabel L7Info = newLabel("Jeu réalisé pour un projet 2A GRP 46");
	public boolean soundOn = true;


	public StartMenu() { // Menu initial + choix niveaux

		super();
		setSize(1000, 564);
		setLocation(100,200);
		setResizable(false);
		ImageIcon icon =new ImageIcon(getClass().getResource("/resources/angabii.png"));
		setIconImage(icon.getImage());
		if (soundOn)
		smenu.loop();

		B1 = new JButton();
		B2 = new JButton();
		B3 = new JButton();
		B4 = new JButton();

		B1.addActionListener (this);
		B2.addActionListener (this);
		B3.addActionListener (this);
		B4.addActionListener (this);
		B1Options.addActionListener (this);

		B1Niveau.addActionListener (this);
		B2Niveau.addActionListener (this);
		B3Niveau.addActionListener (this);
		B4Niveau.addActionListener (this);
		B5Niveau.addActionListener (this);
		B6Niveau.addActionListener (this);
		BRNiveau.addActionListener (this);
		B1Regles.addActionListener (this);


		setUp();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed (ActionEvent e){

		Sound s=new Sound(getClass().getResource("/resources/bouton.wav"));

		// Bouton de choix de niveau

		if(e.getSource()==B1) {
			if(soundOn)
			s.play();
			getContentPane().removeAll();
			repaint();
			setTitle("Choix niveau");
			JLabel background1=new JLabel(new ImageIcon(getClass().getResource("/resources/backgroundNiveau.jpg")));
			add(background1);
			background1.setBounds(0,0,1000,564);

			B1Niveau.setBounds(80,100,100,50);
			background1.add(B1Niveau);

			B2Niveau.setBounds(220,100,100,50);
			background1.add(B2Niveau);

			B3Niveau.setBounds(80,200,100,50);
			background1.add(B3Niveau);

			B4Niveau.setBounds(220,200,100,50);
			background1.add(B4Niveau);

			B5Niveau.setBounds(80,300,100,50);
			background1.add(B5Niveau);

			B6Niveau.setBounds(220,300,100,50);
			background1.add(B6Niveau);



			BRNiveau.setBounds(150,400,100,50);
			background1.add(BRNiveau);
		}

		// Bouton des options

		if(e.getSource()==B2) {
			if (soundOn)
			s.play();
			getContentPane().removeAll();
			repaint();
			setTitle("Options");

			background=new JLabel(new ImageIcon(getClass().getResource("/resources/options.png")));

			background.setBounds(0,0,1000,564);

			T1Options.setBounds(320,108,100,50);
			background.add(T1Options);

			T2Options= new JTextField(String.valueOf(gvt));
			T2Options.setBounds(450,120,80,25);
			background.add(T2Options);

			B1Options.setBounds(440,420,100,50);

			background.add(B1Options);

			titreOptions.setFont(new Font("Tahoma", Font.BOLD, 40));
			titreOptions.setBounds(400, 10, 200, 100);

			tailleLabel.setBounds(250,172,120,50);
			optionTaille0.setBounds(410,175,20,20);
			optionTaille1.setBounds(490,175,20,20);
			optionTaille2.setBounds(570,175,20,20);
			tailleLabel0.setBounds(404,195,60,40);
			tailleLabel1.setBounds(482,195,60,40);
			tailleLabel2.setBounds(562,195,60,40);

			typeLabel.setBounds(238,252,140,50);
			optionType0.setBounds(410,255,20,20);
			optionType1.setBounds(490,255,20,20);
			optionType2.setBounds(570,255,20,20);
			typeLabel0.setBounds(404,275,60,40);
			typeLabel1.setBounds(482,275,60,40);
			typeLabel2.setBounds(562,275,60,40);

			poidsLabel.setBounds(250,332,120,50);
			optionPoids0.setBounds(410,335,20,20);
			optionPoids1.setBounds(490,335,20,20);
			optionPoids2.setBounds(570,335,20,20);
			poidsLabel0.setBounds(404,355,60,40);
			poidsLabel1.setBounds(482,355,60,40);
			poidsLabel2.setBounds(562,355,60,40);

			optionTaille0.setBackground(new Color(0,0,255));
			optionTaille1.setBackground(new Color(0,0,255));
			optionTaille2.setBackground(new Color(0,0,255));

			optionPoids0.setBackground(new Color(0,0,255));
			optionPoids1.setBackground(new Color(0,0,255));
			optionPoids2.setBackground(new Color(0,0,255));

			optionType0.setBackground(new Color(0,0,255));
			optionType1.setBackground(new Color(0,0,255));
			optionType2.setBackground(new Color(0,0,255));

			/*optionTaille2.setSelected(true);
			optionType0.setSelected(true);
			optionPoids1.setSelected(true);*/ //Presets

			background.add(tailleLabel);
			background.add(tailleLabel0);
			background.add(tailleLabel1);
			background.add(tailleLabel2);

			background.add(poidsLabel);
			background.add(poidsLabel0);
			background.add(poidsLabel1);
			background.add(poidsLabel2);

			background.add(typeLabel);
			background.add(typeLabel0);
			background.add(typeLabel1);
			background.add(typeLabel2);

			background.add(optionTaille0);
			background.add(optionTaille1);
			background.add(optionTaille2);

			background.add(optionType0);
			background.add(optionType1);
			background.add(optionType2);

			background.add(optionPoids0);
			background.add(optionPoids1);
			background.add(optionPoids2);

			groupeTaille.add(optionTaille0);
			groupeTaille.add(optionTaille1);
			groupeTaille.add(optionTaille2);

			groupeType.add(optionType0);
			groupeType.add(optionType1);
			groupeType.add(optionType2);

			groupePoids.add(optionPoids0);
			groupePoids.add(optionPoids1);
			groupePoids.add(optionPoids2);

			background.add(titreOptions);

			add(background);




		}

		// Bouton de retour dans les options

		if (e.getSource()==B1Options) {
			if (soundOn)
			s.play();
			gvt = Float.parseFloat(T2Options.getText());
			T2Options.setText(T2Options.getText());
			getContentPane().removeAll();
			repaint();

			setUp();



		}

		// Bouton pour lancer le niveau 1

		if (e.getSource()==B1Niveau) {
			smenu.stop();
			BirdWorld p = new BirdWorld(1); 
			BirdGraphics g = new BirdGraphics(p,1); 
			g.worldGraphics = g;
			g.afficherPlateau(p);
			p.m.SG.setVisible(false);
		}
		// Bouton pour lancer le niveau 2
		
		if (e.getSource()==B2Niveau) {
			smenu.stop();
			BirdWorld p = new BirdWorld(2); 
			BirdGraphics g = new BirdGraphics(p,2); 
			g.worldGraphics = g;
			g.afficherPlateau(p);
			p.m.SG.setVisible(false);
		}
		
		// Bouton pour lancer le niveau 3
		if (e.getSource()==B3Niveau) {
			smenu.stop();
			BirdWorld p = new BirdWorld(3); 
			BirdGraphics g = new BirdGraphics(p,3); 
			g.worldGraphics = g;
			g.afficherPlateau(p);
			p.m.SG.setVisible(false);
		}
		//...
		if (e.getSource()==B4Niveau) {
			smenu.stop();
			BirdWorld p = new BirdWorld(4); 
			BirdGraphics g = new BirdGraphics(p,4); 
			g.worldGraphics = g;
			g.afficherPlateau(p);
			p.m.SG.setVisible(false);
		}
		//...
		if (e.getSource()==B5Niveau) {
			smenu.stop();
			BirdWorld p = new BirdWorld(5); 
			BirdGraphics g = new BirdGraphics(p,5); 
			g.worldGraphics = g;
			g.afficherPlateau(p);
			p.m.SG.setVisible(false);
		}
		//...
		if (e.getSource()==B6Niveau) {
			smenu.stop();
			BirdWorld p = new BirdWorld(6); 
			BirdGraphics g = new BirdGraphics(p,6); 
			g.worldGraphics = g;
			g.afficherPlateau(p);
			p.m.SG.setVisible(false);
		}

		// Bouton de retour dans les niveaux

		if 	(e.getSource()==BRNiveau) {
			if (soundOn)
			s.play();
			getContentPane().removeAll();
			repaint();

			setUp();

		}

		// Bouton pour aller dans les règles

		if(e.getSource()==B3) {
			if (soundOn)
			s.play();
			setTitle("Règles");
			getContentPane().removeAll();
			repaint();
			JLabel background2=new JLabel(new ImageIcon(getClass().getResource("/resources/regles.png")));
			add(background2);
			background2.setBounds(0,0,1000,564);

			B1Regles.setBounds(150,400,100,50);

			background2.add(B1Regles);

			titleRegles.setBounds(150,20,200,100);
			titleRegles.setFont(new Font("Tahoma", Font.BOLD, 40));
			background2.add(titleRegles);

			L1Regles.setBounds(75,100,350,100);
			background2.add(L1Regles);
			L2Regles.setBounds(75,120,350,100);
			background2.add(L2Regles);
			L3Regles.setBounds(75,140,350,100);
			background2.add(L3Regles);
			L4Regles.setBounds(75,160,350,100);
			background2.add(L4Regles);
			L5Regles.setBounds(75,180,350,100);
			background2.add(L5Regles);
			L6Regles.setBounds(75,200,350,100);
			background2.add(L6Regles);
			L7Regles.setBounds(75,220,400,100);
			background2.add(L7Regles);
			L8Regles.setBounds(75,240,400,100);
			background2.add(L8Regles);
			L9Regles.setBounds(75,280,400,100);
			background2.add(L9Regles);



		}

		// Bouton de retour dans les règles

		if (e.getSource()==B1Regles) {
			if (soundOn)
			s.play();
			getContentPane().removeAll();
			repaint();

			setUp();
		}


		// Bouton pour aller dans les règles

		if(e.getSource()==B4) {
			if (soundOn)
			s.play();
			setTitle("Informations");
			getContentPane().removeAll();
			repaint();
			JLabel background2=new JLabel(new ImageIcon(getClass().getResource("/resources/regles.png")));
			add(background2);
			background2.setBounds(0,0,1000,564);

			B1Regles.setBounds(150,400,100,50);

			background2.add(B1Regles);

			titleInfos.setBounds(100,40,400,100);
			titleInfos.setFont(new Font("Tahoma", Font.BOLD, 40));
			background2.add(titleInfos);

			L1Info.setBounds(75,100,350,100);
			background2.add(L1Info);
			L2Info.setBounds(75,120,350,100);
			background2.add(L2Info);
			L3Info.setBounds(75,150,400,100);
			background2.add(L3Info);
			L4Info.setBounds(75,180,400,100);
			background2.add(L4Info);
			L5Info.setBounds(75,210,400,100);
			background2.add(L5Info);
			L6Info.setBounds(75,240,400,100);
			background2.add(L6Info);
			L7Info.setBounds(75,300,400,100);
			background2.add(L7Info);

		}

		// Bouton pour enlever le son

		if (e.getSource()==VolumeButton) {
			if (soundOn)
			s.play();
			if (VolumeButton.getIcon()==volumew || VolumeButton.getIcon()==volume) {
				VolumeButton.setIcon(volumef);
				VolumeButton.setRolloverIcon(volumefw);
				VolumeButton.setPressedIcon(volumefw);
				smenu.stop();
				soundOn=false;

			}
			else if (VolumeButton.getIcon()==volumef || VolumeButton.getIcon()==volumefw) {
				VolumeButton.setIcon(volume);
				VolumeButton.setRolloverIcon(volumew);
				VolumeButton.setPressedIcon(volumew);
				smenu.play();
				soundOn=true;
			}
			VolumeButton.repaint();

		}

	}
	public static float getGvt () {
		return gvt;
	}
	public void setUp() { // Setup de base du menu principale
		setTitle("Angry birds");
		if (soundOn)
		smenu.loop();
		setLayout(null);
		volume = new ImageIcon(getClass().getResource("/resources/soundonn.png"));
		volumew = new ImageIcon(getClass().getResource("/resources/soundonnw.png"));
		volumef = new ImageIcon(getClass().getResource("/resources/soundoff.png"));
		volumefw = new ImageIcon(getClass().getResource("/resources/soundoffw.png"));
		B1Image1 = new ImageIcon(getClass().getResource("/resources/startButton.png"));
		B1Image1bis = new ImageIcon(getClass().getResource("/resources/startButton1.png"));
		B2Image1 = new ImageIcon(getClass().getResource("/resources/optionsButton1.png"));
		B2Image1bis = new ImageIcon(getClass().getResource("/resources/optionsButton2.png"));
		B3Image1 = new ImageIcon(getClass().getResource("/resources/reglesButton.png"));
		B3Image1bis = new ImageIcon(getClass().getResource("/resources/reglesButton1.png"));
		B4Image1 = new ImageIcon(getClass().getResource("/resources/infoButton.png"));
		B4Image1bis = new ImageIcon(getClass().getResource("/resources/infoButton1.png"));



		JLabel background=new JLabel(new ImageIcon(getClass().getResource("/resources/ecran-accueil.png")));
		add(background);
		background.setBounds(0,0,1000,564);


		B1.setBounds(200,200,250,100);
		B1.setBorder(null);
		B1.setContentAreaFilled(false);
		B1.setIcon(B1Image1);
		B1.setRolloverIcon(B1Image1bis);
		B1.setPressedIcon(B1Image1bis);
		B1.setFocusPainted(false);

		background.add(B1);


		B2.setBounds(480,200,350,100);
		B2.setBorder(null);
		B2.setContentAreaFilled(false);
		B2.setIcon(B2Image1);
		B2.setRolloverIcon(B2Image1bis);
		B2.setPressedIcon(B2Image1bis);
		B2.setFocusPainted(false);

		background.add(B2);


		B3.setBounds(190,340,300,100);
		B3.setBorder(null);
		B3.setContentAreaFilled(false);
		B3.setIcon(B3Image1);
		B3.setRolloverIcon(B3Image1bis);
		B3.setPressedIcon(B3Image1bis);
		B3.setFocusPainted(false);

		background.add(B3);

		B4.setBounds(510,340,300,100);
		B4.setBorder(null);
		B4.setContentAreaFilled(false);
		B4.setIcon(B4Image1);
		B4.setRolloverIcon(B4Image1bis);
		B4.setPressedIcon(B4Image1bis);
		B4.setFocusPainted(false);

		background.add(B4);

		VolumeButton = new JButton();
		VolumeButton.setBounds(840,350,120,150);
		VolumeButton.setBorder(null);
		VolumeButton.setContentAreaFilled(false);
		VolumeButton.setIcon(volume);
		VolumeButton.setRolloverIcon(volumew);
		VolumeButton.setPressedIcon(volumew);
		VolumeButton.setFocusPainted(false);
		VolumeButton.addActionListener (this);
		background.add(VolumeButton);
		if (!soundOn)
			VolumeButton.setIcon(volumef);

			
	}

	// Créateur de boutons

	public static JButton newButton(String Titre) {

		JButton jb = new JButton(Titre);	
		Color c = new Color(25);
		jb.setBackground(Color.WHITE);
		jb.setForeground(c);
		Border borderbutt1 = BorderFactory.createLineBorder(c);
		jb.setBorder(borderbutt1);
		jb.setFocusPainted(false);
		jb.setFont(new Font("Tahoma", Font.BOLD, 18));

		return jb;
	}

	// Créateur de labels

	public static JLabel newLabel(String Titre) {

		JLabel jl = new JLabel(Titre);	
		jl.setBackground(Color.WHITE);
		jl.setForeground(Color.BLACK);
		jl.setFont(new Font("Tahoma", Font.BOLD, 13));

		return jl;
	}
}
