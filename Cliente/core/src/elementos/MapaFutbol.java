package elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import utilidades.Utiles;

public class MapaFutbol {
	public Texture	mapa;
	private World world;
	private Rectangle arcoRed;
	private Rectangle arcoBlue;
	
	public MapaFutbol(World world) {
		this.world = world;
		mapa = new Texture("MapaFutbol.png");
		this.world = world;
		arcoRed = new Rectangle(45,180,5,115);
		arcoBlue = new Rectangle(580,180,15,115);
		
	}



		 public void createSolidLines(Viewport viewport) {
		        float worldWidth = mapa.getWidth()+160;
		        float worldHeight = viewport.getWorldHeight();

		        // Escalar las coordenadas de los vértices en función del tamaño de la ventana
		        float scaleX = worldWidth / mapa.getWidth();
		        float scaleY = worldHeight / mapa.getHeight();

		        // Línea de abajo
		        createLineBody(world, 110 * scaleX, 70 * scaleY, 1080* scaleX, 70 * scaleY);

		        // Línea de arriba
		       createLineBody(world, 110 * scaleX, 432 * scaleY, 1080 * scaleX, 432 * scaleY);
		       
		        // Línea Izquierda arriba
		        createLineBody(world, 110 * scaleX, 432 * scaleY, 110 * scaleX, 320 * scaleY);
		        
		        // Línea Izquierda abajo
		        createLineBody(world, 110 * scaleX, 70 * scaleY, 110 * scaleX, 185 * scaleY);
		        
		        // Línea Derecha arriba
		        createLineBody(world, 1080 * scaleX, 432 * scaleY, 1080 * scaleX, 320 * scaleY);
		        
		        // Línea Derecha abajo
		        createLineBody(world, 1080 * scaleX, 70 * scaleY, 1080 * scaleX, 185 * scaleY);
		        
		        //Linea fondo del arco izquierdo
		        createLineBody(world, 85 * scaleX, 190 * scaleY, 85 * scaleX, 311 * scaleY);
		        
		        //Linea arriba del arco izquierdo
		        createLineBody(world, 85 * scaleX, 311 * scaleY, 105 * scaleX, 311 * scaleY);
		        
		        //Linea abajo del arco izquierdo
		        createLineBody(world, 85 * scaleX, 190 * scaleY, 105 * scaleX, 190 * scaleY);
		        
		        //Linea fondo del arco derecho
		        createLineBody(world, 1105 * scaleX, 190 * scaleY, 1105 * scaleX, 311 * scaleY);
		        
		        //Linea abajo del arco derecho
		        createLineBody(world, 1080 * scaleX, 190 * scaleY, 1105 * scaleX, 190 * scaleY);
		        
		      //Linea arriba del arco derecho
		        createLineBody(world, 1080 * scaleX, 311 * scaleY, 1105 * scaleX, 311 * scaleY);
	}

		  private void createLineBody(World world, float x1, float y1, float x2, float y2) {
		        BodyDef bodyDef = new BodyDef();
		        bodyDef.type = BodyDef.BodyType.StaticBody;
		        Body body = world.createBody(bodyDef);
		        EdgeShape edgeShape = new EdgeShape();
		        edgeShape.set(new Vector2(x1, y1), new Vector2(x2, y2));
		        FixtureDef fixtureDef = new FixtureDef();
		        fixtureDef.shape = edgeShape;
		        fixtureDef.filter.categoryBits = Colision.COLISION_LINEAS;
		        fixtureDef.filter.maskBits = Colision.MASCARA_PALOS;
		        body.createFixture(fixtureDef);
		        edgeShape.dispose();
		    }

	public int getWidth() {
		return this.mapa.getWidth();
	}

	public int getHeight() {
		return this.mapa.getHeight();
	}

	public World getWorld() {
		return this.world;
	}
	
	public Rectangle getArcoRed() {
		return this.arcoRed;
	}
	
	public Rectangle getArcoBlue() {
		return this.arcoBlue;
	}

}
