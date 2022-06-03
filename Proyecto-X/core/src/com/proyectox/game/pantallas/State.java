package com.proyectox.game.pantallas;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.proyectox.game.utiles.Render;

public abstract class State {
	protected OrthographicCamera cam;
	protected GameStateManager gsm;
	protected Render r;

	public State(GameStateManager gsm) {
		this.gsm = gsm;
		cam = new OrthographicCamera();
	}

	protected abstract void handleInput();

	public abstract void update(float dt);

	public abstract void render(SpriteBatch spr);

	public abstract void dispose();
}
