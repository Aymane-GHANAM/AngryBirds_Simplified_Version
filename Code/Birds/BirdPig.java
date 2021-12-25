package Birds;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class BirdPig extends Objets {// Fille d'Objets

	private float rayon; // Bird/Pig sont des objets ronds

	// Contructeur
	public BirdPig(float a, float b, float d, float f, float r)  {
		super(a,b,d,f);
		rayon =r;
	}

	// Gets et sets
	public float getRayon() {
		return rayon;
	}

	//m�thode de cr�ation d'un body impl�met�e suivant le moteur physique box2d
	public void CreateBody(World w, int numBody, BodyDef[] BD, Body[] B, PolygonShape[] PS, CircleShape[] CS, FixtureDef[] FD, Fixture[] F) {
		BD[numBody] = new BodyDef(); // cr�ation du "mod�le": BodyDef sur lequel va �tre construit l'objet dans le moteur physique : Body
		BD[numBody].type = BodyType.DYNAMIC; // type dynamique car les birds et pigs peuvent bouger
		BD[numBody].position.set(x,y); // Initialisation position initiale du centre de l'objet
		B[numBody] = w.createBody(BD[numBody]); // creation du body
		B[numBody].setFixedRotation(true); // Coupe la rotation des Pigs et Birds, pour �viter que leur body ne roule. Malgr� leur forme ronde ils restent � l'horizontal
		CS[numBody] = new CircleShape(); // forme ronde
		CS[numBody].m_radius = rayon; // de m�me rayon que l'objet 
		FD[numBody] = new FixtureDef(); // Cr�ation d'une mati�re pour le body qui fera le lien entre le body et sa forme
		FD[numBody].shape = CS[numBody];// Liaison entre la forme et la mati�re
		FD[numBody].density = density; // impl�mentation de la densit� de l'objet
		FD[numBody].friction = friction;// impl�mentation de la friction de l'objet
		B[numBody].createFixture(FD[numBody]);	// Liaison entre la mati�re et le body 
	}
}

