package com.proyectox.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.proyectox.game.elementos.Texto;
import com.proyectox.game.io.Entradas;
import com.proyectox.game.utiles.Config;
import com.proyectox.game.utiles.Recursos;
import com.proyectox.game.utiles.Render;

public class PantallaFinalJuego extends State {

	private Texture fondo, gameOver;

	private Entradas entradas = new Entradas(this);
	private Texto opciones[] = new Texto[1];
	private String textos[] = { "SALIR" };
	private String jugTexto;
	private boolean jugGanador = false;
	private BitmapFont jugadorGanador;
	private int opc = 0, avance = 25;

	public PantallaFinalJuego(GameStateManager gsm, boolean jugGanador) {
		super(gsm);
		this.jugGanador = jugGanador;
		setGanador();

		jugadorGanador = new BitmapFont();
		gameOver = new Texture(Recursos.GAMEOVER);
		fondo = new Texture(Recursos.FONDOMAPA);
		cam.setToOrtho(false, Config.ANCHO / 2, Config.ALTO / 2);
		Gdx.input.setInputProcessor(entradas);

		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTE, 19, Color.WHITE, true);
			opciones[i].setTexto(textos[i]);
			opciones[i].setPosition((Config.ANCHO / 4) - (opciones[i].getAncho() / 2),
					(Config.ALTO / 4) + (opciones[i].getAlto()) - ((opciones[i].getAlto() + avance) * i));
		}

	}

	public void setGanador() {

		if (jugGanador) {

			jugTexto = ("GANO EL PAJARO AMARILLO");

		} else {
			jugTexto = ("GANO EL PAJARO CELESTE");

		}
	}

	@Override
	public void render(SpriteBatch spr) {
		spr = Render.spr;
		Render.LimpiarPantalla();
		spr.setProjectionMatrix(cam.combined);
		spr.begin();
		spr.draw(fondo, 0, 0, Config.ANCHO / 2, Config.ALTO / 2);
		spr.draw(gameOver, 25, 280, 192, 42);
		jugadorGanador.draw(spr, jugTexto, cam.position.x -100, cam.position.y - 310 + (cam.viewportHeight / 2) - 5f);

		for (int i = 0; i < opciones.length; i++) {
			opciones[i].dibujar();
		}
		if (entradas.isEnter()) {
			if (opc == 1) {
				Gdx.app.exit();
			}
		
		}

		for (int i = 0; i < opciones.length; i++) {
			if (entradas.getMouseX() / 2 >= opciones[i].getX()
					&& entradas.getMouseX() / 2 <= opciones[i].getX() + opciones[i].getAncho()) {
				if (entradas.getMouseY() / 2 >= opciones[i].getY() - opciones[i].getAlto()
						&& entradas.getMouseY() / 2 <= opciones[i].getY()) {
					opc = i + 1;
				}
			}
		}

		for (int i = 0; i < opciones.length; i++) {
			if (i == (opc - 1)) {
				opciones[i].setColor(Color.YELLOW);
			} else {
				opciones[i].setColor(Color.WHITE);
			}
		}
		spr.end();

	}

	@Override
	public void dispose() {
		fondo.dispose();
		gameOver.dispose();
		System.out.println("Pantalla Derrota Disposeada");

	}

	@Override
	protected void handleInput() {

	}

	@Override
	public void update(float dt) {

	}

}
