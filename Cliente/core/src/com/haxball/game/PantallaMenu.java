package com.haxball.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.haxball.game.utiles.Entradas;
import com.haxball.game.utiles.Render;
import com.haxball.game.utiles.Texto;
import juegos.Futbol;
import utilidades.Utiles;


public class PantallaMenu implements Screen {

    public float tiempo = 0;
    private int opc = 1;


    private SpriteBatch b;

    private Entradas entradas = new Entradas();

    private int j = 0;
    private Texto opciones[] = new Texto[4];
    private String textos[] = {"Futbol", "Basket", "Bowling", "Salir",};
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private Texto titulo;

    private Image bg;


    @Override
    public void show() {
        camera = new OrthographicCamera(Utiles.WIDTH, Utiles.HEIGHT);
        viewport = new ExtendViewport(Utiles.WIDTH, Utiles.HEIGHT, camera);

        viewport.apply();
        camera.update();
        //inicializamos las entradas
        Gdx.input.setInputProcessor(entradas);
        int avance = 30;

        bg =  new Image(new Texture("landing-video-cover-min.jpg"));
        bg.setBounds(0,0,1280,1000);
        bg.setOrigin(1280/2,720/2);



        titulo = new Texto( 150, Color.YELLOW, true); // creamos el texto en esa posicion
        titulo.setTexto("HAXBALL");
        titulo.setPosition((Utiles.WIDTH / 3.7f), (Utiles.HEIGHT - 150));

        for (int i = 0; i < opciones.length; i++) {
            opciones[i] = new Texto( 50, Color.WHITE, true); // creamos el texto en esa posicion
            opciones[i].setTexto(textos[i]);
            opciones[i].setPosition((Utiles.WIDTH / 2) - (opciones[i].getAncho() / 2), //este calculo centra horizontalmente el texto en la pantalla
                    ((Utiles.HEIGHT / 2) + (opciones[0].getAlto() / 2)) - ((opciones[i].getAlto() * i) + (avance * i))); // Este cálculo implica la colocación vertical del texto en la pantalla.
        }

    }

    @Override
    public void render(float delta) {

        camera.update();
        Render.limpiarPantalla(0, 0, 0);
        Render.batch.setProjectionMatrix(camera.combined);
        Render.batch.begin();
        bg.draw(Render.batch,0.5f);
        titulo.dibujar();
        for (int i = 0; i < opciones.length; i++) {
            opciones[i].dibujar();
        }

        Render.batch.end();

        tiempo += delta;
        if (tiempo > 0.09f) {
            if (entradas.isAbajo()) {
                opc++;
                if (opc > opciones.length) {
                    opc = 1;
                }

            }
            if (entradas.isArriba()) {
                opc--;
                if (opc < 1) {
                    opc = opciones.length;
                }
            }
            tiempo = 0;
        }


        if ((entradas.isEnter())) {
            switch (opc) {
                case 1:
                    Render.app.setScreen(new Futbol("futbol"));
                    break;
                case 2:
                    Render.app.setScreen(new Futbol("basket"));
                    break;
                case 3:
                    Render.app.setScreen(new Futbol("bowling"));
                    break;
                default:
                    Gdx.app.exit();
            }
        }
        for (int i = 0; i < opciones.length; i++) {
            if (i == (opc - 1)) {
                opciones[i].setColor(Color.FOREST);
            } else {
                opciones[i].setColor(Color.GRAY);
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        viewport.update(width, height, true);

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
