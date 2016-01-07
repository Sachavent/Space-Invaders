package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Projectile {
	
	static public float largeur1;   
    static public float hauteur1;
    
    Texture img1;			
	Sprite sprite1;
	Body body1;
	
	static public float largeur;   
	static public float hauteur;
	    
	Texture img;			
	Sprite sprite;
	Body body;
	 
	public Projectile(Vector2 position) {
	        img1 = new Texture("image/bullet.png");
			largeur1=img1.getWidth();
			hauteur1=img1.getHeight();
	        
			sprite1 = new Sprite(img1);
	  	    sprite1.setPosition(position.x , position.y);
	  		 	    
	  	    BodyDef bodyDef = new BodyDef();
	        bodyDef.type = BodyDef.BodyType.DynamicBody;
	        bodyDef.position.set(sprite1.getX(), sprite1.getY());
	        body1 = Game.monde.createBody(bodyDef);
	        // Pour gérer les collisions on crée un cercle
	        CircleShape circ = new CircleShape();
	        circ.setRadius(sprite1.getWidth()/2);
	        circ.setRadius(sprite1.getWidth()/2);

	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = circ;
	        fixtureDef.density = 1;
	        
	        Fixture fixture = body1.createFixture(fixtureDef);

	        circ.dispose();
	        body1.setUserData("tir");
	    }
	
		
}
