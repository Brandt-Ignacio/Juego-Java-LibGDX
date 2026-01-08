package com.haxball.game.utiles;


import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class Entradas implements InputProcessor{
    private	boolean abajo = false, arriba = false;
    private boolean enter  = false;
    private boolean w = false;
    private boolean s = false;
    private boolean e = false;
    private boolean q = false;

    public boolean keyDown(int keycode) {
        if(keycode == Keys.DOWN) {
            abajo= true;
        }
        if(keycode == Keys.UP) {
            arriba = true;
        }
        if(keycode == Keys.ENTER) {
            enter = true;
        }
        if(keycode == Keys.W) {
            w = true;
        }
        if(keycode == Keys.S) {
            s = true;
        }
        if(keycode == Keys.E) {
            e = true;
        }
        if(keycode == Keys.Q) {
            q = true;
        }
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Keys.DOWN) {
            abajo= false;
        }
        if(keycode == Keys.UP) {
            arriba = false;
        }
        if(keycode == Keys.W) {
            w = false;
        }
        if(keycode == Keys.S) {
            s = false;
        }
        if(keycode == Keys.E) {
            e = false;
        }
        if(keycode == Keys.Q) {
            q = false;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isW() {
        return w;
    }

    public boolean isS() {
        return s;
    }

    public boolean isEnter() {
        return enter;
    }

    public boolean isAbajo() {
        return abajo;
    }

    public boolean isArriba() {
        return arriba;
    }
    public boolean isE() {
        return e;
    }
    public boolean isQ() {
        return q;
    }

}
