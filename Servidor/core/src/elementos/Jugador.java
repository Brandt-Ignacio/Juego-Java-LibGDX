package elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import utilidades.Utiles;

public class Jugador {
	private Personaje personaje;
		 public Jugador(Viewport viewport, World world, MapaFutbol mapa) {
		        if (Utiles.jugador == 1) {
		            this.personaje = new JugadorRed(viewport, world, mapa);
		        } else if (Utiles.jugador == 2) {
		            this.personaje = new JugadorBlue(viewport, world, mapa);
		        }		
		        
	       	}
		 
		 public Personaje getPersonaje() {
			 return this.personaje;
		 }
}
