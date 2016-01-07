package com.mygdx.game.desktop;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

//Class où l'on va créer tout ce qui est : vaisseau & asteroid.
// Empeche un "tas" de add dans la classe Game
public class Space {

	static Vaisseau vaisseau;
	static Texture background;
	//static Projectile bullet;
	 public ArrayList<Projectile> bullets;
	public ArrayList<Asteroid> asteroids;
	Asteroid asteroid;
	static int nbasteroidsturn;
	Body sol;
	Body murgauche;
	Body murdroit;
	BodyDef bodyDef;
	PolygonShape poly;
	FixtureDef fixtureDef;
	
	public Space() {
		vaisseau= new Vaisseau (new Vector2(200,100));
		background= new Texture ("image/galaxy.jpg");
		//Asteroid de test
		// A SUPPRIMER
		//asteroid=new Asteroid(new Vector2 (300,200));
		// PROJECTILE TEST
		//bullet= new Projectile(new Vector2(200,200));
		//bullet.body1.setLinearVelocity(bullet.body1.getLinearVelocity().x, bullet.body1.getLinearVelocity().y+400);
		
		bullets=new ArrayList<Projectile>();
		asteroids=new ArrayList<Asteroid>();
		nbasteroidsturn=(int) (Math.random() * (4 - 1 ));
		
		// CREER LE SOL avec SPRITE et polygone (on reprends le principe du TD 5 avec la balle)
		/** Create a horizontal polygon (the floor) **/
		/* Define body properties */
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody; // Gravity is not applied to static objects
		bodyDef.position.set(0,0);
		bodyDef.angle = 0f;
		/* Create body from the world and body properties */
		sol = Game.monde.createBody(bodyDef);
		/* Define shape as polygon */
		poly = new PolygonShape();
		poly.setAsBox(Game.V_WIDTH*Game.SCALE, 0);
		/* Define the fixture properties */
		fixtureDef = new FixtureDef();
		fixtureDef.shape = poly; // The fixture is linked to the shape
		/* Create fixture from the body and fixture properties */
		sol.createFixture(fixtureDef);
		
		//CREE MUR SUR COTE : MEME PRINCIPE QUE SOL mais on change les coordonnes du polygone
		//MUR GAUCHE
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody; // Gravity is not applied to static objects
		bodyDef.position.set(0,0);
		bodyDef.angle = 0f;
		/* Create body from the world and body properties */
		murgauche = Game.monde.createBody(bodyDef);
		/* Define shape as polygon */
		poly = new PolygonShape();
		poly.setAsBox(0, Game.V_HEIGHT*Game.SCALE);
		/* Define the fixture properties */
		fixtureDef = new FixtureDef();
		fixtureDef.shape = poly; // The fixture is linked to the shape
		/* Create fixture from the body and fixture properties */
		murgauche.createFixture(fixtureDef);
		poly.dispose();
		
		//MUR DROIT
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody; // Gravity is not applied to static objects
		bodyDef.position.set(Game.V_WIDTH*Game.SCALE,0);
		bodyDef.angle = 0f;
		/* Create body from the world and body properties */
		murdroit = Game.monde.createBody(bodyDef);
		/* Define shape as polygon */
		poly = new PolygonShape();
		poly.setAsBox(0, Game.V_HEIGHT*Game.SCALE);
		/* Define the fixture properties */
		fixtureDef = new FixtureDef();
		fixtureDef.shape = poly; // The fixture is linked to the shape
		/* Create fixture from the body and fixture properties */
		murdroit.createFixture(fixtureDef);
		poly.dispose();
		
		
	}
	
	//Fonction qui gère la chute d'asteroids
	public void chuteAsteroids() {
		if (Game.exit==0 && Game.pause==false){
		int x=(int) (70+(Math.random() * (300)));
		int y=(int) (Math.random() * (3 - 0 ));
		for (int compteur=0; compteur < y; compteur++) {
			asteroids.add(new Asteroid(new Vector2(x,600)));
			 x=(int) (70+(Math.random() * (300)));
		}
		}
	}
	// FONCTION QUI GERE LE TIR DU VAISSEAU
	public void tir() { 
		
		bullets.add(new Projectile(new Vector2(vaisseau.body.getPosition().x,vaisseau.body.getPosition().y+40)));
		if (bullets.size()== 1) {
			bullets.get(0).body1.setLinearVelocity(0, 1000f);
		}
		bullets.get((bullets.size())-1).body1.setLinearVelocity(0, 100f);
		
	}
	
	//Indique que le joueur a perdu si un asteroid se crash sur Terre
	public void asteroid_crash() {
		
		for (int i=0; i < asteroids.size(); i++) {
			if (asteroids.get(i).body.getPosition().y < vaisseau.body.getPosition().y) {
				System.out.println("PERDU!!! ASTEROIDS CRASH");
				//Game.monde.destroyBody(vaisseau.body);
				// Game over
				Game.exit=1;
			}
		}
		
	}
	//Sert à mettre à jour l'univers après la suppression d'un element
	public void update () { 
		
	}

	//Affiche le background du jeu
	public Texture Background() {
		
		return background;
	}
	
	// DETRUITS TOUS LES ASTEROIDS: BASE DESTRUCTION D'OBJETS
	public void cleanespace() {
		 while (!Game.espace.asteroids.isEmpty()){
			  Game.monde.destroyBody(Game.espace.asteroids.get(0).body);
			  Game.espace.asteroids.remove(0);
		 	}
		 
		 while (!Game.espace.bullets.isEmpty()) {
			 Game.monde.destroyBody(Game.espace.bullets.get(0).body1);
			 Game.espace.bullets.remove(0);
		 }
	}
}
