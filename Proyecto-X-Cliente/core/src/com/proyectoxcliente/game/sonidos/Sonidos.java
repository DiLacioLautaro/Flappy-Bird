package com.proyectoxcliente.game.sonidos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.proyectoxcliente.game.utiles.Config;
import com.proyectoxcliente.game.utiles.Recursos;

public class Sonidos {
	private Sound sonidoSalto;

	public void setUpMusicSalto() {
		sonidoSalto = Gdx.audio.newSound(Gdx.files.internal(Recursos.SONIDOSALTO));
		sonidoSalto.setVolume(0, Config.SONIDOS);
		sonidoSalto.play();
	}
}
