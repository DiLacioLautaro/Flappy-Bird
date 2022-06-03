package com.proyectoxcliente.game.pantallas;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// CLASE UTILIZADA PARA ADMINISTRAR Y MANEJAR TODOS LOS STATES(CLASES DE LAS PANTALLAS) QUE SE ENCUENTREN EN NUESTRO JUEGO
// FACILITA LA APLICACION DE POLIMORFISMO

public class GameStateManager {
	protected int score;
	private Stack<State> states; // SE CREA UN ATRIBUTO DE TIPO PILA PARA ALMACENAR LOS STATES Y PODER ADMINISTRARLOS 

	public GameStateManager() {
		states = new Stack<State>(); // SE CREA EL CONSTRUCTOR Y SE INSTACIA LA PILA. 
	}

	public void push(State state) { // METODO UTILIZADO PARA INSERTAR UN STATE A LA PILA (INGRESAR LA PANTALLA CREADA)
		states.push(state);
	}

	public void pop() { // METODO UTILIZADO PARA ELIMINAR EL ULTIMO STATE QUE SE INSERTO A LA PILA
		states.pop().dispose();
		
	}

	public void set(State state) { //METODO PARA SETEAR UN ESTADO DENTRO DE LA PILA. 
		states.pop().dispose();  //SE REMPLAZA UN STATE YA EXISTENTE
		states.push(state);

	}

	public void update(float dt) { //PEEK: AYUDA A REVISAR QUE PANTALLA SE ENCUENTRA DENTRO DE LA PILA  
		states.peek().update(dt);
	}

	public void render(SpriteBatch spr) {
		states.peek().render(spr);
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public int obtenerPuntaje() {
		return score;
	}
}
