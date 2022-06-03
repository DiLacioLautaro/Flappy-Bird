package com.proyectox.game.mapaelementos;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.proyectox.game.utiles.Global;
import com.proyectox.game.utiles.Recursos;

public class TubosOnline {

	private static final int FLUCTUATION = 67;
	private static final int TUBE_GAP = 120;
	private static final int LOWEST_OPENING = 120;
	private Texture topTube;
	private Texture bottomTube;
	private Vector2 posicionTopTube;
	private Vector2 posicionBotTube;
	private Rectangle boundsTop;
	private Rectangle boundsBot;
	private Random r;

	public TubosOnline(float x) {
		topTube = (new Texture(Recursos.TOPTUBE));
		bottomTube = (new Texture(Recursos.BOTTUBE));
		r = new Random();

		posicionTopTube = (new Vector2(x, r.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING));
		posicionBotTube = (new Vector2(x, posicionTopTube.y - TUBE_GAP - bottomTube.getHeight()));
		boundsTop = new Rectangle(posicionTopTube.x, posicionTopTube.y, topTube.getWidth(), topTube.getHeight());
		boundsBot = new Rectangle(posicionBotTube.x, posicionBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
		Global.hs.enviarMensajeATodos("PosicionTopTube*" + posicionTopTube.x + "*" + posicionTopTube.y);

	}

	public void reposicionar(float x, int i) {
		posicionTopTube.set(x, r.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
		posicionBotTube.set(x, posicionTopTube.y - TUBE_GAP - bottomTube.getHeight());
		boundsTop.setPosition(posicionTopTube.x, posicionTopTube.y);
		boundsBot.setPosition(posicionBotTube.x, posicionBotTube.y);
		Global.hs.enviarMensajeATodos("Reposicionar*TopTubo*" + posicionTopTube.x + "*" + posicionTopTube.y + "*" + i);

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
