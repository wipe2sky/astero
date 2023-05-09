package com.kurtsevich.asteroids.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private static final int SPEED = 300;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 32;
    private static final float HALF_WIDTH = WIDTH / 2f;
    private static final float HALF_HEIGHT = HEIGHT / 2f;
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Vector2 position;
    private final Vector2 nextPosition;
    private final float angle;
    private final CollisionRect collisionRect;

    private boolean destroyed;

    public Bullet(Vector2 position, float angle, float size) {
        this.angle = angle;
        this.position = new Vector2(position.x + size / 2 - 5, position.y + size / 2 - 15);
        this.nextPosition = new Vector2(1, 1);
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

    public void update(float deltaTime) {
        float stepLength = SPEED * deltaTime;
        nextPosition.setLength(stepLength);
        nextPosition.setAngleDeg(angle);

        position.add(nextPosition);
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
                angle - 90
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
