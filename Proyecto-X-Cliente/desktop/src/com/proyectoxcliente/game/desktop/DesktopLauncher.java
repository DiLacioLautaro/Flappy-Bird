package com.proyectoxcliente.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.proyectoxcliente.game.ProyectoXCliente;
import com.proyectoxcliente.game.utiles.Config;


public class DesktopLauncher {
	public static void main (String[] arg) { 
	//CLIENTE 	CLIENTE 	CLIENTE 	CLIENTE 	CLIENTE 	CLIENTE 	CLIENTE 	CLIENTE 	CLIENTE 	CLIENTE 	CLIENTE 	CLIENTE 	
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		new LwjglApplication(new ProyectoXCliente(), config);

		config.width = Config.ANCHO;
		config.height = Config.ALTO;
		config.resizable = false;
		config.title = "CLIENTE FLAPPY BIRD";
	}
}
	