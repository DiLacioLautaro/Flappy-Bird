package com.proyectoxcliente.game.mapaelementos;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.proyectoxcliente.game.utiles.Recursos;

public class Tubos {

	private static final int FLUCTUATION = 67; // NOS INDICA LA RESTRICCION EJE Y, EN EL CUAL SE PUEDE MOVER EL TUBO DE ARRIBA 
	private static final int TUBE_GAP = 150; // DISTANCIA QUE EXISTE ENTRE EL TUBO DE ARRIBA Y EL TUBO DE ABAJO
	private static final int LOWEST_OPENING = 120; // NOS INDICA LA RESTRICCION EJE Y, EN EL CUAL SE PUEDE MOVER EL TUBO DE ABAJO 
	private Texture topTube;
	private Texture bottomTube;
	private Vector2 posicionTopTube; // VECTOR PARA DETERMINAR LA POSICION DEL TUBO DE ARRIBA
	private Vector2 posicionBotTube;// VECTOR PARA DETERMINAR LA POSICION DEL TUBO DE ARRIBA
	private Rectangle boundsTop;// COLISIONES PARA LOS TUBOS DE ABAJO
	private Rectangle boundsBot;// COLISIONES PARA LOS TUBOS DE ARRIBA
	private Random r;

	public Tubos(float x) { // CONSTRUCTOR PARA EL SINGLEPLAYER
		
		// SE CREAN LAS TEXTURAS DE LOS TUBOS,
		topTube = (new Texture(Recursos.TOPTUBE));
		bottomTube = (new Texture(Recursos.BOTTUBE));
		r = new Random();
		
		// SE  INDICA LA POSICION EN EL EJE Y, LA CUAL SE VA A ENCONTRAR UBICADOS DENTRO DEL VECTOR
		posicionTopTube = (new Vector2(x, r.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING));
		posicionBotTube = (new Vector2(x, posicionTopTube.y - TUBE_GAP - bottomTube.getHeight()));
		
		//SE CREA LA COLISION RECTANGULAR QUE VA A HABER EN LOS TUBOS DE ARRIBA Y ABAJO
		boundsTop = new Rectangle(posicionTopTube.x, posicionTopTube.y, topTube.getWidth(), topTube.getHeight());
		boundsBot = new Rectangle(posicionBotTube.x, posicionBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());

	}
	
	// METODO UTILIZADO PARA LA REPOSICION DE LOS TUBOS
	
	public void reposicionar(float x) { //METODO SINGLEPLAYER
		
		// SE SETEA LA POSICION DE LOS TUBOS DE ARRIBA Y ABAJO DENTRO DEL EJE Y
		posicionTopTube.set(x, r.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
		posicionBotTube.set(x, posicionTopTube.y - TUBE_GAP - bottomTube.getHeight());
		
		//SE CREA LA COLISION RECTANGULAR QUE VA A HABER EN LOS TUBOS DE ARRIBA Y ABAJO

		boundsTop.setPosition(posicionTopTube.x, posicionTopTube.y);
		boundsBot.setPosition(posicionBotTube.x, posicionBotTube.y);
	}
	
	// CONSTRUCTOR QUE SE UTILIZA PARA RECIBIR LAS POSICIONES DE LOS TUBOS DEL SERVIDOR PARA EL MULTIPLAYER
	public Tubos(float x, float y) { //CONSTRUCTOR MULTIPLAYER
		
		// SE CREAN LAS TEXTURAS DE LOS TUBOS,
		topTube = (new Texture(Recursos.TOPTUBE));
		bottomTube = (new Texture(Recursos.BOTTUBE));
		
		//SE INDICA SOLAMENTE LA POSICION DENTRO DEL EJE Y DEL BOT TUBE YA QUE EL RESTO SE PASARA POR PARAMETROS
		posicionTopTube = (new Vector2(x, y));
		posicionBotTube = (new Vector2(x, posicionTopTube.y - TUBE_GAP - bottomTube.getHeight()));
		
		//SE CREA LA COLISION RECTANGULAR QUE VA A HABER EN LOS TUBOS DE ARRIBA Y ABAJO
		boundsTop = new Rectangle(posicionTopTube.x, posicionTopTube.y, topTube.getWidth(), topTube.getHeight());
		boundsBot = new Rectangle(posicionBotTube.x, posicionBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
	}



	// METODO UTILIZADO PARA RECIBIR LA REPOSICION DE LOS TUBOS DEL SERVIDOR PARA EL MULTIPLAYER
	
	public void reposicionar(float x, float y) {//METODO MULTIPLAYER
		
		//SE INDICA SOLAMENTE LA REPOSICION DENTRO DEL EJE Y DEL BOT TUBE YA QUE EL RESTO SE PASARA POR PARAMETRO

		posicionTopTube.set(x, y);
		posicionBotTube.set(x, posicionTopTube.y - TUBE_GAP - bottomTube.getHeight());
		
		//SE CREA LA COLISION RECTANGULAR QUE VA A HABER EN LA REPOSICION TUBOS DE ARRIBA Y ABAJO

		boundsTop.setPosition(posicionTopTube.x, posicionTopTube.y);
		boundsBot.setPosition(posicionBotTube.x, posicionBotTube.y);
	}

	public boolean colisionar(Rectangle player) {
		return player.overlaps(boundsTop) || player.overlaps(boundsBot);

	}

	public Texture getTopTube() {
		return topTube;
	}

	public Texture getBottomTube() {
		return bottomTube;
	}

	public Vector2 getPosicionTopTube() {
		return posicionTopTube;
	}

	public Vector2 getPosicionBotTube() {
		return posicionBotTube;
	}

	public void setTopTube(Texture topTube) {
		this.topTube = topTube;
	}

	public void setBottomTube(Texture bottomTube) {
		this.bottomTube = bottomTube;
	}

	public void setPosicionTopTube(Vector2 posicionTopTube) {
		this.posicionTopTube = posicionTopTube;
	}

	public void setPosicionBotTube(Vector2 posicionBotTube) {
		this.posicionBotTube = posicionBotTube;
	}

	public void dispose() {
		topTube.dispose();
		bottomTube.dispose();
	}

}
