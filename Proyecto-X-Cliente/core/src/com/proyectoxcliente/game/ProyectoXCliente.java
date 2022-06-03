package com.proyectoxcliente.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.proyectoxcliente.game.pantallas.GameStateManager;
import com.proyectoxcliente.game.pantallas.PantallaCarga;
import com.proyectoxcliente.game.utiles.Render;

public class ProyectoXCliente extends Game {

	private GameStateManager gsm;

	@Override
	public void create() {

		Render.app = this;

		gsm = new GameStateManager();
		gsm.push(new PantallaCarga(gsm));
		
	
	}

	@Override
	public void render() {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(Render.spr);

	}

	@Override
	public void dispose() {

		super.dispose();

	}

}