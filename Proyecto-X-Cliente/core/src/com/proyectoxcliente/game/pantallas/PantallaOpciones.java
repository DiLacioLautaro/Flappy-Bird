package com.proyectoxcliente.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.proyectoxcliente.game.elementos.Texto;
import com.proyectoxcliente.game.io.Entradas;
import com.proyectoxcliente.game.utiles.Config;
import com.proyectoxcliente.game.utiles.Recursos;
import com.proyectoxcliente.game.utiles.Render;

public class PantallaOpciones extends State {

	private Entradas entradas = new Entradas(this);

	private Texto opciones[] = new Texto[2];
	private String opcT[] = { "MUSICA", "SONIDO" };

	private Texto botones[] = new Texto[5];
	private String textoBotones[] = { "-", "+", "-", "+", "VOLVER" };

	private Texture fondoOpciones;

	private int opc = 0, avance = 25;

	public PantallaOpciones(GameStateManager gsm) {
		super(gsm);
		fondoOpciones = new Texture(Recursos.FONDO);
		cam.setToOrtho(false, Config.ANCHO / 2, Config.ALTO / 2);
		Gdx.input.setInputProcessor(entradas);

		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTEMENU, 20, Color.WHITE, true);
			opciones[i].setTexto(opcT[i]);
			opciones[i].setPosition((Config.ANCHO / 4) - (opciones[i].getAncho() / 2),
					(Config.ALTO / 4) + (opciones[i].getAlto()) - ((opciones[i].getAlto() + avance) * i));
		}

		for (int i = 0; i < botones.length; i++) {

			botones[i] = new Texto(Recursos.FUENTEMENU, 20, Color.WHITE, true);
			botones[i].setTexto(textoBotones[i]);

			if (i == 0) {
				botones[i].setPosition(
						((Config.ANCHO / 4) - (botones[i].getAncho() / 2) - (opciones[i].getAncho() / 2) - 25),
						(Config.ALTO / 4) + (botones[i].getAlto() / 2));
			} else if (i == 1) {
				botones[i].setPosition(
						((Config.ANCHO / 4) - (botones[i].getAncho() / 2) + (opciones[i].getAncho() / 2) + 25),
						(Config.ALTO / 4) + (botones[i].getAlto() / 2));
			} else if (i == 2) {
				botones[i].setPosition(
						((Config.ANCHO / 4) - (botones[i].getAncho() / 2) - (opciones[1].getAncho() / 2) - 25),
						(Config.ALTO / 4) + (botones[i].getAlto() / 2) - (botones[i].getAlto() + avance));
			} else if (i == 3) {
				botones[i].setPosition(
						((Config.ANCHO / 4) - (botones[i].getAncho() / 2) + (opciones[1].getAncho() / 2) + 25),
						(Config.ALTO / 4) + (botones[i].getAlto() / 2) - (botones[i].getAlto() + avance));
			} else {
				botones[i].setPosition((Config.ANCHO / 4) - (botones[i].getAncho() / 2),
						(Config.ALTO / 4) + (botones[i].getAlto() / 2) - (botones[i].getAlto() + avance) * 2);
			}

		}

	}

	@Override
	public void render(SpriteBatch spr) {

		spr = Render.spr;
		Render.LimpiarPantalla();
		Render.spr.setProjectionMatrix(cam.combined);

		spr.begin();

		spr.draw(fondoOpciones, 0, 0, Config.ANCHO / 2, Config.ALTO / 2);

		for (int i = 0; i < opciones.length; i++) {
			opciones[i].dibujar();
		}
		for (int i = 0; i < botones.length; i++) {
			botones[i].dibujar();
		}

		if (entradas.isClick()) {
			sonido.setUpMusicSalto();
			if (opc == 1) {
				Config.VOLUMEN -=0.1f;
				verificarValor(Config.VOLUMEN);
				System.out.println("" + Config.VOLUMEN);

			} else if (opc == 2) {
				Config.VOLUMEN += 0.1f;
				verificarValor(Config.VOLUMEN);
				System.out.println("" + Config.VOLUMEN);

			} else if (opc == 3) {
				Config.SONIDOS -= 0.1f;
				verificarValor(Config.SONIDOS);
				System.out.println("" + Config.SONIDOS);

			} else if (opc == 4) {
				Config.SONIDOS += 0.1f;
				verificarValor(Config.SONIDOS);
				System.out.println("" + Config.SONIDOS);


			} else {
				gsm.set(new PantallaMenu(gsm));
			}

		}

		for (int i = 0; i < botones.length; i++) {
			if (entradas.getMouseX() / 2 >= botones[i].getX()
					&& entradas.getMouseX() / 2 <= botones[i].getX() + botones[i].getAncho()) {
				if (entradas.getMouseY() / 2 >= botones[i].getY() - botones[i].getAlto()
						&& entradas.getMouseY() / 2 <= botones[i].getY()) {
					opc = i + 1;
				}
			}
		}
		for (int i = 0; i < botones.length; i++) {
			if (i == (opc - 1)) {
				botones[i].setColor(Color.YELLOW);
			} else {
				botones[i].setColor(Color.WHITE);
			}
		}
		spr.end();

	}

	private float verificarValor(float valor) {
		if (valor <= 0) {
			return 0;
		}
		if (valor >= 1) {
			return 1;
		}
		return valor;
	}

	@Override
	public void dispose() {
		fondoOpciones.dispose();
		System.out.println("Pantalla Opciones disposeada");

	}

	@Override
	protected void handleInput() {

	}

	@Override
	public void update(float dt) {

	}

}