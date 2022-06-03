package com.proyectox.game.io;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.proyectox.game.pantallas.PantallaMenu;
import com.proyectox.game.pantallas.PantallaFinalJuego;
import com.proyectox.game.pantallas.PantallaJugar;
import com.proyectox.game.utiles.Config;

public class Entradas implements InputProcessor {

	private boolean abajo = false, arriba = false, enter = false, click = false;
	private int mouseX = 0, mouseY = 0;

	PantallaMenu app;

	PantallaJugar app2;
	
	PantallaFinalJuego app3;

	public Entradas(PantallaMenu app) {
		this.app = app;
	}

	public Entradas(PantallaJugar app) {
		this.app2 = app;
	}


	public Entradas(PantallaFinalJuego app) {
		this.app3 = app;
	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Keys.DOWN) {
			abajo = true;
		}

		if (keycode == Keys.UP) {
			arriba = true;
		}

		if (keycode == Keys.ENTER) {
			enter = true;
		}

		return false;
	}

	public boolean keyDownJugar(int keycode) {

		if (keycode == Keys.DOWN) {
			abajo = true;
		}

		if (keycode == Keys.UP) {
			arriba = true;
		}

		if (keycode == Keys.ENTER) {
			enter = true;
		}

		return false;
	}

	public boolean isEnter() {
		return enter;
	}

	public boolean isAbajo() {
		return abajo;
	}

	public boolean isArriba() {
		return arriba;
	}

	@Override
	public boolean keyUp(int keycode) {

		if (keycode == Keys.DOWN) {
			abajo = false;
		}

		if (keycode == Keys.UP) {
			arriba = false;
		}

		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		click = true;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		click = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouseX = screenX;
		mouseY = Config.ALTO - screenY;
		return false;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public boolean scrolled(int amount) {

		return false;
	}

	public boolean isClick() {
		return click;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

}
