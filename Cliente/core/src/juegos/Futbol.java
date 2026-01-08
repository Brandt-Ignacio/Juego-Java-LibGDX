package juegos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.haxball.game.PantallaMenu;
import com.haxball.game.utiles.Render;
import com.haxball.game.utiles.Texto;
import elementos.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import red.HiloCliente;
import utilidades.Utiles;

public class Futbol implements Screen{
	private static Texture mapaBowling;
	private int height = 720;
	private int width = 1280;
	public static Marcador marcador;
	private SpriteBatch batch;

	public static Texture mapa;
	private static Texture mapaFutbol;
	private static Texture mapaBasket;

	//private Pelota pelota;
	//private Palo palo1,palo2,palo3,palo4;
	private Viewport viewport;
	private World world;
	private OrthographicCamera camera;
	private ShapeRenderer shaper;
	private BitmapFont font;
	private Shape shape;
	private HiloCliente cliente;
	private boolean empezar;
	private boolean activo;

	private boolean terminarDeMover;


	public static PersonajeSprite p1;
	public static PersonajeSprite p2;
	public static PelotaRed pelota;

	public static Array<PelotaRed> pelotas = new Array<>();


	Texto sRed = new Texto(50,Color.RED,true);
	Texto spacer = new Texto(50,Color.WHITE,true);
	Texto sBlue = new Texto(50,Color.BLUE,true);

	Texto titulo = new Texto(100,Color.WHITE,true);


	String modo;
	public Futbol(String modo){
		mapaFutbol = new Texture("MapaFutbol.png");
	 	mapaBasket = new Texture("MapaBasket2.png");
		mapaBowling = new Texture("bowling.png");
		this.modo = modo;
		mapa = modo == "futbol" ? mapaFutbol : mapaBasket;
		height = mapa.getHeight() + 160;
		width = mapa.getWidth() + 160;
		camera = new OrthographicCamera();
		viewport = new FitViewport(width, height, camera); // Configura el viewport aquí
		batch = new SpriteBatch();
		font = new BitmapFont();
		p1 = new PersonajeSprite(new Texture(Gdx.files.internal("redplayer.png")));
		p2 = new PersonajeSprite(new Texture(Gdx.files.internal("blueplayer.png")));
		pelota = new PelotaRed();

		pelotas.addAll(new PelotaRed[]{new PelotaRed(), new PelotaRed(),new PelotaRed(),new PelotaRed()});

		p1.setPosition(viewport.getWorldWidth()/4,viewport.getWorldHeight()/3f);
		p2.setPosition(viewport.getWorldWidth()/4,viewport.getWorldHeight()/3f);
		System.out.println("VP "+viewport.getWorldWidth()/4+ " " +viewport.getWorldHeight()/3f);
		shaper = new ShapeRenderer();
		marcador = new Marcador();
		sRed.setPosition(600,height);
		spacer.setPosition(640,height);
		spacer.setTexto("-");
		sBlue.setPosition(670,height);

	}
	
	public void show () {
		if(cliente == null){
			cliente = new HiloCliente();
			cliente.start();
		}
		activo = false;
	}
	
	public void resize() {
		camera.setToOrtho(false, width, height);
		viewport.update(width, height, true);
	}

	@Override
	public void render (float delta) {


		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (!Utiles.empieza) {
			batch.begin();
			p1.draw(batch);
			titulo.setTexto("ESPERANDO JUGADORES");
			titulo.setPosition((Utiles.WIDTH*0.095f), (Utiles.HEIGHT - 150));
			titulo.dibujar(batch);
			imprimirMarcador();
			batch.end();
			if (!activo) {
				cliente.enviarMensaje("Conexion#"+modo);
				activo = true;
			}
			return;
		} if (!Utiles.terminar){
			manejarInputs();
			if(mapa.equals(mapaFutbol)){
				Gdx.gl.glClearColor(0.435f, 0.667f, 0.357f, 1);
			} else {
				Gdx.gl.glClearColor(0.33f, 0.33f, 0.33f, 1);
			}
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			float imageX = camera.position.x - mapa.getWidth() / 2;
			float imageY = camera.position.y - mapa.getHeight() / 2;
			camera.update();
			viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(mapa,imageX,imageY,mapa.getWidth(),mapa.getHeight());
			batch.end();
			batch.begin();

			p1.draw(batch);
			p2.draw(batch);
			pelota.draw(batch);
			imprimirMarcador();
			batch.end();
			return;
		} else {

			batch.begin();
			titulo.setTexto("GANÓ EL "+(marcador.golesRed >= 3 ? "ROJO" : "AZUL"));
			titulo.setPosition((Utiles.WIDTH*0.095f), (Utiles.HEIGHT - 150));
			font.draw(batch, "PRESIONA ENTER PARA IR AL MENU", Utiles.WIDTH/2,Utiles.HEIGHT/2);
			if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
				Utiles.terminar = false;
				Utiles.empieza = false;
				Render.app.setScreen(new PantallaMenu());
				return;
			}
			titulo.dibujar(batch);
			batch.end();
		}
	}

	
	private void imprimirMarcador() {
		sRed.dibujar(batch);
		spacer.dibujar(batch);
		sBlue.dibujar(batch);
		sRed.setTexto(String.valueOf(marcador.golesRed));
		sBlue.setTexto(String.valueOf(marcador.golesBlue));
	}

	public void dispose () {
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


	public void manejarInputs(){
		boolean arriba = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
		boolean abajo = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
		boolean izquierda = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean derecha = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		if(arriba || abajo || izquierda || derecha){
			terminarDeMover = false;
			cliente.enviarMensaje("mover#"+arriba+"#"+abajo+"#"+izquierda+"#"+derecha);
		} else if(!terminarDeMover) {
			cliente.enviarMensaje("mover#"+false+"#"+false+"#"+false+"#"+false);
			terminarDeMover = true;
			System.out.println("TERMINAR DE MOVER.");
		}
		if(Gdx.input.isKeyPressed(Input.Keys.F)){
			cliente.enviarMensaje("patear");
		}
	}
	public static void cambiarMapa(String s){
		mapa = s.equals("FUTBOL")  ? mapaFutbol : s.equals("BASKET") ?  mapaBasket : mapaBowling;
	}
}
