package com.kurtsevich.asteroids.adapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class KeyboardAdapter extends InputAdapter {
    private final Vector2 mousePos = new Vector2();
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean spacePressed;
    private boolean leftButtonPressed;


    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public Vector2 getMousePos() {
        return mousePos;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) leftPressed = true;
        if (keycode == Input.Keys.D) rightPressed = true;
        if (keycode == Input.Keys.W) upPressed = true;
        if (keycode == Input.Keys.SPACE) spacePressed = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A) leftPressed = false;
        if (keycode == Input.Keys.D) rightPressed = false;
        if (keycode == Input.Keys.W) upPressed = false;
        if (keycode == Input.Keys.SPACE) spacePressed = false;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePos.set(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) leftButtonPressed = true;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) leftButtonPressed = false;
        return false;
    }
}
