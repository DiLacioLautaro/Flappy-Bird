package com.proyectoxcliente.game.sonidos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.proyectoxcliente.game.utiles.Config;
import com.proyectoxcliente.game.utiles.Recursos;

public class Musica {

	public Music cancionMenu, cancionJuego;

	public void setUpMusicMenu() {
		cancionMenu = Gdx.audio.newMusic(Gdx.files.internal(Recursos.CANCIONMENU));
		cancionMenu.setLooping(true);
		cancionMenu.setVolume(Config.VOLUMEN);

		cancionMenu.play();
	}

	public void setUpMusicJuego() {
		cancionJuego = Gdx.audio.newMusic(Gdx.files.internal(Recursos.CANCIONJUEGO));
		cancionJuego.setLooping(true);
		cancionJuego.setVolume(Config.SONIDOS);
		cancionJuego.play();
	}



	public void dispose() {
	}
}