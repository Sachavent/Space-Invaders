package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Asteroid {
	   static public float largeur;   
	    static public float hauteur;
	 
    Vector2     position = new Vector2();
    Rectangle   bounds = new Rectangle();
    
    Texture img;			
	Sprite sprite;
	Body body;
 
    public Asteroid(Vector2 pos) {
    	img = new Texture("image/asteroid.png");
    	this.position = pos;
        this.bounds.width = img.getWidth();
        this.bounds.height = img.getHeight();
        this.bounds.x = this.position.x;
        this.bounds.y = this.position.y;
        
        
        largeur=img.getHeight();
		hauteur=img.getHeight();
		
		sprite = new Sprite(img);
  	    sprite.setPosition(position.x , position.y);
  		 	    
  	    BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX(), sprite.getY());
        body = Game.monde.createBody(bodyDef);
        // Comme pour le vaisseau on crée un cercle de collision car plus adapté à la forme
        CircleShape circ = new CircleShape();
        circ.setRadius(sprite.getWidth()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circ;
        fixtureDef.density = 1000000000;
        Fixture fixture = body.createFixture(fixtureDef);

        circ.dispose();
        body.setUserData("asteroid");
    }
    
}
