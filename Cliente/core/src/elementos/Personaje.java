package elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import utilidades.Utiles;

public abstract class Personaje {
	private ShapeRenderer shaper;
	private final float RADIO;
	public Viewport viewport;
	private float x,y;
	private boolean patear = false;
	private Body body;
	private boolean llevandoPelota;
	private Color color;
	protected BodyDef bodyDef = new BodyDef();
	private static final short COLISION_JUGADOR = Colision.COLISION_JUGADOR;
	private static final short MASCARA_JUGADOR = Colision.MASCARA_JUGADOR;
	private boolean arriba,abajo,izquierda,derecha;

	public Personaje(Viewport viewport, World world, MapaFutbolRework mapa,Color color,float x,float y) {
	    llevandoPelota = false;
	    shaper = new ShapeRenderer();
	    RADIO = 16;
	    this.x = x;
	    this.y = y;
	    this.viewport = viewport;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.x,this.y); 
        body = world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(RADIO+2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 50.0f; 
        fixtureDef.filter.categoryBits = COLISION_JUGADOR; 
        fixtureDef.filter.maskBits = MASCARA_JUGADOR;
        bodyDef.bullet = true;
        this.color = color;
        body.createFixture(fixtureDef);
	}

	public void render(OrthographicCamera camera) {
	    // Establecer la proyección de la cámara para el renderizado del personaje
	    shaper.setProjectionMatrix(camera.combined);
	    shaper.begin(ShapeRenderer.ShapeType.Filled);
	    if (patear) {
	        shaper.setColor(Color.WHITE);
	    } else {
	        shaper.setColor(Color.BLACK);
	    }
	    shaper.circle(body.getPosition().x, body.getPosition().y, (RADIO + 2));
	    
	    shaper.setColor(this.color);
	    shaper.circle(body.getPosition().x, body.getPosition().y, RADIO);
	    shaper.end();
	}
		
	  public void moverJugador(Pelota pelota) {
		  	//this.body.setAngularVelocity(0);
	        int velocidad = 150;
	        boolean arriba = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
	        boolean abajo = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
	        boolean izquierda = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
	        boolean derecha = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
	        patear = Gdx.input.isKeyPressed(Input.Keys.X);

	        float velocidadDiagonal = (float) (velocidad * Math.sqrt(2) / 2);
	        float dX = 0, dY = 0;

	        if (arriba && !abajo) {
	            dY += velocidadDiagonal;
	        } else if (abajo && !arriba) {
	            dY -= velocidadDiagonal;
	        }
	        if (derecha && !izquierda) {
	            dX += velocidadDiagonal;
	        } else if (izquierda && !derecha) {
	            dX -= velocidadDiagonal;
	        }
        
	        /*if (patear && colisionaConPelota(pelota)) {
	        	Utiles.patear = true;   
	        } else {
	        	Utiles.patear = false;
	        } */
	        
	        body.setLinearVelocity(dX, dY);
	        this.x = body.getPosition().x;
	        this.y = body.getPosition().y;
	    }
	// metodo para seter direccion actual
	public void moverJugador(boolean arriba, boolean abajo, boolean izquierda, boolean derecha) {
		this.arriba = arriba;
		this.abajo = abajo;
		this.izquierda = izquierda;
		this.derecha = derecha;
	}

	// Metodo para mover al jugador en renders.
	public void mover(){
		this.body.setAngularVelocity(0);
		int velocidad = 150;
		patear = Gdx.input.isKeyPressed(Input.Keys.X);
		float velocidadDiagonal = (float) (velocidad * Math.sqrt(2) / 2);
		float dX = 0, dY = 0;

		if (arriba && !abajo) {
			dY += velocidadDiagonal;
		} else if (abajo && !arriba) {
			dY -= velocidadDiagonal;
		}
		if (derecha && !izquierda) {
			dX += velocidadDiagonal;
		} else if (izquierda && !derecha) {
			dX -= velocidadDiagonal;
		}

		body.setLinearVelocity(dX, dY);
		this.x = body.getPosition().x;
		this.y = body.getPosition().y;
	}
		

	    public boolean colisionaConPelota(Pelota pelota) {
	    	 float distancia = body.getPosition().dst(pelota.body.getPosition())-(this.getRadio() + pelota.getRadio());
	    	    return distancia <=2;
	    }
	    
		
	    public void dispose() {
	        shaper.dispose();
	    }
		    
		public float getRadio() {
		    	return RADIO;
		}	
		
		public void setLlevandoPelota(boolean llevandoPelota) {
	        this.llevandoPelota = llevandoPelota;
	    }
		
		public boolean isLlevandoPelota() {
	        return llevandoPelota;
	    }
		
		public Body getBody() {
			return this.body;
		}
				    
}
