package com.haxball.game.utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;

public class Texto {
    BitmapFont fuente;
    GlyphLayout layout; // <--- para saber el tamaño del "contenedor"

    private float x = 0, y = 0;
    private String texto = "";
    private String rutaFuente = "fuente.otf";
    private int dimension;
    private Color color;
    private boolean sombra;

    public Texto(int dimension, Color color, boolean sombra) {
        this.dimension = dimension;
        this.color = color;
        this.sombra = sombra;
        generarTexto(dimension, color, sombra);
    }

    private void generarTexto(int dimension, Color color, boolean sombra) {
        FreeTypeFontGenerator generador = new FreeTypeFontGenerator(Gdx.files.internal(rutaFuente));
        FreeTypeFontParameter parametro = new FreeTypeFontParameter();

        parametro.size = dimension;
        parametro.color = color;
        if(sombra) {
            parametro.shadowColor = Color.BLACK;
            parametro.shadowOffsetX = 1;
            parametro.shadowOffsetY = 1;
        }


        fuente = generador.generateFont(parametro);
        layout = new GlyphLayout();

    }

    public void dibujar() {
        fuente.draw(Render.batch, texto, x, y);
    }

    public void dibujar(SpriteBatch batch) {
        fuente.draw(batch, texto, x, y);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getTexto() {
        return texto;
    }

    public void setSize(int dimension) {
        generarTexto(dimension, color, sombra);
    }

    public void setTexto(String texto) {
        this.texto = texto;
        layout.setText(fuente, texto);
    }

    public void setColor(Color color) {
        fuente.setColor(color);
    }

    //TAMAÑO
    public float getAncho() {
        return layout.width;
    }

    public float getAlto() {
        return layout.height;
    }

    public Vector2 getDimension() {
        return new Vector2(layout.width, layout.height);
    }
    public Vector2 getPosition() {
        return new Vector2(x, y);
    }
}