package com.proyectoxcliente.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.proyectoxcliente.game.elementos.Texto;
import com.proyectoxcliente.game.io.Entradas;
import com.proyectoxcliente.game.utiles.Config;
import com.proyectoxcliente.game.utiles.Recursos;
import com.proyectoxcliente.game.utiles.Render;

public class PantallaFinalJuegoSingleplayer extends State {
	private Texture fondo, gameOver;
	private Entradas entradas = new Entradas(this);

	private Texto opciones[] = new Texto[2];
	private String textos[] = { "REINTENTAR", "MENU PRINCIPAL" };
	private BitmapFont puntuacionTotalTexto;
	private String puntuacionTotal;
	private BitmapFont puntuacionFinal;
	private int opc = 0, avance = 25;

	public PantallaFinalJuegoSingleplayer(GameStateManager gsm) {

		super(gsm);
		puntuacionTotalTexto = new BitmapFont();
		puntuacionFinal = new BitmapFont();
		gameOver = new Texture(Recursos.GAMEOVER);
		fondo = new Texture(Recursos.FONDOMAPA);
		cam.setToOrtho(false, Config.ANCHO / 2, Config.ALTO / 2);
		Gdx.input.setInputProcessor(entradas);

		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTEMENU, 19, Color.WHITE, true);
			opciones[i].setTexto(textos[i]);
			opciones[i].setPosition((Config.ANCHO / 4) - (opciones[i].getAncho() / 2),
					(Config.ALTO / 4) + (opciones[i].getAlto()) - ((opciones[i].getAlto() + avance) * i));
		}

	}

	@Override
	public void render(SpriteBatch spr) {
		spr = Render.spr;
		Render.LimpiarPantalla();
		puntuacionTotal = "PUNTAJE FINAL: ";
		spr.setProjectionMatrix(cam.combined);
		spr.begin();
		spr.draw(fondo, 0, 0, Config.ANCHO / 2, Config.ALTO / 2);
		spr.draw(gameOver, 25, 280, 192, 42);

		for (int i = 0; i < opciones.length; i++) {
			opciones[i].dibujar();
		}

		puntuacionTotalTexto.draw(spr, puntuacionTotal, cam.position.x - 70,
				cam.position.y - 310 + (cam.viewportHeight / 2) - 5f);

		puntuacionFinal.draw(spr, Integer.toString(gsm.obtenerPuntaje()), cam.position.x + 60,
				cam.position.y - 310 + (cam.viewportHeight / 2) - 5f);

		if (entradas.isEnter()) {
			sonido.setUpMusicSalto();

			if (opc == 1) {
				gsm.set(new Mapa(gsm));
			} else {
				gsm.set(new PantallaMenu(gsm));
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

		System.out.println("Pantalla Derrota disposeado");

	}

	@Override
	protected void handleInput() {

	}

	@Override
	public void update(float dt) {

	}

}
