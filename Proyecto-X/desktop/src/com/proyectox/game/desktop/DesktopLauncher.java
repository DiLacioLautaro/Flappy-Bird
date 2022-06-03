package com.proyectox.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.proyectox.game.ProyectoX;
import com.proyectox.game.utiles.Config;

public class DesktopLauncher {
	public static void main(String[] arg) {
		// SERVIDOR SERVIDOR SERVIDOR SERVIDOR SERVIDOR SERVIDOR SERVIDOR SERVIDOR
	
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		new LwjglApplication(new ProyectoX(), config);

		config.width = Config.ANCHO;
		config.height = Config.ALTO;

		config.resizable = false;

		config.title = "SERVIDOR FLAPPY BIRD";
	}
}
