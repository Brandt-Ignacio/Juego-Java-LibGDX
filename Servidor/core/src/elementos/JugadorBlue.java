package elementos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

public class JugadorBlue extends Personaje{
	public JugadorBlue(Viewport viewport,World world,MapaFutbol mapa) {
		super(viewport,world,mapa,Color.BLUE,(viewport.getWorldWidth()-viewport.getWorldWidth()/3),viewport.getWorldHeight()/2);
	}
}
