package com.proyectox.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.proyectox.game.pantallas.GameStateManager;
import com.proyectox.game.pantallas.PantallaCarga;
import com.proyectox.game.utiles.Render;

public class ProyectoX extends Game {
	private GameStateManager gsm;

	@Override
	public void create() {

		Render.app = this;

		gsm = new GameStateManager();
		gsm.push(new PantallaCarga(gsm));

	}

	@Override
	public void render() {
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(Render.spr);


	}

	@Override
	public void dispose() {

		super.dispose();

	}

}