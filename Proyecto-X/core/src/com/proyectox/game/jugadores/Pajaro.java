package com.proyectox.game.jugadores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.proyectox.game.utiles.Global;
import com.proyectox.game.utiles.Recursos;

public class Pajaro extends Actor {

	private Vector3 position;
	private Vector3 velocity;
	private Rectangle bounds;

	private Texture texBird1, texBird2;

	private static final int GRAVITY = -15;
	private static final int MOVEMENT = 100;

	public Pajaro(int x, int y) {
		position = new Vector3(x, y, 0);
		velocity = new Vector3(0, 0, 0);
		
		texBird1 = new Texture(Recursos.PAJAROAMARILLO);
		if (!Global.singlePlayer) {
			texBird2 = new Texture(Recursos.PAJAROCELESTE);
		}
	
		bounds = new Rectangle(x, y, texBird1.getWidth() / 3.5f, texBird1.getHeight());
		if (!Global.singlePlayer) {
			bounds = new Rectangle(x, y, texBird2.getWidth() / 3.5f, texBird2.getHeight());
		}
	}

	public void update(float dt) {

		if (position.y > 0) {
			velocity.add(0, GRAVITY, dt);

		}
		velocity.scl(dt);
		position.add(MOVEMENT * dt, velocity.y, 0);
		if (position.y < 0) {
			position.y = 0;

		}
		velocity.scl(1 / dt);
		bounds.setPosition(position.x, position.y);
	}

	public Vector3 getPosition() {
		return position;
	}

	public void jump() {
		velocity.y = 250;
	}

	public void isNotJump() {
		velocity.y = 0;
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
