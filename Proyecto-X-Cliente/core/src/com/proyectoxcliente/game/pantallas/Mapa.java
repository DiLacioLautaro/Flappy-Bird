package com.proyectoxcliente.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.proyectoxcliente.game.jugadores.Pajaro;
import com.proyectoxcliente.game.mapaelementos.Tubos;
import com.proyectoxcliente.game.redcliente.HiloCliente;
import com.proyectoxcliente.game.utiles.Config;
import com.proyectoxcliente.game.utiles.Global;
import com.proyectoxcliente.game.utiles.Recursos;
import com.proyectoxcliente.game.utiles.Render;

public class Mapa extends State {

	private static final int TUBE_SPACING = 200;// CONTIENE EL ESPACIO ENTRE EL FINAL Y EL PRINCIPIO DE UN TUBO
	private static final int TUBO_CONTADOR = 4; // CANTIDAD DE TUBOS QUE SE VAN A GENERAR
	private static final int PISO_Y_OFFSET = -30;// SE AGREGA UN ESPACIO DE PERDIDA PARA QUE SE VEA MAS CHICO DENTRO DEL
													// EJE Y (30 PIXELES MENOS)
	private static final int TUBE_ANCHURA = 52; // NOS INDICA LA DISTANCIA QUE VA A HABER ENTRE CADA TUBO

	private Pajaro[] player;
	private int numeroJugador = 0;
	private BitmapFont puntaje;
	private Texture fondo;
	private Array<Tubos> tubes;
	private Texture piso;
	private Vector2 pisoPosicion1;
	private Vector2 pisoPosicion2;
	private int posicionx = 70;
	private int posiciony = 320;
	private Stage stage;
	private Label esperandoJug;
	private Skin skin;
	private Image img;
	private int cont = 0;
	private boolean gano = false;

	public Mapa(GameStateManager gsm) {
		super(gsm);

		// SE CREA EL FONDO, EL PISO Y EL VECTOR DE PLAYERS
		fondo = new Texture(Recursos.FONDOMAPA);
		piso = new Texture(Recursos.PISO);

		player = new Pajaro[2];

		// SE CREA EL JUGADOR 1 YA QUE PARA EL SINGLE PLAYER NECESITA UN SOLO JUGADOR
		// SE INDICA LA POSICION EN LA QUE SE CREARA EL PAJARO
		getPlayer()[0] = new Pajaro(posicionx / 2, posiciony / 2 + 50);

		// SE SETEA EL TAMAÑO DE LA CAMARA(VIEWPORT) QUE SE VA A VER EN EL JUEGO
		cam.setToOrtho(false, Config.ANCHO / 2, Config.ALTO / 2);

		// SE CREA EL ARRAY DE TUBOS
		tubes = new Array<Tubos>();

		// SE CREA EL PUNTAJE QUE SE MOSTRARA EN PANTALLA DURANTE SE JUEGUE EL
		// SINGLEPLAYER
		puntaje = new BitmapFont();

		// SE INICIALIZA LA POSICION DEL PISO
		pisoPosicion1 = new Vector2(cam.position.x - cam.viewportWidth / 2, PISO_Y_OFFSET);
		pisoPosicion2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + piso.getWidth(), PISO_Y_OFFSET);

		if (!Global.singlePlayer) {// MULTIPLAYER

			// SE CREA AL PLAYER RESTANTE Y SE INSTANCIA Y STARTEA EL HILO CLIENTE
			getPlayer()[1] = new Pajaro(posicionx / 2, posiciony / 2);

			Global.hc = new HiloCliente(this);
			Global.hc.start();
			
			// SE CREA UNA PANTALLA NEGRO CON UN LABEL QUE DIRA "ESPERANDO JUGADORES " PARA
			// QUE AL MOMENTO DE JUGAR MULTIPLAYER Y SE NECESITE ESPERAR AL OTRO CLIENTE
			// PARA INICIAR EL JUEGO

			img = new Image();
			skin = new Skin(Gdx.files.internal("ui/comic-ui.json"));
			stage = new Stage();
			stage.addActor(img);

			esperandoJug = new Label("ESPERANDO JUGADORES", skin, "title");
			esperandoJug.setFontScale(0.5f);
			esperandoJug.setPosition(Config.ANCHO / 2 - esperandoJug.getWidth() / 2,
					Config.ALTO / 2 - esperandoJug.getHeight() / 2);
			esperandoJug.setAlignment(1);
			stage.addActor(esperandoJug);
		}

		if (Global.singlePlayer) {// SINGLEPLAYER

			//CREACION DE TUBOS (SE MANDA INFORMACION EN EL EJE X)
			for (int i = 1; i <= TUBO_CONTADOR; i++) {
				tubes.add(new Tubos(i * (TUBE_SPACING + TUBE_ANCHURA)));
			}

		}


	
	}

	@Override
	protected void handleInput() {

		// EN EL SINGLEPLAYER SOLO SE NECESITA INDICAR UN PAJARO PARA SALTAR

		if (Global.singlePlayer) { // SINGLEPLAYER
			if (Gdx.input.justTouched()) { // CUANDO SE PULSA EL CLICK
				getPlayer()[0].jump();
			}

			// EN CAMBIO EN EL MULTIPLAYER SE NECESITA ENVIAR UN MENSAJE AL SERVIDOR CUANDO
			// PULSA Y DEJA DE PULSAR PARA SALTAR. ESTO SE UTILIZA CON EL MOTIVO
			// DE QUE EL SERVIDOR POSTERIORMENTE IDENTIFIQUE DE QUE PLAYER LE LLEGA ESA
			// INFORMACION

		} else { // MULTIPLAYER
			if (Gdx.input.justTouched()) { // CUANDO SE PULSA EL CLICK
				Global.hc.enviarMensaje("ApreteSaltar");
			} else { // CUANDO SE DEJA DE PULSAR
				Global.hc.enviarMensaje("DejeApretarSaltar");
			}
		}
	}

	@Override
	public void update(float dt) {

		// SE UPDATEA EN EL MULTIPLAYER SOLO LAS ENTRADAS, LA REPOSICION DEL PISO, LOS
		// JUGADORES
		// Y LAS PANTALLAS DE FINALIZACION DEL JUEGO.

		if (!Global.singlePlayer) { // MULTIPLAYER
			handleInput();
			reposicionPiso();

			for (int i = 0; i < player.length; i++) {
				cam.position.x = getPlayer()[i].getPosition().x + 80;

			}

			if (Global.termina) {

				gsm.set(new PantallaDerrota(gsm, gano));

			}
			

			cam.update();

		
			// EN CAMBIA EN EL SINGLEPLAYER SE UPDATEA TODO ENTRADAS, REPOSICION DE
			// PISO,JUGADORES,COLISIONES,TUBOS, Y LA PANTALLA DE FINALIZACION DE JUEGO
	
		} else //SINGLEPLAYER
		{
			handleInput();
			reposicionPiso();
			getPlayer()[0].update(dt);
			cam.position.x = getPlayer()[0].getPosition().x + 80;

			if (tubes != null) { // SI EL TUBO ES DISTINTO DE NULL
				for (int i = 0; i < tubes.size; i++) { // SE RECORRE EL ARRAY DE TUBOS Y
					if (tubes.get(i) != null) { // SI LOS TUBOS QUE YA RECORRIO SON DISTINTOS DE NULL
						Tubos tubos = tubes.get(i); // SE IGUALAN VARIABLES
						if (cam.position.x - (cam.viewportWidth / 2) > tubos.getPosicionTopTube().x
								+ tubos.getTopTube().getWidth()) // Y SI CUMPLE ESTA CONDICION SOBRE EL DESPLAZAMIENTO
																	// DE LA CAMARA. ES DECIS SI LA CAMARA ES MAYOR A LA
																	// POSICION DEL TOPTUBE EN EL EJE X
						{
							// SE MANDA INFORMACION DEL EJE X PARA LA REPOSICION DEL NUEVO TUBO
							tubos.reposicionar(
									tubos.getPosicionTopTube().x + ((TUBE_ANCHURA + TUBE_SPACING) * TUBO_CONTADOR));

							// SE UTILIZA UN CONTADOR PARA EL CONTEO DE TUBOS EN SINGLEPLAYER
							cont++;
							gsm.setScore(cont);

						}

						// COLISION DEL PLAYER CON LOS TUBOS
						if (tubos.colisionar(getPlayer()[0].getBounds())) {
							gsm.set(new PantallaFinalJuegoSingleplayer(gsm));

						}

						// COLISION DEL PLAYER CON EL PISO
						if (getPlayer()[0].getPosition().y < piso.getHeight() + PISO_Y_OFFSET) {
							gsm.set(new PantallaFinalJuegoSingleplayer(gsm));

						}

					}

				}

			}
			cam.update();

		}

	}

	@Override
	public void render(SpriteBatch spr) {

		spr = Render.spr;
		Render.LimpiarPantalla();
		spr.setProjectionMatrix(cam.combined);

		// EL SETPROJECTIONMATRIX SE UTILIZA PARA DIBUJAR Y PODER MOSTRAR SOLO LOS
		// OBJETOS
		// QUE SE ENCUENTRAN VISIBLES DENTRO DEL RANGO DE LA CAMARA

		if (!Global.singlePlayer) { // MULTIPLAYER

			// SI EL JUEGO NO EMPEZO EN EL MULTIPLAYER SE DIBUJA SOLO LA PANTALLA QUE INDICA
			// QUE SE ESTA "ESPERANDO LOS JUGADORES"

			if (!Global.empieza) { // JUEGO NO EMPEZO
				stage.act(Gdx.graphics.getDeltaTime());
				stage.draw();

			} else {// JUEGO EMPEZO

				spr.begin();

				// SE DIBUJA EL FONDO
				spr.draw(fondo, cam.position.x - (cam.viewportWidth / 2), cam.position.y - (cam.viewportHeight / 2));

				// SE DIBUJA EL PLAYER 1 Y 2 CON SU RESPECTIVO PAJARO DE COLOR
				for (int i = 0; i < player.length; i++) {
					if (i == 0) {
						spr.draw(player[i].getTextureAmarillo(), player[i].getPosition().x,
								player[i].getPosition().y + 50, 23, 23);
					} else {
						spr.draw(player[i].getTextureCeleste(), player[i].getPosition().x, player[i].getPosition().y,
								23, 23);

					}
				}

				// SI EL TUBO ES DISTINTO DE NULL SE RECORRE EL ARRAY DE TUBOS PARA DIBUJAR
				// TANTO LOS DE ARRIBA COMO LOS DE ABAJO
				if (tubes != null) {
					for (Tubos tubos : tubes) {
						spr.draw(tubos.getTopTube(), tubos.getPosicionTopTube().x, tubos.getPosicionTopTube().y);
						spr.draw(tubos.getBottomTube(), tubos.getPosicionBotTube().x, tubos.getPosicionBotTube().y);
					}
				}

				// SE DIBUJA EL PISO 1 Y EL PISO 2
				spr.draw(piso, pisoPosicion1.x, pisoPosicion1.y);
				spr.draw(piso, pisoPosicion2.x, pisoPosicion2.y);
				spr.end();
			}

		} else { // SINGLEPLAYER
			spr.begin();

			spr.draw(fondo, cam.position.x - (cam.viewportWidth / 2), cam.position.y - (cam.viewportHeight / 2));

			spr.draw(getPlayer()[0].getTextureAmarillo(), player[0].getPosition().x, getPlayer()[0].getPosition().y, 23,
					23);

			for (Tubos tubos : tubes) {
				spr.draw(tubos.getTopTube(), tubos.getPosicionTopTube().x, tubos.getPosicionTopTube().y);
				spr.draw(tubos.getBottomTube(), tubos.getPosicionBotTube().x, tubos.getPosicionBotTube().y);
			}

			spr.draw(piso, pisoPosicion1.x, pisoPosicion1.y);
			spr.draw(piso, pisoPosicion2.x, pisoPosicion2.y);
			puntaje.draw(Render.spr, Integer.toString(cont), cam.position.x,
					cam.position.y + (cam.viewportHeight / 2) - 5f);

			spr.end();

		}

	}

	@Override
	// SE DISPOSEA TODOS LOS OBJETOS QUE SE UTILIZAN EN EL SINGLEPLAYER.
	public void dispose() {

		fondo.dispose();
		piso.dispose();
		getPlayer()[0].dispose();

		if (!Global.singlePlayer) {
			getPlayer()[1].dispose();
		}

		for (Tubos tubos : tubes) {
			tubos.dispose();

		}
		System.out.println("Pantalla Mapa disposeada");
	}

	public void reposicionPiso() { // METODO PARA LA REPOSICION DEL PISO

		// ESTAS CONDICIONES CUMPLEN LA FUNCION DE REPOSICIONAR EL PISO AL MOMENTO DE
		// QUE SE DESPLAZEN POR EL LADO IZQUIERDO DE LA PANTALLA
		if (cam.position.x - (cam.viewportWidth / 2) > pisoPosicion1.x + piso.getWidth()) {
			pisoPosicion1.add(piso.getWidth() * 2, 0);
		} else if (cam.position.x - (cam.viewportWidth / 2) > pisoPosicion2.x + piso.getWidth()) {
			pisoPosicion2.add(piso.getWidth() * 2, 0);

		}
	}

	public void agregarTubo(float x, float y) {
		tubes.add(new Tubos(x, y));
	}

	public void reposicionarTubo(float x, float y, int tubo) {
		tubes.get(tubo).reposicionar(x, y);

	}

	public Pajaro[] getPlayer() {
		return player;
	}

	public int getNumeroJugador() {
		return numeroJugador;
	}

	public void setNumeroJugador(int numeroJugador) {
		this.numeroJugador = numeroJugador;
	}

	public void setGano(boolean gano) {
		this.gano = gano;
	}

}
