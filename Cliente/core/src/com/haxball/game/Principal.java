package com.haxball.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haxball.game.utiles.Render;


public class Principal extends Game {
    SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Load all maps
        Render.app = this;
        Render.batch  = new SpriteBatch();
        this.setScreen(new PantallaMenu());
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    // MÃ©todo para redimensionar la ventana
    public void resize(int width, int height) {

    }
}
/*public class Principal extends Game {
	@Override
    public void create() {
		
        Screen futbol = new Futbol();
        setScreen(futbol);
    }
    
}
*/