package com.mygdx.game.adapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.game.entity.Spaceship.SPACESHIP_SPEED;

public class KeyboardAdapter extends InputAdapter {
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;

    private final Vector2 mousePos = new Vector2();
    private final Vector2 direction = new Vector2();

    public Vector2 getMousePos() {
        return mousePos;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) leftPressed = true;
        if (keycode == Input.Keys.D) rightPressed = true;
        if (keycode == Input.Keys.W) upPressed = true;
        if (keycode == Input.Keys.S) downPressed = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A) leftPressed = false;
        if (keycode == Input.Keys.D) rightPressed = false;
        if (keycode == Input.Keys.W) upPressed = false;
        if (keycode == Input.Keys.S) downPressed = false;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePos.set(screenX, screenY);
        return false;
    }

    public Vector2 getDirection() {
        direction.set(0, 0);

        if (leftPressed) {
            direction.add(-SPACESHIP_SPEED * Gdx.graphics.getDeltaTime(), 0);
//            if (direction.x <= 0) {
//                direction.set(Gdx.graphics.getWidth() + SHIP_WIDTH, direction.y);
//            }

        }
        if (rightPressed) {
            direction.add(SPACESHIP_SPEED * Gdx.graphics.getDeltaTime(), 0);
//            if (direction.x >= Gdx.graphics.getWidth() - SHIP_WIDTH) {
//                direction.x = 0 - SHIP_WIDTH;
//            }
        }
        if (upPressed) {
            direction.add(0, SPACESHIP_SPEED * Gdx.graphics.getDeltaTime());
//            if (direction.y >= Gdx.graphics.getHeight() - SHIP_HEIGHT) {
//                direction.y = -SHIP_HEIGHT;
//            }
        }
        if (downPressed) {
            direction.add(0, -SPACESHIP_SPEED * Gdx.graphics.getDeltaTime());
//            if (direction.y <= 0) {
//                direction.y = Gdx.graphics.getHeight() + SHIP_HEIGHT;
//            }
        }

        return direction;
    }
}
