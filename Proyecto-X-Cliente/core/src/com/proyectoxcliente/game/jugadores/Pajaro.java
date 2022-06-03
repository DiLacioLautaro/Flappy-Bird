package com.proyectoxcliente.game.jugadores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.proyectoxcliente.game.animaciones.Animacion;
import com.proyectoxcliente.game.sonidos.Sonidos;
import com.proyectoxcliente.game.utiles.Global;
import com.proyectoxcliente.game.utiles.Recursos;

public class Pajaro extends Actor {

	private Vector3 position;
	private Vector3 velocity;
	private Rectangle bounds;

	private Animacion birdAnimacionAmarillo, birdAnimacionCeleste;
	private Texture texBird1, texBird2;
	protected Sonidos sonido;
	private static final int GRAVITY = -10 ; // GRAVEDAD DEL PAJARO
	private static final int MOVEMENT = 100; // VELOCIDAD EN LA CUAL SE VA A MOVER POR EL EJE X

	public Pajaro(int x, int y) {
		// SE CREA EL VECTOR 3 DE POSICION SE UTILIZA PARA INDICAR LA POSICION EN X, Y,Z
		// DEL
		// PAJARO DEL PLAYER

		position = new Vector3(x, y, 0);

		// SE CREA EL VECTOR 3 DE VELOCIDAD SE UTILIZA PARA INDICAR LA VELOCIDAD CON LA
		// CUAL SE
		// VA A MOVER EN X,Y,Z
		velocity = new Vector3(0, 0, 0);
		sonido = new Sonidos();

		// SE CREA LA TEXTURA QUE VA A CONTENER EL DIBUJO DEL PAJARO
		texBird1 = new Texture(Recursos.PAJAROAMARILLO);
		if (!Global.singlePlayer) {
			texBird2 = new Texture(Recursos.PAJAROCELESTE);
		}
		// SE CREAN LAS ANIMACIONES QUE SE VAN A UTILIZAR PARA LOS PAJAROS DE LOS PLAYER
		birdAnimacionAmarillo = new Animacion(new TextureRegion(texBird1), 3, 0.5f);
		if (!Global.singlePlayer) {
			birdAnimacionCeleste = new Animacion(new TextureRegion(texBird2), 3, 0.5f);
		}
		// SE CREAN LAS COLISIONES RECTANGULARES QUE SE VAN A UTILIZAR PARA EL PAJARO DE
		// CADA PLAYER
		bounds = new Rectangle(x, y, texBird1.getWidth() / 3.5f, texBird1.getHeight());
		if (!Global.singlePlayer) {
			bounds = new Rectangle(x, y, texBird2.getWidth() / 3.5f, texBird2.getHeight());
		}
	}

	public void update(float dt) {
		// SE UPDATEA LA ANIMACION

		birdAnimacionAmarillo.update(dt);
//		if (!Global.singlePlayer) {
//			birdAnimacionCeleste.update(dt);
//
//		}

		if (position.y > 0) { // SE LE AGREGA GRAVEDAD AL PAJARO AL TENER UNA POSICION MAYOR A 0
			velocity.add(0, GRAVITY, dt);

		}
		velocity.scl(dt); // SE ACTUALIZA LA VELOCIDAD CON LA GRAVEDAD ASIGNADA CONSTATEMENTE
		position.add(MOVEMENT * dt, velocity.y, 0);

		// CONDICION UTILIZADA PARA LIMITAR LA POSICION DEL PAJARO AL BAJAR MAS DE 0
		if (position.y < 0) {
			position.y = 0;

		}
		velocity.scl(1 / dt); // ESCALA UTILIZADA PARA QUE SE ASIGNE LA VELOCIDAD AL PAJARO UNA VEZ CADA
								// SEGUNDO
		bounds.setPosition(position.x, position.y);
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	// ESTOS GETTERS VAN A SER UTILIZADOS PARA DIBUJAR EL PAJARO CON SU RESPECTIVA
	// ANIMACION EN EL MAPA DEL JUEGO

	public TextureRegion getTextureAmarillo() {
		return birdAnimacionAmarillo.getFrame();

	}

	public TextureRegion getTextureCeleste() {
		return birdAnimacionCeleste.getFrame();

	}

	public void jump() {
		// SE INDICA LA VELOCIDAD DE SALTO QUE VA A TENER EL PAJARO EN EL EJE Y. ADEMAS
		// DE SETEARLE AL PAJARO UN SONIDO DE SALTO
		velocity.y = 250;
		sonido.setUpMusicSalto();
	}

	public Rectangle getBounds() {
		return bounds;

	}

	public void dispose() {
		texBird1.dispose();
		if (!Global.singlePlayer) {
			texBird2.dispose();
		}
	}

}
