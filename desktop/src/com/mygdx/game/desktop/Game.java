package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Game implements ApplicationListener {
	
	public static final String TITLE = "Jeu java";
	public static final int V_WIDTH = 200;
	public static final int V_HEIGHT = 310;
	public static final int SCALE = 2;
	
	static boolean presentation= false;
	static int exit=0;
	static boolean replay=false;
	static boolean pause=false;
	static int score=0;
	static int tirtime=50;
	static boolean cantir=true;
	static boolean flag=false;
	static World monde;
	static SpriteBatch batch;
	Box2DDebugRenderer b2dr;
	private OrthographicCamera cam;
	static Space espace;
	//variable qui gere la creation d'asteroids
	int creation;
	Indication info;

	//Charger les différentes ressources nécessaires au jeu
	public void create() { 
		batch = new SpriteBatch();
		// C'est ici qu'on gère la gravité avec -0.98f (gravité terrestre)
		// Le vaisseau sera insensible à la gravité (c'est un super vaisseau u know!)
		monde = new World(new Vector2(0,-20f), true);
		b2dr= new Box2DDebugRenderer();
		cam = new OrthographicCamera();
		cam.setToOrtho(false,V_WIDTH*SCALE, V_HEIGHT*SCALE);
		espace=new Space();
		creation=150;
		info = new Indication();
		
	}
	
	//Methode qui fait office de boucle principale du jeu. Tout le rendu du jeu est dans cette méthode
	public void render() {
		try {
		if (!presentation){
			batch.begin();
			info.presente(batch);
			batch.end();
			Gdx.input.setInputProcessor(espace.vaisseau);
		}
		if (!pause && presentation == true) {
		//Fait "avancer" le monde : step by step
		if (!flag && exit==0) {
		monde.step(Gdx.graphics.getDeltaTime(),6,2);
		espace.asteroid_crash();
		}
		Gdx.input.setInputProcessor(espace.vaisseau);
		monde.setContactListener(espace.vaisseau);
		
		// Espace dans le temps les tir
		if (tirtime < 50) {
			tirtime++;
			cantir=false;
		} else { cantir=true; }
		
		//retire la gravité du vaisseau
		espace.vaisseau.antigravity();
		if (!espace.bullets.isEmpty()) {
			for (int compteur=0; compteur < espace.bullets.size(); compteur ++) {
				espace.bullets.get(compteur).body1.setGravityScale(0);;
			}
		}
		//gere à quelle vitesse tombe les asteroids chute d'asteroids
		if (creation < 170) { creation++; } 
		else {
		//Lance la fonction de chute et renitialise le compteur à 0
		// CHUTE ASTEROID LANCE ICI!
		espace.chuteAsteroids();
		creation=0;
		}
		
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//trace background
		batch.draw(espace.Background(),0,0);
		//trace vaisseau
		if (exit==0)
		batch.draw(espace.vaisseau.sprite,espace.vaisseau.body.getPosition().x-espace.vaisseau.largeur/2, espace.vaisseau.body.getPosition().y-espace.vaisseau.hauteur/2);
		
		// ATTENTION chute d'asteroids prevu!!!!!
		for (int compteur=0; compteur < espace.asteroids.size(); compteur++) {
			 batch.draw(espace.asteroids.get(compteur).sprite,espace.asteroids.get(compteur).body.getPosition().x-espace.asteroids.get(compteur).largeur/2, espace.asteroids.get(compteur).body.getPosition().y-espace.asteroids.get(compteur).hauteur/2);
		}
		
		if (!espace.bullets.isEmpty()) {
			for (int compteur=0; compteur < espace.bullets.size(); compteur ++) {
				batch.draw(espace.bullets.get(compteur).sprite1,espace.bullets.get(compteur).body1.getPosition().x-espace.bullets.get(compteur).largeur1/2, espace.bullets.get(compteur).body1.getPosition().y-espace.bullets.get(compteur).hauteur1/2);
			}
		}
		info.affichagejeu(batch);
		Texture imgbis = new Texture("image/terre.png");
		batch.draw(imgbis,-48,0);
		
		batch.end();
		//b2dr.render(monde,cam.combined);
		} 
		//Donc pause == true, donc on met le jeu en pause
		if (pause==true){
			pause();
		}
		//Gere la sortie du programme grace à la variable exit (quand le vaisseau est touché ou asteroids en dessous
				// d'un certain seuil
		if (exit==1) {
			destruction();
		}
		} catch (Exception e) { }
	}
	
	public void destruction () {
		// Texture destru= new Texture("image/explosion.png");

		espace.cleanespace();
		batch.begin();
		//Indique que le joueur a perdu et demande s'il veut rejouer
		//batch.draw(destru,espace.vaisseau.body.getPosition().x, espace.vaisseau.body.getPosition().y);
		info.gameOver(batch);
		batch.end();
		
		// Si l'utilisateur veut rejouer
		if (replay==true) {
			espace=new Space();
			replay=false;
			render();
		}
	
		
	}
	
	//Executé à la fin de l'exécution : existe pour détruire les objets crées
	public void dispose() { 
		
	}
	//Executé à chaque fois que l'écran est redimensionné
	public void resize(int w,int h) { 
			
	}
	// Executé quand le système sort de l'application pour faire autre chose
	public void pause() {
		batch.begin();
		info.gamepause(batch);
		batch.end();
		
	}
	// Quand l'application redevient l'application principale (après avoir été mis en pause)
	public void resume () { }

}
