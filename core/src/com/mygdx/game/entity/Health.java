package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Health {
    private final float width = 32;
    private final float height = 32;
    private final float halfWidth = width / 2;
    private final float halfHeight = height / 2;
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Vector2 position = new Vector2();


    public Health() {
        this.texture = new Texture("health.png");
        position.set(Gdx.graphics.getWidth() - 100 - width, Gdx.graphics.getHeight() - 40 - height);
        this.textureRegion = new TextureRegion(texture);
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                halfWidth,
                halfHeight,
                width,
                height,
                1,
                1,
                0
        );
    }

    public void dispose() {
        texture.dispose();
    }
}
