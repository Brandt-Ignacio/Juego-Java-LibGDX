package elementos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

public class JugadorRed extends Personaje{
	public JugadorRed(Viewport viewport,World world,MapaFutbol mapa) {
		super(viewport,world,mapa,Color.RED,viewport.getWorldWidth()/3,viewport.getWorldHeight()/2);
	}
}
