package Birds;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Brique extends Objets{ // Les briques  structurant nos niveaux
	private float dim1; // largeur
	private float dim2; // hauteur
	private boolean dynamic; // type de la brique: statique pour celle qui ne bougeront pas (sol par exemple) et dynamique pour les murs qui peuvent être poussés
	private float restitution; // quantifie le rebond des autres objets sur celui-ci, float entre 0 et 1, plus la restitution est élevée, plus le rebond est important

	// Constructeur
	public Brique(float a, float b, float d, float f, float d1, float d2, boolean dy, float r)  {
		super(a,b,d,f);
		dim1 = d1;
		dim2 = d2;
		dynamic = dy;
		restitution = r;
	}
	// Gets et Sets
	public float getDim1() {
		return dim1;
	}

	public float getDim2() {
		return dim2;
	}

	public boolean isDynamic() {
		return dynamic;
	}
	// Creation Body dans moteur physique: même étapes que dans l'implémentation dans la classe BirdPig
	public void CreateBody(World w, int numBody, BodyDef[] BD, Body[] B, PolygonShape[] PS, CircleShape[] CS, FixtureDef[] FD, Fixture[] F) {
		BD[numBody] = new BodyDef();
		if (dynamic) {
			BD[numBody].type = BodyType.DYNAMIC;
		}
		BD[numBody].position.set(x,y);
		B[numBody] = w.createBody(BD[numBody]);
		PS[numBody] = new PolygonShape();
		PS[numBody].setAsBox(dim1, dim2);
		FD[numBody] = new FixtureDef();
		FD[numBody].shape = PS[numBody];
		FD[numBody].density = density;
		FD[numBody].friction = friction;
		if (!dynamic){
			FD[numBody].restitution = restitution;	
		}
		B[numBody].createFixture(FD[numBody]);	
	}

}
