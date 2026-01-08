package elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PersonajeSprite extends Sprite {
    public PersonajeSprite(Texture t){
        super(t);
        setScale(0.18f);
    }
}
