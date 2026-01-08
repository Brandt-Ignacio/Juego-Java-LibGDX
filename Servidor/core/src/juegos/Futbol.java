package juegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.servidor.game.Principal;
import elementos.JugadorBlue;
import elementos.JugadorRed;
import elementos.MapaFutbol;
import elementos.Marcador;
import elementos.Palo;
import elementos.Pelota;
import elementos.Personaje;
import elementos.JugadorRed;
import elementos.JugadorBlue;
import red.HiloServidor;
import utilidades.Utiles;

import static utilidades.Utiles.modoActual;
import static utilidades.Utiles.servidor;


public class Futbol implements Screen {
	private static int height = 720;
	private static int width = 1280;
	private int golesRed;
	private int golesBlue;
	private SpriteBatch batch;
	public static Personaje personajeRed;
	public static Personaje personajeBlue;
	private static MapaFutbol mapa;
	private Marcador marcador;
	public static Pelota pelota;
	private Palo palo1, palo2, palo3, palo4;
	private Box2DDebugRenderer debugRenderer;
	private static Viewport viewport;
	private static World world;
	private static OrthographicCamera camera;
	private ShapeRenderer shaper;
	private BitmapFont font;
	private Shape shape;

	private Principal juego;




	public Futbol(Principal juego) {
		this.juego = juego;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		shaper = new ShapeRenderer();
		font = new BitmapFont();
		marcador = new Marcador();
		if (servidor == null) {
			servidor = new HiloServidor();
			servidor.start();
		} else {
			servidor.terminar();
		}
		shaper.setAutoShapeType(true);

 		// Init world
		world = new World(new Vector2(0, 0), true);
		mapa = new MapaFutbol(world);
		height = mapa.getHeight() + 160;
		width = mapa.getWidth() + 160;
		viewport = new FitViewport(width, height, camera); // Configura el viewport aquí
		personajeRed = new JugadorRed(viewport, world, mapa);
		personajeBlue = new JugadorBlue(viewport, world, mapa);// Pasa el viewport al constructor de Personaje
		pelota = new Pelota(viewport, world);
		mapa.createSolidLines(viewport);
	}

	public void show() {
		/*world = new World(new Vector2(0, 0), true);
		mapa = new MapaFutbol(world);
		debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		personajeRed = new JugadorRed(viewport, world, mapa);
		personajeBlue = new JugadorBlue(viewport, world, mapa);
		pelota = new Pelota(viewport, world);
		mapa.createSolidLines(viewport);
		viewport = new FitViewport(width, height, camera);
		shaper.setAutoShapeType(true);*/
	}

	public void resize() {
		camera.setToOrtho(false, width, height);
		viewport.update(width, height, true);
	}

	@Override
	public void render(float delta) {

		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			servidor.cerrarSocket();
			servidor.stop();
			Gdx.app.exit();
		}
		if (!Utiles.empieza) {
			batch.begin();
			font.draw(batch, "ESPERANDO JUGADORES", 225, 220);
			batch.end();
			return;
		}
		if (golesBlue >= 3 || golesRed >= 3 || Utiles.terminar) {
			servidor.terminar();
			golesRed = 0;
			golesBlue = 0;
			Utiles.empieza = false;
			Utiles.terminar = false;
			Utiles.patear = false;
			Utiles.modoActual = null;
			juego.setScreen(new Futbol(juego));
			return;

		}

		if((Utiles.modoActual != null && Utiles.modoActual.equals(Utiles.MODO.FUTBOL))&& (palo1 == null || palo2 == null|| palo3 == null || palo4 == null)) {
			palo1 = new Palo(world, new Vector2(125, 250));
			palo2 = new Palo(world, new Vector2(125, 410));
			palo3 = new Palo(world, new Vector2(1225, 250));
			palo4 = new Palo(world, new Vector2(1225, 410));
		}
		// GAME LOOP
		System.out.println(personajeRed.getBody().getPosition().x + " " + personajeRed.getBody().getPosition().y);
		Gdx.gl.glClearColor(0.435f, 0.667f, 0.357f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float imageX = camera.position.x - mapa.getWidth() / 2;
		float imageY = camera.position.y - mapa.getHeight() / 2;
		camera.update();
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(mapa.mapa, imageX, imageY, mapa.getWidth(), mapa.getHeight());
		batch.end();
		personajeRed.render(camera);
		personajeBlue.render(camera);
		batch.begin();
		imprimirMarcador();
		batch.end();
		pelota.render(camera);
		pelota.update();
		isGoal();

		if(modoActual.equals(Utiles.MODO.BOWLING)) {
			if(personajeRed.getBody().getPosition().dst(pelota.getBody().getPosition()) <= 31){
				marcador.golesRed++;
				servidor.broadcastCasero("score#" + marcador.golesRed + "#" + marcador.golesBlue);
				reiniciarPosiciones();
			} else if (personajeBlue.getBody().getPosition().dst(pelota.getBody().getPosition()) <= 31){
				marcador.golesBlue++;
				servidor.broadcastCasero("score#" + marcador.golesRed + "#" + marcador.golesBlue);
				reiniciarPosiciones();
			}
		}
		if (personajeBlue.getBody().getLinearVelocity().len() < 1) personajeBlue.getBody().setLinearVelocity(0, 0);
		if (personajeRed.getBody().getLinearVelocity().len() < 1) personajeRed.getBody().setLinearVelocity(0, 0);
		if (pelota.getBody().getLinearVelocity().len() < 1) pelota.getBody().setLinearVelocity(0, 0);
		if (personajeRed.getBody().getLinearVelocity().len() > 1) {
			servidor.broadcastCasero("mover#" + 0 + "#" + (personajeRed.getBody().getPosition().x - 115) + "#" + (personajeRed.getBody().getPosition().y - 115));
		}
		if (personajeBlue.getBody().getLinearVelocity().len() > 1) {
			servidor.broadcastCasero("mover#" + 1 + "#" + (personajeBlue.getBody().getPosition().x - 115) + "#" + (personajeBlue.getBody().getPosition().y - 115));
		}
		if (pelota.getBody().getLinearVelocity().len() > 1) {
			servidor.broadcastCasero("pelota#" + (pelota.getBody().getPosition().x - 115) + "#" + (pelota.getBody().getPosition().y - 115));
		}
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
	}

	private void isGoal() {
		// Gol del azul
		System.out.println("P " + personajeBlue.getBody().getPosition().x + " " + personajeBlue.getBody().getPosition().y);
		if(modoActual.equals(Utiles.MODO.FUTBOL)){

		if (pelota.getBody() != null && pelota.body.getPosition().x < 114 && pelota.body.getPosition().y >= 260 && pelota.body.getPosition().y <= 398) {
			marcador.golesBlue++;
			servidor.broadcastCasero("score#" + marcador.golesRed + "#" + marcador.golesBlue);
			reiniciarPosiciones();
		} else if (pelota.getBody() != null && pelota.body.getPosition().x > 1232 && pelota.body.getPosition().y >= 260 && pelota.body.getPosition().y <= 398) {
			// Gol del rojo
			marcador.golesRed++;
			servidor.broadcastCasero("score#" + marcador.golesRed + "#" + marcador.golesBlue);
			reiniciarPosiciones();
		}
		} else {
			int[][] canasta1 = {{166,200}, {320,340}};
			int[][] canasta2 = {{1160,1194}, {320,340}};
			if(canasta1[0][0] <= pelota.body.getPosition().x && canasta1[0][1] >= pelota.body.getPosition().x && canasta1[1][0] <= pelota.body.getPosition().y && canasta1[1][1] >= pelota.body.getPosition().y){
				marcador.golesBlue++;
				servidor.broadcastCasero("score#" + marcador.golesRed + "#" + marcador.golesBlue);
				reiniciarPosiciones();
			} else if (canasta2[0][0] <= pelota.body.getPosition().x && canasta2[0][1] >= pelota.body.getPosition().x && canasta2[1][0] <= pelota.body.getPosition().y && canasta2[1][1] >= pelota.body.getPosition().y){
				marcador.golesRed++;
				servidor.broadcastCasero("score#" + marcador.golesRed + "#" + marcador.golesBlue);
				reiniciarPosiciones();
			}
		}
		if (marcador.golesBlue >= 3 || marcador.golesRed >= 3) {
			Utiles.terminar = true;
		}
	}


	private void imprimirMarcador() {
		font.draw(batch, marcador.golesRed + "-" + marcador.golesBlue, 600, height);
		font.draw(batch, personajeRed.getBody().getPosition().x + " " + personajeRed.getBody().getPosition().y, 600, height - 200);
		font.draw(batch, personajeBlue.getBody().getPosition().x + " " + personajeBlue.getBody().getPosition().y, 600, height - 400);
	}

	private void reiniciarPosiciones() {

		personajeRed.getBody().setTransform(new Vector2(viewport.getWorldWidth() / 3, viewport.getWorldHeight() / 2), 0);
		personajeBlue.getBody().setTransform(new Vector2(viewport.getWorldWidth() - viewport.getWorldWidth() / 3, viewport.getWorldHeight() / 2), 0);
		//if(modoActual.equals(Utiles.MODO.))


		if(modoActual.equals(Utiles.MODO.BOWLING)){
			float randX = (float) ((float) 0.9f + (Math.random() * (1.1 - 0.9f)));
			float randY = (float) ((float) 0.5f + (Math.random() * (1.5 - 0.5)));
			System.out.println(randX+ " " +randY +" \n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			pelota.getBody().setTransform(new Vector2(viewport.getWorldWidth() / (2 * randX), viewport.getWorldHeight() / (2* randY)) , 0);
		} else {
			pelota.getBody().setTransform(new Vector2(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2), 0);
		}

		personajeRed.getBody().setLinearVelocity(0, 0);
		personajeBlue.getBody().setLinearVelocity(0, 0);
		pelota.getBody().setLinearVelocity(0, 0);

		servidor.broadcastCasero("mover#" + 0 + "#" + (personajeRed.getBody().getPosition().x - 115) + "#" + (personajeRed.getBody().getPosition().y - 115));
		servidor.broadcastCasero("mover#" + 1 + "#" + (personajeBlue.getBody().getPosition().x - 115) + "#" + (personajeBlue.getBody().getPosition().y - 115));
		servidor.broadcastCasero("pelota#" + (pelota.getBody().getPosition().x - 115) + "#" + (pelota.getBody().getPosition().y - 115));

	}


	public void dispose() {
		this.shape.dispose();
		this.batch.dispose();
	}

	@Override
	public void pause() {


	}

	@Override
	public void resume() {


	}

	@Override
	public void hide() {


	}

	@Override
	public void resize(int width, int height) {


	}


}
