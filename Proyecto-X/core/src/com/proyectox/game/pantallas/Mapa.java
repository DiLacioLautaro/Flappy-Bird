package com.proyectox.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.proyectox.game.jugadores.Pajaro;
import com.proyectox.game.mapaelementos.TubosOnline;
import com.proyectox.game.redservidor.HiloServidor;
import com.proyectox.game.utiles.Config;
import com.proyectox.game.utiles.Global;
import com.proyectox.game.utiles.Recursos;
import com.proyectox.game.utiles.Render;

public class Mapa extends State {

	private static final int TUBE_SPACING = 200;
	private static final int TUBO_CONTADOR = 4;
	private static final int PISO_Y_OFFSET = -30;
	private static final int TUBE_ANCHURA = 52;
	private Pajaro[] player;
	private Array<TubosOnline> tubes;
	private Texture piso;
	private Vector2 pisoPosicion1;
	private Vector2 pisoPosicion2;
	private boolean gano = false;

	private int posicionx = 70;
	private int posiciony = 320;
	private Stage[] stage;
	private Label[] label;
	private Skin[] skin;
	private Image[] img;
	private boolean bandera = false;
	protected boolean mouseAbajo = false;

	protected boolean mouseAbajo2 = false;
	private int numero = 2;

	public Mapa(GameStateManager gsm) {
		super(gsm);

		Global.hs = new HiloServidor(this);
		Global.hs.start();
		// SE CREA EL FONDO Y EL VECTOR DE PLAYERS

		player = new Pajaro[2];
		piso = new Texture(Recursos.PISO);

//SE CREAN A LOS DOS JUGADORES 
		for (int i = 0; i < player.length; i++) {
			player[i] = new Pajaro(posicionx / 2, posiciony / 2);

		}
		// SE SETEA EL TAMAÑO DE LA CAMARA(VIEWPORT) QUE SE VA A VER EN EL JUEGO

		cam.setToOrtho(false, Config.ANCHO / 2, Config.ALTO / 2);

		// SE CREA EL ARRAY DE TUBOS
		tubes = new Array<TubosOnline>();

		// SE INICIALIZA LA POSICION DEL PISO
		pisoPosicion1 = new Vector2(cam.position.x - cam.viewportWidth / 2, PISO_Y_OFFSET);
		pisoPosicion2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + piso.getWidth(), PISO_Y_OFFSET);

		// SE INICIALIZAN LAS VARIABLES UTILIZADAS PARA LOS STAGES DE LAS PANTALLAS DE
		// CARGA
		img = new Image[2];
		skin = new Skin[2];
		stage = new Stage[2];
		label = new Label[2];

		// SE CREA UNA PANTALLA NEGRO CON UN LABEL QUE DIRA "ESPERANDO JUGADORES " PARA
		// QUE AL MOMENTO DE JUGAR MULTIPLAYER Y SE NECESITE ESPERAR AL OTRO CLIENTE
		// PARA INICIAR EL JUEGO.

		// AL COMENZAR EL JUEGO EL SERVER SE MOSTRARA OTRA PANTALLA EN NEGRO DURANTE EL
		// TRANSCURSO DE LA PARTIDA
		// QUE DIRA "PARTIDA EN CURSO" Y TERMINARA DE MOSTRARSE HASTA EL MOMENTO DE QUE
		// ALGUN PLAYER PIERDA

		for (int i = 0; i < numero; i++) {
			img[i] = new Image();

			skin[i] = new Skin(Gdx.files.internal("ui/comic-ui.json"));

			stage[i] = new Stage();
			stage[i].addActor(img[i]);

			if (i == 0) {
				label[i] = new Label("ESPERANDO JUGADORES", skin[i], "title");
			} else {
				label[i] = new Label("JUGADORES EN PARTIDA", skin[i], "title");

			}
			label[i].setFontScale(0.5f);
			label[i].setPosition(Config.ANCHO / 2 - label[i].getWidth() / 2,
					Config.ALTO / 2 - label[i].getHeight() / 2);
			label[i].setAlignment(1);
			stage[i].addActor(label[i]);
		}

	}

	protected void handleInput() {

// SE INDICA QUE PLAYER SALTO EN BASE A LOS BOOLEANOS DECLARADOS EN EL CLIENTE

		if (mouseAbajo) {
			player[0].jump();
		} else if (mouseAbajo2) {
			player[1].jump();
		}

	}

	public void update(float dt) {
		if (Global.empieza) {
			handleInput();
			reposicionPiso(); // SE UPDATEA SOLO PARA LA COLISION ENTRE EL PAJARO Y EL PISO

			for (int i = 0; i < player.length; i++) {
				cam.position.x = getPlayer()[i].getPosition().x + 80;

			}
			for (int i = 0; i < player.length; i++) {
				player[i].update(dt);

			}

			for (int i = 0; i < tubes.size; i++) {

				TubosOnline tubos = tubes.get(i);
				if (cam.position.x - (cam.viewportWidth / 2) > tubos.getPosicionTopTube().x
						+ tubos.getTopTube().getWidth()) {
					tubos.reposicionar(tubos.getPosicionTopTube().x + ((TUBE_ANCHURA + TUBE_SPACING) * TUBO_CONTADOR),
							i);

				}
				for (int j = 0; j < player.length; j++) {
					if (tubos.colisionar(player[j].getBounds())) {
						Global.termina = true;
						if (j == 0) {
							gano = false;
						} else {
							gano = true;
						}
						Global.hs.enviarMensajeATodos("TerminaJuego*" + gano);

					}
				}

			}

			for (int i = 0; i < player.length; i++) {
				if (player[i].getPosition().y <= piso.getHeight() + PISO_Y_OFFSET) {
					Global.termina = true;
					if (i == 0) {
						gano = false;
					} else {
						gano = true;
					}
					Global.hs.enviarMensajeATodos("TerminaJuego*" + gano);

				}
			}
			if (Global.termina) {

				gsm.set(new PantallaFinalJuego(gsm, gano));
			}

			cam.update();

			// SE ENVIA MENSAJES A LOS CLIENTES PARA ACTUALIZAR LA POSICION EN X Y EN Y

			Global.hs.enviarMensajeATodos(
					"Actualizar*P1*" + player[0].getPosition().x + "*" + player[0].getPosition().y);
			Global.hs.enviarMensajeATodos(
					"Actualizar*P2*" + player[1].getPosition().x + "*" + player[1].getPosition().y);

			// CONDICION PARA MOSTRAR LA PANTALLA DE DERROTA EN EL SERVER

		}

	}

	@Override
	public void render(SpriteBatch spr) {
		Render.spr = spr;
		Render.LimpiarPantalla();

		if (!Global.empieza) {
			stage[0].act(Gdx.graphics.getDeltaTime());
			stage[0].draw();

		} else {
			spr.begin();
			spr.setProjectionMatrix(cam.combined);

			if (!bandera) {

				for (int j = 1; j <= TUBO_CONTADOR; j++) {
					tubes.add(new TubosOnline(j * (TUBE_SPACING + TUBE_ANCHURA)));
				}
				bandera = true;
			}
			stage[1].act(Gdx.graphics.getDeltaTime());
			stage[1].draw();
			spr.end();

		}

	}

	@Override
	public void dispose() {
		piso.dispose();

		for (int i = 0; i < player.length; i++) {
			getPlayer()[i].dispose();
		}
		for (TubosOnline tubos : tubes) {
			tubos.dispose();

		}
		System.out.println("Pantalla Mapa disposeada");
	}

	public void reposicionPiso() {
		if (cam.position.x - (cam.viewportWidth / 2) > pisoPosicion1.x + piso.getWidth()) {
			pisoPosicion1.add(piso.getWidth() * 2, 0);
		} else if (cam.position.x - (cam.viewportWidth / 2) > pisoPosicion2.x + piso.getWidth()) {
			pisoPosicion2.add(piso.getWidth() * 2, 0);

		}
	}

	public Pajaro[] getPlayer() {
		return player;
	}

	public void setMouseAbajo(boolean mouseAbajo) {
		this.mouseAbajo = mouseAbajo;
	}

	public void setMouseAbajo2(boolean mouseAbajo2) {
		this.mouseAbajo2 = mouseAbajo2;
	}
}
