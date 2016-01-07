package com.mygdx.game.desktop;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;

//Fonction permettant de créer le personnage mais également gérer tout ce qui est controle clavier
//créer personnage
public class Vaisseau implements InputProcessor, ContactListener {
 
 
    static public float largeur;   
    static public float hauteur;
    
    Texture img;			
	Sprite sprite;
	Body body;
 
    public Vaisseau(Vector2 position) {
        img = new Texture("image/vaisseau.png");
		largeur=img.getWidth();
		hauteur=img.getHeight();
        
		sprite = new Sprite(img);
  	    sprite.setPosition(position.x , position.y);
  		 	    
  	    BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX(), sprite.getY());
        body = Game.monde.createBody(bodyDef);
        // Pour gérer les collisions on crée un cercle
        CircleShape circ = new CircleShape();
        circ.setRadius(sprite.getWidth()/2);
        circ.setRadius(sprite.getWidth()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circ;
        fixtureDef.density = 1;
        
        Fixture fixture = body.createFixture(fixtureDef);

        circ.dispose();
        body.setUserData("vaisseau");
    }
    
    // VOIT SI LE VAISSEAU EST EN CONTACT AVEC UN ASTEROID
    public void contact() { 
    	
    }
    
    //retire la gravite 
    public void antigravity() {
    	body.setGravityScale(0);
    }
    //Gère les entrés claviers
    public void update() {
 	   // fonction inutile avec InputProcessor (géré avec les keyup et les keydown)

    }
    
    @Override
    public boolean keyDown(int keycode) {

 	  if (keycode == Keys.RIGHT) {
 		 if (Game.exit==0 && Game.pause==false)
 		  	body.setLinearVelocity(body.getLinearVelocity().x+130, body.getLinearVelocity().y);
 		 
 		  }
 	  
 	  //Demande si l'utilisateur veut rejouer
 	  if (keycode== Keys.R ) {
 		 
 		  if (Game.exit==1) {
 			 Game.score=0;
 			 Game.replay=true;
 			 Game.exit=0;
 			 
 		  }
 	  }
 	 
 	   // Quand on appuie sur la touche gauche
 		  if (keycode == Keys.LEFT) {
 			  if (Game.exit==0 && Game.pause==false)
 			 body.setLinearVelocity(body.getLinearVelocity().x-130, body.getLinearVelocity().y);
 			 
	  } 
 			  
 		  // Met le jeu en pause quand on appuie sur P et retire la pause si on rappuie sur P
 		  if (keycode==Keys.P) {
 			  if (Game.pause==false)
 			  Game.pause=true;
 			  else
 				  Game.pause=false;
 		  }
 		  //Lancement du jeu
 		  if (keycode== Keys.F) {
 		
 			  Game.presentation=true;
 		  }
 		  //TIR QUAND APPUIE SUR ESPACE
 		  if (keycode == Keys.SPACE) {
 			 if (Game.exit==0 && Game.pause==false) {
 			  if (Game.cantir == true) { 
 			  Game.espace.tir();
 			  Game.tirtime=0;
 			  }
 			  }
 		  } 
 	return false;   
    }
    
    @Override
    public boolean keyTyped(char character) {
 	return false;   
    }
    
    @Override
    public boolean keyUp(int keycode) {
    	body.setLinearVelocity(0,0);
 	return false;   
    }
    
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
 	return false;   
    }
    
    @Override
    public boolean scrolled(int amount) {
 	return false;   
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
 	return false;   
    }
    
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
 	return false;   
    }
    
    @Override
    public boolean 	touchUp(int screenX, int screenY, int pointer, int button) {
 	return false;   
    }

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if (fa.getBody().getUserData()== null && fb.getBody().getUserData() == null ) { return;}

		// reconnait tous les contacts du vaisseau: MARCHE
		 if(fa.getBody() == Game.espace.vaisseau.body || fa.getBody()==Game.espace.vaisseau.body){
			 System.out.println("PERDU VAISSEAU DETRUIT");
			 // GAME OVER VAISSEAU DETRUIT MALHEUREUSEMENT
			 Game.flag=true;
			 Game.monde.step(Gdx.graphics.getDeltaTime(),6,2);
			 if (!Game.monde.isLocked()) {
				// Game.monde.destroyBody(fa.getBody());
				// fa.getBody().setUserData(null);
				 Game.flag=false;
			 }
		
			 Game.exit=1;
		 }
		 
		 //Cas où 2 tirs se touchent : il se détruisent
		 
		 for (int i=0; i < Game.espace.bullets.size();i ++) {
			 for (int j=0; j < Game.espace.bullets.size(); j++) {
				 if((fa.getBody() == Game.espace.bullets.get(i).body1 &&fb.getBody()==Game.espace.bullets.get(j).body1) || (fb.getBody() == Game.espace.bullets.get(i).body1 && fa.getBody()==Game.espace.bullets.get(j).body1)){
					 System.out.println("Asteroids collision, explosion!");
					 Game.flag=true;
					 Game.monde.step(Gdx.graphics.getDeltaTime(),6,2);
					 if (!Game.monde.isLocked()) {
					 Game.monde.destroyBody(fa.getBody());
					 Game.monde.destroyBody(fb.getBody());
					 fb.getBody().setUserData(null);
					 fa.getBody().setUserData(null);
					 //Game.espace.asteroids.get(j).body=null;
					 //Game.espace.asteroids.get(i).body=null;
					 Game.espace.bullets.remove(j);
					 Game.espace.bullets.remove(i);
					 Game.flag=false;
					 break;
					 }
				 
			 }
		 }
		 }
		 //Cas où 2 asteroids se touchent : ils se détruisent
		 for (int i=0; i < Game.espace.asteroids.size();i ++) {
			 for (int j=0; j < Game.espace.asteroids.size(); j++) {
				 if((fa.getBody() == Game.espace.asteroids.get(i).body &&fb.getBody()==Game.espace.asteroids.get(j).body) || (fb.getBody() == Game.espace.asteroids.get(i).body && fa.getBody()==Game.espace.asteroids.get(j).body)){
					 System.out.println("Asteroids collision, explosion!");
					 Game.flag=true;
					 Game.monde.step(Gdx.graphics.getDeltaTime(),6,2);
					 if (!Game.monde.isLocked()) {
					 Game.monde.destroyBody(fa.getBody());
					 Game.monde.destroyBody(fb.getBody());
					 fb.getBody().setUserData(null);
					 fa.getBody().setUserData(null);
					 //Game.espace.asteroids.get(j).body=null;
					 //Game.espace.asteroids.get(i).body=null;
					 Game.espace.asteroids.remove(j);
					 Game.espace.asteroids.remove(i);
					 Game.flag=false;
					 break;
					 }
				 
			 }
		 }
		 }	 
		 // Gère les colisions entre les asteroids et les tirs!
		 for (int i=0; i < Game.espace.bullets.size();i ++) {
			 for (int j=0; j < Game.espace.asteroids.size(); j++) {
				 if((fa.getBody() == Game.espace.bullets.get(i).body1 &&fb.getBody()==Game.espace.asteroids.get(j).body) || (fb.getBody() == Game.espace.bullets.get(i).body1 && fa.getBody()==Game.espace.asteroids.get(j).body)){
					 System.out.println("LA TORPILLE A TOUCHE MON KAPTAIN!");
					 Game.score++;
					 Game.flag=true;
					 Game.monde.step(Gdx.graphics.getDeltaTime(),6,2);
					 if (!Game.monde.isLocked()) {
					 
					 Game.monde.destroyBody(fa.getBody());
					 Game.monde.destroyBody(fb.getBody());
					 fb.getBody().setUserData(null);
					 fa.getBody().setUserData(null);
					 Game.espace.asteroids.get(j).body=null;
					 Game.espace.bullets.get(i).body=null;
					 Game.espace.asteroids.remove(j);
					 Game.espace.bullets.remove(i);
					 Game.flag=false;
					 break;
					 }
				 }
			 }
		 }

	 }

	@Override
	public void endContact(Contact arg0) {
		
		
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}  

}