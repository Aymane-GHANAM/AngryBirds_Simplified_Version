package Birds;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;

// Classe graphics qui gère l'affichage du jeu

public class BirdGraphics extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	public BirdGraphics worldGraphics;
	public Niveau pj; // Panel sur lequel on draw le jeu
	public BirdWorld board;
	private Timer t;
	int c1=0; // Compteur de click
	int c=0; // Compteur de relachement de click
	int mf=0; // monde fini, si plus aucun mouvement --> mf=1
	private JButton B1; // Bouton "quitter" (la croix)
	private JButton B2; // Bouton "restart" (symbole restart)
	protected Sound sgame = new Sound(getClass().getResource("/resources/theme.wav"));
	protected Sound sLancer = new Sound(getClass().getResource("/resources/15.wav"));
	public String scoreString = "Score: 0.0"; // Score
	int Niveau; // Choix du niveau
	public Resultat R;
	int tps=0; // Temps d'attente avant affichage de la fenêtre résultat

	public BirdGraphics(BirdWorld p, int Niveau) { // Fenêtre principale du jeu, gameplay
		super("Angry Birds"); 
		pj = new Niveau(p); // création du niveau choisi
		board = p;
		add(pj); 
		setSize(1000,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(50,15);
		setResizable(false);
		setVisible(true);
		getLocationOnScreen();
		setResizable(false);
		if (board.m.SG.soundOn)
			sgame.loop();
		ImageIcon icon =new ImageIcon(getClass().getResource("/resources/angabii.png"));
		setIconImage(icon.getImage());

		setLayout(null);
		B1 = new JButton();
		B1.setBounds(0,0,70,70);
		B1.addActionListener (this);

		B1.setOpaque(false);
		B1.setContentAreaFilled(false);
		B1.setBorderPainted(false);
		add(B1);

		B2 = new JButton();
		B2.setBounds(930,0,70,70);
		B2.addActionListener (this);
		B2.setOpaque(false);
		B2.setContentAreaFilled(false);
		B2.setBorderPainted(false);
		add(B2);

		this.Niveau = Niveau;

		// Actions dans le timer

		ActionListener move = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (c1==0) { // Premiere itération, on fait apparaitre les objets
					afficherPlateau(p);	
				}
				if (c1==1 && c==0) {// Si l'utilisateur clique le bird suit sa souris
					if (MouseInfo.getPointerInfo().getLocation().x-63>=450) { // limiter le lancement sur la droite pour éviter que l'utilisateur puisse tirer l'oiseau n'importe où directement dans les structures 
						board.initX=450;
					}
					else if (MouseInfo.getPointerInfo().getLocation().x-63*1.5<=0) { // limiter le lancement sur le bord gauche
						board.initX=63/2;
					}
					else {
						board.initX=MouseInfo.getPointerInfo().getLocation().x-63;

					}
					if (MouseInfo.getPointerInfo().getLocation().y-63>=650) {// limiter le lancement en bas
						board.initY=650;
					}
					else if (MouseInfo.getPointerInfo().getLocation().y-63*1.5<=0) {// limiter le lancement en haut
						board.initY=63/2;
					}
					else {
						board.initY=MouseInfo.getPointerInfo().getLocation().y-63;

					}
					board.movePieceInit(board.initX,board.initY);
					afficherPlateau(p);	

				}
				if (c>0) {// Si l'utilisateur relache la souris, l'oiseau est lancé
					board.movePiece();
					afficherPlateau(p);	
					if (board.testend()&&mf==0) { // Affichage de la fenêtre resultat en cas de fin de niveau 
						tps+=1;
						if (tps>10) {
							mf=1;
							R = new Resultat(board.levelPassed[Niveau-1]);
						}
					}
				}


			}

		};

		t = new Timer(3, move);
		t.start();

		// Action lorsque l'utilisateur clique puis relache la souris
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				c1++;

			}
			public void mouseReleased(MouseEvent e) {
				if(c==0) { // Au premier click, on applique une force sur le bird qui le lance
					int x1=150;
					int y1=500;
					int x2=board.initX;
					int y2=board.initY;
					board.setForce(x1,x2,y1,y2);
					c++; 

				}
			}

		});

	}
	// Actions lors du click sur les boutons "Quitter" (la croix) et "Restart"(symbole restart) en haut de la fenêtre
	public void actionPerformed (ActionEvent e){
		if (e.getSource()==B1) { // Bouton Quitter
			if (R!=null && R.isVisible()==true){
				R.dispose();
			}
			sgame.stop(); // Arrêt du jeu (world du moteur physique)
			t.stop(); // Arrêt du timer
			// Affichage en vert du bouton du niveau lorsqu'il est réussie
			if (board.levelPassed[0]) {
				board.m.SG.B1Niveau.setBackground(Color.GREEN);
			}
			if (board.levelPassed[1]) {
				board.m.SG.B2Niveau.setBackground(Color.GREEN);
			}
			if (board.levelPassed[2]) {
				board.m.SG.B3Niveau.setBackground(Color.GREEN);
			}
			if (board.levelPassed[3]) {
				board.m.SG.B4Niveau.setBackground(Color.GREEN);
			}
			if (board.levelPassed[4]) {
				board.m.SG.B5Niveau.setBackground(Color.GREEN);
			}
			if (board.levelPassed[5]) {
				board.m.SG.B6Niveau.setBackground(Color.GREEN);
			}


			this.dispose();
			board.m.SG.setVisible(true); // Réaffichage du menu start 

		}
		if (e.getSource()==B2) { // Bouton Restart
			// Arrêt du jeu en cours
			if (R!=null && R.isVisible()==true){
				R.dispose();
			}
			t.stop();
			this.dispose();
			sgame.stop(); 
			// Relance un nouveau jeu avec ses paramètres initiaux
			BirdWorld board = new BirdWorld(Niveau); 
			worldGraphics = new BirdGraphics(board,Niveau); 
			worldGraphics.worldGraphics = worldGraphics;
			afficherPlateau(board);
			board.m.SG.setVisible(false);
			c=0;

		}
	}

	public void afficherPlateau(BirdWorld p) { 
		if (worldGraphics==null) // Si le monde n'est pas encore affiché
			worldGraphics = new BirdGraphics(p,Niveau);
		worldGraphics.repaint();
	}
	// Création des Niveaux
	class Niveau extends JPanel {

		private static final long serialVersionUID = 1L;
		private BufferedImage jeuImage;
		private BirdPig s;

		public Niveau(BirdWorld b) {
			jeuImage = new BufferedImage(1000,800,BufferedImage.TYPE_INT_RGB);
			setPreferredSize(new Dimension(1000,800));
			s=b.ab;


		}
		private void dessineMonde(Graphics g) {

			Toolkit t=Toolkit.getDefaultToolkit();

			Image y=t.getImage(getClass().getResource("/resources/angab.png"));
			g.drawImage(y,1,1,1,1,null);
			y=t.getImage(getClass().getResource("/resources/backab.png"));
			g.drawImage(y,0,0,1000,800,null);

			// On charge les images avant de lancer pour enlever du lag

			y=t.getImage(getClass().getResource("/resources/angab1.png"));
			g.drawImage(y,1,1,1,1,null);
			y=t.getImage(getClass().getResource("/resources/angab2.png"));
			g.drawImage(y,1,1,1,1,null);
			y=t.getImage(getClass().getResource("/resources/angabSmall.png"));
			g.drawImage(y,1,1,1,1,null);

			// On adapte le fond d'écran en fonction de la gravité (gvt)
			
			float gvt=StartMenu.getGvt();
			if (gvt>=1 && gvt<=2){
				y=t.getImage(getClass().getResource("/resources/backab.png"));
				g.drawImage(y,0,0,1000,800,null);
			}
			if (gvt>2){ 
				y=t.getImage(getClass().getResource("/resources/space.png"));
				g.drawImage(y,0,0,1000,800,null);
			}
			if (gvt<1){
				y=t.getImage(getClass().getResource("/resources/atm.png"));
				g.drawImage(y,0,0,1000,800,null);
			}

			y=t.getImage(getClass().getResource("/resources/grass ab.png"));
			g.drawImage(y,0,420,1100,400,null);
			y=t.getImage(getClass().getResource("/resources/slingshot1.png"));
			g.drawImage(y,80,450,130,330,null);
			y=t.getImage(getClass().getResource("/resources/croix.png"));
			g.drawImage(y,10,10,50,50,null);
			y=t.getImage(getClass().getResource("/resources/restart.png"));
			g.drawImage(y,930,10,50,50,null);

			// Affichage des objectifs, des indications pour gagner dans chaque Niveau
			g.setColor(Color.BLACK);
			g.setFont(new Font("Tahoma", Font.BOLD, 18));
			if (c<1) {
				if (board.Niveau == 1) {
					y=t.getImage(getClass().getResource("/resources/arrowdown.png"));
					g.drawImage(y,665,340,70,70,null);
					g.drawString("But: éliminer les 3 cochons", 580, 300);

				}
				if (board.Niveau == 2) {
					y=t.getImage(getClass().getResource("/resources/arrowdown.png"));
					g.drawImage(y,825,380,70,70,null);
					y=t.getImage(getClass().getResource("/resources/arrowupright.png"));
					g.drawImage(y,330,370,70,70,null);
					g.drawString("But: éliminer au minimum 2 cochons", 500, 200);
				}
				if (board.Niveau == 3) {
					y=t.getImage(getClass().getResource("/resources/arrowdownleft.png"));
					g.drawImage(y,220,550,70,70,null);
					y=t.getImage(getClass().getResource("/resources/arrowupupright.png"));
					g.drawImage(y,180,320,70,70,null);
					g.drawString("But: éliminer la cible", 450, 320);
				}
				if (board.Niveau == 4) {
					y=t.getImage(getClass().getResource("/resources/arrowright.png"));
					g.drawImage(y,380,350,60,46,null);
					g.drawString("But: éliminer la cible", 140, 280);
				}

				if (board.Niveau == 5) {
					y=t.getImage(getClass().getResource("/resources/arrowdown.png"));
					g.drawImage(y,380,520,70,70,null);
					g.drawString("But: éliminer les deux cochons", 140, 340);
				}
				if (board.Niveau == 6) {
					y=t.getImage(getClass().getResource("/resources/arrowdown.png"));
					g.drawImage(y,560,100,70,70,null);
					g.drawString("But: éliminer tous les cochons", 480, 70);

				}
			}
			// Tracé des traits représentant l'élastique lors du tire de l'oiseau
			if (c1==1 && c==0) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(102,51,0));
				g2.setStroke(new BasicStroke(6));
				g2.drawLine(190, 490, board.initX, board.initY);
				g2.drawLine(130, 505, board.initX, board.initY);

			}

			// Ajustement de la taille de l'oiseau en fonction de celle choisie en options
			if (board.optionTaille==0) {
				if (board.ab.getX()<200) {
					y=t.getImage(getClass().getResource("/resources/angab.png"));
				}
				if (board.ab.getX()>200) {
					y=t.getImage(getClass().getResource("/resources/angab1.png"));
				}
				if (board.ab.getX()>500) {
					y=t.getImage(getClass().getResource("/resources/angab2.png"));	
				}
			}
			if (board.optionTaille==1) {
				y=t.getImage(getClass().getResource("/resources/angabSmall.png"));
			}
			if (board.optionTaille==2) {
				y=t.getImage(getClass().getResource("/resources/angabVerySmall.png"));
			}

			// Draw le bird toujours droit (mais en meilleur résolution)

			if (board.optionTaille==0) {
				g.drawImage(y,(int)(s.getX()-31),(int)(s.getY()-31),63,63,null);
			}
			if (board.optionTaille==1) {
				g.drawImage(y,(int)(s.getX()-25),(int)(s.getY()-25),50,50,null);
			}
			if (board.optionTaille==2) {
				g.drawImage(y,(int)(s.getX()-20),(int)(s.getY()-20),40,40,null);
			}
			
			// Définit les couleurs en fonction du matériaux choisi en options
			
			Color co= Color.LIGHT_GRAY;
			if (board.optionType==1) {
				co= new Color(102,51,0);
			}
			if (board.optionType==0) {
				co= new Color(255,204,51);
			}
			if (board.optionType==2) {
				co= Color.LIGHT_GRAY;
			}

			// Dessin des Briques (murs et sol) crés dans BirdWolrd pour chaque Niveau

			for (int i=5;i<12;i++) {
				if (Niveau == 1) {
					drawRectangleObject(board.listebody[i],board.listetarget[i], g, co);		
				}
			}
			if (Niveau ==2) {
				drawRectangleObject(board.listebody[5],board.listetarget[5], g, co);		
				drawRectangleObject(board.listebody[6],board.listetarget[6], g, co);
				drawRectangleObject(board.listebody[7],board.listetarget[7], g, Color.BLACK);
				drawRectangleObject(board.listebody[8],board.listetarget[8], g, co);
				drawRectangleObject(board.listebody[9],board.listetarget[9], g, co);
				drawRectangleObject(board.listebody[10],board.listetarget[10], g, Color.BLACK);
				drawRectangleObject(board.listebody[11],board.listetarget[11], g, Color.PINK);
			}

			if (Niveau == 3) {
				drawRectangleObject(board.listebody[3],board.listetarget[3], g, Color.BLACK);	
				drawRectangleObject(board.listebody[4],board.listetarget[4], g, Color.PINK);
				drawRectangleObject(board.listebody[5],board.listetarget[5], g, Color.PINK);
			}
			if (Niveau == 4) {
				drawRectangleObject(board.listebody[3],board.listetarget[3], g, Color.DARK_GRAY);	
				drawRectangleObject(board.listebody[4],board.listetarget[4], g, Color.BLACK);
				drawRectangleObject(board.listebody[5],board.listetarget[5], g, Color.BLACK);
			}
			if (Niveau == 5) {

				drawRectangleObject(board.listebody[4],board.listetarget[4], g, Color.PINK);
				drawRectangleObject(board.listebody[5],board.listetarget[5], g, Color.RED);
				drawRectangleObject(board.listebody[6],board.listetarget[6], g, Color.BLACK);
				drawRectangleObject(board.listebody[7],board.listetarget[7], g, Color.BLACK);
			}
			for (int i=5;i<31;i++) {
				if (Niveau == 6) {
					drawRectangleObject(board.listebody[i],board.listetarget[i], g, co);		
				}
			}

			g.setColor(Color.BLACK);
			g.setFont(new Font("Tahoma", Font.BOLD, 30));
			g.drawString(scoreString, 100, 50);

			g.setColor(Color.green);

			y=t.getImage(getClass().getResource("/resources/pig.png"));

			if (Niveau == 1 || Niveau ==2 || Niveau ==6) {

				// Dessin des Pigs pour chaque Niveau tant qu'ils ne sont pas détruits
				if(!board.destroyed[0]) {
					g.drawImage(y,(int) (board.listebody[2].getPosition().x*100)-25,(int) (board.listebody[2].getPosition().y*100)-25,50,50,null);
				}
				if(!board.destroyed[1]) {
					g.drawImage(y,(int) (board.listebody[3].getPosition().x*100)-25,(int) (board.listebody[3].getPosition().y*100)-25,50,50,null);
				}
				if(!board.destroyed[2]) {
					g.drawImage(y,(int) (board.listebody[4].getPosition().x*100)-25,(int) (board.listebody[4].getPosition().y*100)-25,50,50,null);
				}
			}
			if (Niveau == 3 || Niveau == 4) {
				if(!board.destroyed[0]) {
					g.drawImage(y,(int) (board.listebody[2].getPosition().x*100)-25,(int) (board.listebody[2].getPosition().y*100)-25,50,50,null);
				}
			}
			if (Niveau == 5) {
				if(!board.destroyed[0]) {
					g.drawImage(y,(int) (board.listebody[2].getPosition().x*100)-25,(int) (board.listebody[2].getPosition().y*100)-25,50,50,null);
				}
				if(!board.destroyed[1]) {
					g.drawImage(y,(int) (board.listebody[3].getPosition().x*100)-25,(int) (board.listebody[3].getPosition().y*100)-25,50,50,null);
				}
			}
			if (Niveau ==6) {
				for (int i=3; i<=9; i++) {
					if(!board.destroyed[i]) {
						g.drawImage(y,(int) (board.listebody[28+i].getPosition().x*100)-25,(int) (board.listebody[28+i].getPosition().y*100)-25,50,50,null);
					}
				}
			}

		}

		public void paint(Graphics g) {
			Graphics gj = jeuImage.getGraphics();
			dessineMonde(gj);
			g.drawImage(jeuImage,0,0,null);
			scoreString = ("Score: "+Math.round(board.score*board.multiplicateur));

		}
	}

	/* Méthode affichant, dessinant un rectangle correspondant parfaitement au body du moteur physique
	   En réalité , cette méthode permet de dessiner tout type de polygone, et pas seulement des rectangles
	   car seuls les sommets sont pris en compte*/
	public void drawRectangleObject(Body b, PolygonShape gb, Graphics g, Color color){
		int[] xp= new int[gb.m_count]; 
		int[] yp= new int[gb.m_count];
		// Récupération des coordonnées de tous les sommets du body 
		for (int i=0; i<gb.m_count; i++) {
			xp[i]=(int)((gb.m_vertices[i].x+b.getPosition().x)*100);
			yp[i]=(int)((gb.m_vertices[i].y+b.getPosition().y)*100);
		}
		Polygon rect1 = new Polygon(xp, yp, gb.m_count); // Création du polygone à  dessiner avec les coordonnés précédents
		// Transformation du polygone à  partir de la translation et de la rotation directement données par le moteur physique
		AffineTransform transform = new AffineTransform(); 
		transform.setToRotation(b.getAngle(),b.getPosition().x*100,b.getPosition().y*100); 
		Shape rotatedRect = transform.createTransformedShape(rect1); 

		Graphics2D g2 = (Graphics2D) g; // get it from whatever you're drawing to
		g2.setColor(color);
		g2.setStroke(new BasicStroke(0));
		g2.fill(rotatedRect);
		g2.draw(rotatedRect);
	}
	// Méthode affichant, dessinant un rond en accord avec les données calculées par le moteur physique
	public void drawCircle(Body b, CircleShape gb,  Graphics g){
		g.fillOval((int)((b.getPosition().x)*100)-(int)(gb.m_radius*100), (int)((b.getPosition().y)*100)-(int)(gb.m_radius*100), (int)(gb.m_radius*2*100), (int)(gb.m_radius*2*100));
	}
}
