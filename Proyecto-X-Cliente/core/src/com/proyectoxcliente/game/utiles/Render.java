package com.proyectoxcliente.game.utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.proyectoxcliente.game.ProyectoXCliente;

public abstract class Render {
	
	public static SpriteBatch spr = new SpriteBatch();
	
	public static ProyectoXCliente app;
	
	public static void LimpiarPantalla() {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
	
	}
	
}
