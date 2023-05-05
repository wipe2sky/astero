package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.Bound;

public class Asteroid {
    private final float size = MathUtils.random(32, 64);
    private final float halfSize = size / 2;
    private final Vector2 speed;
    private final Vector2 position = new Vector2();
    private final Texture texture;
    private float rotation = MathUtils.random(0, 360);
    private final float screenWidth;
    private final float screenHeight;
    private TextureRegion textureRegion;


    public Asteroid(float x, float y) {
        this.texture = new Texture("asteroid.png");
        this.textureRegion = new TextureRegion(texture);
        position.set(x, y);
        speed = getSpeed();
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
    }

    private static Vector2 getSpeed() {
        return new Vector2(MathUtils.random(-2f, 2f), MathUtils.random(-2f, 2f));
    }

    public void render(Batch batch) {
        rotate();
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
                rotation
        );
    }

    private void rotate() {
        if (rotation++ > 360) {
            rotation = 0.15f;
        } else {
            rotation += 0.15f;
        }
    }

    public void dispose() {
        texture.dispose();
    }

    public void moveTo() {
        position.add(speed);
    }
}
