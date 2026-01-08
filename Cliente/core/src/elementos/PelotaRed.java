package elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PelotaRed extends Sprite {
    public PelotaRed(){
        super(new Texture(Gdx.files.internal("pelota.png")));
        setScale(0.10f);
    }
}
