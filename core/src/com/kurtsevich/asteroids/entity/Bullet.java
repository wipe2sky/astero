package com.kurtsevich.asteroids.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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
    private final Vector2 textureAngle;
    private final float bulletAngle;
    private final CollisionRect collisionRect;

    private boolean destroyed;

    public Bullet(Vector2 position, Vector2 mousePos, float size) {
        this.textureAngle = new Vector2(mousePos).sub(position.x + HALF_WIDTH, position.y + HALF_HEIGHT);
        this.bulletAngle = MathUtils.atan2(position.y - mousePos.y, position.x - mousePos.x);
        this.position = new Vector2(position);
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
        float vx = SPEED * -MathUtils.cos(bulletAngle);
        float vy = SPEED * -MathUtils.sin(bulletAngle);

        position.add(vx * deltaTime, vy * deltaTime);
        checkBound();
        collisionRect.setPosition(position);
    }


    public void render(SpriteBatch batch) {
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
                textureAngle.angleDeg() - 90
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
