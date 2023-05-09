package com.kurtsevich.asteroids.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class Bullet {
    private static final int SPEED = 300;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 32;
    private static final float HALF_WIDTH = WIDTH / 2f;
    private static final float HALF_HEIGHT = HEIGHT / 2f;
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Vector2 position;
    private final Vector2 angle;
    private final HashMap<Float, Vector2> startBulletOffset = new HashMap<>();
    private static final HashMap<Float, Vector2> MOVE_BULLET_OFFSET = new HashMap<>();
    private final CollisionRect collisionRect;

    private boolean destroyed;

    static {
        MOVE_BULLET_OFFSET.put(0f, new Vector2(0, 1));
        MOVE_BULLET_OFFSET.put(45f, new Vector2(-1, 1));
        MOVE_BULLET_OFFSET.put(90f, new Vector2(-1, 0));
        MOVE_BULLET_OFFSET.put(135f, new Vector2(-1, -1));
        MOVE_BULLET_OFFSET.put(180f, new Vector2(0, -1));
        MOVE_BULLET_OFFSET.put(225f, new Vector2(1, -1));
        MOVE_BULLET_OFFSET.put(270f, new Vector2(1, 0));
        MOVE_BULLET_OFFSET.put(315f, new Vector2(1, 1));
        MOVE_BULLET_OFFSET.put(360f, new Vector2(0, 0));
    }

    public Bullet(Vector2 position, Vector2 angle, float size) {
        this.position = new Vector2().set(position).add(size / 2, size + 1);
        setStartBulletOffset(size);
        calcPosition(angle, position);
        this.angle = new Vector2().set(angle);
        this.collisionRect = new CollisionRect(position.x, position.y, WIDTH, HEIGHT);
        this.texture = new Texture("bullet.png");

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


    private void calcPosition(Vector2 angle, Vector2 position) {
        this.position.set(position).add(startBulletOffset.get(Math.abs(angle.angleDeg())));
    }

    public void update(float deltaTime) {
        if (angle.x == 0.0f && angle.y == 0.0f) angle.set(3, 0);
        position.add(SPEED * deltaTime * MOVE_BULLET_OFFSET.get(Math.abs(angle.angleDeg())).x,
                SPEED * deltaTime * MOVE_BULLET_OFFSET.get(Math.abs(angle.angleDeg())).y);

        checkBound();
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

    private void checkBound() {
        if (position.x > Gdx.graphics.getWidth()
                || position.x < 0
                || position.y > Gdx.graphics.getHeight()
                || position.y < 0) {
            destroyed = true;
        }
    }
}
