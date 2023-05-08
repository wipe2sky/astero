package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.Bound;

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
        this.position = new Vector2().set((Gdx.graphics.getWidth() - SIZE) / 2f,
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
                angle.angleDeg()
        );
    }

    public void dispose() {
        texture.dispose();
    }

    public void moveTo(Vector2 direction) {
        position.add(direction);
    }


    public void rotateTo(Vector2 direction) {
        if (direction.x != 0 || direction.y != 0) {
            angle.set(direction.y, -direction.x);
        }
    }
}
