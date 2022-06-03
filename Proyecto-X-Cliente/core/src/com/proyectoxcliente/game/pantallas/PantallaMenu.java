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

public class PantallaMenu extends State {

	private Texture fondoMenu;
	private Entradas entradas = new Entradas(this);
	private Texto opciones[] = new Texto[3];
	private String textos[] = { "JUGAR", "OPCIONES", "SALIR" };
	private int opc = 0, avance = 25;

	public PantallaMenu(GameStateManager gsm) {
		super(gsm);
		fondoMenu = new Texture(Recursos.FONDO);
		cam.setToOrtho(false, Config.ANCHO / 2, Config.ALTO / 2);
		Gdx.input.setInputProcessor(entradas);

		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTEMENU, 20, Color.WHITE, true);
			opciones[i].setTexto(textos[i]);
			opciones[i].setPosition((Config.ANCHO / 4) - (opciones[i].getAncho() / 2),
					(Config.ALTO / 4) + (opciones[i].getAlto()) - ((opciones[i].getAlto() + avance) * i));
		}

	}

	@Override
	public void render(SpriteBatch spr) {

		spr = Render.spr;

		Render.LimpiarPantalla();
		spr.setProjectionMatrix(cam.combined);

		spr.begin();
		spr.draw(fondoMenu, 0, 0, Config.ANCHO / 2, Config.ALTO / 2);

		for (int i = 0; i < opciones.length; i++) {
			opciones[i].dibujar();
		}

		if (entradas.isClick()) {
			sonido.setUpMusicSalto();			
			if (opc == 1) {

				gsm.set(new PantallaJugar(gsm));
			} else if (opc == 2) {

				gsm.set(new PantallaOpciones(gsm));
			} else {

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
		fondoMenu.dispose();
		System.out.println("Pantalla Menu disposeado");

	}

	@Override
	protected void handleInput() {

	}

	@Override
	public void update(float dt) {

	}

}
