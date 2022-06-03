package com.proyectoxcliente.game.pantallas;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.proyectoxcliente.game.elementos.Imagen;
import com.proyectoxcliente.game.utiles.Config;
import com.proyectoxcliente.game.utiles.Recursos;
import com.proyectoxcliente.game.utiles.Render;

public class PantallaCarga extends State {

	private boolean fadeInTerminado = false, terminado = false;
	private float a = 0, contTiempo = 0, contTiempoT = 0, espera = 1, termina = 5;
	private Imagen logo;

	public PantallaCarga(GameStateManager gsm) {
		super(gsm);
		logo = new Imagen(Recursos.LOGO);
		logo.setAlpha(a);
		musica.setUpMusicMenu();

	}

	private void procesarFade() {

		logo.setAlpha(a);

		if (!fadeInTerminado) {
			a += 0.02f;
			if (a > 1) {
				a = 1;
				fadeInTerminado = true;
			}
		} else {
			contTiempo += 0.02f;
			if (contTiempo > espera) {
				a -= 0.02f;
				if (a < 0) {
					a = 0;
					terminado = true;
				}
			}
		}

		logo.setAlpha(a);

		if (terminado) {

			contTiempoT += 0.04f;
			if (contTiempoT > termina) {
				gsm.set(new PantallaMenu(gsm));
			}

		}

	}

	@Override
	public void render(SpriteBatch spr) {

		spr = Render.spr;
		Render.LimpiarPantalla();
		spr.begin();
		logo.dibujar();
		logo.setSize(Config.ANCHO, Config.ALTO);
		procesarFade();
		spr.end();

	}

	@Override
	public void dispose() {
	}

	@Override
	protected void handleInput() {

	}

	@Override
	public void update(float dt) {

	}

}