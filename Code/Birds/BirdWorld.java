package Birds;

import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.Timer;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public class BirdWorld {

	// Variables

	public BirdPig ab ; // Oiseau
	public double pos = 0;
	public double temps = 0;
	// Position initiale de l'Oiseau
	public int initX = 145;
	public int initY = 485;
	public static boolean pigDestroyed = false;
	public MainBird m;
	public int c=0; // Indicateur de l'étape de tir de l'oiseau: =0, pas encore tiré
	public float birdSize = 0.25f; // taille de l'oiseau
	public int optionTaille = 1; // Pour changer la taille de l'oiseau
	public int optionType = 1; // Pour changer le type de l'oiseau
	public int optionPoids = 1; // Pour changer le poids de l'oiseau
	public float density = 0.75f;
	public float densityS = 0.66f;
	public float timeStep; //timer utilisé par le moteur physique
	// paramètres d'évolution du monde
	private int velocityIterations;  
	private int positionIterations;
	// game-play : score
	public int score = 0;
	public double multiplicateur = 1;
	// Attributs caractérisant le réussite ou non de chaque niveau
	public boolean levelPassed[] = new boolean[6];

	Objets[] listeObjets = new Objets[50]; // tableau d'Objets qui définiront chaque Niveau 
	// Ensemble des Body, Matières (Fixture) et des Formes à  créer pour le bon fonctionnement du moteur physique
	BodyDef[] listebodyDef = new BodyDef[50];
	static Body[] listebody = new Body[50];
	int nb; // nombre d'Objets crés
	PolygonShape[] listetarget = new PolygonShape[50];
	CircleShape[] listetargetbis = new CircleShape[50];
	FixtureDef[] listeFixtureDef = new FixtureDef[50];
	Fixture[] listeFixture = new Fixture[50];
	Vec2  gravity = new Vec2(0,StartMenu.gvt*10); // gravité du monde
	World world = new World(gravity); // Monde
	boolean[] destroyed = new boolean[50]; // Tableau précisant les objets détruits (pigs une fois touchés)
	int Niveau;

	public BirdWorld (int Niveau) { // On fait un nouveau world
		//public BirdWorld (int Niveau) {
		setUpOptions(); // Initialisation du monde avec les options choisies par l'utilisateur
		listeObjets[1]= new BirdPig(145f, 485f, density, 0.1f, birdSize);// Création de l'oiseau
		ab = (BirdPig)listeObjets[1];  
		m = new MainBird();


		this.Niveau = Niveau;
		// Création des projets et de leur body (moteur physique) pour l'initialisation de chaque Niveau
		if (this.Niveau == 1) {
			// Création des objets
			listeObjets[0]= new Brique(7.5f, 6.9f, 0f, 0.5f, 11f, 0.15f, false, 0);
			listeObjets[2]= new BirdPig( 6.5f, 6.5f, 1f, 0.1f, 0.25f);
			listeObjets[3]= new BirdPig(7.5f, 6.5f, 1f, 0.1f, 0.25f);
			listeObjets[4]= new BirdPig(7f, 5.3f, 1f, 0.1f, 0.25f);
			listeObjets[5]= new Brique(6f, 6.23f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[6]= new Brique(7f, 6.23f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[7]= new Brique(7f, 5.65f, densityS, 1f, 1.1f, 0.1f, true, 0);
			listeObjets[8]= new Brique(8f, 6.23f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[9]= new Brique(6.5f, 5.05f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[10]= new Brique(7.5f, 5.05f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[11]= new Brique(7f, 4.45f, densityS, 1f, 0.6f, 0.1f, true, 0);
			nb=12;
			// Création des bodies associés
			for (int i=0; i<nb; i++) {
				listeObjets[i].CreateBody(world, i, listebodyDef, listebody, listetarget, listetargetbis, listeFixtureDef, listeFixture);
			}
			listebody[1].setFixedRotation(true);
		}

		if (Niveau == 2) {

			listeObjets[0]= new Brique(7.5f, 6.9f, 0f, 0.5f, 11f, 0.15f, false, 0);
			listeObjets[2]= new BirdPig( 5f, 4.86f, 1f, 0.1f, 0.25f);
			listeObjets[3]= new BirdPig(6.8f, 3.1f, 1f, 0.1f, 0.25f);
			listeObjets[4]= new BirdPig(8.6f, 4.87f, 1f, 0.1f, 0.25f);
			listeObjets[5]= new Brique(5f, 5.93f, densityS, 1f, 0.15f, 0.8f, true, 0);
			listeObjets[6]= new Brique(5.9f, 5.53f, densityS, 1f, 0.15f, 1.2f, true, 0);
			listeObjets[7]= new Brique(6.8f, 5.13f, densityS, 0.5f, 0.15f, 1.6f, false, 1f);
			listeObjets[8]= new Brique(7.7f, 5.53f, densityS, 1f, 0.15f, 1.2f, true, 0);
			listeObjets[9]= new Brique(8.6f, 5.93f, densityS, 1f, 0.15f, 0.8f, true, 0);
			listeObjets[10]= new Brique(3f, 2f, 500, 1f, 0.1f, 0.8f, true, 0);
			listeObjets[11]= new Brique(9.75f, 3f, 1, 0.5f, 0.1f, 2f, false, 0);
			nb=12;

			for (int i=0; i<nb; i++) {
				listeObjets[i].CreateBody(world, i, listebodyDef, listebody, listetarget, listetargetbis, listeFixtureDef, listeFixture);
			}
			listebody[1].setFixedRotation(true);
			listebody[10].setAngularVelocity(25f);
			listebody[10].setGravityScale(0);
		}

		if (Niveau == 3) {

			listeObjets[0]= new Brique(7.5f, 6.9f, 1, 0.5f, 11f, 0.15f, false, 0);
			listeObjets[2]= new BirdPig(7.2f, 4.8f, 1f, 0.1f, 0.25f);
			listeObjets[3]= new Brique(7.2f, 6f, densityS, 0.5f, 0.15f, 0.7f, false, 0.98f);
			listeObjets[4]= new Brique(3.2f, 3.5f, 1, 0.5f, 0.05f, 2f, false, 0.9f);
			listeObjets[5]= new Brique(3.1f, 6.89f, 1, 0.5f, 1f, 0.15f, false, 1);


			nb=6;

			for (int i=0; i<nb; i++) {
				listeObjets[i].CreateBody(world, i, listebodyDef, listebody, listetarget, listetargetbis, listeFixtureDef, listeFixture);
			}
			listebody[1].setFixedRotation(true);

		}

		if (Niveau == 4) {

			listeObjets[0]= new Brique(7.5f, 6.9f, 1, 0.5f, 11f, 0.15f, false, 0);
			listeObjets[2]= new BirdPig(7.2f, 4.8f, 1f, 0.1f, 0.25f);
			listeObjets[3]= new Brique(7.2f, 6f, densityS, 0.5f, 0.15f, 0.7f, false, 0.98f);
			listeObjets[4]= new Brique(5.2f, 1.5f, 500, 0.5f, 0.1f, 0.8f, true, 0);
			listeObjets[5]= new Brique(5.2f, 5.5f, 500, 0.5f, 0.1f, 0.8f, true, 0);

			nb=6;

			for (int i=0; i<nb; i++) {
				listeObjets[i].CreateBody(world, i, listebodyDef, listebody, listetarget, listetargetbis, listeFixtureDef, listeFixture);
			}
			listebody[1].setFixedRotation(true);
			listebody[4].setFixedRotation(true);
			listebody[5].setFixedRotation(true);
			listebody[4].setGravityScale(0);
			listebody[5].setGravityScale(0);
		}

		if (Niveau == 5) {

			listeObjets[0]= new Brique(7.5f, 6.9f, 1, 0.5f, 11f, 0.15f, false, 0);
			listeObjets[2]= new BirdPig(5.1f, 5.8f,0.01f, 0.01f, 0.25f);
			listeObjets[3]= new BirdPig(0.4f, 0.90f, 1f, 0.1f, 0.25f);
			listeObjets[4]= new Brique(8.2f, 3.5f, 1, 0.5f, 0.05f, 1.2f, false, 1);
			listeObjets[5]= new Brique(4.60f, 6.1f, 0.5f, 1f, 0.8f, 0.05f, true, 0);
			listeObjets[6]= new Brique(4.65f, 6.21f, 1, 1, 0.1f, 0.05f, false, 0);
			listeObjets[7]= new Brique(0, 1.2f, 1f, 1f, 0.8f, 0.05f, false, 0);


			nb=8;

			for (int i=0; i<nb; i++) {
				listeObjets[i].CreateBody(world, i, listebodyDef, listebody, listetarget, listetargetbis, listeFixtureDef, listeFixture);
			}
			listebody[1].setFixedRotation(true);
			listebody[4].setTransform(new Vec2(8.2f, 3.5f), 0.8f);

		}

		if (this.Niveau == 6) {

			listeObjets[0]= new Brique(7.5f, 6.9f, 0f, 0.5f, 11f, 0.15f, false, 0);
			listeObjets[2]= new BirdPig( 7.5f, 6.5f, 1f, 0.1f, 0.25f);
			listeObjets[3]= new BirdPig(8.5f, 6.5f, 1f, 0.1f, 0.25f);
			listeObjets[4]= new BirdPig(8f, 5.3f, 1f, 0.1f, 0.25f);
			listeObjets[5]= new Brique(7f, 6.23f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[6]= new Brique(8f, 6.23f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[7]= new Brique(8f, 5.65f, densityS, 1f, 1.1f, 0.1f, true, 0);
			listeObjets[8]= new Brique(9f, 6.23f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[9]= new Brique(7.5f, 5.05f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[10]= new Brique(8.5f, 5.05f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[11]= new Brique(7.3f, 4.45f, densityS, 1f, 1.3f, 0.1f, true, 0);
			listeObjets[12]= new Brique(3f, 6.23f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[13]= new Brique(4f, 6.23f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[14]= new Brique(4f, 5.65f, densityS, 1f, 1.1f, 0.1f, true, 0);
			listeObjets[15]= new Brique(5f, 6.23f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[16]= new Brique(3.5f, 5.05f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[17]= new Brique(4.5f, 5.05f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[18]= new Brique(4.7f, 4.45f, densityS, 1f, 1.3f, 0.1f, true, 0);
			listeObjets[19]= new Brique(6f, 5.65f, densityS, 1f, 0.6f, 0.1f, true, 0);
			listeObjets[20]= new Brique(5.5f, 6.23f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[21]= new Brique(6.5f, 6.23f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[22]= new Brique(6f, 5.05f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[23]= new Brique(7f, 3.83f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[24]= new Brique(8f, 3.83f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[25]= new Brique(4f, 3.83f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[26]= new Brique(5f, 3.83f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[27]= new Brique(6f, 3.23f, densityS, 1f, 2.1f, 0.1f, true, 0);
			listeObjets[28]= new Brique(5.4f, 2.62f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[29]= new Brique(6.4f, 2.62f, densityS, 1f, 0.1f, 0.5f, true, 0);
			listeObjets[30]= new Brique(5.9f, 2f, densityS, 1f, 0.6f, 0.1f, true, 0);
			listeObjets[31]= new BirdPig( 3.5f, 6.5f, 1f, 0.1f, 0.25f);
			listeObjets[32]= new BirdPig(4.5f, 6.5f, 1f, 0.1f, 0.25f);
			listeObjets[33]= new BirdPig(5.9f, 2.88f, 1f, 0.1f, 0.25f);
			listeObjets[34]= new BirdPig( 4f, 5.3f, 1f, 0.1f, 0.25f);
			listeObjets[35]= new BirdPig(6f, 6.5f, 1f, 0.1f, 0.25f);
			listeObjets[36]= new BirdPig(7.5f, 4.1f, 1f, 0.1f, 0.25f);
			listeObjets[37]= new BirdPig(4.5f, 4.1f, 1f, 0.1f, 0.25f);
			nb=38;

			for (int i=0; i<nb; i++) {
				listeObjets[i].CreateBody(world, i, listebodyDef, listebody, listetarget, listetargetbis, listeFixtureDef, listeFixture);
			}
			listebody[1].setFixedRotation(true);
		}



		// Les bodies sont initialement non détruits
		for (int i =0 ; i<50 ;i++) {
			destroyed[i]=false;
		}

		// Initialisation des paramètres d'évolution du monde
		timeStep = 1.0f/100.0f;
		velocityIterations = 6;
		positionIterations = 2;

		// Détection des contacts pour la destruction des pigs et la mise à  jour du score pour chaque Niveau
		world.setContactListener(new ContactListener() {		
			public void beginContact(Contact contact) { //Entrée à  chaque contact

				if (Niveau ==1) {

					// Si le pig  se fait toucher par un autre objet il disparait excepté s'il s'agit du sol sur lequel il est posé
					if((contact.getFixtureA().getBody()==listebody[2]&&contact.getFixtureB().getBody()!=listebody[0]) || (contact.getFixtureB().getBody()==listebody[2]&&contact.getFixtureA().getBody()!=listebody[0])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[0]=true;
						score=score+200;
					}

					if((contact.getFixtureA().getBody()==listebody[3]&&contact.getFixtureB().getBody()!=listebody[0]) || (contact.getFixtureB().getBody()==listebody[3]&&contact.getFixtureA().getBody()!=listebody[0])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[1]=true;
						score=score+300;
					}

					if((contact.getFixtureA().getBody()==listebody[4]&&contact.getFixtureB().getBody()!=listebody[0]&&contact.getFixtureB().getBody()!=listebody[7]) || (contact.getFixtureB().getBody()==listebody[4]&&contact.getFixtureA().getBody()!=listebody[0]&&contact.getFixtureA().getBody()!=listebody[7])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[2]=true;
						score=score+200;
					}
					if (destroyed[0]==true&&destroyed[1]==true&&destroyed[2]==true) {
						levelPassed[0]=true;
					}
				}

				if (Niveau == 2) {


					if((contact.getFixtureA().getBody()==listebody[2]&&contact.getFixtureB().getBody()!=listebody[5]) || (contact.getFixtureB().getBody()==listebody[2]&&contact.getFixtureA().getBody()!=listebody[5])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[0]=true;
						score=score+200;
					}

					if((contact.getFixtureA().getBody()==listebody[3]&&contact.getFixtureB().getBody()!=listebody[7]) || (contact.getFixtureB().getBody()==listebody[3]&&contact.getFixtureA().getBody()!=listebody[7])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[1]=true;
						score=score+300;
					}

					if((contact.getFixtureA().getBody()==listebody[4]&&contact.getFixtureB().getBody()!=listebody[9]) || (contact.getFixtureB().getBody()==listebody[4]&&contact.getFixtureA().getBody()!=listebody[9])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[2]=true;
						score=score+400;
					}

					if ((destroyed[0]==true&&destroyed[1]==true)||(destroyed[1]==true&&destroyed[2]==true)) {
						levelPassed[1]=true;
					}

					// Bouncers

					if((contact.getFixtureA().getBody()==listebody[11]&&contact.getFixtureB().getBody()==listebody[1])) {	
						Vec2 Force = new Vec2(-2.6f,0);

						Vec2 Point = new Vec2(listebody[1].getPosition().x, listebody[1].getPosition().y);
						listebody[1].applyLinearImpulse(Force,Point, true);
					}


				}

				if (Niveau ==3) {

					if((contact.getFixtureA().getBody()==listebody[2]&&contact.getFixtureB().getBody()!=listebody[3]) || (contact.getFixtureB().getBody()==listebody[2]&&contact.getFixtureA().getBody()!=listebody[3])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[0]=true;
						score=score+500;
					}
					if (destroyed[0]) {
						levelPassed[2]=true;
					}

				}
				if (Niveau ==4) {

					if((contact.getFixtureA().getBody()==listebody[2]&&contact.getFixtureB().getBody()!=listebody[3]) || (contact.getFixtureB().getBody()==listebody[2]&&contact.getFixtureA().getBody()!=listebody[3])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[0]=true;
						score=score+500;
					}
					if (destroyed[0]) {
						levelPassed[3]=true;
					}

				}

				if (Niveau ==5) {

					if((contact.getFixtureA().getBody()==listebody[2]&&contact.getFixtureB().getBody()!=listebody[5]) || (contact.getFixtureB().getBody()==listebody[2]&&contact.getFixtureA().getBody()!=listebody[5])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[0]=true;
						score=score+200;
					}
					if((contact.getFixtureA().getBody()==listebody[3]&&contact.getFixtureB().getBody()!=listebody[7]) || (contact.getFixtureB().getBody()==listebody[3]&&contact.getFixtureA().getBody()!=listebody[7])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[1]=true;
						score=score+800;
					}
					if((contact.getFixtureA().getBody()==listebody[4]&&contact.getFixtureB().getBody()==listebody[1])) {	
						Vec2 Force = new Vec2(-2.3f,-1.4f);
						Vec2 Point = new Vec2(listebody[1].getPosition().x, listebody[1].getPosition().y);
						listebody[1].applyLinearImpulse(Force,Point, true);
					}

					if (destroyed[0]==true&&destroyed[1]==true) {
						levelPassed[4]=true;
					}
				}

				if (Niveau ==6) {


					if((contact.getFixtureA().getBody()==listebody[2]&&contact.getFixtureB().getBody()!=listebody[0]) || (contact.getFixtureB().getBody()==listebody[2]&&contact.getFixtureA().getBody()!=listebody[0])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[0]=true;
						score=score+400;
					}

					if((contact.getFixtureA().getBody()==listebody[3]&&contact.getFixtureB().getBody()!=listebody[0]) || (contact.getFixtureB().getBody()==listebody[3]&&contact.getFixtureA().getBody()!=listebody[0])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[1]=true;
						score=score+400;
					}

					if((contact.getFixtureA().getBody()==listebody[4]&&contact.getFixtureB().getBody()!=listebody[0]&&contact.getFixtureB().getBody()!=listebody[7]) || (contact.getFixtureB().getBody()==listebody[4]&&contact.getFixtureA().getBody()!=listebody[0]&&contact.getFixtureA().getBody()!=listebody[7])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[2]=true;
						score=score+300;
					}

					if((contact.getFixtureA().getBody()==listebody[31]&&contact.getFixtureB().getBody()!=listebody[0]) || (contact.getFixtureB().getBody()==listebody[31]&&contact.getFixtureA().getBody()!=listebody[0])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[3]=true;
						score=score+400;
					}

					if((contact.getFixtureA().getBody()==listebody[32]&&contact.getFixtureB().getBody()!=listebody[0]) || (contact.getFixtureB().getBody()==listebody[32]&&contact.getFixtureA().getBody()!=listebody[0])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[4]=true;
						score=score+400;
					}

					if((contact.getFixtureA().getBody()==listebody[33]&&contact.getFixtureB().getBody()!=listebody[0]&&contact.getFixtureB().getBody()!=listebody[27]) || (contact.getFixtureB().getBody()==listebody[33]&&contact.getFixtureA().getBody()!=listebody[0]&&contact.getFixtureA().getBody()!=listebody[27])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[5]=true;
						score=score+500;
					}

					if((contact.getFixtureA().getBody()==listebody[34]&&contact.getFixtureB().getBody()!=listebody[0]&&contact.getFixtureB().getBody()!=listebody[14]) || (contact.getFixtureB().getBody()==listebody[34]&&contact.getFixtureA().getBody()!=listebody[0]&&contact.getFixtureA().getBody()!=listebody[14])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[6]=true;
						score=score+300;
					}

					if((contact.getFixtureA().getBody()==listebody[35]&&contact.getFixtureB().getBody()!=listebody[0]) || (contact.getFixtureB().getBody()==listebody[35]&&contact.getFixtureA().getBody()!=listebody[0])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[7]=true;
						score=score+400;
					}

					if((contact.getFixtureA().getBody()==listebody[36]&&contact.getFixtureB().getBody()!=listebody[0]&&contact.getFixtureB().getBody()!=listebody[11]) || (contact.getFixtureB().getBody()==listebody[36]&&contact.getFixtureA().getBody()!=listebody[0]&&contact.getFixtureA().getBody()!=listebody[11])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[8]=true;
						score=score+200;
					}
					if((contact.getFixtureA().getBody()==listebody[37]&&contact.getFixtureB().getBody()!=listebody[0]&&contact.getFixtureB().getBody()!=listebody[18]) || (contact.getFixtureB().getBody()==listebody[37]&&contact.getFixtureA().getBody()!=listebody[0]&&contact.getFixtureA().getBody()!=listebody[18])) {	
						contact.getFixtureB().getBody().setUserData(1);
						destroyed[9]=true;
						score=score+200;
					}

					if (destroyed[0]==true&&destroyed[1]==true&&destroyed[2]==true&&destroyed[3]==true&&destroyed[4]==true&&destroyed[5]==true&&destroyed[6]==true&&destroyed[7]==true&&destroyed[8]==true&&destroyed[9]==true) {
						levelPassed[5]=true;
					}
				}

			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub

			}
		});


	}
	// get
	public World getWorld() {
		return world;
	}

	public void movePiece(){ // On avance la position de l'oiseau a chaque itération

		world.step(timeStep, velocityIterations,positionIterations); // Le monde est mis à  jour, il passe à  t+1
		// Supression des body détruits (par les contacts)
		for ( Body b = world.getBodyList(); b!=null; b = b.getNext() ){
			Object  data =  (Object) b.getUserData();
			if(data != null){
				b.setAngularVelocity(0);
				b.setLinearVelocity(new Vec2(0,0));
				world.destroyBody(b);
				if (m.SG.soundOn) {
					Sound s=new Sound(getClass().getResource("/resources/2.wav"));
					s.play();
				}
			}
		}

		float x = ((float)initX)/100;
		float y = ((float)initY)/100;


		if (c==0) { // La premiere position de l'oiseau est là  ou l'on lache la souris
			listebody[1].setTransform(new Vec2(x,y), 1);
			c++;
		}
		/* Une fois l'oiseau laché, on met à  jour sa position grâce au moteur physique (dans un souci 
		   de bon foncitonnement, les valeurs du moteur physique respectent une échelle 100 plus 
		   petite que la réalite (pixels), d'où la multiplication par 100)*/
		Vec2 position = listebody[1].getPosition();
		ab.setX((int) (position.x*100));
		ab.setY((int) (position.y*100));

		// Mise en mouvement des murs en mode "aller-retour"" pour le Niveau 4
		if (Niveau==4) {
			if (listebody[4].getPosition().y<=1.5) {
				listebody[4].setLinearVelocity(new Vec2(0,5f));
			}
			if (listebody[4].getPosition().y>=2.7) {
				listebody[4].setLinearVelocity(new Vec2(0,-5f));
			}
			if (listebody[5].getPosition().y<=4.3) {
				listebody[5].setLinearVelocity(new Vec2(0,5f));
			}
			if (listebody[5].getPosition().y>=5.5) {
				listebody[5].setLinearVelocity(new Vec2(0,-5f));
			}

		}
	}


	public void movePieceInit(int x, int y){ // L'oiseau suit la souris

		ab.setX(x);
		ab.setY(y);

	}

	public void setForce (int xini, int xfin, int yini, int yfin){	

		// On fait le calcul de force grace a la position de la souris
		Vec2  vForce = new Vec2((xini-xfin)*1f,(yini-yfin)*1.5f);
		Vec2  vPoint = new Vec2(initX,initY);
		listebody[1].applyForce(vForce,vPoint);

	}

	// Méthode qui gère et récupère les différentes options choisies par l'utilisateur dans le menu start
	public void setUpOptions() {
		if (MainBird.SG.optionTaille0.isSelected()) {
			birdSize=0.20f;
			optionTaille = 2;
			multiplicateur = multiplicateur * 1.1;
		}
		if (MainBird.SG.optionTaille1.isSelected()) {
			birdSize=0.25f;
			optionTaille = 1;
		}
		if (MainBird.SG.optionTaille2.isSelected()) {
			birdSize=0.315f;
			optionTaille = 0;
			multiplicateur = multiplicateur * 0.9;
		}
		if (MainBird.SG.optionType0.isSelected()) {
			optionType=0;
			densityS = 0.33f;
			multiplicateur = multiplicateur * 0.8;
		}
		if (MainBird.SG.optionType1.isSelected()) {
			optionType=1;
			densityS = 0.66f;
		}
		if (MainBird.SG.optionType2.isSelected()) {
			optionType=2;
			densityS = 1f;
			multiplicateur = multiplicateur * 1.2;
		}
		if (MainBird.SG.optionPoids0.isSelected()) {
			optionPoids=0;
			density = 0.5f;
		}
		if (MainBird.SG.optionPoids1.isSelected()) {
			optionPoids=1;
			density = 0.75f;
		}
		if (MainBird.SG.optionPoids2.isSelected()) {
			optionPoids=2;
			density = 1f;
		}
	}

	public boolean testend() {
		boolean b = true;
		for (int i=0; i<nb; i++) {
			if (!(Niveau==2 && (i==10 || i==3) ) && !(Niveau==3 && i==2) &&!(Niveau==4 && (i==4 || i==5 || i==2))) {
				if ((listebody[i].getAngularVelocity()!=0 || listebody[i].getLinearVelocity().x!=0||listebody[i].getLinearVelocity().y!=0)&&(listebody[i].getPosition().x>-1 && listebody[i].getPosition().x<11)) {
					b = false;
				}
			}
		}
		return b;
	}
}
