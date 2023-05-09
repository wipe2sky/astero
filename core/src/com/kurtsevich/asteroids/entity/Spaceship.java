package com.kurtsevich.asteroids.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.kurtsevich.asteroids.AsterGame;
import com.kurtsevich.asteroids.adapter.KeyboardAdapter;
import com.kurtsevich.asteroids.utils.Bound;

public class Spaceship {
    public static final int SPACESHIP_SPEED = 180;

    private static final int SIZE = 64;
    private static final int HALF_SIZE = SIZE / 2;
    private final Vector2 position;
    private final Vector2 angle = new Vector2();
    private final Texture texture;
    private final CollisionRect collisionRect;
    private final TextureRegion textureRegion;


    public Spaceship() {
        this.texture = new Texture("player_spaceship.png");
        this.textureRegion = new TextureRegion(texture);
        this.position = new Vector2((Gdx.graphics.getWidth() - SIZE) / 2f,
                (Gdx.graphics.getHeight() - SIZE) / 2f);
        this.collisionRect = new CollisionRect(position.x, position.y, SIZE, SIZE);
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getAngle() {
        return angle;
    }

    public float getSize() {
        return SIZE;
    }

    public void render(Batch batch) {
        Bound.screenExitControl(position, SIZE);
        collisionRect.setPosition(position);
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                HALF_SIZE,
                HALF_SIZE,
                SIZE,
                SIZE,
                1,
                1,
                angle.angleDeg() - 90
        );
    }

    public void dispose() {
        texture.dispose();
    }

    public void moveTo(KeyboardAdapter inputProcessor) {
        if (inputProcessor.isLeftPressed()) position.add(-Spaceship.SPACESHIP_SPEED * Gdx.graphics.getDeltaTime(), 0);
        if (inputProcessor.isRightPressed()) position.add(Spaceship.SPACESHIP_SPEED * Gdx.graphics.getDeltaTime(), 0);
        if (inputProcessor.isUpPressed()) position.add(0, Spaceship.SPACESHIP_SPEED * Gdx.graphics.getDeltaTime());
    }


    public void rotateTo(Vector2 mousePos) {
        angle.set(mousePos).sub(position.x + HALF_SIZE, position.y + HALF_SIZE);
    }
}
