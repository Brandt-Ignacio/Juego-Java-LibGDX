package elementos;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import utilidades.Utiles;

public class Pelota {
	private ShapeRenderer shaper;
	private final float RADIO;
    private static final float ROZAMIENTO = 1000000f;
	public float x,y;
	public Viewport viewport;
	World world;
	public Body body;
	private CircleShape shape;

	public Pelota(Viewport viewport,World world) {
	    shaper = new ShapeRenderer();
	    x = viewport.getWorldWidth()/2;
	    y = viewport.getWorldHeight()/2;
	    RADIO = 11;
	    this.viewport = viewport;
	    this.world = world;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        shape = new CircleShape();
        shape.setRadius(RADIO);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 80.0f;
        fixtureDef.restitution = 0.1f; 
        fixtureDef.filter.categoryBits = Colision.COLISION_PELOTA | Colision.COLISION_JUGADOR;
        fixtureDef.filter.maskBits = Colision.MASCARA_PELOTA | Colision.MASCARA_JUGADOR;
        body.createFixture(fixtureDef);
	}

	public void render(OrthographicCamera camera) {
		 shaper.setProjectionMatrix(camera.combined);
		  shaper.begin(ShapeRenderer.ShapeType.Filled);
		    shaper.setColor(0, 0, 0, 1);
		    shaper.circle(body.getPosition().x, body.getPosition().y,(RADIO+2)); 
		 shaper.end();
		 	shaper.begin(ShapeRenderer.ShapeType.Filled);
		 	shaper.setColor(Color.WHITE);
		 	shaper.circle(body.getPosition().x, body.getPosition().y,RADIO); 
		 shaper.end(); 
	}
	
	
	

	public void update() {
		Vector2 velocidad = body.getLinearVelocity();
		Vector2 direccionOpuesta = new Vector2(velocidad).scl(-1);
		Vector2 fuerzaRozamiento = new Vector2(direccionOpuesta).nor().scl(ROZAMIENTO);

		body.applyForce(fuerzaRozamiento, body.getWorldCenter(), true);

		if (Utiles.patear) {
//			System.out.println("F");
		    int fuerzaPateo = 10000000;
		    Vector2 direccion = body.getLinearVelocity();
		    Vector2 fuerza = direccion.scl(fuerzaPateo);
		    body.applyForceToCenter(fuerza,true);
		}
	}
		
	public float getRadio() {
		return RADIO;
	}
	
	public CircleShape getShape() {
		return shape;
	}
	
	public float getRozamiento() {
		return ROZAMIENTO;
	}
	
	public Body getBody() {
		return this.body;
	}
}
