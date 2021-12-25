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

	//méthode de création d'un body implémetée suivant le moteur physique box2d
	public void CreateBody(World w, int numBody, BodyDef[] BD, Body[] B, PolygonShape[] PS, CircleShape[] CS, FixtureDef[] FD, Fixture[] F) {
		BD[numBody] = new BodyDef(); // création du "modèle": BodyDef sur lequel va être construit l'objet dans le moteur physique : Body
		BD[numBody].type = BodyType.DYNAMIC; // type dynamique car les birds et pigs peuvent bouger
		BD[numBody].position.set(x,y); // Initialisation position initiale du centre de l'objet
		B[numBody] = w.createBody(BD[numBody]); // creation du body
		B[numBody].setFixedRotation(true); // Coupe la rotation des Pigs et Birds, pour éviter que leur body ne roule. Malgré leur forme ronde ils restent à  l'horizontal
		CS[numBody] = new CircleShape(); // forme ronde
		CS[numBody].m_radius = rayon; // de même rayon que l'objet 
		FD[numBody] = new FixtureDef(); // Création d'une matière pour le body qui fera le lien entre le body et sa forme
		FD[numBody].shape = CS[numBody];// Liaison entre la forme et la matière
		FD[numBody].density = density; // implémentation de la densité de l'objet
		FD[numBody].friction = friction;// implémentation de la friction de l'objet
		B[numBody].createFixture(FD[numBody]);	// Liaison entre la matière et le body 
	}
}

