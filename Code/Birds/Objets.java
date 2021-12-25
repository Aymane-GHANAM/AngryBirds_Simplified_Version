package Birds;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public abstract class Objets { // classe abstraite jamais instanciée, mère de Bird,Pig,Brique : --> définir les objets que l'on construira via le moteur physique
	protected float x; // position du centre de l'objet en x
	protected float y; // position du centre de l'objet en y
	protected float density; // densité de l'objet
	protected float friction; // sa friction

	//constructeur
	public Objets(float a, float b, float d, float f ) { 
		x = a; 
		y = b;
		density = d;
		friction = f;
	}
	// gets / sets
	public float getX(){
		return x;
	} 	
	public float getY(){
		return y;
	}

	public void setX(int a) {
		this.x=a;
	}
	public void setY(int a) {
		this.y=a;
	}

	public float getFriction() {
		return friction;
	}

	public float getDensity() {
		return density;
	}
	// méthode abstraite de création d'un body par le moteur physique à  partir de l'objet 
	public abstract void CreateBody(World w, int num, BodyDef[] BD, Body[] B, PolygonShape[] PS, CircleShape[] CS, FixtureDef[] FD, Fixture[] F);
}


