package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.Bound;

public class Spaceship {
    public static final float SPACESHIP_SPEED = 180;

    private final float size = 64;
    private final float halfSize = size / 2;
    private final Vector2 position = new Vector2();
    private final Vector2 angle = new Vector2();

    private final float screenWidth;
    private final float screenHeight;

    private TextureRegion textureRegion;
    private final Texture texture;


    public Spaceship() {
        this.texture = new Texture("player_spaceship.png");
        this.textureRegion = new TextureRegion(texture);
        position.set(Gdx.graphics.getWidth() / 2 - size / 2,
                Gdx.graphics.getHeight() / 2 - size / 2);
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
    }

    public void render(Batch batch) {
        Bound.checkBounds(position, size, screenWidth, screenHeight);
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
        if (direction.x != 0 || direction.y != 0) {
            angle.set(direction.y, -direction.x);
        }
    }
}
