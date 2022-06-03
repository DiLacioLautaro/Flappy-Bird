package com.proyectox.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.proyectox.game.elementos.Texto;
import com.proyectox.game.io.Entradas;
import com.proyectox.game.utiles.Config;
import com.proyectox.game.utiles.Global;
import com.proyectox.game.utiles.Recursos;
import com.proyectox.game.utiles.Render;

public class PantallaJugar extends State {


	private int opc =0, avance = 25;

	private Entradas entradas = new Entradas(this);

	private Texto opciones[] = new Texto[2];
	private String textos[] = { "HOSTEAR", "VOLVER" };

	private Texture fondoPantallaJuego;

	public PantallaJugar(GameStateManager gsm) {
		super(gsm);

		Gdx.input.setInputProcessor(entradas);

		fondoPantallaJuego = new Texture(Recursos.FONDO);

		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTE, 20, Color.WHITE, true);
			opciones[i].setTexto(textos[i]);
			opciones[i].setPosition((Config.ANCHO / 4) - (opciones[i].getAncho() / 2),
					(Config.ALTO / 4) + (opciones[i].getAlto()) - ((opciones[i].getAlto() + avance) * i));
		}
	}

	@Override
	public void render(SpriteBatch spr) {

		spr = Render.spr;


		spr.begin();
		spr.draw(fondoPantallaJuego, 0, 0, Config.ANCHO / 2, Config.ALTO / 2);

		for (int i = 0; i < opciones.length; i++) {
			opciones[i].dibujar();
		}

		if (entradas.isClick()) {
			if (opc == 1) {
				Global.singlePlayer=false;
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
		fondoPantallaJuego.dispose();
		System.out.println("Pantalla Jugar disposeada");


	}

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub

	}

}