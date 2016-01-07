package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Fonction permettant d'afficher des informations tel que les scores
public class Indication {
		BitmapFont font;
		CharSequence str;
		CharSequence presentation;
		CharSequence presentationbis;
		CharSequence lancement;
		CharSequence pause;
		CharSequence gameover;
		CharSequence pauseinfo;
		CharSequence replay;
	public Indication() {
		font = new BitmapFont();
		str="voilà le score:"+Game.score;
		}
	public void presente(SpriteBatch sprite) {
		presentation="Aide: -Appuyez sur espace pour tirer";
		presentationbis="-Flèche directionnelle gauche/ droite: déplacement";
		lancement="Pour commencer: appuyez sur F";
		font.draw(sprite,presentation,30,340);
		font.draw(sprite,presentationbis,65,320);
		font.draw(sprite,lancement,70,280);
	}
	
	
	public void affichagejeu(SpriteBatch sprite) {
		str="Sauvetages: "+Game.score;
		pause="Press P: Pause";
		font.draw(sprite, str, 300, 600);
		font.draw(sprite,pause,295,580);
	}
	
	public void gameOver(SpriteBatch sprite) {
		gameover="VOUS AVEZ DETRUIT "+Game.score+" ASTEROIDS";
		replay="Appuyez sur R pour rejouer";
		font.draw(sprite,gameover,70,300);
		font.draw(sprite, replay, 110, 280);
	}
	
	public void gamepause(SpriteBatch sprite) {
		pauseinfo="RAPPUYEZ SUR P POUR RELANCER";
		font.draw(sprite,pauseinfo,80,300);
		
	}
}
