package com.kurtsevich.asteroids.adapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.kurtsevich.asteroids.entity.Spaceship;

public class KeyboardAdapter extends InputAdapter {
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;
    private boolean spacePressed;

    private final Vector2 mousePos = new Vector2();
    private final Vector2 direction = new Vector2();

    public boolean isSpacePressed() {
        return spacePressed;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) leftPressed = true;
        if (keycode == Input.Keys.D) rightPressed = true;
        if (keycode == Input.Keys.W) upPressed = true;
        if (keycode == Input.Keys.S) downPressed = true;
        if (keycode == Input.Keys.SPACE) spacePressed = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A) leftPressed = false;
        if (keycode == Input.Keys.D) rightPressed = false;
        if (keycode == Input.Keys.W) upPressed = false;
        if (keycode == Input.Keys.S) downPressed = false;
        if (keycode == Input.Keys.SPACE) spacePressed = false;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePos.set(screenX, screenY);
        return false;
    }

    public Vector2 getDirection() {
        direction.set(0, 0);

        if (leftPressed) direction.add(-Spaceship.SPACESHIP_SPEED * Gdx.graphics.getDeltaTime(), 0);
        if (rightPressed) direction.add(Spaceship.SPACESHIP_SPEED * Gdx.graphics.getDeltaTime(), 0);
        if (upPressed) direction.add(0, Spaceship.SPACESHIP_SPEED * Gdx.graphics.getDeltaTime());
        if (downPressed) direction.add(0, -Spaceship.SPACESHIP_SPEED * Gdx.graphics.getDeltaTime());
        return direction;
    }
}
