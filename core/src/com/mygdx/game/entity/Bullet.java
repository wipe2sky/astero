package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class Bullet {
    public static final int MAX_COUNT = 5;
    public static final float SHOOT_WAIT_TIME = 0.3f;
    private static final int SPEED = 300;

    private static Texture texture;

    private static final int WIDTH = 10;
    private static final int HEIGHT = 32;
    private static final float HALF_WIDTH = WIDTH >> 1;
    private static final float HALF_HEIGHT = HEIGHT >> 1;
    private final TextureRegion textureRegion;
    private final Vector2 position = new Vector2();
    private final Vector2 angle = new Vector2();
    private final HashMap<Float, Vector2> startBulletOffset = new HashMap<>();
    private final HashMap<Float, Vector2> moveBulletOffset = new HashMap<>();
    private final CollisionRect collisionRect;


    private boolean destroyed;


    public Bullet(Vector2 position, Vector2 angle, float size) {
        this.position.set(position).add(size / 2, size + 1);
        setStartBulletOffset(size);
        setMoveBulletDraft();
        calcPosition(angle, position);
        this.angle.set(angle);
        this.collisionRect = new CollisionRect(position.x, position.y, WIDTH, HEIGHT);


        if (texture == null) {
            texture = new Texture("bullet.png");
        }

        this.textureRegion = new TextureRegion(texture);
    }


    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    private void setStartBulletOffset(float size) {
        startBulletOffset.put(0f, new Vector2(size / 2 - 5, size + 4));
        startBulletOffset.put(45f, new Vector2(-5, size - 15));
        startBulletOffset.put(90f, new Vector2(5, size / 2 - 15));
        startBulletOffset.put(135f, new Vector2(0, -7));
        startBulletOffset.put(180f, new Vector2(size / 2 - 5, -18));
        startBulletOffset.put(225f, new Vector2(size, -17));
        startBulletOffset.put(270f, new Vector2(size + 4, size / 2 - 15));
        startBulletOffset.put(315f, new Vector2(size, size - 10));
        startBulletOffset.put(360f, new Vector2(size / 2 - 5, size + 4));
    }

    private void setMoveBulletDraft() {
        moveBulletOffset.put(0f, new Vector2(0, 1));
        moveBulletOffset.put(45f, new Vector2(-1, 1));
        moveBulletOffset.put(90f, new Vector2(-1, 0));
        moveBulletOffset.put(135f, new Vector2(-1, -1));
        moveBulletOffset.put(180f, new Vector2(0, -1));
        moveBulletOffset.put(225f, new Vector2(1, -1));
        moveBulletOffset.put(270f, new Vector2(1, 0));
        moveBulletOffset.put(315f, new Vector2(1, 1));
        moveBulletOffset.put(360f, new Vector2(0, 0));
    }

    private void calcPosition(Vector2 angle, Vector2 position) {
        this.position.set(position).add(startBulletOffset.get(Math.abs(angle.angleDeg())));
    }

    public void update(float deltaTime) {
        if (angle.x == 0.0f && angle.y == 0.0f) angle.set(3, 0);
        position.add(SPEED * deltaTime * moveBulletOffset.get(Math.abs(angle.angleDeg())).x,
                SPEED * deltaTime * moveBulletOffset.get(Math.abs(angle.angleDeg())).y);
        if (position.x > Gdx.graphics.getWidth()
                || position.x < 0
                || position.y > Gdx.graphics.getHeight()
                || position.y < 0) {
            destroyed = true;
        }
    }

    public void render(SpriteBatch batch) {
        collisionRect.setPosition(position);
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                HALF_WIDTH,
                HALF_HEIGHT,
                WIDTH,
                HEIGHT,
                1,
                1,
                angle.angleDeg()
        );
    }

    public void dispose() {
        texture.dispose();
    }
}
