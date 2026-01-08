package com.servidor.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import juegos.Futbol;

public class Principal extends Game {
	@Override
    public void create() {
        setScreen(new Futbol(this));
    }
}
    

