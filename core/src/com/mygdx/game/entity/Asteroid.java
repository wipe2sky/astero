package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Asteroid {
    private final float size = 64;
    private final float halfSize = size / 2;
    private final Vector2 speed;
    private final Vector2 position = new Vector2();
    private final Vector2 angle = new Vector2(0, 0);
    private final Texture texture;
    private TextureRegion textureRegion;


    public Asteroid(float x, float y) {
        this.texture = new Texture("asteroid.png");
        this.textureRegion = new TextureRegion(texture);
        position.set(x, y);
        speed = new Vector2(MathUtils.random(-1, 1), MathUtils.random(-1, 1));
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

    public void moveTo() {
        position.add(speed);
    }
}
