package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Spaceship {
    public static final float SPACESHIP_SPEED = 180;

    private final float size = 64;
    private final float halfSize = size / 2;
    private final Vector2 position = new Vector2();
    private final Vector2 angle = new Vector2();

    private TextureRegion textureRegion;
    private final Texture texture;


    public Spaceship() {
        this.texture = new Texture("player_spaceship.png");
        this.textureRegion = new TextureRegion(texture);
        position.set(Gdx.graphics.getWidth() / 2 - size / 2,
                Gdx.graphics.getHeight() / 2 - size / 2);
    }

    public void render(Batch batch) {
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                halfSize,
                halfSize,
                size,
                size,
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
        if (direction.x != 0 || direction.y != 0 ) {
            angle.set(direction.y, -direction.x);
        }
    }
}
