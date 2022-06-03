package com.proyectoxcliente.game.pantallas;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.proyectoxcliente.game.sonidos.Musica;
import com.proyectoxcliente.game.sonidos.Sonidos;
import com.proyectoxcliente.game.utiles.Render;

//CLASE ABSTRACTA  UTILIZADA PARA  FACILITAR LA IMPLEMENTACION DE OBJETOS Y METODOS
//A EL RESTO DE CLASES QUE NECESITEN    LA UTILIZACION DE LOS MISMOS
//SE APLICA POLIMORFISMO

public abstract class State {
	protected OrthographicCamera cam; // CAMARA UTILZADO PARA EL DESPLAZAMIENTO DE LA PANTALLA
	protected GameStateManager gsm; // ESTA CLASE SE CARACTERIZA PARA EL MANEJO DE LOS DISTINTOS STATES QUE SE
									// ENCUENTREN
	protected Render r;
	protected Musica musica;
	protected Sonidos sonido;

	public State(GameStateManager gsm) {
		this.gsm = gsm; // SE IGUALAN LAS VARIABLES Y LUEGO SE INICIALIZAN
		cam = new OrthographicCamera();
		musica = new Musica();
		sonido = new Sonidos();
	}

	protected abstract void handleInput(); // PARA MANEJAR LAS ENTRADAS EN CUALQUIER STATE

	public abstract void update(float dt); // METODO UTILIZADO PARA UPDATEAR EL JUEGO CONSTATEMENTE

	public abstract void render(SpriteBatch spr); // METODO UTILIZADO PARA RENDERIZAR EL JUEGO Y MOSTRARLO EN PANTALLA

	public abstract void dispose(); // METODO UTILIZADO PARA DISPOSEAR LOS OBJETOS QUE QUEDAN ALMACENADOS EN MEMORIA

}
